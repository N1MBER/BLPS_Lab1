package com.blps_lab1.demo.main_server.service;

import com.blps_lab1.demo.main_server.DTO.ResponseMessageDTO;
import com.blps_lab1.demo.main_server.beans.TokenObject;
import com.blps_lab1.demo.main_server.DTO.UserDTO;
import com.blps_lab1.demo.main_server.beans.RefreshToken;
import com.blps_lab1.demo.main_server.beans.Role;
import com.blps_lab1.demo.main_server.beans.User;
import com.blps_lab1.demo.main_server.exceptions.UserNotFoundException;
import com.blps_lab1.demo.main_server.repository.RefreshTokenRepository;
import com.blps_lab1.demo.main_server.repository.RoleRepository;
import com.blps_lab1.demo.main_server.repository.UserRepository;
import com.blps_lab1.demo.main_server.utils.DTOConverter;
import com.blps_lab1.demo.main_server.utils.JWTUtils;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Collections;
import java.util.List;

public class KomusUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private User user;

    @Autowired
    private DTOConverter dtoConverter;

    @Autowired
    private JWTUtils jwTutils;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    Logger logger = LogManager.getLogger(KomusUserDetails.class);

    private JAXBContext jaxbContext;

    private String usersXMLPath = "/Users/n1mber/Reports/Бизнес Процессы Программных Систем/lab1/src/users.xml";

    public ResponseEntity<ResponseMessageDTO> registerUserDTO(UserDTO userDTO){
        ResponseMessageDTO message = new ResponseMessageDTO();
        user = dtoConverter.userFromDTOConvertor(userDTO);
        String answerText = "";
        try{
            this.save(user);
            this.setUsersToXML(user);
            message.setMessage("You have successfuly register");
        }catch (DataIntegrityViolationException e){
            if(e.getCause().getClass() == ConstraintViolationException.class){
                answerText = "That's user already exist";
            }else{
                answerText = "We have problem retry later";
            }
            message.setMessage(answerText);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }



    public ResponseEntity<TokenObject> authUserDTO(UserDTO userDTO){
        try {
            TokenObject message = new TokenObject();
            user = this.findByLoginAndPassword(userDTO.getEmail(), userDTO.getPassword());
            TokenObject token = new TokenObject(jwTutils.generateToken(user.getEmail()), jwTutils.generateRefreshToken(user.getEmail()));
            refreshTokenRepository.save(new RefreshToken(user.getID(), token.getRefreshToken()));
            message.setAccessToken(token.getAccessToken());
            message.setRefreshToken(token.getRefreshToken());
            return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
        }catch (UserNotFoundException e){
            ResponseMessageDTO message = new ResponseMessageDTO();
            message.setMessage(e.getErrMessage());
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }
    }

    public void setUsersToXML(List<User> users){
        try{
            jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory
                    .createContext(new Class[]{User.class}, null);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            for(User currentUser: users){
                marshaller.marshal(currentUser, new File(usersXMLPath));
            }
        } catch (JAXBException e){
            System.err.println(e.getMessage());
            e.getStackTrace();
        }
    }

    public void setUsersToXML(User user){
        try{
            jaxbContext = org.eclipse.persistence.jaxb.JAXBContextFactory
                    .createContext(new Class[]{User.class}, null);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(user, new File(usersXMLPath));
        } catch (JAXBException e){
            System.err.println(e.getMessage());
            e.getStackTrace();
        }
    }

    public User findByLoginAndPassword(String email, String password) throws UserNotFoundException{
        User user  = this.userRepository.findByEmailAndPassword(email, password);
        if(user == null){
            throw new UserNotFoundException("Not have user with this email", HttpStatus.BAD_REQUEST);
        }
        return user;
    }

    public void save(User user)  {
//        roleRepository.save(new Role(1L, "ROLE_USER"));
//        roleRepository.save(new Role(2L, "ROLE_ADMIN"));
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        this.userRepository.save(user);
    }


    public User findByLogin(String email) throws UserNotFoundException{
        User user  = this.userRepository.findByEmail(email);
        if(user == null){
            throw new UserNotFoundException("Пользователь с таким login не найден", HttpStatus.BAD_REQUEST);
        }
        return user;
    }

    public void deleteUserById(Long id) throws UserNotFoundException {
        user = this.userRepository.findByID(id);
        if(user == null){
            throw new UserNotFoundException("Пользователь с таким id не существует", HttpStatus.BAD_REQUEST);
        }
        this.userRepository.deleteById(id);
    }

    public User getUserFromRequest(HttpServletRequest request) throws UserNotFoundException{
        String token = jwTutils.getTokenFromRequest(request);
        String login = jwTutils.getEmailFromToken(token);
        try {
            User user = this.findByLogin(login);
            logger.log(Level.INFO, "getting user from request" + user.getEmail());
            return user;
        }catch (UserNotFoundException e){
            throw e;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        return new KomusUserDetails(user);
    }
}

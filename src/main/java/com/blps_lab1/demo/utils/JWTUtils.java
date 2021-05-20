package com.blps_lab1.demo.utils;

import com.blps_lab1.demo.DTO.RefreshDTO;
import com.blps_lab1.demo.DTO.TokenObject;
import com.blps_lab1.demo.beans.RefreshToken;
import com.blps_lab1.demo.beans.User;
import com.blps_lab1.demo.exceptions.UserNotFoundException;
import com.blps_lab1.demo.repository.RefreshTokenRepository;
import com.blps_lab1.demo.service.KomusUserDetailsService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import static org.springframework.util.StringUtils.hasText;

@Component
public class JWTUtils {
    private static final String AUTHORIZATION = "Authorization";
    @Value("$(jwt.secret)")
    private String jwtSecret;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private KomusUserDetailsService komusUserDetailsService;

    public String generateToken(String login){
        Date date = new Date(System.currentTimeMillis() + 3000000);
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateRefreshToken(String login){
        Date date = Date.from(LocalDate.now().plusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            System.out.println("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            System.out.println("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            System.out.println("Malformed jwt");
        } catch (SignatureException sEx) {
            System.out.println("Invalid signature");
        } catch (Exception e) {
            System.out.println("invalid token");
        }
        return false;
    }


    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public String getTokenFromRequest(HttpServletRequest request){
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")){
            return bearer.substring(7);
        }
        return null;
    }

    public String getTokenFromRefresh(RefreshDTO refreshDTO){
        String bearer = refreshDTO.getRefresh();
        if (hasText(bearer) && bearer.startsWith("Bearer ")){
            return bearer.substring(7);
        }
        return null;
    }

    public ResponseEntity<TokenObject> refreshToken(String refreshToken)  {
        String login = getEmailFromToken(refreshToken);
        try {
            User user = komusUserDetailsService.findByLogin(login);
            RefreshToken refreshTokenFromDB = refreshTokenRepository.findById(user.getID()).get();
            if (!refreshToken.equals(refreshTokenFromDB.getRefreshToken())){
                throw new SignatureException("Token not equals");
            }
            refreshToken = generateRefreshToken(login);
            refreshTokenFromDB.setRefreshToken(refreshToken);
            refreshTokenRepository.save(refreshTokenFromDB);
            return new ResponseEntity<>(new TokenObject(generateToken(login), refreshToken), HttpStatus.ACCEPTED);
        }catch (UserNotFoundException e){
            e.setErrMessage("The user for this token does not exist! Access is denied");
            e.setErrStatus(HttpStatus.BAD_REQUEST);
            throw new SignatureException("User doesn't exist");
        }

    }
}

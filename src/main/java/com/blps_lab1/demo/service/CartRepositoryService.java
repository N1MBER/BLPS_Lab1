package com.blps_lab1.demo.service;

import com.blps_lab1.demo.DTO.ProductDTO;
import com.blps_lab1.demo.DTO.ResponseMessageDTO;
import com.blps_lab1.demo.beans.CartItem;
import com.blps_lab1.demo.beans.Notification;
import com.blps_lab1.demo.beans.Product;
import com.blps_lab1.demo.beans.User;
import com.blps_lab1.demo.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartRepositoryService {
    @Autowired
    private DTOConverter dtoConverter;
    @Autowired
    private User user;
    @Autowired
    private Product product;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepositoryService userRepositoryService;
    @Autowired
    private ProductRepositoryService productRepositoryService;

    public CartRepository getCartRepository(){
        return cartRepository;
    }

    public void setCartRepository(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void save(CartItem cartItem){
        this.cartRepository.save(cartItem);
    }

    public ResponseEntity getAllProductForUser(Long id){
        ArrayList<Product> products;
        products = this.findAllByUserID(id);
        if (products == null) {
            return new ResponseEntity("Пользователь не найден", HttpStatus.BAD_REQUEST);
        }
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product: products){
            productDTOS.add(dtoConverter.productDTOConvertor(product));
        }
        return new ResponseEntity(productDTOS, HttpStatus.OK);
    }

    public ResponseEntity<ResponseMessageDTO> addToCart(User user, Product product){
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
        if (product.getID() == null ){
            responseMessageDTO.setMessage("Product does not exist");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
        }
        if (user.getID() == null ){
            responseMessageDTO.setMessage("User does not exist");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
        }
        CartItem cartItem = new CartItem();
        cartItem.setCartItem(product, user);
        try {
            this.save(cartItem);
            responseMessageDTO.setMessage("Added to cart list");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e){
            responseMessageDTO.setMessage("You already have this product in cart list");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<ResponseMessageDTO> deleteFromCart(User user, Product product){
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
        if (product.getID() == null ){
            responseMessageDTO.setMessage("Product does not exist");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
        }
        if (user.getID() == null ) {
            responseMessageDTO.setMessage("User does not exist");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
        }
        try {
            this.cartRepository.delete(this.findByUserIDAndProductID(user.getID(), product.getID()));
            responseMessageDTO.setMessage("Successfully deleted");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.ACCEPTED);
        } catch (DataIntegrityViolationException e){
            responseMessageDTO.setMessage("There are no such product");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<ResponseMessageDTO> clearCart(User user){
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
        boolean flag;
        if (user.getID() == null ) {
            responseMessageDTO.setMessage("User does not exist");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
        }
        try {
            flag = this.deleteAllByUserID(user.getID());
            if (flag){
                responseMessageDTO.setMessage("Cart is clear");
                return new ResponseEntity<>(responseMessageDTO, HttpStatus.OK);
            }else {
                responseMessageDTO.setMessage("Ooops we have problem");
                return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
            }
        } catch (DataIntegrityViolationException e){
            responseMessageDTO.setMessage("Ooops we have problem");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
        }
    }

    public ArrayList<Product> findAllByUserID(Long id){
        ArrayList<Product> arrayList = this.cartRepository.findAllByUserID(id);
        return arrayList;
    }

    public CartItem findByUserIDAndProductID(Long user_id, Long product_id){
        CartItem cartItem = this.cartRepository.findByUserIDAndProductID(user_id, product_id);
        return cartItem;
    }

    public boolean deleteAllByUserID(Long id){
       boolean flag = this.cartRepository.deleteAllByUserID(id);
       return flag;
    }
}

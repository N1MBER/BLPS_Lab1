package com.blps_lab1.demo.service;

import com.blps_lab1.demo.DTO.ProductDTO;
import com.blps_lab1.demo.DTO.ResponseMessageDTO;
import com.blps_lab1.demo.beans.CartItem;
import com.blps_lab1.demo.beans.Notification;
import com.blps_lab1.demo.beans.Product;
import com.blps_lab1.demo.beans.User;
import com.blps_lab1.demo.exceptions.ProductNotFoundException;
import com.blps_lab1.demo.repository.CartRepository;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

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

    public ResponseEntity<ResponseMessageDTO> getAllProductForUser(Long id){
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
        ArrayList<CartItem> product_ids;
        ArrayList<Product> products = new ArrayList<>();
        product_ids = this.findAllByUserID(id);
        CartItem cartItem;
        Iterator iterator = product_ids.iterator();
        while (iterator.hasNext()){
            try {
                cartItem = (CartItem) iterator.next();
                products.add(productRepositoryService.findByID(cartItem.getProduct().getID()));
            }catch (ProductNotFoundException e){
                responseMessageDTO.setMessage("Product does not exist");
                return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
            }
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
        cartItem.setCartItem(product, user,1);
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
        ArrayList <CartItem> cartItems;
        if (user.getID() == null ) {
            responseMessageDTO.setMessage("User does not exist");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
        }
        try {
            cartItems = this.findAllByUserID(user.getID());
            int length = cartItems.size();
            int index = 0;
            Iterator iterator = cartItems.iterator();
            CartItem cartItem;
            while (iterator.hasNext()){
                cartItem = (CartItem) iterator.next();
                this.deleteFromCart(user, cartItem.getProduct());
                index++;
            }
            if (length == index){
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

    public ArrayList<CartItem> findAllByUserID(Long id){
        ArrayList<CartItem> arrayList = this.cartRepository.findAllByUserID(id);
        return arrayList;
    }

    public CartItem findByUserIDAndProductID(Long user_id, Long product_id){
        CartItem cartItem = this.cartRepository.findByUserIDAndProductID(user_id, product_id);
        return cartItem;
    }

    public Integer deleteAllByUserID(Long id){
       Integer flag = this.cartRepository.deleteAllByUserID(id);
       return flag;
    }
}

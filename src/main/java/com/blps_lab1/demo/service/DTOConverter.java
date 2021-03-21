package com.blps_lab1.demo.service;

import com.blps_lab1.demo.DTO.ProductDTO;
import com.blps_lab1.demo.DTO.UserDTO;
import com.blps_lab1.demo.beans.Product;
import com.blps_lab1.demo.beans.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DTOConverter {

    public UserDTO userDTOConvertor(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setDTO(
                user.getID(),
                user.getEmail(),
                user.getName(),
                user.getSurname()
        );
        return userDTO;
    }

    public ProductDTO productDTOConvertor(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getID());
        productDTO.setPrice(product.getPrice());
        productDTO.setName(product.getName());
        return productDTO;
    }

    public ArrayList<ProductDTO> productDTOListConvertor(ArrayList<Product> list){
        ArrayList<ProductDTO> arrayList = new ArrayList<>();
        for (Product product: list){
            arrayList.add(this.productDTOConvertor(product));
        }
        return arrayList;
    }

    public User userFromDTOConvertor(UserDTO userDTO){
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setSurname(userDTO.getSurname());
        user.setPassword(userDTO.getPassword());
        if (userDTO.getId() != null){
            user.setID(userDTO.getId());
        }
        return user;
    }

    public Product productFromDTOConvertor(ProductDTO productDTO){
        Product product = new Product();
        product.setPrice(productDTO.getPrice());
        product.setName(productDTO.getName());
        if (productDTO.getId() != null)
            product.setID(productDTO.getId());
        return product;
    }
}

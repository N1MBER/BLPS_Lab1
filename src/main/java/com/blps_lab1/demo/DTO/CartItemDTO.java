package com.blps_lab1.demo.DTO;

import java.io.Serializable;

public class CartItemDTO implements Serializable {
    private UserDTO userDTO;
    private ProductDTO productDTO;

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}

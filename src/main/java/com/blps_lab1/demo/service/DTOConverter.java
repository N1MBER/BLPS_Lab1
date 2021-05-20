package com.blps_lab1.demo.service;

import com.blps_lab1.demo.DTO.*;
import com.blps_lab1.demo.beans.Order;
import com.blps_lab1.demo.beans.Payment;
import com.blps_lab1.demo.beans.Product;
import com.blps_lab1.demo.beans.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DTOConverter {

    public UserDTO userDTOConvertor(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setDTO(
                user.getEmail(),
                user.getName(),
                user.getSurname()
        );
        return userDTO;
    }

    public ProductDTO productDTOConvertor(Product product){
        ProductDTO productDTO = new ProductDTO();
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
        return user;
    }

    public Product productFromDTOConvertor(ProductDTO productDTO){
        Product product = new Product();
        product.setPrice(productDTO.getPrice());
        product.setName(productDTO.getName());
        return product;
    }

    public OrderDTO orderDTOConvertor(Order order){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setStatus(order.getStatus());
        ArrayList <ProductDTO> productDTOS = new ArrayList<>();
        for (Product product: order.getProducts()){
            productDTOS.add(this.productDTOConvertor(product));
        }
        orderDTO.setId(order.getId());
        orderDTO.setProducts(productDTOS);
        orderDTO.setSubmit_date(order.getSubmit_date().toString());
        orderDTO.setUpdate_date(order.getUpdate_date().toString());
        orderDTO.setUser(this.userDTOConvertor(order.getUser()));
        return orderDTO;
    }

    public PaymentDTO paymentDTOConvertor(Payment payment){
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPaymentType(payment.getPaymentType());
        paymentDTO.setDate(payment.getDate());
        ArrayList <Long> productIds = new ArrayList<>();
        for (Product product: payment.getProducts()){
            productIds.add(product.getID());
        }
        paymentDTO.setProducts_ids(productIds);
        return paymentDTO;
    }

    public PaymentElementDTO paymentElementDTOConvertor(Payment payment){
        PaymentElementDTO paymentElementDTO = new PaymentElementDTO();
        paymentElementDTO.setPaymentType(payment.getPaymentType());
        paymentElementDTO.setDate(payment.getDate());
        paymentElementDTO.setId(payment.getID());
        ArrayList <Long> productIds = new ArrayList<>();
        for (Product product: payment.getProducts()){
            productIds.add(product.getID());
        }
        paymentElementDTO.setProducts_ids(productIds);
        return paymentElementDTO;
    }


}

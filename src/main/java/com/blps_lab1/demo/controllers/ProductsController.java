package com.blps_lab1.demo.controllers;


import com.blps_lab1.demo.DTO.ProductDTO;
import com.blps_lab1.demo.DTO.ResponseMessageDTO;
import com.blps_lab1.demo.beans.Product;
import com.blps_lab1.demo.exceptions.ProductNotFoundException;
import com.blps_lab1.demo.exceptions.ProductValidationException;
import com.blps_lab1.demo.service.ProductRepositoryService;
import com.blps_lab1.demo.service.UserRepositoryService;
import com.blps_lab1.demo.validation.ValidationProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping("/product")
@Api(value = "Product api")

public class ProductsController {

    @Autowired
    private ProductRepositoryService productRepositoryService;

    @Autowired
    private UserRepositoryService userRepositoryService;
    private ResponseMessageDTO message;

    @Autowired
    private Product product;

    @Autowired
    private ValidationProductService validationProductService;

    @PutMapping("/add_product")
    @ApiOperation(value = "Add new product")
    public ResponseEntity<ResponseMessageDTO> addProduct(@RequestBody ProductDTO productDTO, HttpServletRequest request){
        message = new ResponseMessageDTO();
        try{
            validationProductService.validateProductDTO(productDTO);
        }catch (ProductValidationException e){
            message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(this.message, e.getErrStatus());
        }
        return this.productRepositoryService.saveFromDTO(productDTO, request);
    }


    @GetMapping("/products")
    @ApiOperation(value = "Get list of products")
    public ArrayList<ProductDTO> getAllProducts(){
        return this.productRepositoryService.getAllProducts();
    }

    @GetMapping("/product/{id}")
    @ApiOperation(value = "Get product by id")
    public ResponseEntity getProductById(@PathVariable("id") Long id){
        message = new ResponseMessageDTO();
        try {
            ProductDTO productDTO = this.productRepositoryService.findByIDToResponse(id);
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        }catch (ProductNotFoundException e){
            message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }
    }
}
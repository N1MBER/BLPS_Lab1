package com.blps_lab1.demo.service;

import com.blps_lab1.demo.DTO.ProductDTO;
import com.blps_lab1.demo.DTO.ResponseMessageDTO;
import com.blps_lab1.demo.beans.Product;
import com.blps_lab1.demo.exceptions.ProductNotFoundException;
import com.blps_lab1.demo.repository.ProductRepository;
import com.blps_lab1.demo.utils.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductRepositoryService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DTOConverter dtoConverter;

    public void save(Product product){
        this.productRepository.save(product);
    }

    public List<Product> findAll(){
        return this.productRepository.findAll();
    }

    public Product findByID(Long id) throws ProductNotFoundException {
        Product product = this.productRepository.findByID(id);
        if (product == null)
            throw new ProductNotFoundException("Петиция не найдена", HttpStatus.BAD_REQUEST);
        return product;
    }

    public ProductDTO findByIDToResponse(Long id) throws ProductNotFoundException {
        Product product = this.findByID(id);
        ProductDTO productDTO = dtoConverter.productDTOConvertor(product);
        return productDTO;
    }

    public ArrayList<ProductDTO> getAllProducts() {
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        ArrayList<Product> products = new ArrayList<>(this.findAll());
        for (Product product: products){
            productDTOS.add(dtoConverter.productDTOConvertor(product));
        }
        return productDTOS;
    }

    public ResponseEntity<ResponseMessageDTO> saveFromDTO(ProductDTO productDTO, HttpServletRequest request){
        ResponseMessageDTO messageDTO = new ResponseMessageDTO();
        try {
            Product product = dtoConverter.productFromDTOConvertor(productDTO);
            this.productRepository.save(product);
            messageDTO.setMessage("Product was added");
            return new ResponseEntity<>(messageDTO, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e){
            String answerText = "";
            answerText = "УПС! Произошла ошибка, пожалуйста, попробуйте позднее";
            messageDTO.setMessage(answerText);
            return new ResponseEntity<>(messageDTO, HttpStatus.BAD_REQUEST);
        }
    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }
}

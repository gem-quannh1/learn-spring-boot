package com.example.helloworld.controllers;

import com.example.helloworld.models.Product;
import com.example.helloworld.models.ResponseObject;
import com.example.helloworld.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {
    // DI - dependency injection
    @Autowired
     private ProductRepository productRepository;

    @GetMapping("")
    List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    // Let's return an Object with: data, message, status code
    ResponseEntity<ResponseObject> getProductDetail(@PathVariable Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        if (foundProduct.isPresent()) {
            return  ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            "success",
                            "Query product successfully",
                            foundProduct
                    )
            );
        } else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(
                            "fail",
                            "Can't find product by id: " + id,
                            null
                    )
            );
        }
    }
}

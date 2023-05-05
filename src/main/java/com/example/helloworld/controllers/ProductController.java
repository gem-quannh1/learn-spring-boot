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
    ResponseEntity<ResponseObject> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Query all products successfully", products));
    }

    @GetMapping("/{id}")
        // Let's return an Object with: data, message, status code
    ResponseEntity<ResponseObject> getProductDetail(@PathVariable Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        if (foundProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("success", "Query product successfully", foundProduct));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("fail", "Can't find product by id: " + id, null));
        }
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertNewProduct(@RequestBody Product newProduct) {
        List<Product> foundProducts = productRepository.findByName(newProduct.getName().trim());
        if (foundProducts.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new ResponseObject("failed", "Product name already taken", ""));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Insert Product successfully", productRepository.save(newProduct)));
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@RequestBody Product newProduct, @PathVariable Long id) {
        Product updatedProduct = productRepository.findById(id).map(product -> {
            product.setName(newProduct.getName());
            product.setYear(newProduct.getYear());
            product.setCost(newProduct.getCost());
            product.setImageUrl(newProduct.getImageUrl());
            return productRepository.save(product);
        }).orElseGet(() -> {
            newProduct.setId(id);
            return productRepository.save(newProduct);
        });

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Product " + id + " updated", updatedProduct));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
        boolean isExisted = productRepository.existsById(id);
        if (isExisted) {
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "Delete product successfully", ""));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("failed", "Cannot find product to delete", ""));
    }
}

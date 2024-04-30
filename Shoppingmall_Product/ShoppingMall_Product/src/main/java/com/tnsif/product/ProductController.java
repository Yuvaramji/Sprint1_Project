package com.tnsif.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tnsif.product.*;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    // build create Product REST API
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save( product);
    }

    // build get product by id REST API
    @GetMapping("{id}")
    public ResponseEntity<Product> getProductById(@PathVariable  long id){
    	Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Product not exist with id:" + id));
        return ResponseEntity.ok(product);
    }

    // build update product REST API
    @PutMapping("{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id,@RequestBody Product productDetails) {
    	Product updateProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Product not exist with id: " + id));

    	updateProduct.setName(productDetails.getName());
    	updateProduct.setDescription(productDetails.getDescription());
    	updateProduct.setPrice(productDetails.getPrice());
    	updateProduct.setStockQuantity(productDetails.getStockQuantity());
    	updateProduct.setCategory(productDetails.getCategory());
    	updateProduct.setStoreId(productDetails.getStoreId());
    
        productRepository.save(updateProduct);
        return ResponseEntity.ok(updateProduct);
    }

    // build delete product REST API
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable long id){

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Product not exist with id: " + id));

        productRepository.delete(product);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}

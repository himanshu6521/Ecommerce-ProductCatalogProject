package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/products")
public class ProductController {

	private Logger logger=LoggerFactory.getLogger(this.getClass());
//	logger.info("This is an information message.");
//	logger.debug("This is a debug message.");
//	logger.warn("This is a warning message.");
//	logger.error("This is an error message.");
	
	
	
	// getall
	@GetMapping("/home")
	public String home() {
		return "homee";
	}

	@Autowired
	public ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	public ProductController() {
	}

	//creating product
	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		Product createdProduct = productService.createProduct(product);
		return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
	}

	//get all product
	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		// Log each product's name
	    products.forEach(product -> {
	        logger.info("Product Name: {}", product.getProductName());
	    });
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	//below method is for showing fault tolerance using hystrix. we are returing a default 
	//list of products if somehow  geteveryproducts stopped
	@GetMapping("/everyproducts")
	 @HystrixCommand(fallbackMethod = "fallbackGetAllProducts")
	    public ResponseEntity<List<Product>> getEveryProducts() {
	        List<Product> products = productService.getAllProducts();
	        return new ResponseEntity<>(products, HttpStatus.OK);
	    }

	    // Fallback method to be executed when an error occurs
	    public ResponseEntity<List<Product>> fallbackGetEveryProducts() {
	        // Provide a default response or handle the error gracefully
	        List<Product> defaultProducts = new ArrayList<>();
	        logger.warn(" this response is generated from fallback GetEveryProducts method ");
	        return new ResponseEntity<>(defaultProducts, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	//get product by id
	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
		Product product = productService.getProductById(productId);

		if (product != null) {
			return new ResponseEntity<>(product, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	//update by id
	@PutMapping("/products/{productId}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product updatedProduct) {
		Product product = productService.updateProduct(productId, updatedProduct);

		if (product != null) {
			return new ResponseEntity<>(product, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/products/{productId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
		boolean deleted = productService.deleteProduct(productId);

		if (deleted) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	
	
	//-------------------------------
	//Get request to get NumberOfItemsInStock from inventory using productid
	@GetMapping("/products/getNumberOfItemsInStock/{productId}")
    public ResponseEntity<Long> getNumberOfItemsInStock(@PathVariable Long productId) {
        try {
            Long numberOfItemsInStock = productService.getNumberOfItemsInStockForProduct(productId);
            return ResponseEntity.ok(numberOfItemsInStock);
        } catch (RuntimeException e) {
            // Handle the exception, e.g., return an error response
            return ResponseEntity.status(500).body(-1L);
        }
	}
	
	//@GetMapping("/getAvailabilityOfProduct/{productId}")
	
	
	
	//method to get product price from productID
	@GetMapping("/products/getProductPriceByProductId/{productId}") // Updated path
	public Long getProductPriceByProductId(@PathVariable Long productId) {
        return productService.getProductPriceByProductId(productId);
        //return ResponseEntity.ok(productPrice);
    }

}
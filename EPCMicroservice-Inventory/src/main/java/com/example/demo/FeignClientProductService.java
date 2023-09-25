package com.example.demo;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
//enabling ribbon  
@RibbonClient(name="ProductService")
@FeignClient(name="ProductService",url = "http://localhost:8081/products")
public interface FeignClientProductService {

	//create product
	@PostMapping("/products/inventory")
	public Product createProduct(Product value);
	
	
//	//get products
//	@GetMapping("/products")
//	Product getProducts();
//	
//	//update a product
//	@PutMapping("/inventory/products/{productId}")
//	public Product updateProduct(@PathVariable Long productId, @RequestBody Product updatedProduct);
//	
//	//delete a product
	
	
	//get a product by productid
//	@GetMapping("/inventory/products/{productId}")
//	Product getProductByProductId(@PathVariable long productId);
	
	 @GetMapping("/inventory/products")
	    List<Product> getAllProducts();

	    @GetMapping("/inventory/products/{productId}")
	    Product getProductById(@PathVariable Long productId);

	    
	    //this method is working 
	    @GetMapping("/getProductPriceByProductId/{productId}")
	    Long getProductPriceByProductId(@PathVariable("productId") Long productId);
	
}

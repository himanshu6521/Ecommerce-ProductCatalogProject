package com.example.demo;

import java.util.*;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.configuration.*;
import com.example.configuration.*;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public ProductService() {
	}

	@Autowired //private final
	private  RestTemplate restTemplateProduct;

//  --------------------------
	// fetch stock is available or not from inventory
	// giving productid to inventory , it will return inventory detail like
	// numberOfItemsInStock
	// http://localhost:8082/inventories/getNumberOfItemsInStock/102

	public ProductService(ProductRepository productRepository, RestTemplate restTemplateProduct) {
		super();
		this.productRepository = productRepository;
		this.restTemplateProduct = restTemplateProduct;
	}

	public Long getNumberOfItemsInStockForProduct(Long productId) {
		// Make a GET request to the Inventory service to get the number of items in
		// stock
		String inventoryApiUrl = "http://localhost:8082/inventories/getNumberOfItemsInStock/" + productId;

		ResponseEntity<Long> responseEntity = restTemplateProduct.getForEntity(inventoryApiUrl, Long.class);

		if (responseEntity.getStatusCode().is2xxSuccessful()) {
			return responseEntity.getBody();
		} else {
			// Handle error response, e.g., throw an exception or return a default value
			throw new RuntimeException("Failed to retrieve the number of items in stock.");
		}
	}

	 @Autowired // constructor
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;

	}

	// put /create a product
	public Product createProduct(Product product) {
		return productRepository.save(product);
	}

	// get all products
	public List<Product> getAllProducts() {
		return (List<Product>) productRepository.findAll();
	}

	// get product by id
	public Product getProductById(Long productId) {
		Optional<Product> optionalProduct = productRepository.findById(productId);
		return optionalProduct.orElse(null);
	}

	// update product by id
	public Product updateProduct(Long productId, Product updatedProduct) {
		Product existingProduct = getProductById(productId);

		if (existingProduct != null) {
			existingProduct.setProductName(updatedProduct.getProductName());
			existingProduct.setProductPrice(updatedProduct.getProductPrice());
			existingProduct.setProductDescription(updatedProduct.getProductDescription());
			existingProduct.setProductIsAvailable(updatedProduct.isProductIsAvailable());

			return productRepository.save(existingProduct);
		}

		return null; // Product not found
	}

	// delete a product
	public boolean deleteProduct(Long productId) {
		Product existingProduct = getProductById(productId);

		if (existingProduct != null) {
			productRepository.delete(existingProduct);
			return true;
		}

		return false; // Product not found
	}

	public Long getProductPriceByProductId(Long productId) {
		return productRepository.findProductPriceByProductId(productId);
	}

}
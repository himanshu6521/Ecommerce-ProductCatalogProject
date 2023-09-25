package com.example.demo;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.Responses.ProductAvailabilityResponse;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

import java.util.List;

@RestController
@RequestMapping("/inventories")
public class InventoryController {

	@Autowired
	public final InventoryService inventoryService;

	@Autowired
	public InventoryController(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	@Autowired
	public void InventoryController() {

	}
	
	//logger
	Logger logger = LoggerFactory.getLogger(this.getClass());

	// get all inventories
	@GetMapping
	public ResponseEntity<List<Inventory>> getAllInventories() {
		List<Inventory> inventories = inventoryService.getAllInventories();
		return new ResponseEntity<>(inventories, HttpStatus.OK);
	}

	// get inventories by inventory id
	@GetMapping("/{inventoryId}")
	public ResponseEntity<Inventory> getInventoryById(@PathVariable Long inventoryId) {
		Inventory inventory = inventoryService.getInventoryById(inventoryId);
		if (inventory != null) {
			return new ResponseEntity<>(inventory, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// create inventory
	@PostMapping
	public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
		Inventory createdInventory = inventoryService.createInventory(inventory);
		return new ResponseEntity<>(createdInventory, HttpStatus.CREATED);
	}

	// update inventories by inventory id
	@PutMapping("/{inventoryId}")
	public ResponseEntity<Inventory> updateInventory(@PathVariable Long inventoryId,
			@RequestBody Inventory updatedInventory) {
		Inventory inventory = inventoryService.updateInventory(inventoryId, updatedInventory);
		if (inventory != null) {
			return new ResponseEntity<>(inventory, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// delete inventory by inventory id
	@DeleteMapping("/{inventoryId}")
	public ResponseEntity<Void> deleteInventory(@PathVariable Long inventoryId) {
		boolean deleted = inventoryService.deleteInventory(inventoryId);
		if (deleted) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Custom api to get inventory no.of stock by passing product id
	@GetMapping("/getNumberOfItemsInStock/{productId}")
	public Long getNumberOfItemsInStock(@PathVariable Long productId) {
		return inventoryService.getNumberOfItemsInStock(productId);
	}
	
	
	
	
	
	
	
	
	
	int RetryCount=1;
 
	// -------------------------------------BELOW 2 methods show the use of circuit breakers. if 
 //InvenProdBreaker fails then InvenProdFallback will be called we have return internal server error in fallback
	@GetMapping("/ProductAvailability/{productId}/{quantity}")
	@CircuitBreaker(name= "InvenProdBreaker", fallbackMethod="InvenProdFallback")
	//or
	@Retry(name= "InvenProductService", fallbackMethod= "InvenProdFallback" )
	//or
	
	@RateLimiter(name = "InvenRateLimiter",fallbackMethod = "InvenProdFallback")
	public ResponseEntity<ProductAvailabilityResponse> getProductAvailability(
	    @PathVariable Long productId,
	    @PathVariable int quantity) {
		
		//to check how much retry are executed
		logger.info("retry count= {} ", RetryCount);
		RetryCount++;  
//		Retryable Exception: Ensure that the exception being thrown in the method is a 
//		retryable exception. Spring Retry will only retry methods when they throw a specific
//		set of exceptions (e.g., RuntimeException).  If the exception is not retryable, 
//		you might need to customize the exception hierarchy. here exception is not handled that why retry is not working 3 times. implementation is right 
		
		long temp= quantity;
		logger.info("given quantity is {}" + quantity);
	    // Get available quantity from the inventory service
	    Long availableQuantity = inventoryService.getAvailableQuantity(productId, temp);
	    logger.info("available quantity is {} " + availableQuantity);
	    // Check if the product is available in sufficient quantity
	    boolean isProductAvailable = availableQuantity >= quantity;
	    logger.info("is product available {} " + isProductAvailable);
	    
	    // If not available, return a response with isProductAvailable set to false and availableQuantity
	    if (!isProductAvailable) {
	        return ResponseEntity.ok(new ProductAvailabilityResponse(false, availableQuantity, 00));
	    }
	    else {
	    // If available, get the product price from the inventory service
	    Long productPrice = inventoryService.getAvailableProductPrice(productId);
	   

	    if(productPrice ==null) { productPrice= 0L;}
	    else { productPrice= productPrice * quantity;}
	    // Create a response with isProductAvailable set to true, availableQuantity, and productPrice
	    ProductAvailabilityResponse response = new ProductAvailabilityResponse(true, availableQuantity, productPrice);

	    return ResponseEntity.ok(response);
	    }
	}
	
	//@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //<<rate limiter related 
	public ResponseEntity<ProductAvailabilityResponse> InvenProdFallback(Long productId, int quantity, Exception ex) {
        logger.warn("This method is called from the fallback method of circuit breaker for productId: {}, quantity: {}", productId, quantity);

        // Create a dummy ProductAvailabilityResponse with desired values
        ProductAvailabilityResponse dummyResponse = new ProductAvailabilityResponse();
        dummyResponse.setIsProductAvailable(true);
        dummyResponse.setAvailableQuantity(1L);
        dummyResponse.setPrice(1L);

        // Return the dummy response
        return ResponseEntity.ok(dummyResponse);
    }

	
//  In this code:
//We define a ProductController class with a checkProductAvailability method that accepts productId and quantity as path variables.
//Inside the method, we call the getAvailableQuantity method from the InventoryService to retrieve the available quantity.
//We then determine product availability by checking if availableQuantity is greater than 0 and create a ProductAvailabilityResponse object containing the boolean isProductAvailable and the availableQuantity.
//Finally, we return the response using ResponseEntity.ok().

	
	//extra method to get inventory obj by productid
	@GetMapping("/checkAvailability/{productId}/{quantity}")

	public ResponseEntity<Inventory> getAvailabilityById(@PathVariable Long productId, @PathVariable Long quantity) {
		Inventory inventory = inventoryService.getAvailabilityByProductID(productId);
		if (inventory != null) {
			if (inventory.getNumberOfItemsInStock() - quantity > 0) {
				return new ResponseEntity<>(inventory, HttpStatus.OK);
			} else {
				return new ResponseEntity<>( HttpStatus.NOT_FOUND);
			}
			

		}
		return new ResponseEntity<>( HttpStatus.NOT_FOUND);
	}

}
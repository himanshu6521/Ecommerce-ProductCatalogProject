package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Responses.ProductAvailabilityResponse;

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

	// -------------------------------------

	@GetMapping("/ProductAvailability/{productId}/{quantity}")
	public ResponseEntity<ProductAvailabilityResponse> getProductAvailability(
	    @PathVariable Long productId,
	    @PathVariable int quantity) {
	    
		long temp= quantity;
		//System.out.println("given quantity is " + quantity);
	    // Get available quantity from the inventory service
	    Long availableQuantity = inventoryService.getAvailableQuantity(productId, temp);
	   // System.out.println("available quantity is " + availableQuantity);
	    // Check if the product is available in sufficient quantity
	    boolean isProductAvailable = availableQuantity >= quantity;
	   // System.out.println("is product available  " + isProductAvailable);
	    
	    // If not available, return a response with isProductAvailable set to false and availableQuantity
	    if (!isProductAvailable) {
	        return ResponseEntity.ok(new ProductAvailabilityResponse(false, availableQuantity, 00));
	    }

	    // If available, get the product price from the inventory service
	    Long productPrice = inventoryService.getAvailableProductPrice(productId);
	    System.out.println("product price  " + productPrice);

	    if(productPrice ==null) { productPrice= 0L;}
	    
	    // Create a response with isProductAvailable set to true, availableQuantity, and productPrice
	    ProductAvailabilityResponse response = new ProductAvailabilityResponse(true, availableQuantity, productPrice);

	    return ResponseEntity.ok(response);
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
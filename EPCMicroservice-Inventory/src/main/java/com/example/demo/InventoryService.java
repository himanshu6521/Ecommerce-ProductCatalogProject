package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ch.qos.logback.classic.Logger;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private RestTemplate restTemplateInventory;

	@Autowired
	private FeignClientProductService feignClientProductService;
//	public InventoryService(InventoryRepository inventoryRepository, RestTemplate restTemplateInventory) {
//		super();
//		this.inventoryRepository = inventoryRepository;
//		this.restTemplateInventory = restTemplateInventory;
//	}

	// get all
	public List<Inventory> getAllInventories() {
		return inventoryRepository.findAll();
	}

	// get one
	public Inventory getInventoryById(Long inventoryId) {
		Optional<Inventory> optionalInventory = inventoryRepository.findById(inventoryId);
		return optionalInventory.orElse(null);
	}

	// create ,put
	public Inventory createInventory(Inventory inventory) {
		return inventoryRepository.save(inventory);
	}

	// update
	public Inventory updateInventory(Long inventoryId, Inventory updatedInventory) {
		Inventory existingInventory = getInventoryById(inventoryId);

		if (existingInventory != null) {
			existingInventory.setProductId(updatedInventory.getProductId());
			existingInventory.setProductInStock(updatedInventory.isProductInStock());
			existingInventory.setNumberOfItemsInStock(updatedInventory.getNumberOfItemsInStock());
			return inventoryRepository.save(existingInventory);
		}

		return null; // Inventory not found
	}

	// delete
	public boolean deleteInventory(Long inventoryId) {
		Inventory existingInventory = getInventoryById(inventoryId);

		if (existingInventory != null) {
			inventoryRepository.delete(existingInventory);
			return true;
		}

		return false; // Inventory not found
	}

	public Long getNumberOfItemsInStock(Long productId) {
		Inventory inventory = inventoryRepository.findByProductId(productId);
		if (inventory != null) {
			return inventory.getNumberOfItemsInStock();
		} else {
			// Handle the case where the product is not found in the inventory
			return -1L; // You can use a specific value or exception here
		}
	}

	// -----------------------
	// make a service method in InventoryService ,it accepts long productId and long
	// Quantity. checks availability in database using JPA. search the product by
	// ProductId and check (numberOfItemsInStock) , if numberOfItemsInStock >
	// quantity , it is available. if its 0 or negative then item with such quantity
	// not available.

	public Long getAvailableQuantity(Long productId, Long quantity) {
		// Find the product's inventory by productId
		Inventory inventory = inventoryRepository.findByProductId(productId);

		Long response;
		// if inventory is null ,handle
		if (inventory == null) {
			response = 0L;
		}
		System.out.println("the inventory with productid" + productId + " is not available");
		if (inventory.getNumberOfItemsInStock() == null) {
			response = 0L;
		}
		// Check if the available quantity is greater than or equal to the requested
		// quantity
		response = inventory.getNumberOfItemsInStock();
		return response;

		// Product not found in inventory or not available in the requested quantity
		// Return 0 or another appropriate value to indicate unavailability
	}

	public Inventory getAvailabilityByProductID(Long productId) {
		// TODO Auto-generated method stub
		return inventoryRepository.findByProductId(productId);
	}

	public Long getAvailableProductPrice(Long productId) {

		// http://localhost:8081/products/getPricebyProductId/ , its needs the product
		// service running
		// Call the Feign Client method instead of using RestTemplate. resttemplate method and feign client both working 
        //System.out.println("Productid is " +productId);
		
//		String productServiceUrl = "http://localhost:8081"; // Update with your actual URL
//        String endpoint = "/products/getProductPriceByProductId/" + productId;
//        String fullUrl = productServiceUrl + endpoint;
//
//        return restTemplateInventory.getForObject(fullUrl, Long.class);
		
		//both feignclient method and resttemplate is now running properly
		 return feignClientProductService.getProductPriceByProductId(productId);
	}

}

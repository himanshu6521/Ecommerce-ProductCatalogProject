package com.example.demo;

import jakarta.persistence.*;

@Entity
@Table(name = "Inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long inventoryId;

    private Long productId; // This is the product ID linked to the inventory
    
    private Long numberOfItemsInStock;
    
    public Long getNumberOfItemsInStock() {
		return numberOfItemsInStock;
	}
	public void setNumberOfItemsInStock(Long numberOfItemsInStock) {
		this.numberOfItemsInStock = numberOfItemsInStock;
	}
	private boolean productInStock;
	public Long getInventoryId() {
		return inventoryId;
	}
	public Inventory() {
		super();
	}
	public Inventory(Long inventoryId, Long productId, Long numberOfItemsInStock, boolean productInStock) {
		super();
		this.inventoryId = inventoryId;
		this.productId = productId;
		this.numberOfItemsInStock = numberOfItemsInStock;
		this.productInStock = productInStock;
	}
	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public boolean isProductInStock() {
		return productInStock;
	}
	public void setProductInStock(boolean productInStock) {
		this.productInStock = productInStock;
	}

    // Constructors, getters, setters
    
}
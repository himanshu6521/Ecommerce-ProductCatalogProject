package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private Long productId;
    
    private String productName;
    private double productPrice;
    private String productDescription;
    private boolean productIsAvailable;
    public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public boolean isProductIsAvailable() {
		return productIsAvailable;
	}

	public void setProductIsAvailable(boolean productIsAvailable) {
		this.productIsAvailable = productIsAvailable;
	}

	

    // Constructors, getters, setters, and other methods

    // Constructors
    public Product() {
    }

    public Product(String productName, double productPrice, String productDescription, boolean productIsAvailable) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productIsAvailable = productIsAvailable;
    }

	public Product(long l, String string, double d, String string2, boolean b) {
		// TODO Auto-generated constructor stub
	}

    // Getters and setters
    // ...
}

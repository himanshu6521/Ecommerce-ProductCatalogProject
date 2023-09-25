package com.example.Responses;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductAvailabilityResponse {
	
	@JsonProperty("isProductAvailable")
	private boolean isProductAvailable;
	@JsonProperty("availableQuantity")
	private long availableQuantity;
	
	@JsonProperty("Price")
	private long price;
    public boolean isProductAvailable() {
		return isProductAvailable;
	}
	public void setProductAvailable(boolean isProductAvailable) {
		this.isProductAvailable = isProductAvailable;
	}
	public long getAvailableQuantity() {
		return availableQuantity;
	}
	public void setAvailableQuantity(long availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public ProductAvailabilityResponse(boolean isProductAvailable, long availableQuantity, long price) {
        this.isProductAvailable = isProductAvailable;
        this.availableQuantity = availableQuantity;
        this.price = price;
    }
	public ProductAvailabilityResponse() {
		// TODO Auto-generated constructor stub
	}
	public void setIsProductAvailable(boolean b) {
		// TODO Auto-generated method stub
		isProductAvailable=b;
	}

    // Getters and setters (if needed)
}

package com.example.Responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductAvailabilityResponse {

	
	@JsonProperty("isProductAvailable")
	private boolean isProductAvailable;
	@JsonProperty("availableQuantity")
	private long availableQuantity;
	
	@JsonProperty("Price")
	private long price;
	
    public ProductAvailabilityResponse() {
		super();
	}

	public ProductAvailabilityResponse(boolean isProductAvailable, long availableQuantity, long price) {
        this.isProductAvailable = isProductAvailable;
        this.availableQuantity = availableQuantity;
        this.price = price;
    }
	
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
    // Getters and setters (if needed)
}}

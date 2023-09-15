package com.example.demo;


import java.util.List;
import java.util.Map;

import jakarta.persistence.*;

@Entity
@Table(name = "\"order\"")
public class Order {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long orderId;

	    @OneToMany(cascade = CascadeType.ALL)
	   @JoinColumn(name = "orderId")
	    private List<Cart > Selectedcart;

		public Long getOrderId() {
			return orderId;
		}

		public Order(Long orderId, List<Cart> selectedcart) {
			super();
			this.orderId = orderId;
			Selectedcart = selectedcart;
		}

		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}

		public List<Cart> getSelectedcart() {
			return Selectedcart;
		}

		public void setSelectedcart(List<Cart> selectedcart) {
			Selectedcart = selectedcart;
		}

		public Order() {
			super();
			
		}

	    // Other order attributes, getters, setters, etc.
	}
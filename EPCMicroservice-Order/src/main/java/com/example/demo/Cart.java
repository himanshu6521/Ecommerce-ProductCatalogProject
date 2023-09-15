package com.example.demo;

import jakarta.persistence.*;

@Entity
@Table(name= "Cart")
public class Cart {

	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	     Long Cartid;

	    private long productId;
	    private long quantity;
	    
	    @ManyToOne
	    Order ob;
	    
	    @Column(name = "Bill")
	    private Long Bill ;
	    public void setProductId(long productId) {
			this.productId = productId;
		}
		public void setQuantity(long quantity) {
			this.quantity = quantity;
		}



		
	    public Cart(Long cartid, long productId, long quantity, Long bill, Order ob) {
			super();
			Cartid = cartid;
			this.productId = productId;
			this.quantity = quantity;
			this.Bill = bill;
			this.ob = ob;
		}
		public Long getBill() {
			return Bill;
		}
		public void setBill(Long Bill) {
			this.Bill = Bill;
		}
		public Order getOb() {
			return ob;
		}
		public void setOb(Order ob) {
			this.ob = ob;
		}
		
	
	    
		public Long getCartid() {
			return Cartid;
		}
		public Cart() {
			super();
			
		}
		
		public void setCartid(Long cartid) {
			Cartid = cartid;
		}
		public Long getProductId() {
			return productId;
		}
		public void setProductId(Long productId) {
			this.productId = productId;
		}
		public Long getQuantity() {
			return quantity;
		}
		public void setQuantity(Long quantity) {
			this.quantity = quantity;
		}

	    // Constructors, getters, setters, etc.
	}

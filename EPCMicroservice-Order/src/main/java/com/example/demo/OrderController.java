package com.example.demo;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
    private final OrderService orderService;

    
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

 
    
    // Create an order
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
    	
        Order createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    // Read an order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update an order by ID
    @PutMapping("/{orderId}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long orderId, @RequestBody Order updatedOrder) {
        Order order = orderService.updateOrder(orderId, updatedOrder);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete an order by ID
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        boolean deleted = orderService.deleteOrder(orderId);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get a list of all orders
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
    	System.out.println(" in allorders");
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    
    //------------------------------------
    
    @GetMapping("/carts")
    public ResponseEntity<List<Cart>> getAllCarts() {
        List<Cart> carts = orderService.getAllCarts();
        return ResponseEntity.ok(carts);
    }
    
    @GetMapping("/carts/{cartId}")
    public ResponseEntity<Cart> getCartById(@PathVariable Long cartId) {
        Cart cart = orderService.getCartById(cartId);
        return ResponseEntity.ok(cart);
    }
    
    @PostMapping("/carts/")
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        Cart createdCart = orderService.createCart(cart);
        return ResponseEntity.ok(createdCart);
    }

    @PutMapping("/carts/{cartId}")
    public ResponseEntity<Cart> updateCart(@PathVariable Long cartId, @RequestBody Cart updatedCart) {
        Cart cart = orderService.updateCart(cartId, updatedCart);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/carts/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long cartId) {
    	orderService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }
    
    
    @GetMapping("calculateTotalBill/{productId}/{quantity}")
    public ResponseEntity<Long> calculateTotalBill(
            @PathVariable Long productId,
            @PathVariable Long quantity) {

        Long totalBill = (long) orderService.getTotalBill(productId, quantity);

        // Return the totalBill as a ResponseEntity with OK status
        return ResponseEntity.ok(totalBill);
    }
    

    @GetMapping("/purchaseByOrderID/{orderId}")
    public ResponseEntity<Long> purchaseByOrderID(@PathVariable Long orderId) {
        Long totalBill = orderService.purchaseByOrderID(orderId);
        if(totalBill== null) { totalBill=0L;}
        // Return the totalBill as a ResponseEntity with OK status
        return ResponseEntity.ok(totalBill);
    }


    @PostMapping("/createOrder")
    public ResponseEntity<Order> createOrder(
    		@RequestBody Map<Long, Long> productQuantityMap) {
    	StringBuilder result = new StringBuilder();
    	
    	 // Iterate through the productQuantityMap and append data to the result
        for (Map.Entry<Long, Long> entry : productQuantityMap.entrySet()) {
            Long productId = entry.getKey();
            Long quantity = entry.getValue();
            result.append("Product ID: ").append(productId).append(", Quantity: ").append(quantity).append("\n");
        }
        
        System.out.println(result);

        Order order = orderService.createOrder(productQuantityMap);
        
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}

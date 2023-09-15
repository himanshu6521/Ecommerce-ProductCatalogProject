package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.*;
import com.example.Exceptions.CartNotFoundException;
import com.example.Responses.ProductAvailabilityResponse;

import java.util.List;
import java.util.Optional;
import java.util.*;

@Service
public class OrderService {
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	private  RestTemplate restTemplateOrder;

	@Autowired
    private  OrderRepository orderRepository;
	
	@Autowired
	private ProductService productService;

   
    public OrderService(OrderRepository orderRepository,RestTemplate restTemplateOrder,ProductService productService) {
        this.restTemplateOrder = restTemplateOrder;
		this.orderRepository = orderRepository;
		this.productService = productService;
    }
    
    @Autowired
    public OrderService(OrderRepository orderRepository,RestTemplate restTemplateOrder,CartRepository cartrepository) {
        this.restTemplateOrder = restTemplateOrder;
		this.orderRepository = orderRepository;
		this.cartRepository= cartrepository;
    }

    // Create an order
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    // Read an order by ID
    public Order getOrderById(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        return optionalOrder.orElse(null);
    }

    // Update an order
    public Order updateOrder(Long orderId, Order updatedOrder) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            Order existingOrder = optionalOrder.get();
            existingOrder.setSelectedcart(updatedOrder.getSelectedcart());
            // Update other fields as needed

            return orderRepository.save(existingOrder);
        } else {
            return null; // Order not found
        }
    }

    // Delete an order by ID
    public boolean deleteOrder(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            orderRepository.deleteById(orderId);
            return true;
        } else {
            return false; // Order not found
        }
    }

    // Get a list of all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    //------------Carts service methods--------------------------------------
    
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }
    
    public Cart getCartById(Long cartId) throws CartNotFoundException {
        Optional<Cart> cartOptional = cartRepository.findById(cartId);
        if (cartOptional.isPresent()) {
            return cartOptional.get();
        }
        throw new CartNotFoundException("Cart with ID " + cartId + " not found");
    }

    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart updateCart(Long cartId, Cart updatedCart) throws CartNotFoundException {
        if (cartRepository.existsById(cartId)) {
            updatedCart.setCartid(cartId); // Ensure the ID is set to the updated entity.
            return cartRepository.save(updatedCart);
        }
        throw new CartNotFoundException("Cart with ID " + cartId + " not found");
    }

    public void deleteCart(Long cartId) throws CartNotFoundException {
        if (cartRepository.existsById(cartId)) {
            cartRepository.deleteById(cartId);
        } else {
            throw new CartNotFoundException("Cart with ID " + cartId + " not found");
        }
    }
    
    //-----------------------------
    
//   //Method from the above provided classes . Write a service which will accept a productId
//    and Quantity to buy. this method will go to product model and will check if the product
//    is present or not by using id. If it is present , it will then check if the product is in
//    inventory. it will use productId to check numberOfItemsInStock (inventory class) .
//    if (numberOfItemsInStock - given Quantity !=0 || numberOfItemsInStock - 
//    given Quantity < 0) then update the field in product model  boolean productIsAvailable 
//    to true. after this provide productId ,Bill (productPrice * Quantity)and Quantity to 
//    Order class, it will create a cart with these information.
    
    

    public double getTotalBill(Long productId, Long quantity) {
        // Make a REST API call to the Inventory service to get ProductAvailabilityResponse
        ResponseEntity<ProductAvailabilityResponse> responseEntity = restTemplateOrder.getForEntity(
            "http://localhost:8082/inventories/ProductAvailability/" + productId + "/" + quantity,
            ProductAvailabilityResponse.class);

        // Extract the ProductAvailabilityResponse from the response entity
        ProductAvailabilityResponse productAvailabilityResponse = responseEntity.getBody();

        // Ensure a valid response and non-null fields
        if (productAvailabilityResponse != null) {
            boolean isProductAvailable = productAvailabilityResponse.isProductAvailable();
            long price = productAvailabilityResponse.getPrice();

            // Calculate the totalBill if the product is available, otherwise set it to 0.0
            return isProductAvailable ? price * quantity : 0.0;
        }

        // Handle the case when the response or required fields are null
        return 0.0; // You can return some default value or handle it as needed.
    }
    
    
    
    
    public Long purchaseByOrderID(Long orderId) {
        // Retrieve the order by orderId
        Order order = getOrderById(orderId);

        // Check if the order exists
        if (order == null) {
            throw new CartNotFoundException("Order not found with id: ");
        }

        // Initialize the total bill to zero
        Long totalBill = 0L;

        // Calculate the total bill by iterating through the carts in the order
        for (Cart cart : order.getSelectedcart()) {
            Long productId = cart.getProductId();
            Long quantity = cart.getQuantity();

            // Call the calculateTotalBill method for the product and add it to the total bill
            Long productBill = (long) getTotalBill(productId, quantity);
            if(totalBill== null) { totalBill=0L;}
            totalBill += productBill;
        }
        if(totalBill== null) { totalBill=0L;}
        return totalBill;
    }
    
    
    //-------------------------creating order by giving multiple productid and their quantity
    public Order createOrder(Map<Long, Long> productQuantityMap) {
        Order order = new Order();
        List<Cart> carts = new ArrayList<>();

        for (Map.Entry<Long, Long> entry : productQuantityMap.entrySet()) {
            Long productId = entry.getKey();
            Long quantity = entry.getValue();

            System.out.println(productId + " " + quantity);
            // Fetch product price from ProductService using RestTemplate
            //using ProductService name instead of localhost ,its running 
//            ResponseEntity<Long> responseEntity = restTemplateOrder.getForEntity(
//                "http://ProductService/products/getPricebyProductId/" + productId,
//                Long.class);
//
//            if (responseEntity.getStatusCode() == HttpStatus.OK) {
//                Long price = responseEntity.getBody();
//                if(price==null) { price= 0L;}
//                System.out.println(price);
//                Long bill = price * quantity;
//                Cart cart = new Cart();
//                cart.setProductId(productId);
//                cart.setQuantity(quantity);
//                cart.setBill(bill);
//              //  cart.setOb(order);
//                carts.add(cart);
//            } else {
//                // Handle the case when the product price cannot be retrieved
//                return null;
//            }
//        }
//
//        order.setSelectedcart(carts);
//        return orderRepository.save(order);
//    }
    
            // Fetch product price using Feign Client
            Long price = productService.getProductPrice(productId);
            if (price == null) {
                price = 0L;
            }

            System.out.println(price);
            Long bill = price * quantity;
            Cart cart = new Cart();
            cart.setProductId(productId);
            cart.setQuantity(quantity);
            cart.setBill(bill);
            carts.add(cart);
        }

        order.setSelectedcart(carts);
        return orderRepository.save(order);
    }
    
    
}
    
    


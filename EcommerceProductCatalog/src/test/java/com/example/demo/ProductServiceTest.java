package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.junit.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

	 @InjectMocks
	    private ProductService productService;

	    @Mock
	    private ProductRepository productRepository;
	    
//	    @Before
//	    public void init() {
//	    	MockitoAnnotations.initMocks(this);
//	    }
	    
	    @BeforeEach
	    public void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }
	    
	    @Test
	    public void testGetAllProducts() {
	        // Create some mock data for products
	        Product product1 = new Product(1L, "Product 1", 10.0, "Description 1", true);
	        Product product2 = new Product(2L, "Product 2", 20.0, "Description 2", true);
	        List<Product> mockProducts = Arrays.asList(product1, product2);

	        // Define the behavior of the mock productRepository
	        when(productRepository.findAll()).thenReturn(mockProducts);

	        // Call the method you want to test
	        List<Product> products = productService.getAllProducts();

	        // Verify that the method returned the expected data
	        assertEquals(2, products.size()); // Assuming you expect 2 products
	    }
}

package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ObjectWriter;

import ch.qos.logback.core.net.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ProductController.class)
//@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureMockMvc

public class ProductControllerTest {

	
	private static final Logger logger = LoggerFactory.getLogger(ProductControllerTest.class);

	@Autowired
	private MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();

	@Autowired
	@MockBean
	private ProductService productService;

//	@Autowired
//	@InjectMocks
//	private ProductController productController;

	List<Product> mockProducts;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		

		// Configure productService to use the mocked productRepository
		// productService = new ProductService(productRepository);

		this.mockMvc = MockMvcBuilders.standaloneSetup(ProductControllerTest.class).build();
		
		// Create a list of sample products
		List<Product> mockProducts = Arrays.asList(new Product(1L, "Product 1", 10.0, "Description 1", true),
				new Product(2L, "Product 2", 20.0, "Description 2", true));
		logger.debug(mockProducts.get(0).getProductName(), mockProducts.get(1).getProductName());
	}

	@Test
	    public void testGetAllProducts() throws Exception {
		

	        // Define the behavior of your productService mock
	        when(productService.getAllProducts()).thenReturn(mockProducts);

	        // Perform a GET request to the endpoint
	        ResultActions result = mockMvc.perform(
	            MockMvcRequestBuilders.get("/products") // Replace with your actual endpoint
	                .contentType(MediaType.APPLICATION_JSON)
	        );

	        // Assert the response status code
	        result.andExpect(MockMvcResultMatchers.status().isOk());

	        // Deserialize the response JSON to a list of products
	        String jsonResponse = result.andReturn().getResponse().getContentAsString();
	        List<Product> responseProducts = objectMapper.readValue(jsonResponse, new TypeReference<List<Product>>() {});

	        // Perform additional assertions on the response products
	        // For example, you can use JUnit assertions to validate the size of the list, specific product attributes, etc.
	        
	        
	        assertEquals(2, responseProducts.size());
	        assertEquals("Product 1", responseProducts.get(0).getProductName());
	        assertEquals("Product 2", responseProducts.get(1).getProductName());
}

}

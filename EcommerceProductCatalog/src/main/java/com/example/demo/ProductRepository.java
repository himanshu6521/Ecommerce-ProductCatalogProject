package com.example.demo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    // You can define custom queries or methods here if needed
	
	 @Query("SELECT p.productPrice FROM Product p WHERE p.productId = :productId")
	    Long findProductPriceByProductId(@Param("productId") Long productId);
}
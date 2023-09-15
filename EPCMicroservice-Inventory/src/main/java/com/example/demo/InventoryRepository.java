package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // Custom queries can be defined here if needed
	
	Inventory findByProductId(Long productId);
}
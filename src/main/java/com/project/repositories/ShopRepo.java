package com.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.entities.Shop;


public interface ShopRepo extends JpaRepository<Shop, Integer>{
	List<Shop> findByCategory(String category);
	
	@Query(value="SELECT enrollment FROM shop WHERE id = :uid",nativeQuery = true)
	String findEnrollmentByuid(Integer uid);
}

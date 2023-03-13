package com.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entities.Shop;

public interface ShopRepo extends JpaRepository<Shop, Integer>{
	List<Shop> findByEnrollment(long enrollment);
}

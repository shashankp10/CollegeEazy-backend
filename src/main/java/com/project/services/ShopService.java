package com.project.services;

import java.util.List;

import com.project.entities.Shop;
import com.project.module.dto.ShopDto;

public interface ShopService {
	
	//ShopDto addItem(ShopDto shopDto);
	void deleteItem(int id);
	ShopDto updateItem(ShopDto shopDto, int id);
	public List<ShopDto> getAllItem();
	List<Shop> getItemByEnrollment(long enrollment);
	void saveProduct(Shop product);
//	String addItem(String fileName, String name, String description, String path, String price, String contactDetail,
//			long enrollment, MultipartFile multipartFile) throws IOException;
	ShopDto addItem(ShopDto shopDto);
	
}

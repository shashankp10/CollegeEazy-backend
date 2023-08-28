package com.project.services.impl;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entities.Shop;
import com.project.exceptions.ResourceNotFoundException;
import com.project.module.dto.ShopDto;
import com.project.repositories.ShopRepo;
import com.project.services.ShopService;


@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopRepo shopRepo;
	
	@Override
	public ShopDto addItem(ShopDto item) {
		Shop items = this.dtoToShop(item);
		Shop savedItem = this.shopRepo.save(items);
		return this.shopToDto(savedItem);
	}
	
	@Override
	public void deleteItem(int id) {
		Shop shop = this.shopRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Item","Id",id));
		
		this.shopRepo.delete(shop);		
	}
	
	@Override
	public List<ShopDto> getAllItem() {
		List<Shop> items = this.shopRepo.findAll();
		List<ShopDto> itemDtos = items.stream().map(shop -> this.shopToDto(shop)).collect(Collectors.toList());
		return itemDtos;
	}
	
	@Override
	public List<ShopDto> getItemByCategory(String category){
		try{
			List<Shop> itemsByCategory = this.shopRepo.findByCategory(category);
			List<ShopDto> itemsDto = itemsByCategory.stream().map(shop -> this.shopToDto(shop)).collect(Collectors.toList());
			return itemsDto; 
		}catch(Exception e) {
			System.err.println("Could not find category: " + category);
	        e.printStackTrace();
	        return Collections.emptyList();
		}
		
	}
	@Override
	public List<Shop> findByCategory(String category){
		return shopRepo.findByCategory(category);
	}
	
	@Override
	public String findEnrollmentByuid(Integer uid) {
		return shopRepo.findEnrollmentByuid(uid);
	}
	
	@Override
	public ShopDto updateItem(ShopDto shopDto, int id) {
		Shop shop = this.shopRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Item","Id",id));
		
		shop.setCategory(shopDto.getCategory());
		shop.setDescription(shopDto.getDescription());
		shop.setPrice(shopDto.getPrice());
		shop.setName(shop.getName());
		shop.setPath(shop.getPath());
		shop.setTitle(shop.getTitle());
        shop.setContact(shop.getContact());
		
		Shop updatedItem = this.shopRepo.save(shop);
		ShopDto shopDto1 = this.shopToDto(updatedItem);
		return shopDto1;
	}
	
//	private Shop dtoToShop(ShopDto shopDto) {
//		Shop shop = new Shop();
//		shop.setName(shopDto.getName());
//		shop.setPrice(shopDto.getPrice());
//		shop.setCategory(shopDto.getCategory());
//		shop.setDescription(shopDto.getDescription());
//		shop.setEnrollment(shopDto.getEnrollment());
//		shop.setImagePath(shopDto.getImagePath());
//		shop.setTitle(shopDto.getTitle());
//		shop.setContact(shopDto.getContact());
//		return shop;
//	}
			// use model mapper
	private ShopDto shopToDto(Shop shop) {
		ShopDto shopDto = new ShopDto();
		shopDto.setName(shop.getName());
		shopDto.setPrice(shop.getPrice());
		shopDto.setCategory(shop.getCategory());
		shopDto.setDescription(shop.getDescription());
		shopDto.setEnrollment(shop.getEnrollment());
		shopDto.setPath(shop.getPath());
		shopDto.setTitle(shop.getTitle());
		shopDto.setContact(shop.getContact());
		shopDto.setId(shop.getId());
		return shopDto;
	}
	private Shop dtoToShop(ShopDto item) {
		Shop shop = new Shop();
		shop.setName(item.getName());
		shop.setPrice(item.getPrice());
		shop.setCategory(item.getCategory());
		shop.setDescription(item.getDescription());
		shop.setEnrollment(item.getEnrollment());
		shop.setPath(item.getPath());
		shop.setTitle(item.getTitle());
		shop.setContact(item.getContact());
		return shop;
	}
/*
	@Override
	public String saveImage(String fileName, String category, String description, String name, String price,
			String enrollment, String title, String contact, MultipartFile multipartFile)throws IOException {
		
		String fileCode = RandomStringUtils.randomAlphanumeric(8);    
        Path uploadPath = Paths.get("Shop",category);
          
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            
            Shop shop = new Shop();
            
         // Remove the "Files-Upload\" prefix from the file path
            String filePathString = filePath.toString().substring(filePath.toString().indexOf("\\") + 1);
           
            shop.setImagePath(filePathString);
            //shop.setData(fileName);
            shop.setCategory(category);
            shop.setDescription(description);
            shop.setEnrollment(enrollment);
            shop.setName(name);
            shop.setPrice(price);
            shop.setImagePath(filePathString);
            shop.setTitle(title);
            shop.setContact(contact);
            
            shopRepo.save(shop);
            
        } catch (IOException ioe) {       
            throw new IOException("Could not save image: " + fileName, ioe);
        }
         
        return fileCode;
    }
*/

	
	
	
}

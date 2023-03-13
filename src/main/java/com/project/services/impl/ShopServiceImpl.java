package com.project.services.impl;

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
	public ShopDto addItem(ShopDto shopDto) {
		Shop shop = this.dtoToShop(shopDto);
		Shop savedItem = this.shopRepo.save(shop);
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
	public List<Shop> getItemByEnrollment(long enrollment) {
		return shopRepo.findByEnrollment(enrollment);
	}
	@Override
	public ShopDto updateItem(ShopDto shopDto, int id) {
		Shop shop = this.shopRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Item","Id",id));
		
		shop.setContactDetail(shopDto.getContactDetail());
		shop.setDescription(shopDto.getDescription());
		shop.setPrice(shopDto.getPrice());
		shop.setName(shop.getName());
		shop.setImagePath(shop.getImagePath());
		
		Shop updatedItem = this.shopRepo.save(shop);
		ShopDto shopDto1 = this.shopToDto(updatedItem);
		return shopDto1;
	}
	
	private Shop dtoToShop(ShopDto shopDto) {
		Shop shop = new Shop();
		shop.setName(shopDto.getName());
		shop.setPrice(shopDto.getPrice());
		shop.setContactDetail(shopDto.getContactDetail());
		shop.setDescription(shopDto.getDescription());
		shop.setEnrollment(shopDto.getEnrollment());
		shop.setImagePath(shopDto.getImagePath());
		return shop;
	}
	private ShopDto shopToDto(Shop shop) {
		ShopDto shopDto = new ShopDto();
		shopDto.setName(shop.getName());
		shopDto.setPrice(shop.getPrice());
		shopDto.setContactDetail(shop.getContactDetail());
		shopDto.setDescription(shop.getDescription());
		shopDto.setEnrollment(shop.getEnrollment());
		shopDto.setImagePath(shop.getImagePath());
		return shopDto;
	}

	@Override
	public void saveProduct(Shop product) {
		 shopRepo.save(product);
		
	}
/*
	@Override
	public String addItem(String fileName, String name, String description, String path,
			String price, String contactDetail, long enrollment, 
			MultipartFile multipartFile)throws IOException {
		
		String fileCode = RandomStringUtils.randomAlphanumeric(8);    
        Path uploadPath = Paths.get("Image-Upload");
          
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
            System.out.println(filePath);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            
//            Notes notes = new Notes();
//            notes.setPath(filePath.toString());
//            notes.setData(fileName);
//            notes.se;
//            notes.setSubjectId(subjectId);
//            notes.setType(type);
//            notesRepo.save(notes);
            Shop shop = new Shop();
            shop.setContactDetail(contactDetail);
            shop.setDescription(description);
            shop.setEnrollment(enrollment);
            shop.setName(name);
            shop.setPrice(price);
            shop.setPath(filePath.toString());
            shopRepo.save(shop);
        } catch (IOException ioe) {       
            throw new IOException("Could not save file: " + fileName, ioe);
        }
         
        return fileCode;
    }
    */
}

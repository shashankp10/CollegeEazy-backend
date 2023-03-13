package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.module.dto.ShopDto;
import com.project.payload.ApiResponse;
import com.project.services.ShopService;

@RestController
@RequestMapping("/collegeazy/shop")
@CrossOrigin(origins = "http://localhost:3000")
public class ShopController {
	
	@Autowired
	private ShopService shopService;
	
	/*
	@PostMapping("/addItem/{name}/{enrollment}/{description}/{price}/{contactDetail}")
    public ResponseEntity<FileUploadResponse> uploadFile(@PathVariable String name, @PathVariable long enrollment,
    		@PathVariable String description,@PathVariable String price,@PathVariable String contactDetail,
    		@RequestParam("file") MultipartFile multipartFile)throws IOException {
 	  
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();
         
        String filecode = shopService.addItem(fileName,name,description,path,price,contactDetail,enrollment, multipartFile);
         
        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri("/downloadFile/" + filecode);
       
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	*/
	@PostMapping("/addItem")
	public ResponseEntity<ShopDto> createUserAttendance(@RequestBody ShopDto shopDto){
		// validation for login
		ShopDto addItemDto = this.shopService.addItem(shopDto);
		System.out.println("Item added successfully!!");
		return new ResponseEntity<>(addItemDto, HttpStatus.CREATED);
	}
	@DeleteMapping("/removeItem/{uid}")

	public ResponseEntity<ApiResponse> removeItem(@PathVariable Integer uid){
		this.shopService.deleteItem(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Item deleted successfully", true),HttpStatus.OK);

	}
////	@GetMapping("/userId/{enrollment}")
//	public ResponseEntity<List<Shop>> getAll(@PathVariable Integer enrollment){
//		// validation -> for null check
//		
//		return ResponseEntity.ok(this.shopService.getItemByEnrollment(enrollment));
//	}
	@GetMapping("/items")
	public ResponseEntity<List<ShopDto>> getAllItems(){
		return ResponseEntity.ok(this.shopService.getAllItem());
	}
}

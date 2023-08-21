package com.project.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.module.dto.ShopDto;
import com.project.payload.ApiResponse;
import com.project.security.JWTUtils;
import com.project.services.ShopService;
import com.project.services.UserService;

@RestController
@RequestMapping("/store/private")
@CrossOrigin(origins = "http://localhost:3000")
public class ShopController {
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	@PreAuthorize(value = "hasRole('ROLE_USER')") 
	@PostMapping("/addItem")
    public ResponseEntity<ShopDto>  uploadFile(@RequestBody ShopDto item){
		if(item.getPath()==null || item.getCategory()==null || item.getDescription()==null || item.getTitle() ==null ||
				item.getEnrollment() == null || item.getName()==null || item.getPrice()==null || item.getContact()==null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		if(userService.findByEnrollment(item.getEnrollment())==null) {					
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ShopDto createItemDto = this.shopService.addItem(item);
		System.out.println("Item added successfully!!");
		return new ResponseEntity<>(createItemDto, HttpStatus.CREATED);
	}
/*
	@PreAuthorize(value = "hasRole('ROLE_USER')") 
	@PostMapping("/addItem/{category}")
    public ResponseEntity<FileUploadResponse>  uploadFile(@RequestParam("file") MultipartFile file,
                           @RequestParam("directory") String directory,
                           @PathVariable("category") String category,
                           @RequestParam("description") String description,
                           @RequestParam("enrollment") String enrollment,
                           @RequestParam("name") String name,
                           @RequestParam("price") String price,
                           @RequestParam("contact") String contact,
                           @RequestParam("title") String title) throws IOException {
		if(directory==null || category==null || description==null || title ==null ||
				enrollment == null || name==null || price==null || contact==null)
			// return message : seems like one or more field(s) is/are empty
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		if(userService.findByEnrollment(enrollment)==null) {					
			// return message : seems like you haven't registered
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        long size = file.getSize();
        String filecode = shopService.saveImage(fileName, category, description, name, price, enrollment, title, contact, file);
       
        
        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri(filecode);
       
        return new ResponseEntity<>(response, HttpStatus.OK);		       
    }
    */
	/*
	@PreAuthorize(value = "hasRole('ROLE_USER')")
	@GetMapping("/getImage/{category}/{filename:.+}")
 	public void getImage(@PathVariable String category, @PathVariable String filename, HttpServletResponse response) throws IOException {
 	    if(category == null || filename == null)
 	    	return;		// new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		String filePath = directory + category + "\\" + filename;
 	    Path path = Paths.get(filePath);

 	    if (Files.exists(path)) {
 	        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
 	        response.setHeader("Content-Disposition", "inline; filename=" + filename);

 	        try {
 	            Files.copy(path, response.getOutputStream());
 	            response.getOutputStream().flush();
 	        } catch (IOException ex) {
 	            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
 	            response.getWriter().write("Failed to retrieve image: " + ex.getMessage());
 	            response.getWriter().flush();
 	        }
 	    } else {
 	        throw new RuntimeException("File not found");
 	    }
 	}
	*/
	@PreAuthorize(value = "hasRole('ROLE_USER')") // change to admin
	@DeleteMapping("/removeItem/{uid}")
	public ResponseEntity<ApiResponse> removeItem(@RequestHeader("Authorization") String authorizationHeader, 
									@PathVariable Integer uid){
		String token = authorizationHeader.substring(7);
	    String enrollment = jwtUtils.getEnrollmentFromToken(token);
		if(userService.findByEnrollment(shopService.findEnrollmentByuid(uid)).toString().equals(enrollment)) {					
			// return message : seems like you're trying delete someone else item
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		this.shopService.deleteItem(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Item deleted successfully", true),HttpStatus.OK);

	}
	
	@PreAuthorize(value = "hasRole('ROLE_USER')") // change to admin
	@GetMapping("/")
	public ResponseEntity<List<ShopDto>> getAllItems(){
		return ResponseEntity.ok(this.shopService.getAllItem());
		//List<ShopDto> items = this.shopService.getAllItem();

//	    for (ShopDto item : items) {
//	        String imagePath = "/getImage/" + item.getCategory() + "/" + item.getImagePath();
//	        item.setImagePath(imagePath);
//	    }
		
	}
	/*
	@PreAuthorize(value = "hasRole('ROLE_USER')") // change to admin
	@GetMapping("/items/{category}")
	public ResponseEntity<List<ShopDto>> getItemByCategory(@PathVariable String category, HttpServletResponse response) throws IOException{
//		String filename = "wk8ATHRV-book1.jpg";
//		getImage(category,filename,response);
		return ResponseEntity.ok(this.shopService.getItemByCategory(category));
	}
	*/
	@PreAuthorize(value = "hasRole('ROLE_USER')") // change to admin
	@PutMapping("/updateItem/{id}")
	public ResponseEntity<ShopDto> updateItem(@RequestBody ShopDto shopDto,@PathVariable int id){
			// not working properly
		return ResponseEntity.ok(this.shopService.updateItem(shopDto, id));
	}
	
	
}

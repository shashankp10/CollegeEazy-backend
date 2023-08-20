package com.project.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import com.project.module.dto.NotesDto;
import com.project.payload.ApiResponse;
import com.project.payload.FileUploadResponse;
import com.project.services.NotesService;
import com.project.services.impl.NotesServiceImpl;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.core.io.Resource;

@RestController
@RequestMapping("/notes/private")
@CrossOrigin(origins = "http://localhost:3000")
public class NotesController {
	
	@Autowired
	private NotesService notesService;
	
	@PreAuthorize(value = "hasRole('ROLE_USER')") // change to admin
	@PostMapping("/uploadLink")
	public ResponseEntity<NotesDto> uploadLink(@RequestBody NotesDto notesDto){
		if(notesDto.getBranch()==null || notesDto.getType()==null || notesDto.getData()==null
				|| notesDto.getSubjectId()==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
			// validation to check if user has logged in
		NotesDto createNotesDto = this.notesService.createNotes(notesDto);
		System.out.println("Link uploaded successfully!!");
		return new ResponseEntity<>(createNotesDto, HttpStatus.CREATED);
	}
	/*
	@PutMapping("/update/{subjectId}/{type}")
	public ResponseEntity<NotesDto> updateNotes(@RequestBody NotesDto notesDto, @PathVariable("subjectId")String branchId, @PathVariable("type")String type){
		if(branchId==null || type == null || notesDto == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(notesDto.getBranch()==null || notesDto.getType()==null || notesDto.getData()==null
				|| notesDto.getSubjectId()==null || notesDto.getPath()==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
				// validation to check if user has logged in
		NotesDto updateNotes = this.notesService.updateNotes(notesDto, branchId, type);
		System.out.println("Detail has been updated succesfully!!");
		return ResponseEntity.ok(updateNotes);
	}
	*/
	@PreAuthorize(value = "hasRole('ROLE_USER')")  // change to admin
	@DeleteMapping("/delete/{uid}")
	public ResponseEntity<ApiResponse> deleteNotes(@PathVariable Long uid){
		// check if uid is valid
		// validation to check if user has logged in
		// requested uid for deletion must belongs to the logged in user
		this.notesService.deleteNotes(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully", true),HttpStatus.OK);

	}
	

	/*
	 * 	File related APIs 
	 */
	@PreAuthorize(value = "hasRole('ROLE_USER')")  // change to admin
	@PostMapping("/uploadFile/{branch}/{type}/{subjectId}")
    public ResponseEntity<FileUploadResponse> uploadFile(@PathVariable String branch,
    		@PathVariable String type,@PathVariable String subjectId, 
    		@RequestParam("file") MultipartFile multipartFile) throws IOException {
 	  
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();
         
        String filecode = notesService.uploadFile(fileName,branch,type,subjectId, multipartFile);
       
        
        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri("/downloadFile/" + filecode);
       
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
 		// this end-point to download the file
	@PreAuthorize(value = "hasRole('ROLE_USER')")
	@GetMapping("/downloadFile/{parentDir}/{fileCode:.+}")
	public ResponseEntity<?> downloadFile(@PathVariable("parentDir") String parentDir,
				@PathVariable("fileCode") String fileCode) {
	    
	    // validate if user has logged in
	    
	    NotesServiceImpl downloadFiles = new NotesServiceImpl();
	    
	    Resource resource = null;
	    try {
	        resource = downloadFiles.downloadFile(parentDir, fileCode);
	    } catch (IOException e) {
	        return ResponseEntity.internalServerError().build();
	    }
	    
	    if (resource == null) {
	        return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
	    }
	    
	    String contentType = "application/octet-stream";
	    String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
	    
	    return ResponseEntity.ok()
	            .contentType(MediaType.parseMediaType(contentType))
	            .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
	            .body(resource);
	}
 	
 		// this end-point for Preview
 	
}

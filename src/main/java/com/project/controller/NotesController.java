package com.project.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.project.entities.Notes;
import com.project.module.dto.NotesDto;
import com.project.payload.ApiResponse;
import com.project.payload.FileUploadResponse;
import com.project.services.NotesService;
import com.project.services.impl.NotesServiceImpl;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.core.io.Resource;

@RestController
@RequestMapping("/collegeazy/notes")
public class NotesController {
	
	@Autowired
	private NotesService notesService;
	
	
	@PostMapping("/uploadLink")
	public ResponseEntity<NotesDto> uploadLink(@RequestBody NotesDto notesDto){
		NotesDto createNotesDto = this.notesService.createNotes(notesDto);
		System.out.println("Link uploaded successfully!!");
		return new ResponseEntity<>(createNotesDto, HttpStatus.CREATED);
	}
	/*
	@PutMapping("/update/{subjectId}/{type}")
	public ResponseEntity<NotesDto> updateNotes(@RequestBody NotesDto notesDto, @PathVariable("subjectId")String branchId, @PathVariable("type")String type){
		NotesDto updateNotes = this.notesService.updateNotes(notesDto, branchId, type);
		System.out.println("Detail has been updated succesfully!!");
		return ResponseEntity.ok(updateNotes);
	}
	*/
	@DeleteMapping("/delete/{uid}")
	public ResponseEntity<ApiResponse> deleteNotes(@PathVariable Long uid){
		this.notesService.deleteNotes(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully", true),HttpStatus.OK);

	}
	@GetMapping(value="/fetch/{subjectId}/{type}")
	public ResponseEntity<List<Notes>> getNotes(@PathVariable String subjectId,@PathVariable String type){
		return ResponseEntity.ok(this.notesService.findAllNotesBySubjectId(subjectId,type));
	}
	
	@GetMapping(value="/fetch/{subjectId}/{type}/{path}")
	public ResponseEntity<List<Notes>> getNotesByPath(@PathVariable String subjectId,@PathVariable String type,
														@PathVariable String path){
		return ResponseEntity.ok(this.notesService.findByPath(subjectId,type,path));
	}
//	@GetMapping(value="/fetch/{path}")  // {subjectId}/{type}/      @PathVariable String subjectId,@PathVariable String type, 
//	public ResponseEntity<List<Object[]>> getNotesByPath(@PathVariable String path){
//		return ResponseEntity.ok(this.notesService.findByPath(path));
//	}
	
	/*
	 * 	File related APIs 
	 */
	
	
 	@PostMapping("/uploadFile/{branch}/{type}/{subjectId}")
    public ResponseEntity<FileUploadResponse> uploadFile(@PathVariable String branch, @PathVariable String type,@PathVariable String subjectId,
    		@RequestParam("file") MultipartFile multipartFile)throws IOException {
 	  
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
 	@GetMapping("/downloadFile/{fileCode}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) {
 		
 			// validate if user has logged in
 		
        NotesServiceImpl downloadFiles = new NotesServiceImpl();
         
        Resource resource = null;
        try {
            resource = downloadFiles.downloadFile(fileCode);
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
 	@GetMapping("/file/{filename:.+}")
 	public void getFile(@PathVariable String filename, HttpServletResponse response) {
 	    String filePath = "C:\\Users\\pande\\Documents\\workspace-spring-tool-suite-4-4.16.0.RELEASE\\Project-2\\Files-Upload\\" + filename;
 	    Path path = Paths.get(filePath);

 	    if (Files.exists(path)) {
 	        response.setContentType("application/pdf");
 	        response.setHeader("Content-Disposition", "inline; filename=" + filename);

 	        try {
 	            Files.copy(path, response.getOutputStream());
 	            response.getOutputStream().flush();
 	        } catch (IOException ex) {
 	            ex.printStackTrace();
 	        }
 	    } else {
 	        throw new RuntimeException("File not found");
 	    }
 	}
 	
}

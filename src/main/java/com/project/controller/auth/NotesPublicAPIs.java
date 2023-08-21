package com.project.controller.auth;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.entities.Notes;
import com.project.services.NotesService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/notes/public")
@CrossOrigin(origins = "http://localhost:3000")
public class NotesPublicAPIs {
	
	@Autowired
	private NotesService notesService;
	

	
	private final String parentDirectory = "C:\\Users\\pande\\Documents\\workspace-spring-tool-suite-4-4.16.0.RELEASE\\Project-2\\Notes\\" ;
	
	@GetMapping(value="/fetch/{subjectId}/{type}")
	public ResponseEntity<List<Notes>> getNotes(@PathVariable String subjectId,@PathVariable String type){
		if(subjectId == null || type == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.ok(this.notesService.findAllNotesBySubjectId(subjectId,type));
	}
	
	@GetMapping(value="/fetch/{subjectId}/{type}/{path}")
	public ResponseEntity<List<Notes>> getNotesByPath(@PathVariable String subjectId,
			@PathVariable String type, @PathVariable String path){
		
		if(subjectId == null || type == null || path == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.ok(this.notesService.findByPath(subjectId,type,path));
	}
	
	@GetMapping("/file/{subjectCode}/{filename:.+}")
 	public void getFile(@PathVariable String subjectCode, @PathVariable String filename, HttpServletResponse response) {
 	    String filePath = parentDirectory + subjectCode + "\\" + filename;
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

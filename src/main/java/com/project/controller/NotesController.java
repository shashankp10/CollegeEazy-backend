package com.project.controller;


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
import org.springframework.web.bind.annotation.RestController;

import com.project.entities.Notes;
import com.project.module.dto.NotesDto;
import com.project.payload.ApiResponse;
import com.project.services.NotesService;

@RestController
@RequestMapping("/collegeazy/notes")
public class NotesController {
	
	@Autowired
	private NotesService notesService;
	
	@PostMapping("/upload")
	public ResponseEntity<NotesDto> createNotes(@RequestBody NotesDto notesDto){
		NotesDto createNotesDto = this.notesService.createNotes(notesDto);
		System.out.println("Notes uploaded successfully!!");
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

<<<<<<< HEAD
	}	
=======
	}
	
>>>>>>> cb7bff8f99c4b00054a618ccc5e4d41c170aa3ce
	@GetMapping(value="/fetch/{subjectId}/{type}")
	public ResponseEntity<List<Notes>> getNotes(@PathVariable String subjectId,@PathVariable String type){
		return ResponseEntity.ok(this.notesService.findAllNotesBySubjectId(subjectId,type));
	}
}

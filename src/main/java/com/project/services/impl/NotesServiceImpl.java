package com.project.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entities.Notes;
import com.project.exceptions.ResourceNotFoundException;
import com.project.module.dto.NotesDto;
import com.project.repositories.NotesRepo;
import com.project.services.NotesService;

@Service
public class NotesServiceImpl implements NotesService {

	@Autowired
	private NotesRepo notesRepo;
		
	@Override
	public NotesDto createNotes(NotesDto notesDto) {
		Notes notes = this.dtoToNotes(notesDto);
		Notes savedNotes = this.notesRepo.save(notes);
		return this.notesToDto(savedNotes);
	}
	@Override
	public NotesDto updateNotes(NotesDto notesDto, String subjectId, String type) {
//		Notes notes = this.notesRepo.findById(subjectId)
//				.orElseThrow(() -> new ResourceNotFoundException("Notes : ","Id",subjectId));
		/*
		 * Notes notes = this.notesRepo.findBySubjectId(subjectId);
		 * notes.setBranch(notesDto.getBranch()); notes.setData(notes.getData());
		 * notes.setSubjectId(notes.getSubjectId()); notes.setType(notes.getType());
		 * 
		 * Notes updatedNotes = this.notesRepo.save(notes); NotesDto notesDto1 =
		 * this.notesToDto(updatedNotes);
		 */
		return null;
	}
	@Override
	public List<NotesDto> getById(String subjectId,String type) {
//		Notes notes = this.notesRepo.findById(subjectId)
//				.orElseThrow(() -> new ResourceNotFoundException("Notes : ","Id",subjectId));
		List<Notes> notes = this.notesRepo.findBySubjectIdAndType(subjectId,type);
		return null;
	}
	
	@Override
	public void deleteNotes(Long uid) {
		Notes notes = this.notesRepo.findById(uid)
				.orElseThrow(() -> new ResourceNotFoundException("Notes","Id",uid));
		
		this.notesRepo.delete(notes);
		
	}
	public List<Notes> findById(String subjectId,String type) {
		return notesRepo.findBySubjectIdAndType(subjectId,type);
	}
	private Notes dtoToNotes(NotesDto notesDto) {
		Notes notes = new Notes();
		notes.setBranch(notesDto.getBranch());
		notes.setData(notesDto.getData());
		notes.setSubjectId(notesDto.getSubjectId());
		notes.setType(notesDto.getType());
		return notes;
	}
	private NotesDto notesToDto(Notes notes) {
		NotesDto notesDto = new NotesDto();
		notesDto.setBranch(notes.getBranch());
		notesDto.setData(notes.getData());
		notesDto.setSubjectId(notes.getSubjectId());
		notesDto.setType(notesDto.getType());
		return notesDto;
	}
	@Override
	public List<Notes> findAllNotesBySubjectId(String subjectId, String type) {
		return notesRepo.findBySubjectIdAndType(subjectId,type);
	}
}

package com.project.services;

import java.util.List;

import com.project.entities.Notes;
import com.project.module.dto.NotesDto;


public interface NotesService {
	NotesDto createNotes(NotesDto notesDto);
	List<NotesDto> getById(String subjectId,String types);
	List<Notes> findAllNotesBySubjectId(String subjectId,String types);
	void deleteNotes(Long Id);
	NotesDto updateNotes(NotesDto notesDto, String subjectId, String type);
}

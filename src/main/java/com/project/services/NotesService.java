package com.project.services;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.project.entities.Notes;
import com.project.module.dto.NotesDto;


public interface NotesService {
	NotesDto createNotes(NotesDto notesDto);
	List<NotesDto> getById(String subjectId,String types);
	List<Notes> findAllNotesBySubjectId(String subjectId,String types);
	void deleteNotes(Long Id);
	NotesDto updateNotes(NotesDto notesDto, String subjectId, String type);
	String uploadFile(String fileName,String branch, String type, String subjectId, MultipartFile multipartFile) throws IOException;
	List<Notes> findByPath(String subjectId,String type, String path);
	Resource downloadFile(String parentDir, String fileCode) throws IOException;
}

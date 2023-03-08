package com.project.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.entities.Notes;
import com.project.exceptions.ResourceNotFoundException;
import com.project.module.dto.NotesDto;
import com.project.repositories.NotesRepo;
import com.project.services.NotesService;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

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
//		Notes notes = this.notesRepo.findById(uid).orElseThrow(() -> new ResourceNotFoundException("Notes","Id",uid));
//		this.notesRepo.delete(notes);
		// User don't know what the uid... Do something else to delete the row!!
	}
	
	
	private Notes dtoToNotes(NotesDto notesDto) {
		Notes notes = new Notes();
		notes.setBranch(notesDto.getBranch());
		notes.setData(notesDto.getData());
		notes.setSubjectId(notesDto.getSubjectId());
		notes.setType(notesDto.getType());
		notes.setPath(notesDto.getPath());
		return notes;
	}
	private NotesDto notesToDto(Notes notes) {
		NotesDto notesDto = new NotesDto();
		notesDto.setBranch(notes.getBranch());
		notesDto.setData(notes.getData());
		notesDto.setSubjectId(notes.getSubjectId());
		notesDto.setType(notesDto.getType());
		notesDto.setPath(notes.getPath());
		return notesDto;
	}
	@Override
	public List<Notes> findAllNotesBySubjectId(String subjectId, String type) {
		return notesRepo.findBySubjectIdAndType(subjectId,type);
	}
	 
	@Override
	public String uploadFile(String fileName, String branch, String type, String subjectId, 
			MultipartFile multipartFile)throws IOException {
		
		String fileCode = RandomStringUtils.randomAlphanumeric(8);    
        Path uploadPath = Paths.get("Files-Upload");
          
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
            System.out.println(filePath);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            
            Notes notes = new Notes();
            notes.setPath(filePath.toString());
            notes.setData(fileName);
            notes.setBranch(branch);
            notes.setSubjectId(subjectId);
            notes.setType(type);
            notesRepo.save(notes);
        } catch (IOException ioe) {       
            throw new IOException("Could not save file: " + fileName, ioe);
        }
         
        return fileCode;
    }
	
	private Path foundFile;
	@Override
	public Resource downloadFile(String fileCode) throws IOException {
	        Path dirPath = Paths.get("Files-Upload");
	         
	        Files.list(dirPath).forEach(file -> {
	            if (file.getFileName().toString().startsWith(fileCode)) {
	                foundFile = file;
	                return;
	            }
	        });
	 
	        if (foundFile != null) {
	            return new UrlResource(foundFile.toUri());
	        }
	         
	        return null;
	    }
//	@Override
//	public List<Notes> findNotesByPath(String subjectId, String type, String path) {
//		return notesRepo.findNotesByPath(subjectId, type, path);
//	}
	@Override
	public List<Notes> findByPath(String subjectId, String type, String path) {
		return notesRepo.findByPath(subjectId, type, path);
	}
	
}

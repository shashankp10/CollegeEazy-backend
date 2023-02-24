package com.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entities.Notes;

public interface NotesRepo extends JpaRepository<Notes, Long> {
	List<Notes> findBySubjectIdAndType(String subjectId,String type	);
}

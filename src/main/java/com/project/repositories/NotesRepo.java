package com.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entities.Notes;

public interface NotesRepo extends JpaRepository<Notes, Long> {
<<<<<<< HEAD
	List<Notes> findBySubjectIdAndType(String subjectId,String type	);
=======
	List<Notes> findBySubjectIdAndType(String subjectId,String type);

>>>>>>> cb7bff8f99c4b00054a618ccc5e4d41c170aa3ce
}

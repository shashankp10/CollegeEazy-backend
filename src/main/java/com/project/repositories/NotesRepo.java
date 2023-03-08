package com.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.entities.Notes;

public interface NotesRepo extends JpaRepository<Notes, Long> {

	List<Notes> findBySubjectIdAndType(String subjectId,String type);
	
	@Query(value = "SELECT n.subject_id, n.type, n.path FROM notes n WHERE n.path = :path AND n.subject_id = :subjectId AND n.type = :type",nativeQuery = true)
	List<Notes> findByPath(@Param("subjectId")String subjectId, @Param("type")String type, @Param("path")String path);
//	@Query("SELECT n.subject_id, n.type, n.path FROM Note n WHERE n.path = :path")
//	List<Object[]> findByPath(@Param("path") String path);

}

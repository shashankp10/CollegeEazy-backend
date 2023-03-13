package com.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.entities.Attendance;


public interface AttendanceRepo extends JpaRepository<Attendance, Integer>{
	
	Attendance findByEnrollment(int enrollment);
	
	@Query(value = "SELECT u.semester,u.branch, a.subject1_present, a.subject1_absent,  a.subject2_present, a.subject2_absent, a.subject3_present, a.subject3_absent,  a.subject4_present, a.subject4_absent,  a.subject5_present, a.subject5_absent FROM users u INNER JOIN attendance a ON u.enrollment = a.enrollment WHERE u.enrollment = :enrollment", nativeQuery = true)
	List<Object[]> getAll(@Param("enrollment") int enrollment);

	
	@Modifying
    @Query("UPDATE Attendance a SET a.subject1_present = :present, a.subject1_absent = :absent WHERE a.enrollment = :enrollment")
    void updateSubject1Attendance(@Param("enrollment") int enrollment, @Param("present") int present, @Param("absent") int absent);

    @Modifying
    @Query("UPDATE Attendance a SET a.subject2_present = :present, a.subject2_absent = :absent WHERE a.enrollment = :enrollment")
    void updateSubject2Attendance(@Param("enrollment") int enrollment, @Param("present") int present, @Param("absent") int absent);

    @Modifying
    @Query("UPDATE Attendance a SET a.subject3_present = :present, a.subject3_absent = :absent WHERE a.enrollment = :enrollment")
    void updateSubject3Attendance(@Param("enrollment") int enrollment, @Param("present") int present, @Param("absent") int absent);

    @Modifying
    @Query("UPDATE Attendance a SET a.subject4_present = :present, a.subject4_absent = :absent WHERE a.enrollment = :enrollment")
    void updateSubject4Attendance(@Param("enrollment") int enrollment, @Param("present") int present, @Param("absent") int absent);

    @Modifying
    @Query("UPDATE Attendance a SET a.subject5_present = :present, a.subject5_absent = :absent WHERE a.enrollment = :enrollment")
    void updateSubject5Attendance(@Param("enrollment") int enrollment, @Param("present") int present, @Param("absent") int absent);

    @Modifying
    @Query("UPDATE Attendance a SET a.subject6_present = :present, a.subject6_absent = :absent WHERE a.enrollment = :enrollment")
    void updateSubject6Attendance(@Param("enrollment") int enrollment, @Param("present") int present, @Param("absent") int absent);

}

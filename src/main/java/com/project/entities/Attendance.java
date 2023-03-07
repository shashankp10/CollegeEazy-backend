package com.project.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table
@Entity
public class Attendance {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int Id;
	//@JoinColumn(name = "enrollment", foreignKey = @ForeignKey(name = "enrollment"))
	private int enrollment;
	
	private String subject1;
	private int subject1_present;
	private int subject1_absent;
	
	private String subject2;
	private int subject2_present;
	private int subject2_absent;
	
	private String subject3;
	private int subject3_present;
	private int subject3_absent;
	
	private String subject4;
	private int subject4_present;
	private int subject4_absent;
	
	private String subject5;
	private int subject5_present;
	private int subject5_absent;
	
	private String subject6;
	private int subject6_present;
	private int subject6_absent;
	
	
}

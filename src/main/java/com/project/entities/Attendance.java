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
	private double subject1;
	private double subject2;
	private double subject3;
	private double subject4;
	private double subject5;
	
}

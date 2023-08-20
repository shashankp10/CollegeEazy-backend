package com.project.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="users")
@Getter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
@Setter
public class User{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false)
	private long id;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "enrollment")
	private String enrollment;
	@ToString.Exclude
    @Column(name = "password", nullable = false, columnDefinition = "text")
	private String password;
	@Column(name = "branch", nullable = false)
	private String branch;
	@Column(name = "semester", nullable = false)
	private int semester;
	@Column(name = "roles", nullable = false)
    private String roles;

}

package com.project.module.dto;

import lombok.Data;

@Data 
public class ShopDto {
	
	private int id;
	private String name;
	private long enrollment; 
	private String description;
	private String price;
	private String contactDetail;
	private String imagePath;
}

package com.stackroute.restaurant.domain;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "restaurant")
public class Restaurant {
	
    @Id
	private int id;
	private String name;
	private String image;
	private String location;
	
	
	public Restaurant()
	{
		
	}
	
	public Restaurant(int id , String name , String image , String location)
	{
		this.id = id;
		this.name = name;
		this.image = image;
		this.location = location;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		
		this.name = name;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	

}

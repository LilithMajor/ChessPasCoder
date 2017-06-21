package com;

import java.util.Date;

public class Topic {
	
	private int id;
	private String name;
	private String creator;
	Date dateCreation;
	Date dateClose;
	
	private static int compteur = 1;
	
	public Topic (String name, String creator) {
		this.id = this.compteur++;
		this.name = name;
		this.creator = creator;
		dateCreation = new java.util.Date();
		dateClose = null;
	}
	
	public Topic () {
		
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

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Date getDateClose() {
		return dateClose;
	}

	public void setDateClose(Date dateClose) {
		this.dateClose = dateClose;
	}


}

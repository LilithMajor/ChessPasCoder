package com;

import java.util.ArrayList;
import java.util.Date;

public class Topic {
	
	private int id;
	private String name;
	private String creator;
	private Date dateCreation;
	private Date dateClose;
	private ArrayList<Response> L_Rep;
	
	private static int compteur = 1;
	
	public Topic (String name, String creator) {
		this.id = this.compteur++;
		this.name = name;
		this.creator = creator;
		dateCreation = new java.util.Date();
		dateClose = null;
	}
	
	public Topic (int i, String name, String creator, Date dateOpen, Date dateClose) {
		this.id = id;
		this.name = name;
		this.creator = creator;
		dateCreation = dateOpen;
		dateClose = dateClose;
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

	public ArrayList<Response> getL_Rep() {
		return L_Rep;
	}

	public void setL_Rep(ArrayList<Response> l_Rep) {
		L_Rep = l_Rep;
	}
	
	public int getNumberOfResponse(){
		return L_Rep.size();
	}
}

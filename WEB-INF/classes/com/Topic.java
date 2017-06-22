package com;

import java.util.ArrayList;
import java.util.Date;

public class Topic {
	
	private int id;
	private String name;
	private String creator;
	private Date dateCreation;
	private ArrayList<Response> L_Rep;
	
	private static int compteur = 1;
	
	public Topic (String name, String creator) {
		this.id = this.compteur++;
		this.name = name;
		this.creator = creator;
		dateCreation = new java.util.Date();
		this.L_Rep = new ArrayList<Response>();
	}
	
	public Topic (int i, String name, String creator, Date dateOpen, ArrayList<Response> rep) {
		this.id = i;
		this.name = name;
		this.creator = creator;
		this.dateCreation = dateOpen;
		this.setL_Rep(rep);
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

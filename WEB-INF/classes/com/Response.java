package com;

import java.util.Date;

public class Response {
	
	private int id;
	private String text;
	private String creator;
	private Date datePost;
	private int idTopic;
	private static int compteur = 1;
	
	public Response(String text, String creator, int idTopic) {
		this.id = compteur++;
		this.text = text;
		this.creator = creator;
		this.datePost = new java.util.Date();
		this.idTopic = idTopic;
	}
	
	public Response(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getDatePost() {
		return datePost;
	}

	public void setDatePost(Date datePost) {
		this.datePost = datePost;
	}

	public int getIdTopic() {
		return idTopic;
	}

	public void setIdTopic(int idTopic) {
		this.idTopic = idTopic;
	}

	
}

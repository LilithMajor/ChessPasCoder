package com;

import java.util.ArrayList;

public class User {
	private String name;
	private String login;
	private String password;
	private String email;
	private int elo;
	private ArrayList<Game> games;
	
	public User(String name, String login, String password, String email, int elo, ArrayList<Game> games) {
		this.name = name;
		this.login = login;
		this.password = password;
		this.email = email;
		this.elo = elo;
		this.setGames(games);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getElo() {
		return elo;
	}
	public void setElo(int elo) {
		this.elo = elo;
	}

	public ArrayList<Game> getGames() {
		return games;
	}

	public void setGames(ArrayList<Game> games) {
		this.games = games;
	}
}

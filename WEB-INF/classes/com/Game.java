package com;

public class Game {
	private int id;
	private int nbMove;
	private String LoginWin;
	private String LoginLoss;
	
	public Game(int id, int nbMove, String LoginWin, String LoginLoss) {
		this.setId(id);
		this.nbMove = nbMove;
		this.LoginWin = LoginWin;
		this.LoginLoss = LoginLoss;
	}
	
	public int getNbMove() {
		return nbMove;
	}
	public void setNbMove(int nbMove) {
		this.nbMove = nbMove;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginWin() {
		return LoginWin;
	}

	public void setLoginWin(String loginWin) {
		LoginWin = loginWin;
	}

	public String getLoginLoss() {
		return LoginLoss;
	}

	public void setLoginLoss(String loginLoss) {
		LoginLoss = loginLoss;
	}
}

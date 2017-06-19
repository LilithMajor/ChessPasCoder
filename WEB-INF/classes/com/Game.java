package com;

public class Game {
	private int id;
	private int nbMove;
	private String loginWin;
	private String loginLoss;
	private int onGoing;
	
	public Game(int id, int nbMove, String LoginWin, String LoginLoss, int onGoing) {
		this.setId(id);
		this.nbMove = nbMove;
		this.loginWin = LoginWin;
		this.loginLoss = LoginLoss;
		this.onGoing = onGoing;
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
		return loginWin;
	}

	public void setLoginWin(String loginWin) {
		this.loginWin = loginWin;
	}

	public String getLoginLoss() {
		return loginLoss;
	}

	public void setLoginLoss(String loginLoss) {
		this.loginLoss = loginLoss;
	}

	public int getOnGoing() {
		return onGoing;
	}

	public void setOnGoing(int onGoing) {
		this.onGoing = onGoing;
	}
}

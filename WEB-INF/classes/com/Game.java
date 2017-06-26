package com;

public class Game {
	private int id;
	private int nbMove;
	private String loginWin;
	private String loginLoss;
	private int nbPlayer;
	private int onGoing;
	private String player1;
	private String player2;

	public Game(int id, int nbMove, String LoginWin, String LoginLoss, int nbPlayer, int onGoing, String player1,
			String player2) {
		this.setId(id);
		this.nbMove = nbMove;
		this.loginWin = LoginWin;
		this.loginLoss = LoginLoss;
		this.nbPlayer = nbPlayer;
		this.onGoing = onGoing;
		this.setPlayer1(player1);
		this.setPlayer2(player2);
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

	public int getNbPlayer() {
		return nbPlayer;
	}

	public void setNbPlayer(int nbPlayer) {
		this.nbPlayer = nbPlayer;
	}

	public void addNbPlayer() {
		nbPlayer++;
	}

	public int getOnGoing() {
		return onGoing;
	}

	public void setOnGoing(int onGoing) {
		this.onGoing = onGoing;
	}

	public String getPlayer1() {
		return player1;
	}

	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	public String getPlayer2() {
		return player2;
	}

	public void setPlayer2(String player2) {
		this.player2 = player2;
	}
}

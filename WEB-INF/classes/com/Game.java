package com;

public class Game {
	private int id;
	private int nbMove;
	private boolean victory;
	
	public Game(int id, int nbMove, boolean result) {
		this.setId(id);
		this.nbMove = nbMove;
		this.victory = result;
	}
	
	public int getNbMove() {
		return nbMove;
	}
	public void setNbMove(int nbMove) {
		this.nbMove = nbMove;
	}
	public boolean isResult() {
		return victory;
	}
	public void setResult(boolean result) {
		this.victory = result;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

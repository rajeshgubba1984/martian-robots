package com.martian.robots.service;

public class Movements {

	public int startX;
	public int startY;
	
	public int up = 0;
	public int down = 0;
	public int left = 0;
	public int right = 0;
	
	public String direction;
	
	public Movements(int x, int y, String direction) {
		this.startX = x;
		this.startY = y;
		this.direction = direction;
	}
	
	public int getXposition() {
		return startX-left+right;
	}
	
	public int getYposition() {
		return startY+up-down;
	}
	
	public String getPositionString() {
		return this.getXposition() + " " + this.getYposition() + " " + direction;
	}
}

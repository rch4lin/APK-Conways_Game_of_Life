package com.robin.conwaygameoflife;

public class Cell {
	private int xCoord, yCoord;
	int left, right, top, bottom;
	private int cellSize;
	private boolean isLive;
	
	/**
	 * @param args
	 */
	public Cell(boolean isLive, int cellSize, int left, int top, int right, int bottom){
		this.cellSize = cellSize;
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.isLive = isLive;
	}
	
	public Cell(int xCoord, int yCoord, boolean isLive, int cellSize, int left, int top, int right, int bottom){
		this.cellSize = cellSize;
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.isLive = isLive;
	}
	
	public int getXCoord() {
		return xCoord;
	}
	
	public int getYCoord() {
		return yCoord;
	}
	
	public int getLeftSide() {
		return left;
	}
	
	public int getRightSide() {
		return right;
	}
	
	public int getTopSide() {
		return top;
	}
	
	public int getBottomSide() {
		return bottom;
	}
	
	public int getCellSize() {
		return cellSize;
	}
	
	public void setAlive(boolean state){
		isLive = state;
	}
	
	/**
	 * true = alive
	 * false = dead
	 * @return
	 */
	public boolean isAlive(){
		return isLive;
	}
	
}


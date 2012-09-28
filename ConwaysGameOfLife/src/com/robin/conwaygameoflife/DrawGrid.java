package com.robin.conwaygameoflife;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.app.Activity;

public class DrawGrid extends View{
	private static Paint p = new Paint();
	private int numOfHorzCells;
	private int numOfVertCells;
	private int cellSize;
	private static Cell[][] cellArray;
	private Canvas canvas;
	
	private ArrayList<Cell> cellList;

	
	public DrawGrid(Context context, int cellSize, int width, int height){
		super(context);
		Log.d(LifeGrid.TAG, "running");
		
		this.cellSize = cellSize;
		numOfHorzCells = width/cellSize; //48
		numOfVertCells = height/cellSize;	//80
		//[y][x]
		cellArray = new Cell[numOfVertCells][numOfHorzCells];
		
		
		cellList = new ArrayList<Cell>();
	}
	
	@Override
	public void onDraw(Canvas canvas){
		if (cellList.size()!=0){
			this.canvas = canvas;
			canvas.drawColor(Color.rgb(68, 144, 212));
			for (Cell c : cellList){
				Rect r = new Rect(c.left, c.top, c.right, c.bottom); 
				canvas.drawRect(r,p);
			}
			return;
		}
		this.canvas = canvas;
		canvas.drawColor(Color.rgb(68, 144, 212));
//		p.setColor(Color.WHITE);
		
		Log.d(LifeGrid.TAG, "Drawing cells");
		int left = 0;
		int top = 0;
		int right = cellSize;
		int bottom = cellSize;
				
		Cell cell;
		for (int y=0;y<numOfVertCells;y++){
			for (int x=0; x<numOfHorzCells ; x++){
				cell = new Cell(false, cellSize, left, top, right, bottom); 

				left+=cellSize;
				right+=cellSize;
				
				//add to list
				cellArray[y][x] = cell;
			}
			left = 0; //resets back
			right = cellSize;
			top += cellSize;
			bottom += cellSize;
		}
		
//		Cell c = cellArray[50][20];
//		Rect r = new Rect(c.left, c.top, c.right, c.bottom); 
//		canvas.drawRect(r,p);
	}
	
	public Cell getCell(int x, int y){
		return cellArray[y][x];
	}
	
	public void setLiveCell(int x, int y) throws InterruptedException{
		
		Cell c = cellArray[y][x];
		c.isLive = true;
		cellArray[y][x] = c;
		p.setColor(Color.BLACK);
		
		Rect r = new Rect(c.left, c.top, c.right, c.bottom);
		canvas.drawRect(r, p);
		invalidate();
		Log.d(LifeGrid.TAG, "refreshing" );
//		requestLayout();
		
	}
	
	public void run(ArrayList<Cell> cL){
		cellList = cL;
		p.setColor(Color.WHITE);
		Log.d(LifeGrid.TAG, "run");
		invalidate();
	}
}

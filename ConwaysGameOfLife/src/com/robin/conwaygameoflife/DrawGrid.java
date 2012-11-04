package com.robin.conwaygameoflife;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

public class DrawGrid extends View{
	private static Paint p = new Paint();
	private final static String TAG = "DrawGrid";
	private ArrayList<Cell> activeCells;
	
	public DrawGrid(Context context, int cellSize, int width, int height, ArrayList<Cell> activeCells){
		super(context);
		Log.d(TAG, "Setting up cell list");
		this.activeCells = activeCells;
	}
	
	
	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas){
		Log.d(TAG, "Drawing canvas");
		if (activeCells.size()!=0){
			canvas.drawColor(Color.rgb(68, 144, 212));
			for (Cell c : activeCells){
				Rect r = new Rect(c.left, c.top, c.right, c.bottom); 
				canvas.drawRect(r,p);
			}
			
			return;
		}else {
			canvas.drawColor(Color.rgb(68, 144, 212));
		}

	}

	
	public void run(ArrayList<Cell> cL){
		activeCells = cL;
		p.setColor(Color.WHITE);
		Log.d(TAG, "Receiving live cell array");
		invalidate();
	}
}

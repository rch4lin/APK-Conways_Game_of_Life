package com.robin.conwaygameoflife;

import java.util.ArrayList;

import com.robin.conwaygameoflife.R;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class LifeGrid extends Activity {
	final static String TAG = "GridLife";
	static Display display;
	private static final int CELLSIZE = 10;
	private static ArrayList<Cell> liveCells, deadCells;
	private static Cell[][] cellGrid;
	private static int width, height;
	public DrawGrid GRID;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_life_grid);
        display = getWindowManager().getDefaultDisplay(); 
        width = display.getWidth();
        height = display.getHeight();
        
        Log.d(TAG, "Size of display is " + width + " px in width, and " + height + " px in height");
        Log.d(TAG, "Setting up canvas...");
        Log.d(TAG, "Cell size is " + CELLSIZE + " px");
        GRID = new DrawGrid(this, CELLSIZE, width, height);
        setContentView(GRID);
        Log.d(TAG, "Creating cell grid");
        setupCellGrid();
        liveCells = new ArrayList<Cell>();
        deadCells = new ArrayList<Cell>();
        
        
//        Button b = (Button)findViewById(R.id.button1);
//        b.setOnClickListener(onSave);
    }
   
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_life_grid, menu);
//    	super.onCreateOptionsMenu(menu);
    	menu.add(R.string.startMenu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Cell c = new Cell(false, 10, 0, 0, 10, 10);
    	Cell c2 = new Cell(false, 10, 20, 0, 10, 10);
    	liveCells.add(c);
    	liveCells.add(c2);
    	
    	GRID.run(liveCells);
    	
    	return true;
    }
    
    private void setupCellGrid() {
    	int numOfHorzCells = width/CELLSIZE; //48
		int numOfVertCells = height/CELLSIZE;	//80
		//[y][x]
		cellGrid = new Cell[numOfVertCells][numOfHorzCells];
		Log.d(LifeGrid.TAG, "Drawing cells");
		int left = 0;
		int top = 0;
		int right = CELLSIZE;
		int bottom = CELLSIZE;
				
		Cell cell;
		for (int y=0;y<numOfVertCells;y++){
			for (int x=0; x<numOfHorzCells ; x++){
				cell = new Cell(false, CELLSIZE, left, top, right, bottom); 

				left+=CELLSIZE;
				right+=CELLSIZE;
				
				//add to grid
				cellGrid[y][x] = cell;
			}
			left = 0; //resets back
			right = CELLSIZE;
			top += CELLSIZE;
			bottom += CELLSIZE;
		}
    }
}
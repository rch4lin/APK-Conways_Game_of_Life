package com.robin.conwaygameoflife;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;

public class LifeGrid extends Activity {
	private final static String TAG = "LifeGrid";
	static Display display;
	private static final int CELLSIZE = 10;
	public static ArrayList<Cell> currentLiveCells;
	public static ArrayList<Cell> futureLiveCells;
	private static Cell[][] cellGrid;
	private static int width, height;
	public DrawGrid GRID;
	private boolean firstRun;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        display = getWindowManager().getDefaultDisplay(); 
        width = display.getWidth();
        height = display.getHeight();
        
        Log.d(TAG, "Size of display is " + width + " px in width, and " + height + " px in height");
        Log.d(TAG, "Setting up canvas...");
        Log.d(TAG, "Cell size is " + CELLSIZE + " px");
        
        currentLiveCells = new ArrayList<Cell>();
        futureLiveCells = new ArrayList<Cell>();
//        int myincome = (test < 300) ? true : false;
        
        firstRun = true;
        if (firstRun) Log.d(TAG, "First run");
        
        GRID = new DrawGrid(this, CELLSIZE, width, height, currentLiveCells);
        setContentView(GRID);
        Log.d(TAG, "Creating cell grid");
        setupCellGrid();

    	createSeed();
    	GRID.run(currentLiveCells);
    }
   
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(R.string.startMenu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if (!firstRun){
    		reset(); //resets grid to reflect cells of future cell
    		migrate(futureLiveCells, currentLiveCells);
    	}
    	firstRun = false;
    	generate();

    	GRID.run(currentLiveCells);
    	return true;
    }
    
    /**
     * Empty 'destination', and then copy all elements from 'source' to 'destination'
     * @param source
     * @param destination
     */
    private void migrate(ArrayList<Cell> source, ArrayList<Cell> destination){
    	destination.clear();
    	for (Cell c : source){
    		destination.add(c);
    	}
    	source.clear();
    }
    
    private void reset() {
    	//resets everything
    	for (Cell c : currentLiveCells){
    		cellGrid[c.getYCoord()][c.getXCoord()].setAlive(false);
    	}
    	
    	for (Cell c: futureLiveCells){
    		cellGrid[c.getYCoord()][c.getXCoord()].setAlive(true);
    	}
    }
    
    /**
     * Creates an X, where the centre of the X is the centre of the grid
     */
    private void createSeed() {
//    	int myincome = (test < 300) ? true : false;

    	Cell centreCell = cellGrid[30][23];
    	centreCell.setAlive(true);

    	Cell leftTopCell = cellGrid[29][23];
    	leftTopCell.setAlive(true);
    	
    	//delete
    	Cell rightTopCell = cellGrid[28][23];
    	rightTopCell.setAlive(true);
    	
    	Cell leftBottomCell = cellGrid[29][22];
    	leftBottomCell.setAlive(true);
    	
    	currentLiveCells.add(leftTopCell);
    	currentLiveCells.add(rightTopCell);
    	currentLiveCells.add(centreCell);
    	currentLiveCells.add(leftBottomCell);
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
				cell = new Cell(x, y, false, CELLSIZE, left, top, right, bottom);
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
    
    private boolean isAlive(Cell cell) {
    	//condition 1
    	//Any live cell with fewer than two live neighbours dies, as if caused by under-population.
    	boolean alive;
    	switch (getNumOfNeighbouringActiveCells(cell)){
    	case 0: //dies, underpopulation 
    		alive = false;
    		break;
    	case 1: //dies underpopulation
    		alive = false;
    		break;
    	case 2: //lives
    		alive = true;
    		break;
    	case 3: 
    		alive = true;
    		break;
    	default: //dies, overpopulation
    		alive = false;
    		break;
    	}
    	return alive;
    }
    
    private void determineReproducedCell(Cell cell){
    	
    	//centreCell = Cell that's centre relative to the current cell
    	//it looks for cells in the grid around the centre cells
    	//top left corner
    	Cell centreCell = cellGrid[cell.getYCoord()-1][cell.getXCoord()-1];
    	if ((getNumOfNeighbouringActiveCells(centreCell))==3){ //plus 1 since the live cell used as a refernce is not counted
    		futureLiveCells.add(centreCell);
    	}
    	
    	//top centre
    	centreCell = cellGrid[cell.getYCoord()-1][cell.getXCoord()];
    	if ((getNumOfNeighbouringActiveCells(centreCell))==3){
    		futureLiveCells.add(centreCell);
    	}
    	
    	//top right
    	centreCell = cellGrid[cell.getYCoord()-1][cell.getXCoord()+1];
    	if ((getNumOfNeighbouringActiveCells(centreCell))==3){
    		futureLiveCells.add(centreCell);
    	}
    	
    	//left
    	centreCell = cellGrid[cell.getYCoord()][cell.getXCoord()-1];
    	int c = getNumOfNeighbouringActiveCells(centreCell);
    	if ((c)==3){
    		futureLiveCells.add(centreCell);
    	}
    	
    	//right
    	centreCell = cellGrid[cell.getYCoord()][cell.getXCoord()+1];
    	if ((getNumOfNeighbouringActiveCells(centreCell))==3){
    		futureLiveCells.add(centreCell);
    	}
    	
    	//left corner bottom
    	centreCell = cellGrid[cell.getYCoord()+1][cell.getXCoord()-1];
    	if ((getNumOfNeighbouringActiveCells(centreCell))==3){
    		futureLiveCells.add(centreCell);
    	}
    	
    	//bottom centre
    	centreCell = cellGrid[cell.getYCoord()+1][cell.getXCoord()];
    	int y = (getNumOfNeighbouringActiveCells(centreCell));
    	if (y==3){
    		futureLiveCells.add(centreCell);
    	}
    	
    	//right corner bottom
    	centreCell = cellGrid[cell.getYCoord()+1][cell.getXCoord()+1];
    	if ((getNumOfNeighbouringActiveCells(centreCell))==3){
    		futureLiveCells.add(centreCell);
    	}

    }
    
    private void generate() {
    	//have each cell set to delete? then do a loop to remove? less memory usage
    	for (Cell c : currentLiveCells){
    		if (isAlive(c)){
    			futureLiveCells.add(c);
    		}
    		determineReproducedCell(c);
    	}
    }
    
    /**
     * Determine neighbouring 8 cells
     * 
     * @param c
     * @return
     */
    private int getNumOfNeighbouringActiveCells(Cell c) { //something wrong here
    	//determine centre coordinate of cell
    	int numOfLiveCells=0; //counts first live cell used as reference to centreCell
    	int yCoord = c.getYCoord();
    	int xCoord = c.getXCoord();
    	
    	//need to check for index out of bounds
    	//top left
    	Cell currentCell;
    	currentCell = cellGrid[yCoord-1][xCoord-1];
    	if (currentCell.isAlive()){
    		numOfLiveCells++;	
    	}
    	
    	//top centre
    	currentCell = cellGrid[yCoord-1][xCoord];
    	if (currentCell.isAlive()){
    		numOfLiveCells++;	
    	}
    	
    	//corner top right
    	currentCell = cellGrid[yCoord-1][xCoord+1];
    	if (currentCell.isAlive()){
    		numOfLiveCells++;	
    	}
    	
    	//left
    	currentCell = cellGrid[yCoord][xCoord-1];
    	if (currentCell.isAlive()){
    		numOfLiveCells++;	
    	}
    	
    	//right
    	currentCell = cellGrid[yCoord][xCoord+1];
    	if (currentCell.isAlive()){
    		numOfLiveCells++;	
    	}
    	
    	//bottom left
    	currentCell = cellGrid[yCoord+1][xCoord-1];
    	if (currentCell.isAlive()){
    		numOfLiveCells++;	
    	}
    	
    	//bottom
    	currentCell = cellGrid[yCoord+1][xCoord];
    	if (currentCell.isAlive()){
    		numOfLiveCells++;	
    	}
    	
    	//bottom right
    	currentCell = cellGrid[yCoord+1][xCoord+1];
    	if (currentCell.isAlive()){
    		numOfLiveCells++;	
    	}
    	return numOfLiveCells;
    }
    
}

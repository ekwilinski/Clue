package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection doorDirection;		// helps us keep track of door direction
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassage;
	private boolean isSecretPassageway;			// tells us if a cell is secret passageway
	private Set<BoardCell> adjList = new HashSet<BoardCell>();		//holds the adjList for cells
	private boolean isDoorway, isOccupied, isUnused;		// bools to help with conditions for adjList	

	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public void addAdj(BoardCell adj) {
		adjList.add(adj);
	}

	public void setUnused() {
		isUnused = true;
	}

	public void setIsDoor(boolean isDoor) {
		isDoorway = isDoor;
	}

	public boolean isDoorway() {
		return isDoorway;
	}

	public void setDoorDirection(DoorDirection direction) {
		doorDirection = direction;
	}

	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return doorDirection;
	}

	public void setIsLabel() {
		roomLabel = true;
	}

	public boolean isLabel() {
		return roomLabel;
	}

	public void setIsRoomCenter() {
		roomCenter = true;
	}

	public boolean isRoomCenter() {
		return roomCenter;
	}

	public boolean isPassageway() {
		return isSecretPassageway;
	}

	public void setSecretPassage(char k) {
		secretPassage = k;
		isSecretPassageway = true;
	}

	public char getSecretPassage() {
		return secretPassage;
	}

	public void setInitial(char initial) {
		this.initial = initial;
	}

	public char getInitial() {
		return initial;
	}

	public void setOccupied(boolean b) {
		isOccupied= b;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public Set<BoardCell> getAdjList() {
		return adjList;
	}

	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}

	public void draw(int boardCellWidth, int boardCellHeight, Graphics g) {
		int xCoord = column * boardCellWidth;
		int yCoord = row * boardCellHeight;


		if(isDoorway()) {
			g.setColor(new Color(255, 253, 208));
			g.fillRect(xCoord, yCoord, boardCellWidth, boardCellHeight);
			if(doorDirection == DoorDirection.DOWN) {
				g.setColor(new Color(205, 133, 63));
				g.fillRect(xCoord, yCoord + (3*boardCellHeight/4) + 1, boardCellWidth, boardCellHeight/4);
			}
			else if(doorDirection == DoorDirection.UP) {
				g.setColor(new Color(205, 133, 63));
				g.fillRect(xCoord, yCoord, boardCellWidth, boardCellHeight/4);
			}
			else if(doorDirection == DoorDirection.LEFT) {
				g.setColor(new Color(205, 133, 63));
				g.fillRect(xCoord, yCoord, boardCellWidth/4 , boardCellHeight);
			}
			else {
				g.setColor(new Color(205, 133, 63));
				g.fillRect(xCoord + (3*boardCellWidth/4) + 1, yCoord, boardCellWidth/4, boardCellHeight);
			}
			
			g.setColor(Color.BLACK);
			g.drawRect(xCoord, yCoord, boardCellWidth, boardCellHeight);
		}
		else if(initial != 'X' && initial != 'H' && !isDoorway()) {
			g.setColor(new Color(137, 207, 240));
			g.fillRect(xCoord, yCoord, boardCellWidth, boardCellHeight);

		}

		else if(initial == 'X') {
			g.setColor(new Color(101, 67, 33));
			g.fillRect(xCoord, yCoord, boardCellWidth, boardCellHeight);
		}
		else {
			g.setColor(new Color(255, 253, 208));
			g.fillRect(xCoord, yCoord, boardCellWidth, boardCellHeight);
			g.setColor(Color.BLACK);
			g.drawRect(xCoord, yCoord, boardCellWidth, boardCellHeight);
		}

	}

	private boolean isUnused() {
		// TODO Auto-generated method stub
		return isUnused;
	}
}

package clueGame;
import java.util.HashSet;
import java.util.Set;

import clueGame.Board;

public class ComputerPlayer extends Player {
	private String name;
	private String color;
	private int startRow, startColumn, position;
	

	public ComputerPlayer(String name, String color, int startRow, int startColumn, int position) {
		this.name = name;
		this.color = color;
		this.startRow = startRow;
		this.startColumn = startColumn;
		this.position = position;
	}

	public String getColor() {
		return color;
	}

	public Object getName() {
		return name;
	}

	public int getRow() {
		return startRow;
	}

	public int getColumn() {
		return startColumn;
	}
	
	public int getPosition() {
		return position;
	}
	
	public BoardCell selectTarget(Set<BoardCell> targetList, BoardCell selectedTarget) {
		BoardCell boardCell = new BoardCell(0, 0);
		return boardCell;
	}
}

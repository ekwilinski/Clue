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
		targetList= new HashSet<BoardCell>();
		//looping through targetList to see if in room && not in seen list
		for(BoardCell cell : targetList) {
			if(cell.equals(selectedTarget) && ((cell.getInitial() != 'H') || cell.getInitial() != 'X')) {
				//if multiple then return random room
				/*
				if() {
					//cant figure out how to check if multiple or in seen list :(
				}
				else {
					return cell;		//return room;
				}
				*/
			}
			else {
				//return random target from targetList
				int rand = (int)(Math.random() * (targetList.size() - 1) + 1);
				int i = 1;
				for(BoardCell rand_cell : targetList) {
					if(i == rand) {
						return rand_cell;
					}
					i++;
				}
			}
		}
		return null;
	}
}

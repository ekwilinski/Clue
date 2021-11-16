package clueGame;

import java.util.Set;

public class HumanPlayer extends Player {


	public HumanPlayer(String name, String color, int startRow, int startColumn, int position) {
		this.name = name;
		this.color = color;
		this.startRow = startRow;
		this.startColumn = startColumn;
		this.position = position;
	}

	public String getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public int getPosition() {
		return position;
	}



}

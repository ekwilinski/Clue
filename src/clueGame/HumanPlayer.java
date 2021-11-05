package clueGame;

public class HumanPlayer extends Player {
	private String name;
	private String color;
	private int startRow, startColumn, position;

	public HumanPlayer(String name, String color, int startRow, int startColumn, int position) {
		this.name = name;
		this.color = color;
		this.startRow = startRow;
		this.startColumn = startColumn;
		this.position = position;
	}

	public Object getColor() {
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
}

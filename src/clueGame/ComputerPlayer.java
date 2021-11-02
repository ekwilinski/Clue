package clueGame;

public class ComputerPlayer extends Player {
	private String name;
	private String color;
	private int startRow, startColumn;

	public ComputerPlayer(String name, String color, int startRow, int startColumn) {
		this.name = name;
		this.color = color;
		this.startRow = startRow;
		this.startColumn = startColumn;
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

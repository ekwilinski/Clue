package clueGame;

public class HumanPlayer extends Player {
	
	private String name;
	private String color;
	private int startRow, startColumn;

	public HumanPlayer(String name, String color, int startRow, int startColumn) {
		this.name = name;
		this.color = color;
		this.startRow = startRow;
		this.startColumn = startColumn;
	}
	
}

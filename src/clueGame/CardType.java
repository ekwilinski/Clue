package clueGame;

public enum CardType {
	
	 ROOM("Room"),
	 PERSON("Person"),
	 WEAPON("Weapon");

	private String card;
	
	private CardType(String card) {
		this.card= card;
	}
	
	public String toString() {
		return card;
	}

}

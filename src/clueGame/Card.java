package clueGame;

public class Card {
	
	private String name;
	private CardType type;
	
	public Card(String name, CardType type) {
		this.name = name;
		this.type = type;
	}
	
	public Boolean equals(Card target) {
		return false;
	}
	
	public CardType getType() {
		return type;
	}
	
	public void setType(CardType type) {
		this.type = type;
	}

}

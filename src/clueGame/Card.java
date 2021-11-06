package clueGame;

public class Card {
	private String name;
	private CardType type;
	private char initial;
	
	public Card(String name, CardType type) {
		this.name = name;
		this.type = type;
		setInitial(name.charAt(0));
	}
	
	public Boolean equals(Card target) {
		if(name.equals(target.getName()) && type == target.getType()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public CardType getType() {
		return type;
	}
	
	public void setType(CardType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setInitial(char initial) {
		this.initial = initial;
	}
	public char getInitial() {
		return initial;
	}
}

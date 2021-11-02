package clueGame;

import java.util.HashSet;
import java.util.Set;

public abstract class Player {

	private String name;
	private String color;
	private int row, column;
	private Set<Card> hand = new HashSet<Card>();
	
	public void updateHand(Card card) {
		hand.add(card);
	}

	public Set<Card> getHand() {
		return hand;
	}
	
}

package clueGame;

import java.util.HashSet;
import java.util.Set;

public abstract class Player {
	private Set<Card> hand = new HashSet<Card>();
	protected Set<Card> seenCards = new HashSet<Card>();
	
	public void updateHand(Card card) {
		hand.add(card);
	}
	
	public void removeCard(Card cardToRemove) {
		for(Card card : hand) {
			if(card.equals(cardToRemove)) {
				hand.remove(card);
				break;
			}
		}
	}

	public Set<Card> getHand() {
		return hand;
	}

	public Card disproveSuggestion(Solution solution) {
		Set<Card> matches = new HashSet<Card>();
		for(Card card : hand) {
			if(card.getType() == CardType.PERSON) {
				if(card.equals(solution.getPlayer())) {
					matches.add(card);
				}
			}
			else if(card.getType() == CardType.ROOM) {
				if(card.equals(solution.getRoom())) {
					matches.add(card);
				}
			}
			else {
				if(card.equals(solution.getWeapon())) {
					matches.add(card);
				}
			}
		}
		if(matches.isEmpty()) {
			return null;
		}
		else if(matches.size()>1) {
			//get weapon
			int rand = (int)(Math.random() * (matches.size() - 1) + 1);
			int i = 1;
			for(Card cardmatch : matches) {
				if(i == rand) {
					return cardmatch;
				}
				i++;
			}
		}
		else {
			for(Card cardmatch : matches) {
				return cardmatch;
			}
		}
		return null;
	}

	public abstract String getColor();
	
	public void updateSeen(Card card) {
		seenCards.add(card);
	}
	
	public Set<Card> getSeenCards() {
		return seenCards;
	}

}

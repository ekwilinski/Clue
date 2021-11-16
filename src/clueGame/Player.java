package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Player {
	protected String name;
	protected String color;
	protected int startRow, startColumn, position;
	protected Set<Card> hand = new HashSet<Card>();
	protected Set<Card> seenCards = new HashSet<Card>();
	private Set<Card> allRooms = new HashSet<Card>();
	protected Set<Card> allWeapons = new HashSet<Card>();
	protected Set<Card> allPlayers = new HashSet<Card>();
	protected Map<Card, String> seenAndColors = new HashMap<Card, String>();
	
	public void updateHand(Card card) {
		hand.add(card);
	}
	
	public void updateSeen(Card card, String color) {
		seenAndColors.put(card, color);
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
	
	public Map<Card, String> getSeenAndColorCards() {
		return seenAndColors;
	}
	
	public void giveCards(Set<Card> roomCards, Set<Card> weaponCards, Set<Card> playerCards) {
		allRooms = roomCards;
		allWeapons = weaponCards;
		allPlayers = playerCards;
		
	}
	
	public void draw(int boardCellWidth, int boardCellHeight, Graphics g) {
		Color drawColor = convertColor(color); 
		g.setColor(drawColor);
		g.fillOval(boardCellWidth * startColumn,boardCellHeight * startRow, boardCellWidth, boardCellHeight);
	}

	public Color convertColor(String color2) {
		Color convertedColor = new Color(0,0,0);
		switch(color.toLowerCase()) {
		case "blue" :
			convertedColor = Color.BLUE;
			break;
		case "tan" :
			convertedColor = new Color(210, 180, 140);
			break;
		case "white":
			convertedColor = Color.WHITE;
			break;
		case "yellow" :
			convertedColor = Color.YELLOW;
			break;
		case "gray" :
			convertedColor = Color.GRAY;
			break;
		case "purple" :
			convertedColor = new Color(148, 0, 211);
		}

		return convertedColor;
	}
	
	public int getRow() {
		return startRow;
	}

	public int getColumn() {
		return startColumn;
	}
	
	public String getName() {
		return name;
	}
}

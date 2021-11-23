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

	public abstract Card disproveSuggestion(Solution solution);

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
	
	public void setLocation(int x, int y) {
		startRow = x;
		startColumn = y;
	}
	
	public int getPosition() {
		return position;
	}

	public abstract BoardCell selectTarget(Set<BoardCell> targets);

	public abstract boolean getMakeAccusation();

	public abstract void makeAccusation();
	
}

package clueGame;
import java.util.HashSet;
import java.util.Set;

import clueGame.Board;

public class ComputerPlayer extends Player {
	private String name;
	private String color;
	private int startRow, startColumn, position;


	public ComputerPlayer(String name, String color, int startRow, int startColumn, int position) {
		this.name = name;
		this.color = color;
		this.startRow = startRow;
		this.startColumn = startColumn;
		this.position = position;
	}

	public String getColor() {
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

	public int getPosition() {
		return position;
	}

	public BoardCell selectTarget(Set<BoardCell> targetList) {
		boolean hasBeenSeen = false;
		Set<BoardCell> randomTargets = new HashSet<BoardCell>();
		for(BoardCell target : targetList) {
			if(target.isRoomCenter()) {
				for(Card card : seenCards) {
					char cardName = card.getInitial();
					char targetName = target.getInitial();
					if(cardName == targetName) {
						hasBeenSeen = true;
					}
				}
				if(!hasBeenSeen) {
					return target;
				}
			}
			else {
				randomTargets.add(target);
			}
		}

		//get random card
		int rand = (int)(Math.random() * (randomTargets.size() - 1) + 1);
		int i = 1;
		for(BoardCell target : randomTargets) {
			if(i == rand) {
				return target;
			}
			i++;
		}
		return null;
	}

	public Solution createSuggestion(Card room) {
		Set<Card> unseenWeapons = new HashSet<Card>();
		Set<Card> unseenPersons = new HashSet<Card>();

		for(Card weapon : allWeapons) {
			if(!seenCards.contains(weapon) && !hand.contains(weapon)) {
				unseenWeapons.add(weapon);
			}
		}
		for(Card person : allPlayers) {
			if(!seenCards.contains(person) && !hand.contains(person)) {
				unseenPersons.add(person);
			}
		}
		
		Card Weapon = null;

		//get random weapon card
		int rand = (int)(Math.random() * (unseenWeapons.size() - 1) + 1);
		int i = 1;
		for(Card weapon : unseenWeapons) {
			if(i == rand) {
				Weapon = weapon;
			}
			i++;
		}
		
		Card Person = null;

		//get random weapon card
		rand = (int)(Math.random() * (unseenPersons.size() - 1) + 1);
		i = 1;
		for(Card person : unseenPersons) {
			if(i == rand) {
				Person = person;
			}
			i++;
		}
		
		Solution suggestion = new Solution(Person, room ,Weapon);
		return suggestion;
	}


}

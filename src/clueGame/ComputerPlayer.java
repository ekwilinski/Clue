package clueGame;
import java.util.HashSet;
import java.util.Set;

import clueGame.Board;

public class ComputerPlayer extends Player {
	
	private Boolean makeAccusation = false;

	public ComputerPlayer(String name, String color, int startRow, int startColumn, int position) {
		this.name = name;
		this.color = color;
		this.startRow = startRow;
		this.startColumn = startColumn;
		this.position  = position;
	}

	public String getColor() {
		return color;
	}

	public String getName() {
		return name;
	}
	
	public void makeAccusation() {
		makeAccusation = true;
	}
	
	public boolean getMakeAccusation() {
		return makeAccusation;
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

	@Override
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


}

package clueGame;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class HumanPlayer extends Player {


	private boolean makeAccusation;

	public HumanPlayer(String name, String color, int startRow, int startColumn, int position) {
		this.name = name;
		this.color = color;
		this.startRow = startRow;
		this.startColumn = startColumn;
		this.position = position;
	}

	public String getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	@Override
	public BoardCell selectTarget(Set<BoardCell> targets) {
		// TODO Auto-generated method stub
		return null;
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
			String[] matchs = new String[matches.size()];
			int j = 0;
			for(Card cardInHand : matches) {
				matchs[j] = cardInHand.getName();
				j++;
			}
			
			JComboBox matchStrings = new JComboBox(matchs);
			
			Object[] options = { "Submit", "Cancel" };
			 JOptionPane.showOptionDialog(null, matchs, null, 
			 JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, 
			 options[0]);
			 
			 int IDX = matchStrings.getSelectedIndex();
			 String name = matchs[IDX];
			 
			 for(Card cardInHand : matches) {
					if(name == cardInHand.getName()) {
						return cardInHand;
					}
				}
		}
		else {
			for(Card cardmatch : matches) {
				return cardmatch;
			}
		}
		return null;
	}

	public void makeAccusation() {
		makeAccusation = true;
	}
	
	@Override
	public boolean getMakeAccusation() {
		// TODO Auto-generated method stub
		return makeAccusation;
	}


}

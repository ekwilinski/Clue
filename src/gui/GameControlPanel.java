package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JOptionPane;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Room;
import clueGame.Solution;
import jdk.jshell.SourceCodeAnalysis.Suggestion;

@SuppressWarnings("serial")
public class GameControlPanel extends JPanel {
	private JPanel guess, guessResult, upperPanel, bottomPanel, turnPanel, rollPanel;
	private JLabel turnLabel, rollLabel;
	private JTextField guessField, turnField, rollField, guessResultField;
	private JButton accusationButton, nextButton;
	private Board board = Board.getInstance();
	private Boolean isFinished = true;
	private Solution noDispute;
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		setLayout(new GridLayout(2,1));
		createUpperPanel();
		createBottomPanel();
	}
	
	public void createUpperPanel() {
		upperPanel = new JPanel();
		upperPanel.setLayout(new GridLayout(1, 4));
		
		turnPanel = new JPanel();
		turnLabel = new JLabel("Whose Turn?");
		turnPanel.add(turnLabel, BorderLayout.NORTH);
		
		turnField = new JTextField(18);
		turnField.setText(board.getCurrentPlayer().getName());
		turnField.setBackground(convertColor(board.getCurrentPlayer().getColor()));
		turnPanel.add(turnField, BorderLayout.CENTER);
		upperPanel.add(turnPanel);
		
		rollPanel = new JPanel();
		rollLabel = new JLabel("Roll:");
		rollPanel.add(rollLabel, BorderLayout.CENTER);
		rollField = new JTextField(6);
		rollPanel.add(rollField, BorderLayout.CENTER);
		upperPanel.add(rollPanel);
		
		int firstRoll = rollDice();
		rollField.setText(String.valueOf(firstRoll));
		board.calcTargets(board.getCell(board.getCurrentPlayer().getRow(), board.getCurrentPlayer().getColumn()), firstRoll);
		repaint();
		
		accusationButton = new JButton("Make Accusation");
		accusationButtonListener jim = new accusationButtonListener();
		accusationButton.addActionListener(jim);
		upperPanel.add(accusationButton);
		
		nextButton = new JButton("NEXT!");
		nextButtonListener bob = new nextButtonListener();
		nextButton.addActionListener(bob);
		upperPanel.add(nextButton);
		
		add(upperPanel);
	}
	
	public void createBottomPanel() {
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 2));
		
		guess = new JPanel();
		guess.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		guessField = new JTextField(36);
		guess.add(guessField, BorderLayout.CENTER);
		bottomPanel.add(guess, BorderLayout.SOUTH);
		
		guessResult = new JPanel();
		guessResult.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		guessResultField = new JTextField(36);
		guessResult.add(guessResultField, BorderLayout.CENTER);
		bottomPanel.add(guessResult, BorderLayout.SOUTH);
	
		
		add(bottomPanel);
	}
	
	public void setGuessResult(String guessResult) {
		guessResultField.setText(guessResult);
	}

	public void setGuess(String guess) {
		guessField.setText(guess);
	}

	private void setTurn(Player computerPlayer, int i) {
		turnField.setText(computerPlayer.getName());
		turnField.setBackground(convertColor(computerPlayer.getColor()));
		rollField.setText((String.valueOf(i)));
	}
	
	private Color convertColor(String color) {
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
	
	private class accusationButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(!board.getPlayerFinished()) {
				board.getCurrentPlayer().makeAccusation();
				doHumanAccusation();
			}
		}
	}
	
	private class nextButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(board.getPlayerFinished()) {
				setGuess("");
				setGuessResult("");
				
				board.updateCurrentPlayer();
				int roll = rollDice();
				setTurn(board.getCurrentPlayer(), roll);
				board.calcTargets(board.getCell(board.getCurrentPlayer().getRow(), board.getCurrentPlayer().getColumn()), roll);
				updatePanel(roll, board.getCurrentPlayer().getName());
				if(board.getCurrentPlayer() instanceof HumanPlayer) {
					displayTargets();
					board.setPlayerNotFinished();
				} 
				else {
					Boolean skip = doAccusation();
					if(!skip) {
						BoardCell removeOccupied = board.getCell(board.getCurrentPlayer().getRow(), board.getCurrentPlayer().getColumn());
						removeOccupied.setOccupied(false);
						BoardCell moveto = board.getCurrentPlayer().selectTarget(board.getTargets());
						moveto.setOccupied(true);
						board.movePlayer(moveto.getRow(), moveto.getColumn());
						board.repaint();
						doSuggestion();
					}
				}
			}
			else {
				JFrame error = new JFrame();
				JOptionPane.showMessageDialog(error, "Your turn is not over yet!", "oopsies...", JOptionPane.WARNING_MESSAGE);
			}
		}

	}
	
	private void displayTargets() {
		board.repaint();
	}

	private int rollDice() {
		// TODO Auto-generated method stub
		Random roll = new Random();
		return roll.nextInt(6)+1;
	}
	
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		panel.setTurn(new ComputerPlayer( "Col. Mustard", "yellow", 0, 0, 5), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}

	public void updatePanel(int roll, String name) {
		rollField.setText(String.valueOf(roll)); 
		turnField.setText(name);
	}
	
	private void doHumanAccusation() {
		String[] roomCardList = new String[9];
		String[] weaponCardList = new String[6];
		String[] personCardList = new String[6];
		
		int i = 0;
		int j = 0;
		int k = 0;
		for(Card cardInHand : board.getAllCards()) {
			if(cardInHand.getType() == CardType.WEAPON) {
				weaponCardList[i] = cardInHand.getName();
				i++;
			}
			else if(cardInHand.getType() == CardType.PERSON) {
				personCardList[j] = cardInHand.getName();
				j++;
			}
			else {
				roomCardList[k] = cardInHand.getName();
				k++;
			}
		}
		
		JComboBox roomCards = new JComboBox(roomCardList);
		JComboBox weaponCards = new JComboBox(weaponCardList);
		JComboBox personCards = new JComboBox(personCardList);
		
		JPanel suggestions = new JPanel();
		suggestions.add(roomCards, BorderLayout.NORTH);
		suggestions.add(weaponCards, BorderLayout.CENTER);
		suggestions.add(personCards, BorderLayout.SOUTH);
		
		Object[] options = { "Submit", "Cancel" };
		 JOptionPane.showOptionDialog(null, suggestions, null, 
		 JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, 
		 options[0]);
		 
		 int roomIDX = roomCards.getSelectedIndex();
		 String roomName = roomCardList[roomIDX];
		 int weaponIDX = weaponCards.getSelectedIndex();
		 String weaponName = weaponCardList[weaponIDX];
		 int personIDX = personCards.getSelectedIndex();
		 String personName = personCardList[personIDX];
		 
		 Card room = null;
		 Card weapon = null;
		 Card person = null;
		 
		 for(Card cards : board.getAllCards()) {
			 if(weaponName == cards.getName()) {
				 weapon = cards;
			 }
			 else if(personName == cards.getName()) {
				 person = cards;
			 }
			 else if(roomName == cards.getName()){
				 room = cards;
			 }
		 }
		 
		 Solution sol = new Solution(person, room, weapon);
		 if(board.checkAccusation(sol)) {
			JOptionPane.showMessageDialog(this, "You won! Good game.");
			System.exit(0);
		 }
		 else {
			JOptionPane.showMessageDialog(this, "You lost... Game over.");
			System.exit(0);
		 }
	}
	
	private boolean doAccusation() {
		if(board.getCurrentPlayer().getMakeAccusation()) {
			Boolean win = board.checkAccusation(noDispute);
			String name = board.getCurrentPlayer().getName();
			if(win) {
				JOptionPane.showMessageDialog(this, name + " won! Game over.");
				System.exit(0);
			}
			else {
				board.removeCurrentPlayer();
				JOptionPane.showMessageDialog(this, name + " made the wrong accusation! They're out.");
				return true;
			}
		}
		return false;
	}
	
	private void doSuggestion() {
		if(board.getCell(board.getCurrentPlayer().getRow(), board.getCurrentPlayer().getColumn()).isRoomCenter()) {
			Card currentRoom = board.getCard(board.getCurrentRoom().getName());
			Solution s = ((ComputerPlayer) board.getCurrentPlayer()).createSuggestion(currentRoom);
			setGuess(s.getPlayer().getName() + ", " + s.getWeapon().getName() + ", " + s.getRoom().getName());
			Card disproval = board.handleSuggestion(board.getCurrentPlayer(), s);
			if(disproval == null) {
				setGuessResult("no one disproved the suggestion!");
				noDispute = s;
				((ComputerPlayer) board.getCurrentPlayer()).makeAccusation();
			}
			else {
				board.getCurrentPlayer().updateSeen(disproval);
				setGuessResult("someone disproved the result!");
			}
			
		}
		
	}
}

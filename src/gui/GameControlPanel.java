package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.ComputerPlayer;

@SuppressWarnings("serial")
public class GameControlPanel extends JPanel {
	private JPanel guess, guessResult, upperPanel, bottomPanel, turnPanel, rollPanel;
	private JLabel turnLabel, rollLabel;
	private JTextField guessField, turnField, rollField, guessResultField;
	private JButton accusationButton, nextButton;
	
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
		turnPanel.add(turnField, BorderLayout.CENTER);
		upperPanel.add(turnPanel);
		
		rollPanel = new JPanel();
		rollLabel = new JLabel("Roll:");
		rollPanel.add(rollLabel, BorderLayout.CENTER);
		rollField = new JTextField(6);
		rollPanel.add(rollField, BorderLayout.CENTER);
		upperPanel.add(rollPanel);
		
		accusationButton = new JButton("Make Accusation");
		upperPanel.add(accusationButton);
		nextButton = new JButton("NEXT!");
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
	
	private void setGuessResult(String guessResult) {
		guessResultField.setText(guessResult);
	}

	private void setGuess(String guess) {
		guessField.setText(guess);
	}

	private void setTurn(ComputerPlayer computerPlayer, int i) {
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
}

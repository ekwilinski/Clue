package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.ComputerPlayer;

@SuppressWarnings("serial")
public class GameControlPanel extends JPanel {
	private String guessText = "default guess";
	private JPanel guess, guessResult, upperPanel, bottomPanel, turnPanel, rollPanel;
	private JLabel turnLabel;
	private JTextField turnField;
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
		turnPanel.setLayout(new GridLayout(2, 1));
		turnLabel = new JLabel("Whose Turn?");
		turnPanel.add(turnLabel);
		turnField = new JTextField(16);
		turnPanel.add(turnField);
		
		
	}
	
	public void createBottomPanel() {
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 2));
		
		guess = new JPanel();
		guess.setBorder(new TitledBorder ( new EtchedBorder(), "Guess"));
		JTextField guessText = new JTextField(36);
		guess.add(guessText, BorderLayout.CENTER);
		bottomPanel.add(guess, BorderLayout.SOUTH);
		
		guessResult = new JPanel();
		guessResult.setBorder(new TitledBorder ( new EtchedBorder(), "Guess Result"));
		JTextField guessResultText = new JTextField(36);
		guessResult.add(guessResultText, BorderLayout.CENTER);
		bottomPanel.add(guessResult, BorderLayout.SOUTH);
		
		add(bottomPanel);
	}


	private void setGuessResult(String string) {
		// TODO Auto-generated method stub
		
	}


	private void setGuess(String string) {
		// TODO Auto-generated method stub
		
	}


	private void setTurn(ComputerPlayer computerPlayer, int i) {
		// TODO Auto-generated method stub
		
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
		panel.setTurn(new ComputerPlayer( "Col. Mustard", "orange", 0, 0, 5), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}
	
	
}

package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import clueGame.Board;

@SuppressWarnings("serial")
public class ClueGame extends JFrame {
	private JPanel gamePanel;
	private Board board;

	ClueGame() {
		createLayout();
	}

	public void createLayout() {

		gamePanel = new JPanel();
		
		board = Board.getInstance();
		gamePanel.add(board, BorderLayout.CENTER);
		
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");	
		board.initialize();
		
		GameControlPanel controlPanel = new GameControlPanel();
		//gamePanel.add(controlPanel, BorderLayout.SOUTH);
		
		CardPanel cardPanel = new CardPanel();
		//gamePanel.add(cardPanel, BorderLayout.EAST);
		setContentPane(gamePanel);
	}
	
	public static void main(String[] args) {
		ClueGame clueGame = new ClueGame();
		clueGame.setSize(600, 600);
		clueGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clueGame.setVisible(true);
	}

}

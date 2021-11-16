package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clueGame.Board;
import clueGame.HumanPlayer;

@SuppressWarnings("serial")
public class ClueGame extends JFrame {
	private JPanel gamePanel;
	private Board board;

	ClueGame() {
		board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");	
		board.initialize();
		
		JOptionPane.showMessageDialog(this, "You are " + board.getHumanPlayer().getName() + ".\nCan you find the solution\nbefore the computer players?", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		
		createLayout();
	}

	public void createLayout() {
		
		add(board, BorderLayout.CENTER);
		
		GameControlPanel controlPanel = new GameControlPanel();
		add(controlPanel, BorderLayout.SOUTH);
		
		CardPanel cardPanel = new CardPanel();
		add(cardPanel, BorderLayout.EAST);
		cardPanel.setPreferredSize(new Dimension(140,500));
	}
	
	
	public static void main(String[] args) {
		ClueGame clueGame = new ClueGame();
		clueGame.setSize(600, 600);
		clueGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		clueGame.setVisible(true);
	}
}

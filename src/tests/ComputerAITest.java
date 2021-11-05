package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;
class ComputerAITest {

	private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}
	
	@Test
	void testCreateSuggestion() {
		
	}
	
	@Test
	void testSelectTargets() {
		//cp select target. is in room. room not in seenCards. select room or random selection
		ComputerPlayer computerPlayer4 = new ComputerPlayer("Computer 4", "green", 1, 1, 3);
		assertEquals(computerPlayer4.selectTarget(), board.getTargets());
		assertFalse(computerPlayer4.selectTarget(), board.getTargets());
	}
}

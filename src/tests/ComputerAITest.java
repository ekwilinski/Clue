package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
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
	
	/*@Test
	void testCreateSuggestion() {
		
	}
	*/
	@Test
	void testSelectTargets() {
		
		//targets contains room - room not in seen list - select room
		ComputerPlayer computerPlayer4 = new ComputerPlayer("Computer 4", "green", 2, 2, 3);
		Set<BoardCell> targets = new HashSet<BoardCell>();
		targets.add(new BoardCell(1,1));
		targets.add(new BoardCell(1,2));
		targets.add(new BoardCell(2,2));
		BoardCell room = new BoardCell(2,3);
		room.setInitial('k');
		room.setIsRoomCenter();
		targets.add(room);
		assertEquals(room, computerPlayer4.selectTarget(targets));
		
		//targets contains room - target in seen list - randomly select
		computerPlayer4.updateSeen(new Card("kitchen", CardType.ROOM));
		assertTrue(!room.equals(computerPlayer4.selectTarget(targets)));
		assertTrue(targets.contains(computerPlayer4.selectTarget(targets)));
		
		//no rooms in targets - randomly select
		targets.remove(room);
		assertTrue(targets.contains(computerPlayer4.selectTarget(targets)));
		
	}
}

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

class gameSetupTests {

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
	public void testRoomCards() {
		Set<Card> roomCards = board.getRoomCards();
		assertEquals(9, roomCards.size());
		Card kitchen = new Card("Kitchen", CardType.ROOM);
		assertTrue(kitchen.equals(board.getCard("Kitchen")));
		
		Card cubicle = new Card("Cubicle", CardType.ROOM);
		assertTrue(cubicle.equals(board.getCard("Cubicle")));
		
		Card manager = new Card("Regional Manager's Office", CardType.ROOM);
		assertTrue(manager.equals(board.getCard("Regional Manager's Office")));
	}
	
	@Test
	public void testPlayerCards() {
		Set<Card> playerCards = board.getPlayerCards();
		assertEquals(6, playerCards.size());
		
		Card jim = new Card("Jim Halpert", CardType.PERSON);
		assertTrue(jim.equals(board.getCard("Jim Halpert")));
		
		Card dwight = new Card("Dwight Schrute", CardType.PERSON);
		assertTrue(dwight.equals(board.getCard("Dwight Schrute")));
		
		Card angela = new Card("Angela Schrute", CardType.PERSON);
		assertTrue(angela.equals(board.getCard("Angela Schrute")));
	}
	
	@Test
	public void testWeaponCards() {
		Set<Card> weaponCards = board.getWeaponCards();
		assertEquals(6, weaponCards.size());
		
		Card pepper = new Card("Pepper Spray", CardType.WEAPON);
		assertTrue(pepper.equals(board.getCard("Pepper Spray")));
		Card brass = new Card("Brass Knuckles", CardType.WEAPON);
		assertTrue(brass.equals(board.getCard("Brass Knuckles")));
		Card revolver = new Card("Revolver", CardType.WEAPON);
		assertTrue(revolver.equals(board.getCard("Revolver")));
	}
	
	@Test
	public void testHumanPlayer() {
		HumanPlayer jim = board.getHumanPlayer();
		assertTrue(jim.getName().equals("Jim Halpert"));
		assertTrue(jim.getColor().equals("blue"));
		assertEquals(9, jim.getRow());
		assertEquals(6, jim.getColumn());
	}
	
	@Test
	public void testComputerPlayer() {
		Set<ComputerPlayer> computerPlayers = board.getComputerPlayers();
		assertEquals(5, computerPlayers.size());
		
		ComputerPlayer michael = board.getComputerPlayer("Michael Scott");
		assertTrue(michael.getName().equals("Michael Scott"));
		assertTrue(michael.getColor().equals("grey"));
		assertEquals(19, michael.getRow());
		assertEquals(8, michael.getColumn());
		
		ComputerPlayer pam = board.getComputerPlayer("Pam Beesly");
		assertTrue(pam.getName().equals("Pam Beesly"));
		assertTrue(pam.getColor().equals("purple"));
		assertEquals(4, pam.getRow());
		assertEquals(8, pam.getColumn());
		
		ComputerPlayer angela = board.getComputerPlayer("Angela Schrute");
		assertTrue(angela.getName().equals("Angela Schrute"));
		assertTrue(angela.getColor().equals("white"));
		assertEquals(12, angela.getRow());
		assertEquals(3, angela.getColumn());
	}
	
	@Test
	public void testSolution() {
		
		Set<Card> solutionCards = board.getSolution();
		assertEquals(3, solutionCards.size());
		
		int numWeapons = 0;
		int numRooms = 0;
		int numPlayers = 0;
		
		for(Card card : solutionCards) {
			if(card.getType() == CardType.PERSON) {
				numPlayers++;
			}
			if(card.getType() == CardType.WEAPON) {
				numWeapons++;
			}
			if(card.getType() == CardType.ROOM) {
				numRooms++;
			}
		}
		assertEquals(1, numWeapons);
		assertEquals(1, numRooms);
		assertEquals(1, numPlayers);
	}
	
	@Test
	public void testHands() {
		for(ComputerPlayer player : board.getComputerPlayers()) {
			assertEquals(3, player.getHand().size());
		}
		assertEquals(3, board.getHumanPlayer().getHand().size());
	}
}

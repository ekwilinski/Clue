package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Solution;

class GameSolutionTest {
	
	private static Board board;
	private static Card jim, bob, steve, aretha, elena, elliot, sword, knife, ak47, glock19, cal50bmg, thumbs, culina, kitchen, bathroom, dungeon, library, dining, bedroom, poolhouse, traphouse; 

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		
		//create cards to test
		//people
		jim = new Card("Jim", CardType.PERSON);
		bob = new Card("Bob", CardType.PERSON);
		steve = new Card("Steve", CardType.PERSON);
		aretha = new Card("Aretha", CardType.PERSON);
		elena = new Card("Elena", CardType.PERSON);
		elliot = new Card("Elliot", CardType.PERSON);
		//weapons
		sword = new Card("Sword", CardType.WEAPON);
		knife = new Card("Knife", CardType.WEAPON);
		ak47 = new Card("AK47", CardType.WEAPON);
		cal50bmg = new Card("50 BMG", CardType.WEAPON);
		glock19 = new Card("Glock 19", CardType.WEAPON);
		thumbs = new Card("Thumbs", CardType.WEAPON);
		//rooms
		culina = new Card("Culina", CardType.ROOM);
		kitchen = new Card("Kitchen", CardType.ROOM);
		bathroom = new Card("Bathroom", CardType.ROOM);
		dungeon = new Card("Dungeon", CardType.ROOM);
		library = new Card("Library", CardType.ROOM);
		dining = new Card("Dining", CardType.ROOM);
		bedroom = new Card("Bedroom", CardType.ROOM);
		poolhouse = new Card("Poolhouse", CardType.ROOM);
		traphouse = new Card("Traphouse", CardType.ROOM);
	}
	

	@Test
	void testCheckAccusation() {
		board.setSolution(jim, kitchen, glock19);
		Solution testAnswer = new Solution();
		testAnswer.addPlayer(jim);
		testAnswer.addRoom(kitchen);
		testAnswer.addWeapon(glock19);
		assertTrue(board.checkAccusation(testAnswer));
		//checking the 3 false tests to make sure
		testAnswer.addWeapon(ak47);
		assertFalse(board.checkAccusation(testAnswer));
		
		testAnswer.addWeapon(glock19);
		testAnswer.addRoom(bedroom);
		assertFalse(board.checkAccusation(testAnswer));
		
		testAnswer.addRoom(kitchen);
		testAnswer.addPlayer(elena);
		assertFalse(board.checkAccusation(testAnswer));
	}

}

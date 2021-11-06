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
	private static Card jim, bob, steve, aretha, elena, elliot, sword, knife, ak47, glock19, cal50bmg, thumbs, culina, kitchen, bathroom, dungeon,
	library, dining, bedroom, poolhouse, traphouse; 

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
	void testCreateSuggestion() {

		ComputerPlayer computerPlayer = new ComputerPlayer("Computer", "green", 2, 2, 3);
		//give player hand
		computerPlayer.updateHand(bathroom);
		computerPlayer.updateHand(knife);
		computerPlayer.updateHand(jim);
		//give player seen cards
		computerPlayer.updateSeen(kitchen);
		computerPlayer.updateSeen(ak47);
		computerPlayer.updateSeen(aretha);

		//set and test player location card
		board.addToRoomCards(bedroom);
		board.addToRoomMap('b', bedroom, 2, 2);
		Solution suggestion = computerPlayer.createSuggestion(board.getCard(bedroom.getName()));
		Card computerRoom = board.getCard(bedroom.getName());
		assertEquals(suggestion.getRoom(), computerRoom);

		Set<Card> weaponCards = new HashSet<Card>();
		weaponCards.add(knife);
		weaponCards.add(ak47);
		weaponCards.add(sword);
		Set<Card> roomCards = new HashSet<Card>();
		Set<Card> playerCards = new HashSet<Card>();
		playerCards.add(jim);
		playerCards.add(aretha);
		playerCards.add(elliot);
		computerPlayer.giveCards(roomCards, weaponCards, playerCards);
		suggestion = computerPlayer.createSuggestion(board.getCard(bedroom.getName()));
		
		//test unseen weapon is selected
		assertEquals(suggestion.getWeapon(), sword);

		//test unseen person is selected
		assertEquals(suggestion.getPlayer(), elliot);
	}


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
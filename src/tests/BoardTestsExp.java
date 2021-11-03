package tests;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

class BoardTestsExp {
	TestBoard board;
	
	@BeforeEach
	void boardSetup() {
		board = new TestBoard();
	}

	@Test
	void testCornersAndEdges() {
		TestBoardCell cellTopLeft = board.getCell(0, 0);
		Set<TestBoardCell> testList = cellTopLeft.getAdjList();
		assertTrue(testList.contains(board.getCell(1,0)));
		assertTrue(testList.contains(board.getCell(0,1)));
		assertEquals(2, testList.size());
		
		TestBoardCell cellBottomRight = board.getCell(3, 3);
		Set<TestBoardCell> testList2 = cellBottomRight.getAdjList();
		assertTrue(testList2.contains(board.getCell(3,2)));
		assertTrue(testList2.contains(board.getCell(2,3)));
		assertEquals(2, testList2.size());
		
		TestBoardCell cellRightEdge = board.getCell(1, 3);
		Set<TestBoardCell> testList3 = cellRightEdge.getAdjList();
		assertTrue(testList3.contains(board.getCell(0,3)));
		assertTrue(testList3.contains(board.getCell(1,2)));
		assertTrue(testList3.contains(board.getCell(2,3)));
		assertEquals(3, testList3.size());
		
		TestBoardCell cellLeftEdge = board.getCell(3,0);
		Set<TestBoardCell> testList4 = cellLeftEdge.getAdjList();
		assertTrue(testList4.contains(board.getCell(2,0)));
		assertTrue(testList4.contains(board.getCell(3,1)));
		assertEquals(2, testList4.size());
		
		TestBoardCell cellMiddle = board.getCell(2, 2);
		Set<TestBoardCell> testList5 = cellMiddle.getAdjList();
		assertTrue(testList5.contains(board.getCell(2,1)));
		assertTrue(testList5.contains(board.getCell(2,3)));
		assertTrue(testList5.contains(board.getCell(1,2)));
		assertTrue(testList5.contains(board.getCell(3,2)));
		assertEquals(4, testList5.size());
	}
	
	@Test
	void testEmptyTarget() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(2,0)));
		assertTrue(targets.contains(board.getCell(1,1)));
		assertTrue(targets.contains(board.getCell(0,2)));
		assertEquals(3, targets.size());
	}
	
	@Test
	void testOccupiedTarget() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		board.getCell(2, 0).setOccupied(true);
		Set<TestBoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(1,1)));
		assertTrue(targets.contains(board.getCell(0,2)));
		assertEquals(3, targets.size());
	}
	
	@Test
	void testRoomTarget() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		board.getCell(2, 0).setRoom(true);
		Set<TestBoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(1,1)));
		assertTrue(targets.contains(board.getCell(0,2)));
		assertEquals(3, targets.size());
	}
	
	@Test
	void testRoomOccupied() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		board.getCell(2, 0).setRoom(true);
		board.getCell(1, 1).setOccupied(true);
		Set<TestBoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(1,1)));
		assertTrue(targets.contains(board.getCell(0,2)));
		assertEquals(3, targets.size());
	}
	
	@Test
	void testRollSix() {
		TestBoardCell cell = board.getCell(3, 3);
		board.calcTargets(cell, 6);
		Set<TestBoardCell> targets = board.getTargets();
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertEquals(7, targets.size());
	}

}

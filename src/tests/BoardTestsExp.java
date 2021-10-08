package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
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
		Assert.assertTrue(testList.contains(board.getCell(1,0)));
		Assert.assertTrue(testList.contains(board.getCell(0,1)));
		Assert.assertEquals(2, testList.size());
		
		TestBoardCell cellBottomRight = board.getCell(3, 3);
		Set<TestBoardCell> testList2 = cellBottomRight.getAdjList();
		Assert.assertTrue(testList2.contains(board.getCell(3,2)));
		Assert.assertTrue(testList2.contains(board.getCell(2,3)));
		Assert.assertEquals(2, testList2.size());
		
		TestBoardCell cellRightEdge = board.getCell(1, 3);
		Set<TestBoardCell> testList3 = cellRightEdge.getAdjList();
		Assert.assertTrue(testList3.contains(board.getCell(0,3)));
		Assert.assertTrue(testList3.contains(board.getCell(1,2)));
		Assert.assertTrue(testList3.contains(board.getCell(2,3)));
		Assert.assertEquals(3, testList3.size());
		
		TestBoardCell cellLeftEdge = board.getCell(3,0);
		Set<TestBoardCell> testList4 = cellLeftEdge.getAdjList();
		Assert.assertTrue(testList4.contains(board.getCell(2,0)));
		Assert.assertTrue(testList4.contains(board.getCell(3,1)));
		Assert.assertEquals(2, testList4.size());
		
		TestBoardCell cellMiddle = board.getCell(2, 2);
		Set<TestBoardCell> testList5 = cellMiddle.getAdjList();
		Assert.assertTrue(testList5.contains(board.getCell(2,1)));
		Assert.assertTrue(testList5.contains(board.getCell(2,3)));
		Assert.assertTrue(testList5.contains(board.getCell(1,2)));
		Assert.assertTrue(testList5.contains(board.getCell(3,2)));
		Assert.assertEquals(4, testList5.size());
	}
	
	@Test
	void testEmptyTarget() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getCell(2,0)));
		Assert.assertTrue(targets.contains(board.getCell(1,1)));
		Assert.assertTrue(targets.contains(board.getCell(0,2)));
		Assert.assertEquals(3, targets.size());
	}
	
	@Test
	void testOccupiedTarget() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		board.getCell(2, 0).setOccupied(true);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1,1)));
		Assert.assertTrue(targets.contains(board.getCell(0,2)));
		Assert.assertEquals(3, targets.size());
	}
	
	@Test
	void testRoomTarget() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		board.getCell(2, 0).setRoom(true);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1,1)));
		Assert.assertTrue(targets.contains(board.getCell(0,2)));
		Assert.assertEquals(3, targets.size());
	}
	
	@Test
	void testRoomOccupied() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 2);
		board.getCell(2, 0).setRoom(true);
		board.getCell(1, 1).setOccupied(true);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1,1)));
		Assert.assertTrue(targets.contains(board.getCell(0,2)));
		Assert.assertEquals(3, targets.size());
	}
	
	@Test
	void testRollSix() {
		TestBoardCell cell = board.getCell(3, 3);
		board.calcTargets(cell, 6);
		Set<TestBoardCell> targets = board.getTargets();
		Assert.assertTrue(targets.contains(board.getCell(0, 0)));
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
		Assert.assertEquals(7, targets.size());
		
		
		
	}

}

package hangman;

import static org.junit.Assert.*;

import org.junit.Test;

import hangman.GuessManagerContract.GuessResponse;

public class GuessManagerTest {

	@Test
	public void testGetBadGuessesLeft() {
		GuessManager gm = new GuessManager("play", 6);
		assertEquals(6, gm.getBadGuessesLeft());
	}

	@Test
	public void testGetCurrentHint() {
		
		GuessManager gm = new GuessManager("play", 6);
		assertEquals("____", gm.getCurrentHint());
	}
	
	@Test
	public void testGoodGuess() {
		
		GuessManager gm = new GuessManager("play", 6);
		gm.getGuessResponse('a');
		char[] soFar = {'_', '_', 'a', '_'};
		assertArrayEquals(soFar, gm.wordA);
	}
	
	@Test
	public void testBadGuess() {
		
		GuessManager gm = new GuessManager("play", 6);
		gm.getGuessResponse('b');
		char[] soFar = {'_', '_', '_', '_'};
		assertArrayEquals(soFar, gm.wordA);
	}
	
	@Test
	public void testLoseGuess() {
		
		GuessManager gm = new GuessManager("play", 3);
		gm.badGuesses = 0;
		assertEquals(GuessResponse.GUESS_LOSE, gm.getGuessResponse('b'));
	}
	
	@Test
	public void testWinGuess() {
		
		GuessManager gm = new GuessManager("play", 3);
		gm.wordA[0] = 'p';
		gm.wordA[1] = 'l';
		gm.wordA[2] = 'a';

		assertEquals(GuessResponse.GUESS_WIN, gm.getGuessResponse('y'));

	}
}

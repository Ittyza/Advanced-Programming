package hangman;

public class GuessManager implements GuessManagerContract{

	String word;
	char[] wordA;
	int badGuesses;
	
	/**
	 *  Constructor for the Guess Manager
	 *  
	 * @param w - the word being guessed
	 * @param bg - amount of bad guesses allocated
	 */
	public GuessManager (String w, int bg){
		word = w;
		badGuesses = bg;
		
		// This is an array of characters coinciding with the hint
		// We will initiate the array as a word with no matching characters
		wordA = new char[w.length()];
		for (int i = 0; i < wordA.length; i++) {
			wordA[i] = NON_MATCH;
		}
	}
	
	@Override
	public int getBadGuessesLeft() {
		
		int bgLeft = badGuesses;
		return bgLeft;
	}
	
	@Override
	public String getCurrentHint() {
		
		// Returns the characters saved in the array as a string
		String soFar = new String (wordA);
		return soFar;
	}

	@Override
	public GuessResponse getGuessResponse(char letter) {
		
		boolean guessVal = false;
		
		// Checks to see if the letter exists in the word and if it does, 
		// adds the letter to the hint 
		for (int i = 0; i < wordA.length; i++) {
			if (letter == word.charAt(i)){
				guessVal = true;
				wordA[i] = letter;	
			
			}
		}
		
		// If the letter was in the word, we check if its a winning guess
		if (guessVal) {
			String soFar = new String (wordA);
			
			if (soFar.equals(word)){
				return GuessResponse.GUESS_WIN;
			}
			return GuessResponse.GUESS_GOOD;
			
		} else {
			badGuesses--;
			
			// If the letter wasn't in the word, we check if its a losing guess
			if (badGuesses < 0) {
				return GuessResponse.GUESS_LOSE;
			}
			return GuessResponse.GUESS_BAD;
		}
	}
}

package hangman;

public interface GuessManagerContractCaseInsensitive extends GuessManagerContract {
	/**
	 * This method should work identically to {@link #getGuessResponse(char)}, except that the 
	 * letter is considered found even if the case doesn't match (i.e., if the word contains 'A' but
	 * the input letter was 'a'). Note that the hint returned by the {@link #getCurrentHint()} method 
	 * should reflect the original word case (regardless of the guess's case).

	 * @param letter the letter that was guessed.
	 * @return the appropriate {@link GuessResponse}
	 */
	public GuessResponse getGuessResponseCaseInsensitive(char letter);
}

package hangman;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import junit.framework.JUnit4TestAdapter;

@RunWith(Suite.class)
@Suite.SuiteClasses ({ GuessManagerTest.class, AsciiPictureTest.class})

public class TestSuite {

	public static junit.framework.Test suite(){
	
		return new JUnit4TestAdapter (TestSuite.class);
	}

}

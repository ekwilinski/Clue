package clueGame;

// this class throws bad config format exception
@SuppressWarnings("serial")
public class BadConfigFormatException extends Exception {

	public BadConfigFormatException() {
		System.out.println("Bad File Configuration.");	// errors message to console
	}

	public BadConfigFormatException(String message) {
		super(message);
	}

}

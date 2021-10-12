package clueGame;

@SuppressWarnings("serial")
public class BadConfigFormatException extends Exception {

	public BadConfigFormatException() {
		System.out.println("Bad File Configuration.");
	}

	public BadConfigFormatException(String message) {
		super(message);
	}

}

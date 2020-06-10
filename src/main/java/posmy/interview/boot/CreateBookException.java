package posmy.interview.boot;

public class CreateBookException extends RuntimeException {
	CreateBookException(String bookName) {
		super("Could not create book: " + bookName);
	}
}

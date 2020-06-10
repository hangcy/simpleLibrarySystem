package posmy.interview.boot;

public class UpdateBookException extends RuntimeException {
	UpdateBookException(String bookName) {
		super("Could not update book: " + bookName);
	}
}

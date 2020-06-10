package posmy.interview.boot;

public class BookNotFoundException extends RuntimeException {
	BookNotFoundException(String bookName) {
		super("Could not find book bookName: " + bookName);
	}

	public BookNotFoundException(Long id) {
		super("Could not find book id: " + id);
		// TODO Auto-generated constructor stub
	}
}

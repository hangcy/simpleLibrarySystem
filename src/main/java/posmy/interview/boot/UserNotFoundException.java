package posmy.interview.boot;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String username) {
		super("Could not find user: " + username);
	}

	public UserNotFoundException(Long id) {
		// TODO Auto-generated constructor stub
		super("Could not find user: id" + id);
	}
}

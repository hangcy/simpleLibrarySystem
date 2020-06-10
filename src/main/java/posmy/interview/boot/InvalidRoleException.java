package posmy.interview.boot;

public class InvalidRoleException extends RuntimeException {
	InvalidRoleException(Long userId, String action) {
		super("Role: " + userId + " is unable to perform action: " + action);
	}
}

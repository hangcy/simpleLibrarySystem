package posmy.interview.boot;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionAdvice {
	
	@ResponseBody
	@ExceptionHandler(BookNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String BookNotFoundHandler(BookNotFoundException ex) {
	  return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(InvalidRoleException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String InvalidRoleHandler(InvalidRoleException ex) {
	  return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(CreateBookException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String CreateBookHandler(CreateBookException ex) {
	  return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String getUserExceptionHandler(UserNotFoundException ex) {
	  return ex.getMessage();
	}
}
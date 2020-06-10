package posmy.interview.boot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class BookController {
	private final BookRepository repo;
	private final UserRepository userRepo;
	
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);


	BookController(BookRepository repo, UserRepository userrRepo) {
		this.userRepo = userrRepo;
		this.repo = repo;
	}
	  
	@GetMapping("/books/getBook")
	@ResponseBody
	public ResponseEntity<Book> getBook(@RequestParam Long bookId , @RequestParam Long userId) {
		log.info("getBook id: " + bookId + ", userId: " + userId);
		verifyUserRole(userId, "getBook");
		Book book = repo.findById(bookId)
			.orElseThrow(() -> new BookNotFoundException(bookId));
		return ResponseEntity.ok().body(book);
	    		
	}
	
	@PostMapping("/books/createBooks")
	@ResponseBody
	public ResponseEntity<List<Book>> createBooks(@RequestBody Map<String,Object> params) {
		log.info("createBooks params: " + params);

		Long userId = Long.parseLong((String) params.get("userId"));
		verifyUserRole(userId , "createBooks");
		
		List<Map> BookList = (List<Map>) params.get("bookList");
		List<Book> newBookList = new ArrayList();
		for (Map book : BookList) {
			String bookName = (String) book.get("name"); 
			Book newBook = new Book(
					bookName, 
					BookStatus.valueOf("AVAILABLE"),
					(String) book.get("author")
			);
			
			try {
				repo.save(newBook);
			}
			catch (Exception ex) {
				throw new CreateBookException(bookName);
			}
			newBookList.add(newBook);
		}
		
		return ResponseEntity.ok().body(newBookList);
	}
	
	@PostMapping("/books/updateBooks")
	@ResponseBody
	public ResponseEntity<List<Book>> updateBooks(@RequestBody Map<String,Object> params) {
		log.info("updateBooks params: " + params);
		Long userId = Long.parseLong((String) params.get("userId"));
		verifyUserRole(userId, "updateBooks");
		
		List<Map> BookList = (List<Map>) params.get("bookList");
		List<Book> updatedBookList = new ArrayList();
		for (Map book : BookList) {
			String bookName = (String) book.get("name");
			Long bookId = Long.parseLong((String) book.get("id"));
			Book currentBook = repo.findById(bookId)
					.orElseThrow(() -> new BookNotFoundException(bookName));
			
			currentBook.setName(bookName);
			currentBook.setStatus((BookStatus) BookStatus.valueOf((String) book.get("status")));
			currentBook.setAuthor((String) book.get("author"));
			
			try {
				repo.save(currentBook);
			}
			catch (Exception ex) {
				throw new UpdateBookException(bookName);
			}
			
			updatedBookList.add(currentBook);
		}
		
		return ResponseEntity.ok().body(updatedBookList);
	}
	
	@DeleteMapping("/books/deleteBooks")
	@ResponseBody
	public ResponseEntity<String> deleteBooks(@RequestParam Long id , @RequestParam Long userId) {
		verifyUserRole(userId, "deleteBooks");
		Optional<Book> book = repo.findById(id);
		if (book == null) {
			throw new BookNotFoundException(id);
		}
		repo.deleteById(id);
		return ResponseEntity.ok().body("Books succesfully deleted");		
	}
	
	@PostMapping("/books/manageBook")
	@ResponseBody
	public ResponseEntity <Book> manageBook(@RequestBody Map<String,Object> params) {
		log.info("manageBook params: " + params);
		Long userId = Long.parseLong((String) params.get("userId"));
		verifyUserRole(userId, "manageBook");
		Long bookId = Long.parseLong((String) params.get("bookId"));
		Book currentBook = repo.findById(bookId)
				.orElseThrow(() -> new BookNotFoundException(bookId));
		
		BookStatus bookStatus = currentBook.getStatus();
		if (params.get("action").toString().toLowerCase().equals("borrow")) {
			bookStatus = BookStatus.BORROWED;
		}
		else if (params.get("action").toString().toLowerCase().equals("return")) {
			bookStatus = BookStatus.AVAILABLE;
		}
		currentBook.setStatus(bookStatus);
		repo.save(currentBook);
		return ResponseEntity.ok().body(currentBook);
	}
	
	public void verifyUserRole(Long userId, String action) {
		log.info("verifyUserRole userId: " + userId + ", action: " + action);
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(userId));
		Boolean validAccess = false;
		switch (action) {
			case "createBooks":
				validAccess = user.getRole().getCreateBook();
				break;
			case "getBook":
				validAccess = user.getRole().getReadBook();
				break;
			case "updateBooks":
				validAccess = user.getRole().getUpdateBook();
				break;
			case "deleteBooks":
				validAccess = user.getRole().getDeleteBook();
				break;
			case "manageBook":
				validAccess = user.getRole().getManageBook();
				break;
			default:
				validAccess = false;
				break;
		}

		if (!validAccess) {
			throw new InvalidRoleException(userId, action);
		}
	}
}

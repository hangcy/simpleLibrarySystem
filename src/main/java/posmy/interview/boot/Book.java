package posmy.interview.boot;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {
	private @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;
	private String name;
	private BookStatus status;
	private String author;
	  
	Book() {}

	Book(String name, BookStatus status, String author) {
		this.name = name;		
		this.status = status;
		this.author = author;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BookStatus getStatus() {
		return status;
	}

	public void setStatus(BookStatus status) {
		this.status = status;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	  
	  
}

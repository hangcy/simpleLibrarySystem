package posmy.interview.boot;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Role {
	private @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;
	private String name;
	private Boolean readBook;
	private Boolean createBook;
	private Boolean updateBook;
	private Boolean deleteBook;
	private Boolean manageBook;
	private Boolean readAcc;
	private Boolean createAcc;
	private Boolean updateAcc;
	private Boolean deleteOwnAcc;
	private Boolean deleteAllAcc;
	
	Role() {}

	public Role(String name, Boolean readBook, Boolean createBook, Boolean updateBook, Boolean deleteBook,
			Boolean readAcc, Boolean createAcc, Boolean updateAcc, Boolean deleteOwnAcc, Boolean deleteAllAcc,
			Boolean manageBook) {
		super();
		this.name = name;
		this.readBook = readBook;
		this.createBook = createBook;
		this.updateBook = updateBook;
		this.deleteBook = deleteBook;
		this.manageBook = manageBook;
		this.readAcc = readAcc;
		this.createAcc = createAcc;
		this.updateAcc = updateAcc;
		this.deleteOwnAcc = deleteOwnAcc;
		this.deleteAllAcc = deleteAllAcc;
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

	public Boolean getReadBook() {
		return readBook;
	}

	public void setReadBook(Boolean readBook) {
		this.readBook = readBook;
	}

	public Boolean getCreateBook() {
		return createBook;
	}

	public void setCreateBook(Boolean createBook) {
		this.createBook = createBook;
	}

	public Boolean getUpdateBook() {
		return updateBook;
	}

	public void setUpdateBook(Boolean updateBook) {
		this.updateBook = updateBook;
	}

	public Boolean getDeleteBook() {
		return deleteBook;
	}

	public void setDeleteBook(Boolean deleteBook) {
		this.deleteBook = deleteBook;
	}

	public Boolean getReadAcc() {
		return readAcc;
	}

	public void setReadAcc(Boolean readAcc) {
		this.readAcc = readAcc;
	}

	public Boolean getDeleteOwnAcc() {
		return deleteOwnAcc;
	}

	public void setDeleteOwnAcc(Boolean deleteOwnAcc) {
		this.deleteOwnAcc = deleteOwnAcc;
	}

	public Boolean getDeleteAllAcc() {
		return deleteAllAcc;
	}

	public void setDeleteAllAcc(Boolean deleteAllAcc) {
		this.deleteAllAcc = deleteAllAcc;
	}

	public Boolean getCreateAcc() {
		return createAcc;
	}

	public void setCreateAcc(Boolean createAcc) {
		this.createAcc = createAcc;
	}

	public Boolean getUpdateAcc() {
		return updateAcc;
	}

	public void setUpdateAcc(Boolean updateAcc) {
		this.updateAcc = updateAcc;
	}

	public Boolean getManageBook() {
		return manageBook;
	}

	public void setManageBook(Boolean manageBook) {
		this.manageBook = manageBook;
	}
}

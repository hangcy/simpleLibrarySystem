package posmy.interview.boot;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

	Book findByName(String bookName);

}

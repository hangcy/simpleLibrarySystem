package posmy.interview.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(BookRepository bookRepo, UserRepository userRepo, RoleRepository roleRepo ) {	  
	  return args -> {
		  bookRepo.save(new Book("Greatest Story of All Time", BookStatus.AVAILABLE, "Danny Phantom"));
		  bookRepo.save(new Book("Finding Love in Mars", BookStatus.AVAILABLE, "Portia Gold"));
		  bookRepo.findAll().forEach(book -> log.info("Preloaded: " + book.getId()));
		  Role member = new Role("Member", true, false, false, false, true, true, false, true, false, true); 
		  Role librarian = new Role("Librarian", true, true, true, true, true, true, true, true, true, false); 
		  roleRepo.save(member); 
		  roleRepo.save(librarian);
		  roleRepo.findAll().forEach(role -> log.info("Preloaded: " + role.getId() + " " + role.getName()));

		  userRepo.save(new User("Josh", member));
		  userRepo.save(new User("Emily", librarian));
		  userRepo.findAll().forEach(user -> log.info("Preloaded: " + user.getId() + " " +  user.getRole().getId() + " " +  user.getName()));		 
    };
  }
}
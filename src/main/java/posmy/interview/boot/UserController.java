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
public class UserController {
	private static UserRepository repo;
	private static RoleRepository roleRepo;
	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	UserController(UserRepository repo, RoleRepository roleRepo) {
		this.repo = repo;
		this.roleRepo  = roleRepo;
	}

	@GetMapping("/users/getUser")
	@ResponseBody
	public ResponseEntity<User> getUser(@RequestParam Long accountId , @RequestParam Long userId) {
		log.info("getAccount id: " + accountId + ", userId: " + userId);
		verifyUserRole(userId, "getUser", null);
		User user = repo.findById(accountId)
				.orElseThrow(() -> new UserNotFoundException(accountId));
		return ResponseEntity.ok().body(user);
	}
	
	@PostMapping("/users/createUsers")
	@ResponseBody
	public ResponseEntity<List<User>> createUsers(@RequestBody Map<String,Object> params) {
		log.info("createBooks params: " + params);

		Long userId = Long.parseLong((String) params.get("userId"));
		verifyUserRole(userId , "createUsers", null);
		
		List<Map> userList = (List<Map>) params.get("userList");
		List<User> newUserList = new ArrayList();
		for (Map user : userList) {
			String username = (String) user.get("name"); 
			Role role = roleRepo.findByName((String) user.get("role"));
			
			User newUser = new User(username, role);
			
			try {
				repo.save(newUser);
			}
			catch (Exception ex) {
				throw new CreateBookException(username);
			}
			newUserList.add(newUser);
		}
		
		return ResponseEntity.ok().body(newUserList);
	}
	
	@PostMapping("/users/updateUsers")
	@ResponseBody
	public ResponseEntity<List<User>> updateUsers(@RequestBody Map<String,Object> params) {
		log.info("updateUsers params: " + params);
		Long userId = Long.parseLong((String) params.get("userId"));
		verifyUserRole(userId, "updateUsers", null);
		
		List<Map> userList = (List<Map>) params.get("userList");
		List<User> updatedUserList = new ArrayList();
		for (Map user : userList) {
			String userName = (String) user.get("name");
			Long accoundId = Long.parseLong((String) user.get("id"));
			User currentUser = repo.findById(accoundId)
					.orElseThrow(() -> new UserNotFoundException(accoundId));
			
			Role role = roleRepo.findByName((String) user.get("role"));

			currentUser.setName(userName);
			currentUser.setRole(roleRepo.findByName((String) user.get("role")));
			
			try {
				repo.save(currentUser);
			}
			catch (Exception ex) {
				throw new UpdateBookException(userName);
			}
			
			updatedUserList.add(currentUser);
		}
		
		return ResponseEntity.ok().body(updatedUserList);
	}
	
	@DeleteMapping("/users/deleteUser")
	@ResponseBody
	public ResponseEntity<String> deleteUser(@RequestParam Long accountId, @RequestParam Long userId) {
		log.info("deleteUser accountId: " + accountId + " , userid: " + userId);
		verifyUserRole(userId, "deleteUser", accountId);
		Optional<User> user = repo.findById(accountId);
		if (user == null) {
			throw new BookNotFoundException(accountId);
		}
		repo.deleteById(accountId);

		return ResponseEntity.ok().body("User succesfully deleted");		
	}
	
	public void verifyUserRole(Long userId, String action, Long accountId) {
		log.info("verifyUserRole id: " + userId + " accountId:" + accountId + " , action: " + action);
		User user = repo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(userId));
		Boolean validAccess = false;
		
		switch (action) {
			case "getUser":
				validAccess = user.getRole().getReadAcc();
				break;
			case "createUsers":
				validAccess = user.getRole().getCreateAcc();
				break;
			case "updateUsers":
				validAccess = user.getRole().getUpdateAcc();
				break;
			case "deleteUser":
				if (accountId == userId) {
					validAccess = user.getRole().getDeleteOwnAcc();
				}
				else {
					validAccess = user.getRole().getDeleteAllAcc();
				}
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

package posmy.interview.boot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.mapping.Map;
import org.json.JSONObject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import org.junit.jupiter.api.Test;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private BookController bookController;
    
	@Test
    void testGetBook() throws Exception {
    	mockMvc.perform(get("/books/getBook")
    	.contentType("application/json")
    	.param("bookId", "1")
    	.param("userId", "1"))
    	.andExpect(status().isOk());	
    }
	
	@Test
    void testDeleteBooks() throws Exception {
    	mockMvc.perform(delete("/books/deleteBooks")
    	.contentType("application/json")
    	.param("id", "2")
    	.param("userId", "2"))
    	.andExpect(status().isOk());	
    }

	@Test
    void testCreateBooks() throws Exception {
		HashMap<String, Object> bookMap = new HashMap<String, Object>();
		bookMap.put("name", "Book One");
		bookMap.put("author", "AuthorOne");
		List listParams = new ArrayList();
		listParams.add(bookMap);
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", "2");
		paramMap.put("bookList", listParams);
		
		JSONObject json = new JSONObject(paramMap);
    	mockMvc.perform(post("/books/createBooks")
    	.contentType("application/json")
    	.content(json.toString()))
    	.andExpect(status().isOk());  	
    }
	
	@Test
    void testUpdateBooks() throws Exception {
		HashMap<String, Object> bookMap = new HashMap<String, Object>();
		bookMap.put("id", "2");
		bookMap.put("name", "Book One");
		bookMap.put("author", "AuthorOne");
		bookMap.put("status", "AVAILABLE");
		List listParams = new ArrayList();
		listParams.add(bookMap);
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", "2");
		paramMap.put("bookList", listParams);
		
		JSONObject json = new JSONObject(paramMap);
    	mockMvc.perform(post("/books/updateBooks")
    	.contentType("application/json")
    	.content(json.toString()))
    	.andExpect(status().isOk());  	
    }
	
	@Test
    void testManageBook() throws Exception {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", "1");
		paramMap.put("bookId", "1");
		paramMap.put("action", "Borrow");
		
		JSONObject json = new JSONObject(paramMap);
    	mockMvc.perform(post("/books/manageBook")
    	.contentType("application/json")
    	.content(json.toString()))
    	.andExpect(status().isOk());    	
    }
	
	@Test
    void testGetUser() throws Exception {
    	mockMvc.perform(get("/users/getUser")
    	.contentType("application/json")
    	.param("accountId", "1")
    	.param("userId", "1"))
    	.andExpect(status().isOk());	
    }
	
	@Test
    void testCreateUsers() throws Exception {
		HashMap<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("name", "Jono");
		userMap.put("role", "Member");
		List listParams = new ArrayList();
		listParams.add(userMap);
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", "2");
		paramMap.put("userList", listParams);
		
		JSONObject json = new JSONObject(paramMap);
    	mockMvc.perform(post("/users/createUsers")
    	.contentType("application/json")
    	.content(json.toString()))
    	.andExpect(status().isOk());  	
    }
	
	@Test
    void testUpdateUsers() throws Exception {
		HashMap<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("id", "1");
		userMap.put("name", "User One");
		userMap.put("role", "Member");
		List listParams = new ArrayList();
		listParams.add(userMap);
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", "2");
		paramMap.put("userList", listParams);
		
		JSONObject json = new JSONObject(paramMap);
    	mockMvc.perform(post("/users/updateUsers")
    	.contentType("application/json")
    	.content(json.toString()))
    	.andExpect(status().isOk());  	
    }
	
	@Test
    void testDeleteUser() throws Exception {
    	mockMvc.perform(delete("/users/deleteUser")
    	.contentType("application/json")
    	.param("accountId", "1")
    	.param("userId", "2"))
    	.andExpect(status().isOk());	
    }
	
	@Test
	void testVerifyUserRole() {
		Long userId = (long) 1;
		String action = "getBook";
		bookController.verifyUserRole(userId, action);
	}
}

package edu.demo3;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.test.context.SpringBootTest;
import edu.demo3.User.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestUserService {
	@Mock
	UserService userService = mock(UserService.class);	

	@InjectMocks
	UserController controller = new UserController();

	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void userCreationCheck() throws Exception {
		when(userService.findByStudentid(1234)).thenReturn(new User(1234, "jazzlover", "barry", "b. benson", "dead@meme.net", "verycomicalpassword"));
		assertEquals(controller.getUserByStudentid(1234).getStudentid(), 1234);
		assertEquals(controller.getUserByStudentid(1234).getUsername(), "jazzlover");
		assertEquals(controller.getUserByStudentid(1234).getFirstname(), "barry");
		assertEquals(controller.getUserByStudentid(1234).getLastname(), "b. benson");
		assertEquals(controller.getUserByStudentid(1234).getEmail(), "dead@meme.net");
		assertEquals(controller.getUserByStudentid(1234).getPassword(), "verycomicalpassword");
	}

	@Test
	public void userSearchCheck() throws Exception {
		when(userService.findByStudentid(1234)).thenReturn(new User(1234, "jazzlover", "barry", "b. benson", "dead@meme.net", "verycomicalpassword"));
		//assertEquals(controller.getUserByStudentid(12345), null);
	}

	@Test
	public void updateUsernameTest() throws Exception {
		when(userService.findByStudentid(1234)).thenReturn(new User(1234, "jazzlover", "barry", "b. benson", "dead@meme.net", "verycomicalpassword"));
		assertEquals(controller.updateName(1234, "newvalue").getUsername(), "newvalue");
	}

	@Test
	public void updateEmailTest() throws Exception {
		when(userService.findByStudentid(1234)).thenReturn(new User(1234, "jazzlover", "barry", "b. benson", "dead@meme.net", "verycomicalpassword"));
		assertEquals(controller.updateEmail(1234, "newvalue").getEmail(), "newvalue");
	}

	@Test
	public void updateFirstnameTest() throws Exception {
		when(userService.findByStudentid(1234)).thenReturn(new User(1234, "jazzlover", "barry", "b. benson", "dead@meme.net", "verycomicalpassword"));
		assertEquals(controller.updateFirstname(1234, "newvalue").getFirstname(), "newvalue");
	}

	@Test
	public void updateLastnameTest() throws Exception {
		when(userService.findByStudentid(1234)).thenReturn(new User(1234, "jazzlover", "barry", "b. benson", "dead@meme.net", "verycomicalpassword"));
		assertEquals(controller.updateLastname(1234, "newvalue").getLastname(), "newvalue");
	}

	@Test
	public void updatePasswordTest() throws Exception {
		when(userService.findByStudentid(1234)).thenReturn(new User(1234, "jazzlover", "barry", "b. benson", "dead@meme.net", "verycomicalpassword"));
		assertEquals(controller.updatePassword(1234, "verycomicalpassword", "newvalue").getPassword(), "newvalue");
	}

	@Test
	public void findByStudentidTest() {
        
        when(userService.findByStudentid(1)).thenReturn(new User(1, "jDoe", "john", "doe", "jDoe@gmail.com", "password"));

		User user = controller.getUserByStudentid(1);

		assertEquals("jDoe", user.getUsername());
		assertEquals("password", user.getPassword());
		assertEquals("jDoe@gmail.com", user.getEmail());
	}

	@Test
	public void getAllUsersTest() {
		List<User> list = new ArrayList<User>();
		User user1 = new User(1, "JohnDoe", "john", "doe", "john@gmail.com", "johnpass");
		User user2 = new User(2, "AlexSmith", "alex", "smith", "alex@yahoo.com", "alexpass");
		User user3 = new User(3, "SteveLee", "steve",  "lee","steve@gmail.com", "stevepass");

		list.add(user1);
		list.add(user2);
		list.add(user3);

		when(userService.findAll()).thenReturn(list);

		Iterable<User> userList = controller.getAllUsers();

		assertEquals(3, ((List<User>) userList).size());
		//verify(userService, times(1));
	}
}
package edu.demo3;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.springframework.boot.test.context.SpringBootTest;
import edu.demo3.DirectMessage.DirectMessage;
import edu.demo3.DirectMessage.DirectMessageController;
import edu.demo3.DirectMessage.DirectMessageService;
import edu.demo3.Room.Room;
import edu.demo3.User.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestDirectMessageController {

	@Mock
	DirectMessageService directMessageService = mock(DirectMessageService.class);	

	@InjectMocks
	DirectMessageController controller = new DirectMessageController();

    @Before
    public void init() {
		MockitoAnnotations.openMocks(this);
	}

    @Test
	public void findAllTest() 
	{ 
		ArrayList<DirectMessage> mocking_messages = new ArrayList<DirectMessage>();
		DirectMessage mocking_message = new DirectMessage(new User(123456789, "fitnessman_pacer_test", "roger", "fransisco", "fitness@aol.com", "20meters"), new Room("culvers"), "the pacer test will begin in 20 seconds");
		mocking_messages.add(mocking_message);
        when(directMessageService.findAll()).thenReturn(mocking_messages);
		Iterable<DirectMessage> msgs = controller.getAllMessages();
		Boolean contains = false;
		for (DirectMessage msg : msgs)
		{
			if (msg.getId() == mocking_message.getId())
			{
				contains = true;
			}
		}
		assert(contains);
	}
}

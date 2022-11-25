package edu.demo3;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.Before;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.springframework.boot.test.context.SpringBootTest;
import edu.demo3.Club.Club;
import edu.demo3.Message.Message;
import edu.demo3.Message.MessageController;
import edu.demo3.Message.MessageService;
import edu.demo3.User.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestMessageController {

	@Mock
	MessageService messageService = mock(MessageService.class);	

	@InjectMocks
	MessageController controller = new MessageController();

    @Before
    public void init() {
		MockitoAnnotations.openMocks(this);
	}

    @Test
	public void findAllTest() 
	{ 
		ArrayList<Message> mocking_messages = new ArrayList<Message>();
		Message mocking_message = new Message(new User(123456789, "fitnessman_pacer_test", "roger", "fransisco", "fitness@aol.com", "20meters"), new Club("robotics club", "we are robots"), "the pacer test will begin in 20 seconds", 0);
		mocking_messages.add(mocking_message);

        when(messageService.findAll()).thenReturn(mocking_messages);
		Iterable<Message> msgs = controller.getAllMessages();
		Boolean contains = false;
		for (Message msg : msgs)
		{
			if (msg.getId() == mocking_message.getId())
			{
				contains = true;
			}
		}
		assert(contains);
	}

	@Test
	public void findByClubTest()
	{
		ArrayList<Message> mocking_messages = new ArrayList<Message>();
		Message mocking_message = new Message(new User(123456789, "fitnessman_pacer_test", "roger", "fransisco", "fitness@aol.com", "20meters"), new Club("robotics club", "we are robots"), "the pacer test will begin in 20 seconds", 0);
		Club mocking_club = new Club("rasczak's roughnecks", "satirical literature club");
		mocking_messages.add(mocking_message);

        when(messageService.findByClubchat(1)).thenReturn(new ArrayList<Message>());
		Iterable<Message> msgs = controller.getMessagesByClub(mocking_club.getId());
		
		Boolean contains = false;
		for (Message msg : msgs)
		{
			msg.getId();
			contains = true;
		}
		assert(!contains);
	}
}

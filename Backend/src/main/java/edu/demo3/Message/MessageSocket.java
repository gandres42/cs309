package edu.demo3.Message;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import edu.demo3.Club.ClubRepository;
import edu.demo3.User.UserRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@ServerEndpoint(value = "/chat/{clubid}/{studentid}")
public class MessageSocket {

	private static MessageRepository messageRepository;
	private static UserRepository userRepository;
	private static ClubRepository clubRepository;

	@Autowired
	public void setMessageRepository(MessageRepository repo) {
		messageRepository = repo;
	}

	@Autowired
	public void setMessageRepository(UserRepository repo) {
		userRepository = repo;
	}

	@Autowired
	public void setMessageRepository(ClubRepository repo) {
		clubRepository = repo;
	}

	private static Map<Session, String> sessionStudentIdMap = new Hashtable<>();
	private static Map<Session, String> sessionClubIdMap = new Hashtable<>();

	private final Logger logger = LoggerFactory.getLogger(MessageSocket.class);
    private static ObjectMapper mapper = new ObjectMapper();

	@OnOpen
	public void onOpen(Session session, @PathParam("clubid") String clubid, @PathParam("studentid") String studentId) throws IOException {
		if (userRepository.findByStudentid(Integer.parseInt(studentId)) == null)
		{
			CloseReason reason = new CloseReason(CloseCodes.CANNOT_ACCEPT, "user does not exist");
			session.close(reason);
			return;
		}
		
		if (clubRepository.findById(Integer.parseInt(clubid)) == null)
		{
			CloseReason reason = new CloseReason(CloseCodes.CANNOT_ACCEPT, "club does not exist");
			session.close(reason);
			return;
		}
		
		sessionStudentIdMap.put(session, studentId);
		sessionClubIdMap.put(session, clubid);
		sendToSession(session, getChatHistoryByClubId(clubid));
	}

	@OnMessage
	public void onMessage(Session session, String message) throws IOException 
    {		
		if (userRepository.findByStudentid(Integer.parseInt(sessionStudentIdMap.get(session))) != null && clubRepository.findById(Integer.parseInt(sessionClubIdMap.get(session))) != null)
		{
			Message msg = new Message(userRepository.findByStudentid(Integer.parseInt(sessionStudentIdMap.get(session))), clubRepository.findById(Integer.parseInt(sessionClubIdMap.get(session))), message, 0);
			sendToClubchat(sessionClubIdMap.get(session), mapper.writeValueAsString(msg));
			messageRepository.save(msg);
		}
	}

	@OnClose
	public void onClose(Session session) throws IOException {
		String studentId = sessionStudentIdMap.get(session);
		sessionStudentIdMap.remove(session);
		sessionClubIdMap.remove(session);
		String message = studentId + " disconnected";
		sendToAll(message);
	}


	@OnError
	public void onError(Session session, Throwable throwable) {
		throwable.printStackTrace();
	}

	private void sendToClubchat(String clubId, String message)
	{
		sessionClubIdMap.forEach((session, sessionClubId) -> {
			try {
				logger.info(sessionClubId);
				if (sessionClubId.equals(clubId))
				{
					session.getBasicRemote().sendText(message);
				}
			} 
			catch (IOException e) {
				logger.info("Exception: " + e.getMessage().toString());
				e.printStackTrace();
			}
		});
		
	}

	private void sendToSession(Session session, String message) {
		try {
			session.getBasicRemote().sendText(message);
		} 
        catch (IOException e) {
			logger.info("Exception: " + e.getMessage().toString());
			e.printStackTrace();
		}
	}


	private void sendToAll(String message) {
		sessionStudentIdMap.forEach((session, studentId) -> {
			try {
				session.getBasicRemote().sendText(message);
			} 
      		catch (IOException e) {
				logger.info("Exception: " + e.getMessage().toString());
				e.printStackTrace();
			}
		});
	}

	private String getChatHistoryByClubId(String clubId) {
		Iterable<Message> messages = messageRepository.findAll();
        String chat = "[";
		Boolean first = true;
		if (messages != null) {
			for (Message message : messages) {
                try
                {
					if (message.getClubchat().getId() == Integer.parseInt(clubId) && message.getSubchatid() == 0)
					{
						
						if (first == true)
						{
							first = false;
						}
						else
						{
							chat = chat + ",";
						}

						chat += mapper.writeValueAsString(message);
					}
                }
                catch (Exception e)
                {
                    logger.error(e.getMessage());
                }
			}
		}
		chat = chat + "]";
		return chat;
	}
}
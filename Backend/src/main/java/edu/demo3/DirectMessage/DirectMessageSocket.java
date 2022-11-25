package edu.demo3.DirectMessage;

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

import edu.demo3.Room.RoomRepository;
import edu.demo3.User.UserRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@ServerEndpoint(value = "/direct_chat/{roomid}/{studentid}")
public class DirectMessageSocket {

	private static DirectMessageRepository directMessageRepository;
	private static UserRepository userRepository;
	private static RoomRepository roomRepository;

	@Autowired
	public void setMessageRepository(DirectMessageRepository repo) {
		directMessageRepository = repo;
	}

	@Autowired
	public void setMessageRepository(UserRepository repo) {
		userRepository = repo;
	}

	@Autowired
	public void setMessageRepository(RoomRepository repo) {
		roomRepository = repo;
	}

	private static Map<Session, String> sessionStudentIdMap = new Hashtable<>();
	private static Map<Session, String> sessionRoomIdMap = new Hashtable<>();

	private final Logger logger = LoggerFactory.getLogger(DirectMessageSocket.class);
    private static ObjectMapper mapper = new ObjectMapper();

	@OnOpen
	public void onOpen(Session session, @PathParam("roomid") String roomid, @PathParam("studentid") String studentId) throws IOException {
		if (userRepository.findByStudentid(Integer.parseInt(studentId)) == null)
		{
			CloseReason reason = new CloseReason(CloseCodes.CANNOT_ACCEPT, "user does not exist");
			session.close(reason);
			return;
		}
		
		if (!roomRepository.findById(Integer.parseInt(roomid)).isPresent())
		{
			CloseReason reason = new CloseReason(CloseCodes.CANNOT_ACCEPT, "room does not exist");
			session.close(reason);
			return;
		}
		
		sessionStudentIdMap.put(session, studentId);
		sessionRoomIdMap.put(session, roomid);
		sendToSession(session, getChatHistoryByRoomId(roomid));
	}

	@OnMessage
	public void onMessage(Session session, String message) throws IOException 
    {		
		DirectMessage msg = new DirectMessage(userRepository.findById(Integer.parseInt(sessionStudentIdMap.get(session))).get(), roomRepository.findById(Integer.parseInt(sessionRoomIdMap.get(session))).get(), message);
		sendToClubchat(sessionRoomIdMap.get(session), mapper.writeValueAsString(msg));
		directMessageRepository.save(msg);
	}

	@OnClose
	public void onClose(Session session) throws IOException {
		String studentId = sessionStudentIdMap.get(session);
		sessionStudentIdMap.remove(session);
		sessionRoomIdMap.remove(session);
		String message = studentId + " disconnected";
		sendToAll(message);
	}


	@OnError
	public void onError(Session session, Throwable throwable) {
		throwable.printStackTrace();
	}

	private void sendToClubchat(String roomid, String message)
	{
		sessionRoomIdMap.forEach((session, sessionRoomId) -> {
			try {
				logger.info(sessionRoomId);
				if (sessionRoomId.equals(roomid))
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

	private String getChatHistoryByRoomId(String roomid) {
		Iterable<DirectMessage> messages = directMessageRepository.findAll();
        String chat = "[";
		Boolean first = true;
		if (messages != null) {
			for (DirectMessage message : messages) {
                try
                {
					if (message.getRoom().getId() == Integer.parseInt(roomid))
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
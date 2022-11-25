package com.cs309.WebSocketsExample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/*
 * ** IMPORTANT **
 * NEED TO ADD @EnableWebSocket annotation!!
 */
@SpringBootApplication
//@EnableWebSocket
public class WebSocketsExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSocketsExampleApplication.class, args);
	}

}

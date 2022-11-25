package com.cs309.tutorial.tests;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	public TestData server = new TestData();
	
	@GetMapping("/getTest")
	public String getTest(@RequestParam(value = "key", defaultValue = "void") String key) {
		//Change requestparams with ?msg1={val}&msg2={val}
		//Different from pathparams, which are specified by path, these are same path but specified with variables and have a default option
		if (key.equals("message"))
		{
			return String.format("You sent a get request with a parameter: %s\nCurrent message: %s\n", key, server.getMessage());
		}
		return String.format("You sent a get request with a parameter: %s\nNo such variable\n", key);
	}
	
	@PostMapping("/postTest")
	public String postTest(@RequestParam(value = "message", defaultValue = "void") String message) {
		server.setMessage(message);
		return String.format("You sent a post request with a parameter!\nServer message set to: %s\n", message);
	}
	
	@DeleteMapping("/deleteTest")
	public void deleteTest() {
		//TODO
	}
	
	@PutMapping("/putTest")
	public void putTest() {
		//TODO
	}
}

package com.dailybagel.controller;


import java.io.IOException;
import java.io.PrintWriter;

import com.dailybagel.user.resources.User;
import com.dailybagel.user.resources.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dailybagel.DatabaseHandler.*;

@CrossOrigin
@Controller
public class UserController {
	
	UserService us;

	public UserController() {
		us = new UserService();
	}

	@RequestMapping("/getAllUsers")
	public void getAllUsers(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, InterruptedException {
		
		/* getAllUsers takes no input and returns a JSON array of JSON objects
		 * representing all users in the database. */
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		if (DBHandler.testDBConnection()) {
			out.println(gson.toJson(us.getAllUsers()));
		} else {
			response.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, 
					"Database did not respond");
		}
	}

	@RequestMapping("/getUserPage")
	public void getUserPage(
			HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, InterruptedException {
		
		/* getUserPage accepts input as HTTP request parameters "pageNumber"
		 * and "itemsPerPage". It will return "itemsPerPage" number of users
		 * starting with Nth result (N = "itemsPerPage" * "pageNumber") in the
		 * form of a JSON array of JSON objects. If no input or incorrect input
		 * is provided, defaults to the first five results */
		
		int pageNumber;
		int itemsPerPage;
		
		try {
			pageNumber = Integer.valueOf(
					request.getParameter("pageNumber"));
			itemsPerPage = Integer.valueOf(
					request.getParameter("itemsPerPage"));
		} catch (NumberFormatException e) {
			pageNumber = 0;
			itemsPerPage = 5;
		}
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		if (DBHandler.testDBConnection()) {
			out.println(gson.toJson(us.getUserPage(pageNumber,itemsPerPage)));
		} else {
			response.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, 
					"Database did not respond");
		}
	}

	@RequestMapping("/getUser")
	public void getUser(
			HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, InterruptedException {
		
		/* getUser accepts one input as HTTP request parameter "userID" and
		 * returns a single JSON object for the user with the specified ID. */
		
		int userId;
		
		try {
			userId = Integer.valueOf(request.getParameter("userId"));
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
					"Parameter 'userId' is not formatted correctly");
			return;
		}
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		if (DBHandler.testDBConnection()) {
			out.println(gson.toJson(us.getUser(userId)));
		} else {
			response.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, 
					"Database did not respond");
		}
	}

	@RequestMapping("/addUser")
	public void addUser(
			HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, InterruptedException {

		/* addUser accepts input as a JSON object in the body of the HTTP
		 * request in a format that must match the User model. It does not
		 * return a response unless there was an error. */
		
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		User user;
		
		try {
			JsonObject obj = (JsonObject) parser.parse(request.getReader());
			user = gson.fromJson(obj, new User().getClass());
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
					"Data received was incomplete or incorrectly formatted");
			return;
		}
		
		if (!DBHandler.testDBConnection()) {
			response.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, 
					"Database did not respond");
			return;
		}
		
		if (us.getUser(user.email) == null) {
			us.addUser(user);
		} else {
			response.sendError(HttpServletResponse.SC_CONFLICT, 
					"User already exists.");
		}
	}

	@RequestMapping("/deleteUser")
	public void deleteUser(
			HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, InterruptedException {

		/* deleteUser accepts as a JSON object in the body of the HTTP
		 * request in a format that must match the User model. It does not
		 * return a response unless there was an error. */
		
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		User user;
		
		try {
			JsonObject obj = (JsonObject) parser.parse(request.getReader());
			user = gson.fromJson(obj, new User().getClass());
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
					"Data received was incomplete or incorrectly formatted");
			return;
		}
		
		if (!DBHandler.testDBConnection()) {
			response.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, 
					"Database did not respond");
			return;
		}
		
		if (us.getUser(user.userId) != null) {
			us.deleteUser(user);
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
					"That user does not exist");
		}
	}

	@RequestMapping("/updateUser")
	public void updateUser(
			HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, InterruptedException {

		/* updateUser accepts as a JSON object in the body of the HTTP
		 * request in a format that must match the User model. It does not
		 * return a response unless there was an error. */
		
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		User user;
		
		try {
			JsonObject obj = (JsonObject) parser.parse(request.getReader());

			user = gson.fromJson(obj, new User().getClass());
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
					"Data received was incomplete or incorrectly formatted");
			return;
		}
		if (!DBHandler.testDBConnection()) {
			response.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, 
					"Database did not respond");
			return;
		}
		if (us.getUser(user.userId) != null) {
			us.updateUser(user);
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
					"That user does not exist");
		}
	}

	@RequestMapping("/updateUserPassword")
	public void updateUserPassword(
			HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, InterruptedException {

		/* updateUserPassword accepts as a JSON object in the body of the HTTP
		 * request in a format that must match the User model. It does not
		 * return a response unless there was an error. */
		
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		User user;
		
		try {
			JsonObject obj = (JsonObject) parser.parse(request.getReader());
			user = gson.fromJson(obj, new User().getClass());
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
					"Data received was incomplete or incorrectly formatted");
			return;
		}
		
		if (!DBHandler.testDBConnection()) {
			response.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, 
					"Database did not respond");
			return;
		}
		
		if (us.getUser(user.userId) != null) {
			us.updateUserPassword(user);
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
					"That user does not exist");
		}
	}

	@RequestMapping("/updateUserRole")
	public void updateUserRole(
			HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, InterruptedException {

		/* updateUserRole accepts as a JSON object in the body of the HTTP
		 * request in a format that must match the User model. It does not
		 * return a response unless there was an error. */
		
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		User user;
		
		try {
			JsonObject obj = (JsonObject) parser.parse(request.getReader());
			user = gson.fromJson(obj, new User().getClass());
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
					"Data received was incomplete or incorrectly formatted");
			return;
		}
		
		if (!DBHandler.testDBConnection()) {
			response.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, 
					"Database did not respond");
			return;
		}
		
		if (us.getUser(user.userId) != null) {
			us.updateUserRole(user);
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
					"That user does not exist");
		}
	}
}


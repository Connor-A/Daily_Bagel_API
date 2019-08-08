package com.dailybagel.controller;


import java.io.IOException;
import java.io.PrintWriter;

import com.dailybagel.DatabaseHandler.DBHandler;
import com.dailybagel.auth.AuthService;
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




@CrossOrigin
@Controller
public class LogInController {
	AuthService as;

	public LogInController() {
		as = new AuthService();
	}

	@RequestMapping("/logIn")
	public void getAllUsers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, InterruptedException {
		String email;
		String password;
		try {
			email = String.valueOf(request.getParameter("email"));
			password = String.valueOf(request.getParameter("password"));
		} catch (NumberFormatException e) {
			email = null;
			password = null;
		}
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		if (DBHandler.testDBConnection()) {
			out.println(gson.toJson(this.as.login(email, password)));
		} else {
			response.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, "Database did not response");
		}
		
	}

	@RequestMapping("/isLoggedIn")
	public void getUserPage(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, InterruptedException {
		String token;
		try {
			token = String.valueOf(request.getParameter("token"));
		} catch (NumberFormatException e) {
			token = null;
		}
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		if (DBHandler.testDBConnection()) {
			out.println(gson.toJson(this.as.testToken(token)));			
		} else {
			response.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, "Database did not response");
		}
		
	}
}


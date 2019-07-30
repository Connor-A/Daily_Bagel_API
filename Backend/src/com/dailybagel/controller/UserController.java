package com.dailybagel.controller;


import java.io.IOException;
import java.io.PrintWriter;
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
public class UserController {

	public UserController() {
		// TODO Auto-generated constructor stub
	}

	@RequestMapping("/dummy")
	public void dummy(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		  int pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
		  int itemsPerPage = Integer.valueOf(request.getParameter("itemsPerPage"));
		  PrintWriter out = response.getWriter();
	      Gson gson = new Gson();
	      //out.println(gson.toJson(this.it.getPage(pageNumber,itemsPerPage)));
	}

	
}


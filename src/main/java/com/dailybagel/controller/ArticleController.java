package com.dailybagel.controller;


import java.io.IOException;
import java.io.PrintWriter;

import com.dailybagel.DatabaseHandler.DBHandler;
import com.dailybagel.article.resources.Article;
import com.dailybagel.article.resources.ArticleService;
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
public class ArticleController {

	ArticleService as;

	public ArticleController() {
		as = new ArticleService();
	}

	@RequestMapping("/getAllArticles")
	public void getAllArticles(
			HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException, InterruptedException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		if (DBHandler.testDBConnection()) {
			out.println(gson.toJson(as.getAllArticles()));
		} else {
			response.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, 
					"Database did not respond");
		}
	}

	@RequestMapping("/getArticlePage")
	public void getArticlePage(
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException, InterruptedException {

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
			out.println(gson.toJson(
					as.getArticlePage(pageNumber,itemsPerPage)));
		} else {
			response.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, 
					"Database did not respond");
		}
	}

	@RequestMapping("/getFeaturedArticles")
	public void getFeaturedArticles(
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException, InterruptedException {

		int articleCount;

		try {
			articleCount = Integer.valueOf(
					request.getParameter("articleCount"));
		} catch (NumberFormatException e) {
			articleCount = 5;
		}

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		Gson gson = new Gson();

		if (DBHandler.testDBConnection()) {
			out.println(gson.toJson(as.getFeaturedArticles(articleCount)));
		} else {
			response.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, 
					"Database did not respond");
		}
	}

	@RequestMapping("/getArticle")
	public void getArticle(
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException, InterruptedException {

		int articleId;
		try {
			articleId = Integer.valueOf(request.getParameter("articleId"));
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
					"Parameter 'userId' was not passed or "
					+ "is not formatted correctly");
			return;
		}

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		Gson gson = new Gson();

		if (DBHandler.testDBConnection()) {
			out.println(gson.toJson(this.as.getArticle(articleId)));
		} else {
			response.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, 
					"Database did not respond");
		}
	}

	@RequestMapping("/getArticleByAuthor")
	public void getArticleByAuthor(
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException, InterruptedException {

		int authorId;
		try {
			authorId = Integer.valueOf(request.getParameter("authorId"));
		} catch (NumberFormatException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
					"Parameter 'userId' was not passed or "
					+ "is not formatted correctly");
			return;
		}

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();
		Gson gson = new Gson();

		if (DBHandler.testDBConnection()) {
			out.println(gson.toJson(this.as.getArticleByAuthor(authorId)));
		} else {
			response.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, 
					"Database did not respond");
		}
	}

	
	@RequestMapping("/addArticle")
	public void addArticle(
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException, InterruptedException {

		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		Article article;

		try {
			JsonObject obj = (JsonObject) parser.parse(request.getReader());
			article = gson.fromJson(obj, new Article().getClass());
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
					"Data received was incomplete or incorrectly formatted");
			return;
		}

		if (DBHandler.testDBConnection()) {
			as.addArticle(article);
		} else {
			response.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, 
					"Database did not respond");
		}
	}

	@RequestMapping("/deleteArticle")
	public void deleteArticle(
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException, InterruptedException {

		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		Article article;

		try {
			JsonObject obj = (JsonObject) parser.parse(request.getReader());
			article = gson.fromJson(obj, new Article().getClass());
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
					"Data received was incomplete or incorrectly formatted");
			return;
		}

		if (DBHandler.testDBConnection()) {
			as.deleteArticle(article);
		} else {
			response.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, 
					"Database did not respond");
		}
	}

	@RequestMapping("/updateArticle")
	public void updateArticle(
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException, InterruptedException {

		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		Article article;

		try {
			JsonObject obj = (JsonObject) parser.parse(request.getReader());
			article = gson.fromJson(obj, new Article().getClass());
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
					"Data received was incomplete or incorrectly formatted");
			return;
		}

		if (DBHandler.testDBConnection()) {
			as.updateArticle(article);
		} else {
			response.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, 
					"Database did not respond");
		}
	}

	@RequestMapping("/updateArticleViews")
	public void updateArticleViews(
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException, InterruptedException {

		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		Article article;

		try {
			JsonObject obj = (JsonObject) parser.parse(request.getReader());

			article = gson.fromJson(obj, new Article().getClass());
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
					"Data received was incomplete or incorrectly formatted");
			return;
		}

		if (DBHandler.testDBConnection()) {
			as.updateArticleViews(article);
		} else {
			response.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, 
					"Database did not respond");
		}
	}

	@RequestMapping("/updateArticleAuthor")
	public void updateArticleAuthor(
			HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException, InterruptedException {

		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		Article article;

		try {
			JsonObject obj = (JsonObject) parser.parse(request.getReader());
			article = gson.fromJson(obj, new Article().getClass());
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
					"Data received was incomplete or incorrectly formatted");
			return;
		}

		if (DBHandler.testDBConnection()) {
			as.updateArticleAuthor(article);
		} else {
			response.sendError(HttpServletResponse.SC_GATEWAY_TIMEOUT, 
					"Database did not respond");
		}
	}
	
	
}
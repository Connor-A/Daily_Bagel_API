package com.dailybagel.controller;


import java.io.IOException;
import java.io.PrintWriter;

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
	public void getAllArticles(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		out.println(gson.toJson(this.as.getAllArticles()));
	}
	
	@RequestMapping("/getArticlePage")
	public void getArticlePage(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		int pageNumber;
		int itemsPerPage;
		try {
			 pageNumber = Integer.valueOf(request.getParameter("pageNumber"));
			 itemsPerPage = Integer.valueOf(request.getParameter("itemsPerPage"));
		} catch (NumberFormatException e) {
			pageNumber = 0;
			itemsPerPage = 5;
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
	    Gson gson = new Gson();
	    out.println(gson.toJson(this.as.getArticlePage(pageNumber,itemsPerPage)));
	}
	
	@RequestMapping("/getFeaturedArticles")
	public void getFeaturedArticles(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		int articleCount;
		try {
			articleCount = Integer.valueOf(request.getParameter("articleCount"));
		} catch (NumberFormatException e)
		{
			articleCount = 5;
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
	    Gson gson = new Gson();
	    out.println(gson.toJson(this.as.getFeaturedArticles(articleCount)));
	}
	
	@RequestMapping("/getArticle")
	public void getArticle(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		int userId = Integer.valueOf(request.getParameter("userId"));
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		out.println(gson.toJson(this.as.getArticle(userId)));
	}
		
	@RequestMapping("/addArticle")
	public void addArticle(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		   
		Gson gson = new Gson();
	    JsonParser parser = new JsonParser();
	    JsonObject obj = (JsonObject) parser.parse(request.getReader());
	        
	    Article article = gson.fromJson(obj, new Article().getClass());
	    as.addArticle(article);
	    
	}
	
	@RequestMapping("/deleteArticle")
	public void deleteArticle(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		   
		Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(request.getReader());
        
        Article article = gson.fromJson(obj, new Article().getClass());
        as.deleteArticle(article);
	}
	
	@RequestMapping("/updateArticle")
	public void updateArticle(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		   
		Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(request.getReader());

        Article article = gson.fromJson(obj, new Article().getClass());
        as.updateArticle(article);
	}

	@RequestMapping("/updateArticleViews")
	public void updateArticleViews(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		   
		Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(request.getReader());

        Article article = gson.fromJson(obj, new Article().getClass());
        as.updateArticleViews(article);
	}
	
	@RequestMapping("/updateArticleAuthor")
	public void updateArticleAuthor(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		   
		Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(request.getReader());

        Article article = gson.fromJson(obj, new Article().getClass());
        as.updateArticleAuthor(article);
	}
	
}


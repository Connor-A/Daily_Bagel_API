package com.dailybagel.article.resources;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dailybagel.DatabaseHandler.DBHandler;



public class ArticleService {
	Session session;

	public ArticleService() {
		this.session = DBHandler.getSessionFactory().openSession();
	}
		
	public List<Article> getAllArticles() {
		List<Article> list = session.createQuery("from Article").list();
		return list;
	}
	
	public ArticleModelView getArticlePage(int pageNumber, int itemsPerPage) {
		ArticleModelView avm = new ArticleModelView();
		
		Query q = session.createQuery("from Article");
		q = q.setFirstResult(pageNumber*itemsPerPage);
		q = q.setMaxResults(itemsPerPage);
		
		avm.articles = q.list();
		int temp = session.createQuery("from Article").list().size();
		temp = temp % itemsPerPage == 0 ? temp/itemsPerPage - 1: temp/itemsPerPage;
		
		
		avm.maxPageSize = temp;
		
		return avm;
	}
	
	public List<Article> getFeaturedArticles(int articleCount) {
		Query q = session.createQuery("from Article order by featuredRank");
		q = q.setMaxResults(articleCount);
		
		return q.list();
	}
	
	public Article getArticle(long articleId) {
		Article Article = (Article) session.get(Article.class, articleId);
		return Article;
	}

	public void addArticle(Article Article) {
		Transaction tx = session.beginTransaction();
		session.save(Article);
		tx.commit();
		session.flush();
	}
	
	public void deleteArticle(Article article) {
		Object a = session.load(Article.class, article.articleId);
		if (a != null) { 
			Transaction tx = session.beginTransaction();
			session.delete(a);
		    tx.commit();
		}
		session.flush();
	}
	
	public void updateArticle(Article article) {
		Article a = (Article)session.load(Article.class, article.articleId);
		if (a != null) {
			Transaction tx = session.beginTransaction();
			a.title = article.title;
			a.content = article.content;
			a.featuredRank = article.featuredRank;
			session.save(a);
		    tx.commit();
		}
		session.flush();
	}
	
	public void updateArticleViews(Article article) {
		Article a = (Article)session.load(Article.class, article.articleId);
		if (a != null) {
			Transaction tx = session.beginTransaction();
			a.views = article.views;
			session.save(a);
		    tx.commit();
		}
	}
	
	public void updateArticleAuthor(Article article) {
		Article a = (Article)session.load(Article.class, article.articleId);
		if (a != null) {
			Transaction tx = session.beginTransaction();
			a.authorId = article.authorId;
			session.save(a);
		    tx.commit();
		}
		session.flush();
	}
	
	
}

package com.dailybagel.article.resources;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



@Entity
@Table(name = "articles", uniqueConstraints = {
@UniqueConstraint(columnNames = "articleID") })
public class Article implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "articleId", unique = true, nullable = false)
	public long articleId;
	
	@Column(name = "title", unique = false, nullable = false)
	public String title;
	
	@Column(name = "content", unique = false, nullable = false)
	public String content;
	
	@Column(name = "featuredRank", unique = true, nullable = false)
	public int featuredRank;
	
	@Column(name = "views", unique = false, nullable = false)
	public int views;
	
	@Column(name = "authorId", unique = false, nullable = false)
	public long authorId;
		
	
	public Article(){
	}
}

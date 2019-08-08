package com.dailybagel.user.resources;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



@Entity
@Table(name = "users", uniqueConstraints = {
@UniqueConstraint(columnNames = "userID") })
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", unique = true, nullable = false)
	public int userId;
	
	@Column(name = "firstName", unique = false, nullable = false)
	public String firstName;
	
	@Column(name = "lastName", unique = false, nullable = false)
	public String lastName;
	
	@Column(name = "email", unique = true, nullable = false)
	public String email;
	
	@Column(name = "password", unique = false, nullable = false)
	public String password;
	
	@Column(name = "role", unique = false, nullable = false)
	public String role;
	
	@Column(name = "token", unique = true, nullable = true)
	public String token;
		
	
	public User(){
	}
}


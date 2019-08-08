package com.dailybagel.auth;

import java.security.SecureRandom;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dailybagel.DatabaseHandler.DBHandler;
import com.dailybagel.password.Password;
import com.dailybagel.user.resources.User;
import com.dailybagel.user.resources.UserModelView;
import com.google.gson.JsonElement;



public class AuthService {
	Session session;

	public AuthService() {
		this.session = DBHandler.getSessionFactory().openSession();
	}
		
	

	public User login(String email, String password) {
		if(email == null || password == null)
			return null;
		
		Query q = session.createQuery("from User u where u.email = ?")
				.setParameter(0, email);
		
		@SuppressWarnings("unchecked")
		List<User> list = q.list();
		
		if(list.size() != 1)
			return null;
		
		User u = (User) list.get(0);
		
		try {
			if(Password.check(password, u.password))
			{
				SecureRandom random = new SecureRandom();
				byte bytes[] = new byte[20];
				random.nextBytes(bytes);
				
				u.token = bytes.toString() + email;
				
				Transaction tx = session.beginTransaction();
				session.save(u);
			    tx.commit();
				u.password = "";
				
				return u;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}


	public User testToken(String token) {
		if(token == null)
			return null;
		
		User u = new User();
		
		u = (User)session.createQuery("from User u where u.token = " + token);
		u.password = "";
		return u;
	}
	
	}

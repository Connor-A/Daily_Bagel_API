package com.dailybagel.user.resources;

import java.security.SecureRandom;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dailybagel.DatabaseHandler.DBHandler;
import com.dailybagel.password.Password;



public class UserService {
	Session session;

	public UserService() {
		this.session = DBHandler.getSessionFactory().openSession();
	}
		
	public List<User> getAllUsers() {
		
		List<User> list = session.createQuery("from User").list();
		for (User u : list) {
			u.password = "";
			u.token = "";
		}
		
		return list;
	}
	
	public UserModelView getUserPage(int pageNumber, int itemsPerPage) {
		UserModelView uvm = new UserModelView();
		
		Query q = session.createQuery("from User");
		q = q.setFirstResult(pageNumber*itemsPerPage);
		q = q.setMaxResults(itemsPerPage);
		
		uvm.users = q.list();
		int temp = session.createQuery("from User").list().size();
		temp = temp % itemsPerPage == 0 ? temp/itemsPerPage - 1: temp/itemsPerPage;
		
		for (User u : uvm.users) {
			u.password = "";
			u.token = "";
		}
		
		uvm.maxPageSize = temp;
		
		return uvm;
	}
	
	public User getUser(long userId) {
		User user = (User) session.get(User.class, userId);
		user.password = "";
		user.token = "";
		return user;
	}
	
	public User getUser(String email) {
		User user = (User) session
				.createQuery("from User as u where u.email = :email")
				.setParameter("email", email).uniqueResult();
		user.password = "";
		user.token = "";
		return user;
	}
	
	public void addUser(User user) {
		try {
			user.password = Password.getSaltedHash(user.password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		Transaction tx = session.beginTransaction();
		session.save(user);
		tx.commit();
		//session.flush();
	}
	
	public void deleteUser(User user) {
		Object u = session.load(User.class, user.userId);
		if (u != null) {
			Transaction tx = session.beginTransaction();
			session.delete(u);
		    tx.commit();
		}
		session.flush();
	}
	
	public void updateUser(User user) {
		User u = (User)session.load(User.class, user.userId);
		if (u != null) {
			Transaction tx = session.beginTransaction();
			u.firstName = user.firstName;
			u.lastName = user.lastName;
			u.email = user.email;
			session.save(u);
		    tx.commit();
		}
		session.flush();
	}
	
	public void updateUserPassword(User user) {
		User u = (User)session.load(User.class, user.userId);
		if (u != null) {
			Transaction tx = session.beginTransaction();
			
			try {
				u.password = Password.getSaltedHash(user.password);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			session.save(u);
		    tx.commit();
		}
		session.flush();
	}
	
	public void updateUserRole(User user) {
		User u = (User)session.load(User.class, user.userId);
		if (u != null) {
			Transaction tx = session.beginTransaction();
			u.role = user.role;
			session.save(u);
		    tx.commit();
		}
		session.flush();
	}
}

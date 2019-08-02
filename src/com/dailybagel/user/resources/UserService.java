package com.dailybagel.user.resources;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.DatabaseHandler.DBHandler;

import java.math.BigInteger;
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException;


public class UserService {
	Session session;

	public UserService() {
		this.session = DBHandler.getSessionFactory().openSession();
	}
		
	public List<User> getAllUsers() {
		List<User> list = session.createQuery("from User").list();
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
		
		
		uvm.maxPageSize = temp;
		
		return uvm;
	}
	
	public User getUser(int userId) {
		User user = (User) session.get(User.class, userId);
		return user;
	}

	public void addUser(User user) {
		user.password = getSha(user.password);
		Transaction tx = session.beginTransaction();
		if(!this.userExists(user))
		{
			session.save(user);
			tx.commit();
			session.flush();
		}
		else {return;}
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
		user.password = getSha(user.password);
		User u = (User)session.load(User.class, user.userId);
		if (u != null) {
			Transaction tx = session.beginTransaction();
			u.password = user.password;
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
	
	private boolean userExists(User user) {
		Object u = session.load(User.class, user.userId);
		if (u == null)
			return false;
		else
			return true;
	}
	
	private String getSha(String password)
	{
        try { 
            MessageDigest md = MessageDigest.getInstance("SHA-256"); 
            byte[] messageDigest = md.digest(password.getBytes());  
            BigInteger no = new BigInteger(1, messageDigest); 
  
            // Convert message digest into hex value 
            String hashtext = no.toString(16); 
  
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
  
            return hashtext; 
        } 
  
        // For specifying wrong message digest algorithms 
        catch (NoSuchAlgorithmException e) { 
            System.out.println("Exception thrown"
                               + " for incorrect algorithm: " + e); 
  
            return null; 
        } 
    }
	
	private boolean isSignInValid(User user) {
		user.password = getSha(user.password);
		User u = (User)session.load(User.class, user.userId);
		if (u != null) {
			if(u.password.equals(user.password)) {
				session.flush();
				return true;
			}
		}
		session.flush();
		return false;
	}
		
}

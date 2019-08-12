package com.dailybagel.DatabaseHandler;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
 
public class DBHandler
{
    private static final SessionFactory sessionFactory;
    
    static {
    	try {
            sessionFactory =  new AnnotationConfiguration().configure().buildSessionFactory();
 
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
 
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static boolean testDBConnection() throws InterruptedException, IOException {
    	Session session = sessionFactory.openSession();
    	int numTries = 15;
    	
    	for (int i = 0; i < numTries; i++) {
    		try {
    			session.createSQLQuery("SELECT 1").uniqueResult();
    		} catch (Throwable e) { 
    			e.printStackTrace();
    			Thread.sleep(1000);
    			continue;
    		}
    		session.close();
    		return true;
    	}
    	session.close();
    	return false;
    }
 
    public static void shutdown() {
        getSessionFactory().close();
    }
}
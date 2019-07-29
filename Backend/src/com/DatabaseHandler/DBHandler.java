package com.DatabaseHandler;

import java.io.File;

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
 
    public static void shutdown() {
        getSessionFactory().close();
    }
}
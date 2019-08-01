package com.DatabaseHandler;

import java.io.File;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
 
import com.dailybagel.article.resources.Article;
import com.dailybagel.user.resources.User;

public class DBHandler
{
    private static final SessionFactory sessionFactory;
    
    static {
    	try {
    		Properties prop = new Properties();
    		Properties prop= new Properties();
            prop.setProperty("hibernate.connection.url", "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net/heroku_d80dc7ab129a7ce?reconnect=true");
            prop.setProperty("hibernate.connection.username", "baaa95d23db726");
            prop.setProperty("hibernate.connection.password", "de7ce257");
            prop.setProperty("dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
            sessionFactory = new AnnotationConfiguration()
            				.addPackage("com.dailybagel.user.resources")
            				.addPackage("com.dailybagel.article.resources")
            				.addAnnotatedClass(User.class)
            				.addAnnotatedClass(Article.class)
                            .addProperties(prop)                  
                            .buildSessionFactory();
            
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
package com.dailybagel.utilities;

import java.io.EOFException;

import org.hibernate.Session;

public class ServerUtil {

	public ServerUtil() {
		// TODO Auto-generated constructor stub
	}
	// we MAY need this. Unsure
	/*static void pingServer(Session session) {
		boolean ping = false;

		while(!ping) {
			try {
				session.createQuery("from Article");
				ping = true;
			}
			catch (EOFException e){
				System.out.println("ping failed");
				ping = false;
			}
		}
	}*/
}

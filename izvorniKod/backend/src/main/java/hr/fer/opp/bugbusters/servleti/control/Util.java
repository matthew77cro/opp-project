package hr.fer.opp.bugbusters.servleti.control;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Util {
	
	private static char[] characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
	private static Random rnd = new Random(System.currentTimeMillis());
	
	/**
	 * Sends the user an error as a html page with the given error message and a button
	 * to go back to the home page.
	 * @param req users request
	 * @param resp users response
	 * @param error error message for the user
	 * @throws ServletException if the target resource throws this exception
	 * @throws IOException if the target resource throws this exception
	 * @throws IllegalStateException if the response was already committed
	 */
	public static void sendError(HttpServletRequest req, HttpServletResponse resp, String error) throws ServletException, IOException {
		req.setAttribute("error", error);
		req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
		return;
	}
	
	public static String hash(String passwordToHash) {
		String generatedPassword = null;
		try {
		    // Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			//Add password bytes to digest
			md.update(passwordToHash.getBytes());
			//Get the hash's bytes 
			byte[] bytes = md.digest();
			//This bytes[] has bytes in decimal format;
			//Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for(int i=0; i< bytes.length ;i++)
			{
			    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			//Get complete hashed password in hex format
			    generatedPassword = sb.toString();
			} 
		catch (NoSuchAlgorithmException e) {
		    e.printStackTrace();
		}
		return generatedPassword;
	}
	
	public static String getRandomString(int length) {
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i<length; i++) {
			sb.append(characters[rnd.nextInt(characters.length)]);
		}
		
		return sb.toString();
	}

}

package com.cnsi.story.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.catalina.User;

/**
 * This class is used to encrypt the password such that it can be checked against 
 * password stored in the DB
 * @author turlapatip
 *
 */

public class PwdEncyptDecrypt {

	public String getEncryptedValueAsString(String p_sValueToEncrypt)
			throws NoSuchAlgorithmException, CloneNotSupportedException {
		// BASE64Encoder comes with JDK
		String encoding = new sun.misc.BASE64Encoder()
				.encode(getEncryptedValueAsByte(p_sValueToEncrypt));
		return encoding;
	}

	public byte[] getEncryptedValueAsByte(String p_sValueToEncrypt)
			throws NoSuchAlgorithmException, CloneNotSupportedException {
		// username=phani;password=phani
		
		if (p_sValueToEncrypt == null)
			System.out.println("No Value to Enrypt");
		;
		MessageDigest md = (MessageDigest) (MessageDigest.getInstance("SHA")
				.clone());
		byte dataBytes[] = p_sValueToEncrypt.getBytes();
		return md.digest(dataBytes);
	}
	
	
	  private static String windowsLogin(){
		  String username = System.getProperty("user.name");
		return username;
		  
	  }

	  public static void main(String[] args) throws Exception{

				String urlStr = "http://localhost:8080/StoryProject/jsp/index.jsp";
				String domain = ""; // May also be referred as realm
				String userName = "pturlapati";
				String password = "Dodger1234$";		

				String responseText = getAuthenticatedResponse(urlStr, domain, userName, password);

			    System.out.println("response: " + responseText);
			}


private static String getAuthenticatedResponse(final String urlStr, final String domain, final String userName, final String password) throws IOException {

    StringBuilder response = new StringBuilder();
    
    System.out.println(password.toCharArray());
    
	Authenticator.setDefault(new Authenticator() {
        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(domain + "\\" + userName, password.toCharArray());
        }
    });

    URL urlRequest = new URL(urlStr);
    HttpURLConnection conn = (HttpURLConnection) urlRequest.openConnection();
    conn.setDoOutput(true);
    conn.setDoInput(true);
    conn.setRequestMethod("GET");

    InputStream stream = conn.getInputStream();
    BufferedReader in = new BufferedReader(new InputStreamReader(stream));
    String str = "";
    while ((str = in.readLine()) != null) {
        response.append(str);
    }
    in.close();		

    return response.toString();
}
}

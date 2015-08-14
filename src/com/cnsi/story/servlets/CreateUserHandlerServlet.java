package com.cnsi.story.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cnsi.story.constants.LoginConstants;
import com.cnsi.story.dao.SecurityDao;
import com.cnsi.story.helper.PwdEncyptDecrypt;
import com.cnsi.story.trace.Trace;

/**
 * Servlet implementation class CreateUserHandlerServlet
 */

public class CreateUserHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public LoginConstants loginConstants;
	public PwdEncyptDecrypt pwdEncyptDecrypt;
    public Trace trace = new Trace();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUserHandlerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		trace.classNames(CreateUserHandlerServlet.class.getName().toString());
		if ("create".equalsIgnoreCase(request.getParameter("button"))){
			loginConstants = new LoginConstants();
			String pwd;
			try {
				pwd = new PwdEncyptDecrypt().getEncryptedValueAsString((request.getParameter("pwd")));
				loginConstants.setUsrName(request.getParameter("usrName"));
				loginConstants.setPwd(pwd);
				loginConstants.setEmail(request.getParameter("email"));
				loginConstants.setBa(request.getParameter("selectBA"));
				new SecurityDao().createUser(loginConstants); 
				RequestDispatcher dispatcher = getServletContext()
						.getRequestDispatcher("/jsp/index.jsp");
				dispatcher.forward(request, response);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{	
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/jsp/index.jsp");
			dispatcher.forward(request, response);
		}
		
		
		
		
		
		
	}

}

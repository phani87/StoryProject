package com.cnsi.story.servlets;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cnsi.story.constants.LoginConstants;
import com.cnsi.story.constants.UserCredentials;
import com.cnsi.story.dao.SecurityDao;
import com.cnsi.story.helper.PwdEncyptDecrypt;
import com.cnsi.story.trace.Trace;
import com.sun.xml.internal.bind.CycleRecoverable.Context;

/**
 * Servlet implementation class LoginRequestHandlerServlet
 */
public class LoginRequestHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public LoginConstants loginConstants;
	public Trace trace = new Trace();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginRequestHandlerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
	trace.classNames(LoginRequestHandlerServlet.class.getName().toString());
	
		try {
			System.out.println("Enter LoginRequestHandlerServlet");
			String submit = request.getParameter("submit");
			List<String> businessAreaList = new ArrayList<String>();
			if ("createUser".equalsIgnoreCase(submit)) {
				businessAreaList = new SecurityDao().getAllBusinessAreas();
				request.setAttribute("businessAreaList", businessAreaList);
				RequestDispatcher dispatcher = getServletContext()
						.getRequestDispatcher("/jsp/createUser.jsp");
				dispatcher.forward(request, response);
			} else if ("submit".equalsIgnoreCase(submit)) {
				loginConstants = new LoginConstants();
				loginConstants.setUsrName(request.getParameter("username"));
				loginConstants.setPwd(request.getParameter("password"));

				
				String hashPwd = new PwdEncyptDecrypt()
						.getEncryptedValueAsString(loginConstants.getPwd());
				loginConstants.setHashpwd(hashPwd);
				LoginConstants loginConstants2 = new SecurityDao()
						.checkUser(loginConstants);
				if (loginConstants2.isUser()) {
					businessAreaList = new SecurityDao().getAllBusinessAreas();
					UserCredentials userCredentials = new SecurityDao()
							.getUserDetails(loginConstants2);
					request.setAttribute("businessAreaList", businessAreaList);
					request.setAttribute("userCredentials", userCredentials);
					RequestDispatcher dispatcher = getServletContext()
							.getRequestDispatcher("/jsp/businessAreas.jsp");
					dispatcher.forward(request, response);
				} else {
					String error = "Invalid Username or Password, Please Re-enter";
					request.setAttribute("Error", error);
					RequestDispatcher dispatcher = getServletContext()
							.getRequestDispatcher("/jsp/index.jsp");
					dispatcher.forward(request, response);
				}
			}
		
			trace.dumpTrace();
			// System.out.println("Exit LoginRequestHandlerServlet" + isUser);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

}

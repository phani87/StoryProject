package com.cnsi.story.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cnsi.story.constants.StoryConstants;
import com.cnsi.story.constants.UserCredentials;
import com.cnsi.story.dao.SecurityDao;
import com.cnsi.story.trace.Trace;

/**
 * Servlet implementation class BusinessAreaHandlerServlet
 */

public class BusinessAreaHandlerServlet extends HttpServlet {
	public Trace trace = new Trace();
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BusinessAreaHandlerServlet() {
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
	
		trace.classNames(BusinessAreaHandlerServlet.class.getName().toString());
		UserCredentials credentials = new UserCredentials();
		credentials.setUserName(request.getParameter("userName"));
		credentials.setUserBusinessArea(request.getParameter("userBA"));
		credentials.setUserLastLogin(request.getParameter("userLastLogin"));
		String logout = request.getParameter("baFrmAction");
		if (!(logout.equals("Logout"))) {
		List<StoryConstants> StoryListForBA = new SecurityDao()
				.getAllStoriesForBA(request.getParameter("selectBA").toString());
	
	

			if (StoryListForBA.size() > 0) {
				request.setAttribute("StoryListForBA", StoryListForBA);
				request.setAttribute("credentials", credentials);
				request.setAttribute("userBA", request.getParameter("selectBA")
						.toString());
				request.setAttribute("ba", request.getParameter("selectBA").toString());
				RequestDispatcher dispatcher = getServletContext()
						.getRequestDispatcher("/jsp/storyTable.jsp");
				dispatcher.forward(request, response);
			} else {
				RequestDispatcher dispatcher = getServletContext()
						.getRequestDispatcher("/jsp/error.jsp");
				dispatcher.forward(request, response);
			}
		}else{
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/jsp/index.jsp");
			dispatcher.forward(request, response);
		}
		trace.dumpTrace();
	}

}

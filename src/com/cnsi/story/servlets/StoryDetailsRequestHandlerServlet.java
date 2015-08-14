package com.cnsi.story.servlets;

import java.io.IOException;
import java.util.ArrayList;
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
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * Servlet implementation class StoryDetailsRequestHandlerServlet
 */
public class StoryDetailsRequestHandlerServlet extends HttpServlet {
	public Trace trace = new Trace();
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StoryDetailsRequestHandlerServlet() {
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
		trace.classNames(StoryDetailsRequestHandlerServlet.class.getName().toString());
		UserCredentials credentials = new UserCredentials();
		String storyDetailsNew = (request.getParameter("storyDetailsNew"));
		credentials.setUserName(request.getParameter("userName"));
		credentials.setUserBusinessArea(request.getParameter("userBA"));
		credentials.setUserLastLogin(request.getParameter("userLastLogin"));
		List<String> businessAreaList = new ArrayList<String>();
		if (!(request.getParameter("storyID").toString().equals("Logout"))) {
			if ((!(storyDetailsNew.toString().equals("New")))
					&& (!(storyDetailsNew.toString().equals("Back")))) {
				List<StoryConstants> StoryListDetails = new SecurityDao()
						.getSelectedStoryDetails(request
								.getParameter("storyID").toString(), request
								.getParameter("storyBA".toString()));
				if (StoryListDetails.size() > 0) {
					request.setAttribute("itrList",
							new SecurityDao().getAllItrNames());
					request.setAttribute("StoryListDetails", StoryListDetails);
					request.setAttribute("credentials", credentials);
					RequestDispatcher dispatcher = getServletContext()
							.getRequestDispatcher("/jsp/storyDetails.jsp");
					dispatcher.forward(request, response);
				} else {
					RequestDispatcher dispatcher = getServletContext()
							.getRequestDispatcher("/jsp/error.jsp");
					dispatcher.forward(request, response);
				}

			} else if ((storyDetailsNew.toString().equals("New"))) {

				List<String> itrNameList = new SecurityDao().getAllItrNames();
				Integer getLastStoryID = new SecurityDao()
						.getLastStoryId(request.getParameter("ba").toString());
				Integer newStoryID = getLastStoryID + 1;
				request.setAttribute("storyIDLast", newStoryID);
				request.setAttribute("ba", request.getParameter("ba")
						.toString());
				request.setAttribute("itrNameList", itrNameList);
				request.setAttribute("credentials", credentials);
				storyDetailsNew = null;
				RequestDispatcher dispatcher = getServletContext()
						.getRequestDispatcher("/jsp/createNewStory.jsp");
				dispatcher.forward(request, response);

			} else if (storyDetailsNew.toString().equals("Back")) {

				businessAreaList = new SecurityDao().getAllBusinessAreas();

				request.setAttribute("businessAreaList", businessAreaList);
				request.setAttribute("userCredentials", credentials);
				RequestDispatcher dispatcher = getServletContext()
						.getRequestDispatcher("/jsp/businessAreas.jsp");
				dispatcher.forward(request, response);

			}
		} else {
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/jsp/index.jsp");
			dispatcher.forward(request, response);
		}
	}

}

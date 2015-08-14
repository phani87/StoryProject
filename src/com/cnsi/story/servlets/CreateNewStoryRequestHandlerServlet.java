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
 * Servlet implementation class CreateNewStoryRequestHandlerServlet
 */
@WebServlet("/CreateNewStoryRequestHandlerServlet")
public class CreateNewStoryRequestHandlerServlet extends HttpServlet {
	public Trace trace = new Trace();
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateNewStoryRequestHandlerServlet() {
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
		trace.classNames(CreateNewStoryRequestHandlerServlet.class.getName().toString());
		String formAction = request.getParameter("formAction").toString();
		String logout = request.getParameter("logout").toString();
		System.out.println(request.getParameter("storyID"));
		UserCredentials credentials = new UserCredentials();
		credentials.setUserName(request.getParameter("userName"));
		credentials.setUserBusinessArea(request.getParameter("userBA"));
		if(!(logout.equals("logout"))){
			if (formAction.equals("Save")) {
				serviceRequest(request, response, credentials);
			}else if(formAction.equals("Close") ){
				List<StoryConstants> StoryListForBA = new SecurityDao()
				.getAllStoriesForBA(request.getParameter("baPrblmInst")
						.toString());
		if (StoryListForBA.size() > 0) {
			request.setAttribute("StoryListForBA", StoryListForBA);
			request.setAttribute("credentials", credentials);
			request.setAttribute("userBA", request.getParameter("baPrblmInst")
					.toString());

			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/jsp/storyTable.jsp");
			dispatcher.forward(request, response);
		}
			}
		}else{
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/jsp/index.jsp");
			dispatcher.forward(request, response);
		}
		
	}

	private void serviceRequest(HttpServletRequest request,
			HttpServletResponse response, UserCredentials credentials)
			throws ServletException, IOException {
		System.out.println(""+request.getParameter("storyID"));
		StoryConstants cons = storyMapper(request);
		new SecurityDao().createNewStory(cons);
		List<StoryConstants> StoryListForBA = new SecurityDao()
				.getAllStoriesForBA(request.getParameter("baPrblmInst")
						.toString());
		if (StoryListForBA.size() > 0) {
			request.setAttribute("StoryListForBA", StoryListForBA);
			request.setAttribute("credentials", credentials);
			request.setAttribute("userBA", request.getParameter("baPrblmInst")
					.toString());

			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/jsp/storyTable.jsp");
			dispatcher.forward(request, response);
		}

	}

	private StoryConstants storyMapper(HttpServletRequest request)
			throws ServletException, IOException {
		StoryConstants storyConstants = new StoryConstants();
		System.out.println("" + request.getParameter("selectStoryItrName"));
		storyConstants.setStoryID(Integer.parseInt(request
				.getParameter("storyID")));
		storyConstants.setStoryBaName(request.getParameter("baPrblmInst"));
		String itrID = new SecurityDao().getItrID(request
				.getParameter("selectStoryItrName"));
		storyConstants.setStoryItrID(itrID);
		storyConstants.setStoryItrName(request.getParameter("storyItrName"));
		storyConstants.setStoryKey(request.getParameter("storyKey"));
		storyConstants.setStoryTitle(request.getParameter("storyTitle"));
		storyConstants.setStoryDesc(request.getParameter("storyDesc"));
		storyConstants.setStoryImpactArea(request
				.getParameter("storyImpactArea"));
		storyConstants.setStoryEstimatePoints(request
				.getParameter("storyEstimatePoints"));
		storyConstants.setStoryHours(request.getParameter("storyHours"));
		storyConstants.setStoryNotes(request.getParameter("storyNotes"));
		storyConstants.setStoryCreatedBy(request.getParameter("userName"));
		storyConstants.setStoryCreatedDate(request
				.getParameter("storyCreatedDate"));
		return storyConstants;

	}

}

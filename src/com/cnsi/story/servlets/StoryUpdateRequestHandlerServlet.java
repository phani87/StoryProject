package com.cnsi.story.servlets;

import java.awt.Stroke;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cnsi.story.constants.StoryConstants;
import com.cnsi.story.constants.UserCredentials;
import com.cnsi.story.dao.SecurityDao;
import com.cnsi.story.helper.MailHelper;
import com.cnsi.story.trace.Trace;

/**
 * Servlet implementation class StoryUpdateRequestHandlerServlet
 * This was created to handle update or new story request. Whenever the story is updated a mail is sent to the user.
 * There are mappers used to map the story. The story ID is created which is one more than the last ID. 
 * 
 */
public class StoryUpdateRequestHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public MailHelper mailHelper;
	public Trace trace = new Trace();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StoryUpdateRequestHandlerServlet() {
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
		trace.classNames(StoryUpdateRequestHandlerServlet.class.getName().toString());
		String formAction = request.getParameter("formAction").toString();
		UserCredentials credentials = new UserCredentials();
		credentials.setUserName(request.getParameter("userName"));
		credentials.setUserBusinessArea(request.getParameter("userBA"));
		credentials.setUserLastLogin(request.getParameter("userLastLogin"));
		String logout = request.getParameter("logout").toString();
		if (!(logout.equals("logout"))) {
			if (formAction.equals("New")) {
				List<String> itrNameList = new SecurityDao().getAllItrNames();
				Integer getLastStoryID = new SecurityDao()
						.getLastStoryId(request.getParameter("storyBaName")
								.toString());
				Integer newStoryID = getLastStoryID + 1;
				request.setAttribute("storyIDLast", newStoryID);
				request.setAttribute("ba", request.getParameter("storyBaName")
						.toString());
				request.setAttribute("itrNameList", itrNameList);
				request.setAttribute("credentials", credentials);
				RequestDispatcher dispatcher = getServletContext()
						.getRequestDispatcher("/jsp/createNewStory.jsp");
				dispatcher.forward(request, response);
			} else if (formAction.equals("Update")) {
				serviceRequest(request, response, credentials);
			} else if (formAction.equals("Close")) {
				List<StoryConstants> StoryListForBA = new SecurityDao()
						.getAllStoriesForBA(request.getParameter("storyBaName")
								.toString());
				if (StoryListForBA.size() > 0) {
					request.setAttribute("StoryListForBA", StoryListForBA);
					request.setAttribute("credentials", credentials);
					request.setAttribute("userBA",
							request.getParameter("storyBaName").toString());

					RequestDispatcher dispatcher = getServletContext()
							.getRequestDispatcher("/jsp/storyTable.jsp");
					dispatcher.forward(request, response);
				} else {
					RequestDispatcher dispatcher = getServletContext()
							.getRequestDispatcher("/jsp/error.jsp");
					dispatcher.forward(request, response);
				}
			}

		} else {
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/jsp/index.jsp");
			dispatcher.forward(request, response);
		}

	}

	private void serviceRequest(HttpServletRequest request,
			HttpServletResponse response, UserCredentials credentials)
			throws ServletException, IOException {
		StoryConstants cons = storyMapper(request);
		List<StoryConstants> tempList1 = new SecurityDao()
				.getSelectedStoryDetails(Integer.toString(cons.getStoryID()),
						cons.getStoryBaName());
		StoryConstants constants = storyMapperFromList(tempList1);
		Map<Integer, StoryConstants> tempMap = new HashMap();
		tempMap.put(1, constants);

		new SecurityDao().updateStory(cons);
		List<StoryConstants> tempList = new SecurityDao()
				.getSelectedStoryDetails(Integer.toString(cons.getStoryID()),
						cons.getStoryBaName());
		List<StoryConstants> mailList = updateComparer(tempMap,
				storyMapperFromList(tempList));
		mailHelper = new MailHelper();
		mailHelper.sendUpdateMail(mailList);

		List<StoryConstants> StoryListForBA = new SecurityDao()
				.getAllStoriesForBA(request.getParameter("storyBaName")
						.toString());
		if (StoryListForBA.size() > 0) {
			request.setAttribute("StoryListForBA", StoryListForBA);
			request.setAttribute("credentials", credentials);
			request.setAttribute("userBA", request.getParameter("storyBaName")
					.toString());

			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/jsp/storyTable.jsp");
			dispatcher.forward(request, response);
		}
	}

	private StoryConstants storyMapper(HttpServletRequest request)
			throws ServletException, IOException {
		StoryConstants storyConstants = new StoryConstants();
		storyConstants.setStoryID(Integer.parseInt(request
				.getParameter("storyId")));
		storyConstants.setStoryBaName(request.getParameter("storyBaName"));
		storyConstants.setStoryItrName(request.getParameter("selectStoryItrName"));
		String itrID = new SecurityDao().getItrID(request.getParameter("selectStoryItrName"));
		storyConstants.setStoryItrID(itrID);
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
		storyConstants.setStoryModifiedBy(request.getParameter("userName"));
		storyConstants.setStoryModifiedDate(new Date().toString());
		return storyConstants;

	}

	private List<StoryConstants> updateComparer(
			Map<Integer, StoryConstants> map1, StoryConstants constants) {
		List<StoryConstants> updatedValues = null;
		for (StoryConstants key : map1.values()) {
			updatedValues = new ArrayList<StoryConstants>();
			StoryConstants constants2 = new StoryConstants();
			constants2.setStoryID((constants.getStoryID()));
			constants2.setStoryBaName(constants.getStoryBaName());
			
			/*if (!(key.getStoryItrID().equals(constants.getStoryItrID()))) {
				constants2.setStoryItrID(constants.getStoryItrID());
			}*/
			if (!(key.getStoryItrName().equals(constants.getStoryItrName()))) {
				constants2.setStoryItrName(constants.getStoryItrName());
			}

			if (!(key.getStoryKey().equals(constants.getStoryKey()))) {
				constants2.setStoryKey(constants.getStoryKey());
			}
			if (!(key.getStoryTitle().equals(constants.getStoryTitle()))) {
				constants2.setStoryTitle(constants.getStoryTitle());
			}
			if (!(key.getStoryDesc().equals(constants.getStoryDesc()))) {
				constants2.setStoryDesc(constants.getStoryDesc());
			}
			if (!(key.getStoryImpactArea().equals(constants
					.getStoryImpactArea()))) {
				constants2.setStoryImpactArea(constants.getStoryImpactArea());
			}
			if (!(key.getStoryEstimatePoints().equals(constants
					.getStoryEstimatePoints()))) {
				constants2.setStoryEstimatePoints(constants
						.getStoryEstimatePoints());
			}
			if (!(key.getStoryHours().equals(constants.getStoryHours()))) {
				constants2.setStoryHours(constants.getStoryHours());
			}
			if (!(key.getStoryNotes().equals(constants.getStoryNotes()))) {
				constants2.setStoryNotes(constants.getStoryNotes());
			}
			if (!(key.getStoryCreatedBy().equals(constants.getStoryCreatedBy()))) {
				constants2.setStoryCreatedBy(constants.getStoryCreatedBy());
			}
			if (!(key.getStoryCreatedDate().equals(constants
					.getStoryCreatedDate()))) {
				constants2.setStoryCreatedDate(constants.getStoryCreatedDate());
			}
			if (!(key.getStoryModifiedBy().equals(constants
					.getStoryModifiedBy()))) {
				constants2.setStoryModifiedBy(constants.getStoryModifiedBy());
			}
			if (!(key.getStoryModifiedDate().equals(constants
					.getStoryModifiedDate()))) {
				constants2.setStoryModifiedDate(constants
						.getStoryModifiedDate());
			}

			updatedValues.add(constants2);

		}

		return updatedValues;

	}

	private StoryConstants storyMapperFromList(List<StoryConstants> tempList) {
		StoryConstants constants2 = null;
		for (StoryConstants constants : tempList) {
			constants2 = new StoryConstants();
			constants2.setStoryID(constants.getStoryID());
			constants2.setStoryBaName(constants.getStoryBaName());
			constants2.setStoryItrID(constants.getStoryItrID());
			constants2.setStoryItrName(constants.getStoryItrName());
			constants2.setStoryKey(constants.getStoryKey());
			constants2.setStoryTitle(constants.getStoryTitle());
			constants2.setStoryDesc(constants.getStoryDesc());
			constants2.setStoryImpactArea(constants.getStoryImpactArea());
			constants2.setStoryEstimatePoints(constants
					.getStoryEstimatePoints());
			constants2.setStoryHours(constants.getStoryHours());
			constants2.setStoryNotes(constants.getStoryNotes());
			constants2.setStoryCreatedBy(constants.getStoryCreatedBy());
			constants2.setStoryCreatedDate(constants.getStoryCreatedDate());
			constants2.setStoryModifiedBy(constants.getStoryModifiedBy());
			constants2.setStoryModifiedDate(constants.getStoryModifiedDate());
		}

		return constants2;

	}
}

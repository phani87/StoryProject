package com.cnsi.story.dao;

import java.io.IOException;
import java.util.List;

import com.cnsi.story.constants.LoginConstants;
import com.cnsi.story.constants.StoryConstants;
import com.cnsi.story.constants.UserCredentials;
import com.cnsi.story.db.LoginCheckDB;
import com.cnsi.story.servlets.BusinessAreaHandlerServlet;
import com.cnsi.story.trace.Trace;

public class SecurityDao implements ISecurityDao {
	public Trace trace = new Trace();
	public LoginCheckDB loginCheckDB = new LoginCheckDB();
	
	@Override
	public LoginConstants checkUser(LoginConstants loginConstants) throws IOException {
		trace.classNames(SecurityDao.class.getName().toString());
		LoginConstants isUser = loginCheckDB.isUserValid(loginConstants);
		return isUser;
	}

	@Override
	public List<String> getAllBusinessAreas() throws IOException {
		trace.classNames(SecurityDao.class.getName().toString());
		List<String> businessAreaList = loginCheckDB.getAllBusinessArea();
		return businessAreaList;
	}

	@Override
	public List<StoryConstants> getAllStoriesForBA(String ba) throws IOException {
		trace.classNames(SecurityDao.class.getName().toString());
		List<StoryConstants> storyListforBA = loginCheckDB.getAllStoriesForBA(ba);
		return storyListforBA;
	}

	@Override
	public List<StoryConstants> getSelectedStoryDetails(String storyID, String userBA) throws IOException {
		trace.classNames(SecurityDao.class.getName().toString());
		List<StoryConstants> storyListDetails = loginCheckDB.getStoryDetails(storyID, userBA);
		return storyListDetails;
	}

	@Override
	public UserCredentials getUserDetails(LoginConstants log) throws IOException {
		trace.classNames(SecurityDao.class.getName().toString());
		UserCredentials  userCredentials = loginCheckDB.getUserCredentials(log);
		return userCredentials;
	}

	@Override
	public void createNewStory(StoryConstants constants) throws IOException {
		trace.classNames(SecurityDao.class.getName().toString());
		loginCheckDB.createNewStory(constants);
		
	}

	@Override
	public List<String> getAllItrNames() throws IOException {
		trace.classNames(SecurityDao.class.getName().toString());
		List<String> itrNamesList = loginCheckDB.getAllItrName();
		return itrNamesList;
		
	}

	@Override
	public Integer getLastStoryId(String baName) throws IOException {
		trace.classNames(SecurityDao.class.getName().toString());
		Integer storyListforBA = loginCheckDB.getLastStoryID(baName);
		return storyListforBA;
	}

	@Override
	public String getItrID(String itrName) throws IOException {
		trace.classNames(SecurityDao.class.getName().toString());
		String itrID = loginCheckDB.getItrID(itrName);
		return itrID;
	}

	@Override
	public void updateStory(StoryConstants constants) throws IOException {
		trace.classNames(SecurityDao.class.getName().toString());
		loginCheckDB.updateStory(constants);
		
	}

	@Override
	public void createUser(LoginConstants constants) throws IOException {
		trace.classNames(SecurityDao.class.getName().toString());
		loginCheckDB.createNewUser(constants);
		
	}

	

}

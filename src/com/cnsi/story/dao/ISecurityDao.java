package com.cnsi.story.dao;

import java.io.IOException;
import java.util.List;

import com.cnsi.story.constants.LoginConstants;
import com.cnsi.story.constants.StoryConstants;
import com.cnsi.story.constants.UserCredentials;

public interface ISecurityDao {

	public LoginConstants checkUser(LoginConstants constants) throws IOException;
	
	public UserCredentials getUserDetails(LoginConstants log) throws IOException;
	
	public List<String> getAllBusinessAreas() throws IOException;
	
	public List<StoryConstants> getAllStoriesForBA(String ba) throws IOException;
	
	public List<StoryConstants> getSelectedStoryDetails(String storyID, String userBA) throws IOException;
	
	public void createNewStory(StoryConstants constants) throws IOException;
	
	public List<String> getAllItrNames() throws IOException;
	
	public Integer getLastStoryId(String baName) throws IOException;
	
	public String getItrID(String itrName) throws IOException;
	
	public void updateStory(StoryConstants constants) throws IOException;
	
	public void createUser(LoginConstants constants) throws IOException;
	

}

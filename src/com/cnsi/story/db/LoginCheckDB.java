package com.cnsi.story.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cnsi.story.constants.LoginConstants;
import com.cnsi.story.constants.StoryConstants;
import com.cnsi.story.constants.UserCredentials;
import com.cnsi.story.dao.SecurityDao;
import com.cnsi.story.helper.UniqueIdGenerator;
import com.cnsi.story.trace.Trace;

/**
 * Java Class: LoginCheckDB. This class was created to handle the Database
 * requests. There are several methods that perform CRUD operations on the
 * requests There are helper classes that allow connection to the DB using
 * properties file.
 * 
 * @author turlapatip
 * 
 */

public class LoginCheckDB {

	public ConnectionDB connectionDB = new ConnectionDB();
	public PreparedStatement preparedStatement;
	public ResultSet resultSet;
	private boolean isUser;
	public UniqueIdGenerator idGenerator;
	public Trace trace = new Trace();
	public LoginConstants isUserValid(LoginConstants constants) throws IOException {
		trace.classNames(LoginCheckDB.class.getName().toString());
		trace.dumpStacks();
		Connection connection = null;
		LoginConstants constants2 = null;
		try {
			connection = connectionDB.getConnection();

			preparedStatement = connection
					.prepareStatement(QueriesSQL.CHECKUSER);
			preparedStatement.setString(1, constants.getUsrName());
			preparedStatement.setString(2, constants.getHashpwd());

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				int validUser = resultSet.getInt(1);
				if (validUser == 1) {
					updateLastLogin(resultSet.getString(3));
					constants2 = new LoginConstants();
					constants2.setUser(true);
					constants2.setUsrName(resultSet.getString(2));
					constants2.setUniqueUserID(resultSet.getString(3));
				} else {
					constants2 = new LoginConstants();
					constants2.setUser(false);
				}
			}
			connectionDB.closeConnection(connection, resultSet,
					preparedStatement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return constants2;

	}

	private void updateLastLogin(String uUserId) throws IOException {
		trace.classNames(LoginCheckDB.class.getName().toString());
		trace.dumpStacks();
		Date date = new Date();
		System.out.println(date.toString());

		Connection connection = null;
		try {
			connection = connectionDB.getConnection();

			preparedStatement = connection
					.prepareStatement(QueriesSQL.LASTLOGIN);
			preparedStatement.setString(1, date.toString());
			preparedStatement.setString(2, uUserId);

			preparedStatement.executeUpdate();

			connectionDB.closeConnection(connection, preparedStatement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public UserCredentials getUserCredentials(LoginConstants constants) throws IOException {
		trace.classNames(LoginCheckDB.class.getName().toString());
		trace.dumpStacks();
		Connection connection = null;
		UserCredentials userCredentials = null;
		try {
			connection = connectionDB.getConnection();

			preparedStatement = connection
					.prepareStatement(QueriesSQL.GETUSERDETAILS);
			preparedStatement.setString(1, constants.getUniqueUserID());
			preparedStatement.setString(2, constants.getUniqueUserID());

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				userCredentials = new UserCredentials();
				userCredentials.setUserName(resultSet.getString(1));
				userCredentials.setUserLastLogin(resultSet.getString(4));
				userCredentials.setUserBusinessArea(resultSet.getString(3));
			}
			connectionDB.closeConnection(connection, resultSet,
					preparedStatement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userCredentials;

	}

	public void createNewUser(LoginConstants constants) throws IOException {
		trace.classNames(LoginCheckDB.class.getName().toString());
		trace.dumpStacks();
		Connection connection = null;
		idGenerator = new UniqueIdGenerator();
		String userId = idGenerator.createUniqueID();
		try {
			connection = connectionDB.getConnection();

			preparedStatement = connection
					.prepareStatement(QueriesSQL.CREATEUSER);
			preparedStatement.setString(1, constants.getUsrName());
			preparedStatement.setString(2, constants.getPwd());
			preparedStatement.setString(3, userId);
			preparedStatement.setString(4, constants.getEmail());
			preparedStatement.executeUpdate();
			updateUserXBusinessTable(constants, userId);
			connectionDB.closeConnection(connection, preparedStatement);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void updateUserXBusinessTable(LoginConstants constants,
			String userId) throws SQLException, IOException {
		trace.classNames(LoginCheckDB.class.getName().toString());
	trace.dumpStacks();
		Connection connection = null;
		connection = connectionDB.getConnection();
		preparedStatement = connection
				.prepareStatement(QueriesSQL.INSERTUSERXBUSINESS);
		preparedStatement.setString(1, constants.getUsrName());
		preparedStatement.setString(2, constants.getBa());
		preparedStatement.setString(3, "");
		preparedStatement.setString(4, userId);
		preparedStatement.executeUpdate();
		connectionDB.closeConnection(connection, preparedStatement);
	}

	public UserCredentials createNewStory(StoryConstants constants) throws IOException {
		trace.classNames(LoginCheckDB.class.getName().toString());
		trace.dumpStacks();
		Connection connection = null;
		UserCredentials userCredentials = null;
		try {
			connection = connectionDB.getConnection();

			String tableName = getTableName(constants.getStoryBaName());

			String sql = "INSERT INTO "
					+ tableName
					+ " (`story_id`,`story_itr_id`, `story_ba_name`, `story_key`, `story_title`, `story_desc`, `story_impactarea`, `story_estPoints`, `story_hours`, `story_sampleStoryFlag`, `story_notes`, `story_createdby`, `story_createddate`, `story_modifiedBy`, `story_modifiedDate`, `story_builtCRNotes`, `story_complete`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, constants.getStoryID());
			preparedStatement.setString(2, constants.getStoryItrID());
			preparedStatement.setString(3, constants.getStoryBaName());
			preparedStatement.setString(4, constants.getStoryKey());
			preparedStatement.setString(5, constants.getStoryTitle());
			preparedStatement.setString(6, constants.getStoryDesc());
			preparedStatement.setString(7, constants.getStoryImpactArea());
			preparedStatement.setString(8, constants.getStoryEstimatePoints());
			preparedStatement.setString(9, constants.getStoryHours());
			preparedStatement
					.setBoolean(10, constants.isStorySampleStoryFlag());
			preparedStatement.setString(11, constants.getStoryNotes());
			preparedStatement.setString(12, constants.getStoryCreatedBy());
			preparedStatement.setString(13, constants.getStoryCreatedDate());
			preparedStatement.setString(14, constants.getStoryModifiedBy());
			preparedStatement.setString(15, constants.getStoryModifiedDate());
			preparedStatement.setString(16, constants.getStoryBuiltCRNotes());
			preparedStatement.setBoolean(17, constants.isStoryComplete());
			preparedStatement.executeUpdate();
			connectionDB.closeConnection(connection, resultSet,
					preparedStatement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userCredentials;

	}

	public void updateStory(StoryConstants constants) throws IOException {
		trace.classNames(LoginCheckDB.class.getName().toString());
		trace.dumpStacks();
		Connection connection = null;
		UserCredentials userCredentials = null;
		try {
			connection = connectionDB.getConnection();

			String tableName = getTableName(constants.getStoryBaName());

			String sql = "UPDATE  "
					+ tableName
					+ " SET `story_itr_id` = ?, `story_ba_name` = ?, `story_key` = ?, `story_title` = ?, `story_desc` = ?, `story_impactarea` = ?, `story_estPoints` = ?, `story_hours` = ?, `story_sampleStoryFlag` = ?, `story_notes` = ?, `story_createdby` = ?, `story_createddate` = ?, `story_modifiedBy` = ?, `story_modifiedDate` = ?, `story_builtCRNotes` = ?, `story_complete` = ? WHERE `story_id` = ? ";

			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, constants.getStoryItrID());
			preparedStatement.setString(2, constants.getStoryBaName());
			preparedStatement.setString(3, constants.getStoryKey());
			preparedStatement.setString(4, constants.getStoryTitle());
			preparedStatement.setString(5, constants.getStoryDesc());
			preparedStatement.setString(6, constants.getStoryImpactArea());
			preparedStatement.setString(7, constants.getStoryEstimatePoints());
			preparedStatement.setString(8, constants.getStoryHours());
			preparedStatement.setBoolean(9, constants.isStorySampleStoryFlag());
			preparedStatement.setString(10, constants.getStoryNotes());
			preparedStatement.setString(11, constants.getStoryCreatedBy());
			preparedStatement.setString(12, constants.getStoryCreatedDate());
			preparedStatement.setString(13, constants.getStoryModifiedBy());
			preparedStatement.setString(14, constants.getStoryModifiedDate());
			preparedStatement.setString(15, constants.getStoryBuiltCRNotes());
			preparedStatement.setBoolean(16, constants.isStoryComplete());
			preparedStatement.setInt(17, constants.getStoryID());
			preparedStatement.executeUpdate();
			connectionDB.closeConnection(connection, resultSet,
					preparedStatement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<String> getAllItrName() throws IOException {
		trace.classNames(LoginCheckDB.class.getName().toString());
		trace.dumpStacks();
		Connection connection = null;
		List<String> itrNameList = new ArrayList<String>();
		try {
			connection = connectionDB.getConnection();

			preparedStatement = connection
					.prepareStatement(QueriesSQL.ALLITRNAME);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				itrNameList.add(resultSet.getString(1));

			}
			connectionDB.closeConnection(connection, resultSet,
					preparedStatement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return itrNameList;

	}

	public List<String> getAllBusinessArea() throws IOException {
		trace.classNames(LoginCheckDB.class.getName().toString());
		trace.dumpStacks();
		Connection connection = null;
		List<String> businessAreaList = new ArrayList<String>();
		try {
			connection = connectionDB.getConnection();

			preparedStatement = connection
					.prepareStatement(QueriesSQL.BUSINESSAREA);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				businessAreaList.add(resultSet.getString(1));

			}
			connectionDB.closeConnection(connection, resultSet,
					preparedStatement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return businessAreaList;

	}

	public List<StoryConstants> getAllStoriesForBA(String ba) throws IOException {
		Connection connection = null;
		List<StoryConstants> storyListForBA = new ArrayList<StoryConstants>();
		trace.classNames(LoginCheckDB.class.getName().toString());
		trace.dumpStacks();
		try {
			connection = connectionDB.getConnection();

			String tableName = getTableName(ba);
			String sql = "SELECT story_id, story_itr_id, story_ba_name, story_key, story_title, story_desc, story_impactarea, story_estPoints, story_hours, story_sampleStoryFlag, story_notes, story_createdby, story_createddate, story_modifiedBy, story_modifiedDate, story_builtCRNotes, story_complete FROM "
					+ tableName + " where story_ba_name = ?";
			System.out.println(sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, ba);
			ResultSet resultSet1 = preparedStatement.executeQuery();

			storyListForBA.clear();

			while (resultSet1.next()) {
				StoryConstants constants = new StoryConstants();
				constants.setStoryID(resultSet1.getInt(1));
				constants.setStoryBaName(resultSet1.getString(3));
				constants.setStoryTitle(resultSet1.getString(5));
				if (resultSet1.getString(2) != null) {
					constants.setStoryItrName(getItrName(resultSet1
							.getString(2)));

				} else {
					constants.setStoryItrName("");
				}
				storyListForBA.add(constants);

			}
			connectionDB.closeConnection(connection, resultSet1,
					preparedStatement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return storyListForBA;

	}

	public Integer getLastStoryID(String ba) throws IOException {
		trace.classNames(LoginCheckDB.class.getName().toString());
		trace.dumpStacks();
		Connection connection = null;
		Integer storyID = 0;
		List<StoryConstants> storyListForBA = new ArrayList<StoryConstants>();
		try {
			connection = connectionDB.getConnection();

			String tableName = getTableName(ba);
			String sql = "SELECT story_id FROM " + tableName
					+ " ORDER BY story_id DESC " + "LIMIT 1";
			System.out.println(sql);
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			storyListForBA.clear();

			while (resultSet.next()) {
				storyID = resultSet.getInt(1);

			}
			connectionDB.closeConnection(connection, resultSet,
					preparedStatement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return storyID;

	}

	private String getTableName(String ba) throws IOException {
	trace.classNames(LoginCheckDB.class.getName().toString());
		trace.dumpStacks();
		Connection connection = null;
		String storyTableName = "";
		try {
			connection = connectionDB.getConnection();
			preparedStatement = connection
					.prepareStatement(QueriesSQL.GETSTORIESBYBUSINESSAREA);
			preparedStatement.setString(1, ba);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				storyTableName = resultSet.getString(1);
			}
			connectionDB.closeConnection(connection, resultSet,
					preparedStatement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return storyTableName;

	}

	public List<StoryConstants> getStoryDetails(String storyID, String usrBA) throws IOException {
		trace.classNames(LoginCheckDB.class.getName().toString());
		trace.dumpStacks();
		Connection connection = null;
		List<StoryConstants> storyListForBA = new ArrayList<StoryConstants>();
		try {
			connection = connectionDB.getConnection();
			String tableName = getTableName(usrBA);

			String sql = "SELECT story_id, story_itr_id, story_ba_name, story_key, story_title, story_desc, story_impactarea, story_estPoints, story_hours, story_sampleStoryFlag, story_notes, story_createdby, story_createddate, story_modifiedBy, story_modifiedDate, story_builtCRNotes, story_complete FROM "
					+ tableName + " where story_id = ?";

			System.out.println(sql);

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.parseInt(storyID));
			resultSet = preparedStatement.executeQuery();

			storyListForBA.clear();

			while (resultSet.next()) {
				StoryConstants constants = new StoryConstants();
				constants.setStoryID(resultSet.getInt(1));
				constants.setStoryBaName(resultSet.getString(3));
				constants.setStoryCreatedBy(resultSet.getString(12));
				constants.setStoryCreatedDate(resultSet.getString(13));
				constants.setStoryDesc(resultSet.getString(6));
				constants.setStoryEstimatePoints(resultSet.getString(8));
				constants.setStoryHours(resultSet.getString(9));
				constants.setStoryImpactArea(resultSet.getString(7));
				constants.setStoryItrID(resultSet.getString(2));
				constants.setStoryKey(resultSet.getString(4));
				constants.setStoryModifiedBy(resultSet.getString(14));
				constants.setStoryModifiedDate(resultSet.getString(15));
				constants.setStoryNotes(resultSet.getString(11));
				constants.setStorySampleStoryFlag(resultSet.getBoolean(10));
				constants.setStoryTitle(resultSet.getString(5));
				constants.setStoryBuiltCRNotes(resultSet.getString(16));
				constants.setStoryComplete(resultSet.getBoolean(17));
				constants.setStoryItrName(getItrName(resultSet.getString(2)));
				storyListForBA.add(constants);

			}
			connectionDB.closeConnection(connection, resultSet,
					preparedStatement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return storyListForBA;
	}

	private String getItrName(String itrId) throws IOException {
		trace.classNames(LoginCheckDB.class.getName().toString());
		trace.dumpStacks();
		Connection connection = null;
		String itrName = "";
		List<StoryConstants> storyListForBA = new ArrayList<StoryConstants>();
		try {
			connection = connectionDB.getConnection();
			preparedStatement = connection.prepareStatement(QueriesSQL.ITRNAME);
			preparedStatement.setString(1, itrId);
			resultSet = preparedStatement.executeQuery();

			storyListForBA.clear();

			while (resultSet.next()) {
				itrName = resultSet.getString(1);

			}
			connectionDB.closeConnection(connection, resultSet,
					preparedStatement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return itrName;
	}

	public String getItrID(String itrName) throws IOException {
		trace.classNames(LoginCheckDB.class.getName().toString());
		trace.dumpStacks();
		Connection connection = null;
		String itrID = "";
		try {
			connection = connectionDB.getConnection();

			preparedStatement = connection.prepareStatement(QueriesSQL.ITRID);
			preparedStatement.setString(1, itrName);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				itrID = resultSet.getString(1);

			}
			connectionDB.closeConnection(connection, resultSet,
					preparedStatement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return itrID;
	}

}

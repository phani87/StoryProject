package com.cnsi.story.db;

public class QueriesSQL {

	public static String CHECKUSER = "SELECT COUNT(user_id), user_id, user_unique_id FROM USER_TABLE WHERE user_id = ? AND user_pwd = ?";
	
	public static String LASTLOGIN = "UPDATE `storydb`.`user_x_business` SET `user_lastLogin` = ? WHERE `usr_unique_id` = ?";
	public static String CREATEUSER = "INSERT INTO user_table(user_Id, user_pwd, user_unique_id, user_emai_id) VALUES (?,?,?,?)";
	public static String INSERTUSERXBUSINESS = "INSERT INTO `storydb`.`user_x_business`(`user_userName`, `user_busineesArea_id`, `user_lastLogin` , `usr_unique_id`) VALUES(?, (SELECT ba_id FROM business_area WHERE ba_name = ?), ?, ?);";
	public static String GETUSERDETAILS = "SELECT ux.user_userName, ux.usr_unique_id, (SELECT ba_name FROM business_area ba, user_x_business uxb WHERE uxb.user_busineesArea_id = ba.ba_id AND uxb.usr_unique_id = ?) AS businessArea, user_lastLogin FROM user_x_business ux WHERE ux.usr_unique_id = ?"; 
	public static String BUSINESSAREA = "SELECT ba_name FROM Business_Area";
	public static String GETSTORIESBYBUSINESSAREA = "SELECT b_x_s_tableName FROM storydb.businessarea_x_story_table where b_x_s_ba = ?";
	public static String ALLSTORIESFORBA = "SELECT story_id, story_itr_id, story_ba_name, story_key, story_title, story_desc, story_impactarea, story_estPoints, story_hours, story_sampleStoryFlag, story_notes, story_createdby, story_createddate, story_modifiedBy, story_modifiedDate, story_builtCRNotes, story_complete FROM ? where story_ba_name = ?";
	public static String GETDETAILSOFSTORY = "SELECT story_id, story_itr_id, story_ba_name, story_key, story_title, story_desc, story_impactarea, story_estPoints, story_hours, story_sampleStoryFlag, story_notes, story_createdby, story_createddate, story_modifiedBy, story_modifiedDate, story_builtCRNotes, story_complete FROM storydb.story_table where story_id = ?";
	public static String ITRNAME= "SELECT itr_name FROM storydb.iteration_table WHERE itr_id = ?";
	public static String ITRID= "SELECT itr_id FROM storydb.iteration_table WHERE itr_name = ?";
	public static String ALLITRNAME="SELECT itr_name FROM storydb.iteration_table";
	public static String CREATENEWSTORY = "INSERT INTO `storydb`.`story_table` (`story_id`,`story_itr_id`, `story_ba_name`, `story_key`, `story_title`, `story_desc`, `story_impactarea`, `story_estPoints`, `story_hours`, `story_sampleStoryFlag`, `story_notes`, `story_createdby`, `story_createddate`, `story_modifiedBy`, `story_modifiedDate`, `story_builtCRNotes`, `story_complete`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
	
}

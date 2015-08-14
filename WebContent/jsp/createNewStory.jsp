<%@page import="com.cnsi.story.constants.*"%>
<%@page import="java.util.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create New Story</title>
<link rel="stylesheet" href="/StoryProject/resources/css/form.css">
<link rel="stylesheet" href="/StoryProject/resources/css/button.css">
<script type="text/javascript"
	src="/StoryProject/resources/js/logout.js"></script>
<script type="text/javascript">
	function submitForm(e) {
		var c = e.target.value;
		document.getElementById("strFrmAction").value = c;
		document.forms["createStoryFrm"].submit();
	}

	function logout(event) {
		var c = event.target.name;
		document.getElementById("logout").value = c;
		document.forms["createStoryFrm"].submit();
		logout
	}
</script>
<link rel="stylesheet" href="/StoryProject/resources/css/footer.css">
</head>

<div class="page-wrap">
<body>
	<%
		UserCredentials credentials = (UserCredentials) request
				.getAttribute("credentials");
		List<String> itrNameList = (List<String>) request
				.getAttribute("itrNameList");
		List<String> baNameList = (List<String>) request
				.getAttribute("baList");
		String baName = (String) request.getAttribute("ba");
		Integer newStoryID = (Integer) request.getAttribute("storyIDLast");
	%>
	<form action="/StoryProject/CreateNewStoryHandler" id="createStoryFrm"
		method="post" class="dark-matter">
		<div class="log">
			<p>
				Logged in as
				<%=credentials.getUserName()%>
				<a onclick="logout(event)" name="logout">Logout</a>
			</p>
		</div>
		<table>
			<tr>
				<td><label>Story ID</label></td>
				<td><input type="text" value="<%=newStoryID%>" name="storyID">
				</td>
			</tr>
			<tr>
				<td><label>Business Area Name</label></td>
				<td><input type="text" readonly="readonly" value="<%=baName%>">
				</td>
			</tr>
			<tr>
				<td><label>Iteration ID</label></td>
				<td><input type="text" name="storyItrId"></td>
			</tr>
			<tr>
				<td><label>Iteration Name</label></td>
				<td><select name="selectStoryItrName">
						<%
							for (String itrName : itrNameList) {
						%>
						<option><%=itrName.toUpperCase()%></option>
						<%
							}
						%>
				</select></td>
			</tr>
			<tr>
				<td><label>Key</label></td>
				<td><input type="text" value="" name="storyKey"></td>
			</tr>
			<tr>
				<td><label>Title</label></td>
				<td><input type="text" value="" name="storyTitle"></td>
			</tr>
			<tr>
				<td><label>Description</label></td>
				<td><textarea rows="3" cols="21" name="storyDesc"></textarea> <%-- <input type="text" value="<%=sc.getStoryDesc()%>" readonly="readonly">  --%>
				</td>
			</tr>
			<tr>
				<td><label>Impacted Area</label></td>
				<td><input type="text" value="" name="storyImpactArea">
				</td>
			</tr>
			<tr>
				<td><label>Estimate Points</label></td>
				<td><input type="text" value="" name="storyEstimatePoints">
				</td>
			</tr>
			<tr>
				<td><label>Hours</label></td>
				<td><input type="text" value="" name="storyHours"></td>
			</tr>
			<tr>
				<td><label>Notes</label></td>
				<td><textarea rows="4" cols="21" name="storyNotes"></textarea>
				</td>
			</tr>
			<tr>
				<td><label>Created By</label></td>
				<td><input type="text" value="<%=credentials.getUserName()%>"
					name="storyCreatedBy" readonly="readonly"></td>
			</tr>
			<tr>
				<td><label>Created Date</label></td>
				<td><input type="text" value="" name="storyCreatedDate">
				</td>
			</tr>
			<tr>
				<td><label>Modified By</label></td>
				<td><input type="text" value="" name="storyModifiedBy">
				</td>
			</tr>
			<tr>
				<td><label>Modified Date</label></td>
				<td><input type="text" value="" name="storyModifiedDate">
				</td>
			</tr>
			<tr>
				<td><label>Built CR/Notes</label></td>
				<td><textarea rows="4" cols="21" name="storyBuiltCRNotes"></textarea>
					<%--  <input type="text" value="<%=sc.getStoryBuiltCRNotes()%>" >  --%></td>
			</tr>

		</table>
		<table>
			<tr>
				<td><input type="button" name="submitAction" value="Save"
					onclick="submitForm(event)" class="button"></td>
				<td><input type="button" name="submitAction" value="Close"
					onclick="submitForm(event)" class="button"></td>
			</tr>
		</table>
		<input type="hidden" id="strFrmAction" value="" name="formAction">
		<input type="hidden" value="<%=credentials.getUserName()%>"
			name="userName"> <input type="hidden"
			value="<%=credentials.getUserBusinessArea()%>" name="userBA">
		<input type="hidden" value="<%=newStoryID%>" name="newStoryID">
		<input type="hidden" value="" name="logout" id="logout"> <input
			type="hidden" value="<%=baName%>" name="baPrblmInst">
	</form>
	</div>



</body>
</div>
<footer class="site-footer">
Last Logged In Time:<%=credentials.getUserLastLogin()%>
</footer>
</html>
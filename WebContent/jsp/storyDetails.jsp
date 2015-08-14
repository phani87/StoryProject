<%@page import="com.cnsi.story.dao.SecurityDao"%>
<%@page import="java.util.List"%>
<%@page import="com.cnsi.story.constants.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="/StoryProject/resources/js/logout.js"></script>
<title>Insert title here</title>

<link rel="stylesheet" href="/StoryProject/resources/css/form.css">
<link rel="stylesheet" href="/StoryProject/resources/css/footer.css">

<script type="text/javascript">
	function submitForm(e) {
		var c = e.target.value;

		document.getElementById("strFrmAction").value = c;
		document.forms["storyDetailForm"].submit();

	}

	function logout(e) {
		var c = e.target.name;
		document.getElementById("logout").value = c;
		document.forms["storyDetailForm"].submit();
	}
</script>
</head>
<body>
	<div class="container">
		<%
			List<StoryConstants> StoryList = (List<StoryConstants>) request
					.getAttribute("StoryListDetails");
			UserCredentials credentials = (UserCredentials) request
					.getAttribute("credentials");
			List<String> itrList = (List<String>) request
					.getAttribute("itrList");
		%>

		<form action="/StoryProject/StoryUpdateRequestHandler"
			id="storyDetailForm" method="post" class="dark-matter">
			<p>
				Logged in as
				<%=credentials.getUserName()%>
				<a onclick="logout(event)" name="logout">Logout</a><br>
			</p>

			<table>
				<%
					for (StoryConstants sc : StoryList) {
				%>
				<tr>

					<td><label>Story ID</label></td>
					<td><div class="form-group">
							<input type="text" value="<%=sc.getStoryID()%>"
								readonly="readonly">
						</div> <input type="hidden" value="<%=sc.getStoryID()%>" name="storyId"></td>
					</div>
				</tr>
				<tr>
					<td><label>Business Area Name</label></td>
					<td><input type="text" value="<%=sc.getStoryBaName()%>">
						<input type="hidden" value="<%=sc.getStoryBaName()%>"
						name="storyBaName"></td>
				</tr>
				<tr>
					<td><label>Iteration Name</label></td>
					<td><select name="selectStoryItrName">
							<%
								for (String itrName : itrList) {
							%>
							<option
								<%=(sc.getStoryItrName().equals(itrName) ? "selected"
							: "")%>><%=itrName.toUpperCase()%></option>
							<%
								}
							%>
					</select></td>
				</tr>
				<tr>
					<td><label>Key</label></td>
					<td><input type="text" value="<%=sc.getStoryKey()%>"
						name="storyKey"></td>
				</tr>
				<tr>
					<td><label>Title</label></td>
					<td><input type="text" value="<%=sc.getStoryTitle()%>"
						name="storyTitle"></td>
				</tr>
				<tr>
					<td><label>Description</label></td>
					<td><textarea rows="3" cols="21" name="storyDesc"><%=sc.getStoryDesc()%></textarea>
						<%-- <input type="text" value="<%=sc.getStoryDesc()%>" readonly="readonly">  --%>
				</tr>
				<tr>
					<td><label>Impacted Area</label></td>
					<td><input type="text" value="<%=sc.getStoryImpactArea()%>"
						name="storyImpactArea"></td>
				</tr>
				<tr>
					<td><label>Estimate Points</label></td>
					<td><input type="text"
						value="<%=sc.getStoryEstimatePoints()%>"
						name="storyEstimatePoints"></td>
				</tr>
				<tr>
					<td><label>Hours</label></td>
					<td><input type="text" value="<%=sc.getStoryHours()%>"
						name="storyHours">
				</tr>
				<tr>
					<td><label>Notes</label></td>
					<td><textarea rows="4" cols="21" name="storyNotes"><%=sc.getStoryNotes()%></textarea>
					</td>
				</tr>
				<tr>
					<td><label>Created By</label></td>
					<td><input type="text" value="<%=sc.getStoryCreatedBy()%>">
						<input type="hidden" value="<%=sc.getStoryCreatedBy()%>"
						name="storyCreatedBy"></td>
				</tr>
				<tr>
					<td><label>Created Date</label></td>
					<td><input type="text" value="<%=sc.getStoryCreatedDate()%>">
					</td>
					<input type="hidden" value="<%=sc.getStoryCreatedDate()%>"
						name="storyCreatedDate">
				</tr>
				<tr>
					<td><label>Modified By</label></td>
					<td><input type="text" value="<%=sc.getStoryModifiedBy()%>">
					</td>
				</tr>
				<tr>
					<td><label>Modified Date</label></td>
					<td><input type="text" value="<%=sc.getStoryModifiedDate()%>">
					</td>
				</tr>
				<tr>
					<td><label>Built CR/Notes</label></td>
					<td><textarea rows="4" cols="21"><%=sc.getStoryBuiltCRNotes()%></textarea>
						<%--  <input type="text" value="<%=sc.getStoryBuiltCRNotes()%>" >  --%></td>
				</tr>

				<%
					}
				%>
			</table>
			<table>
				<tr>
					<td><input type="button" name="submitAction" value="Update"
						onclick="submitForm(event)" class="button"></td>
					<td><input type="button" name="submitAction" value="New"
						onclick="submitForm(event)" class="button"></td>
					<td><input type="button" name="submitAction" value="Close"
						onclick="submitForm(event)" class="button"></td>
				</tr>
			</table>
			<input type="hidden" id="strFrmAction" value="" name="formAction">
			<input type="hidden" value="<%=credentials.getUserName()%>" name="userName"> 
			<input type="hidden" value="<%=credentials.getUserBusinessArea()%>" name="userBA">
			<input type="hidden" value="<%=credentials.getUserLastLogin()%>" name="userLastLogin">
			<input type="hidden" id="logout" value="" name="logout">

		</form>


	</div>
</body>

<footer class="site-footer">
<p>Last Logged
				In Time:<%=credentials.getUserLastLogin()%></p>
</footer>

</html>
<%@page import="com.cnsi.story.constants.UserCredentials"%>
<%@page import="com.cnsi.story.constants.StoryConstants"%>
<%@page import="com.cnsi.story.dao.SecurityDao"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Story Table</title>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="/StoryProject/resources/js/logout.js"></script>
<script type="text/javascript" src="/StoryProject/resources/js/pagination.js"></script>
<link rel="stylesheet" href="/StoryProject/resources/css/footer.css">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link href="/StoryProject/resources/css/table.css" rel="stylesheet">
<link href="/StoryProject/resources/css/header.css" rel="stylesheet">

<link rel="stylesheet" href="http://cdn.datatables.net/1.10.7/css/jquery.dataTables.css">
<script  src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="http://cdn.datatables.net/1.10.7/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
	function getStoryDetails(event) {
		var e = event.target.innerHTML;
		document.getElementById("storyID").value = e;
		document.getElementById("storyDetailNew").value = "";
		document.forms["storyForm"].submit();
	}

	function submitForm(event) {
		var e = event.target.value;
		document.getElementById("storyDetailNew").value = e;
		document.forms["storyForm"].submit();
	}
</script>

</head>
<%
		List<StoryConstants> StoryList = (List<StoryConstants>) request.getAttribute("StoryListForBA");
		UserCredentials credentials = (UserCredentials) request.getAttribute("credentials");
		String usrBA = (String) request.getAttribute("userBA").toString();
		String ba = (String) request.getAttribute("ba");
	%>
<div class="header">
		<p>
			Logged in as <%=credentials.getUserName()%>
			<a onclick="getStoryDetails(event)" name="Logout">Logout</a></br>
		</p>
	</div>
<body>
		
	<div class="container">
		<form action="/StoryProject/StoryDetailsRequestHandler" method="post" id="storyForm">
			<div style="margin-left: 950px;">
				<input type="button" name="submitAction" onclick="submitForm(event)" class="button" value="New"> 
				<input type="button" name="submitAction" value="Back" onclick="submitForm(event)" class="button">
			</div>
			<div class=datagrid>
				<table id="storyTable" class="display" cellspacing="0" width="100%">
					<thead>
						<tr>
							<th>Story ID</th>
							<th>Business Area</th>
							<th>Story Title</th>
							<th>Iteration Name</th>
						</tr>
					</thead>
					<tbody>
						<%
							for (StoryConstants sc : StoryList) {
						%>

						<tr>
							<td><a href="#" onclick="getStoryDetails(event)"><%=sc.getStoryID()%></a></td>
							<td><%=sc.getStoryBaName()%></td>
							<td><%=sc.getStoryTitle()%></td>
							<td><%=sc.getStoryItrName()%></td>
						</tr>
						<%
							}
						%>
					</tbody>
				</table>
			</div>
			<input type="hidden" id="storyID" value="" name="storyID"> 
			<input type="hidden" value="<%=credentials.getUserName()%>" name="userName">
			<input type="hidden" value="<%=credentials.getUserBusinessArea()%>" name="userBA"> 
			<input type="hidden" value="<%=usrBA%>"	name="storyBA" id="storyBA"> 
			<input type="hidden" value="<%=credentials.getUserLastLogin()%>" name="userLastLogin" id="userLastLogin">
			<input type="hidden" id="storyDetailNew" value="" name="storyDetailsNew"> 
			<input type="hidden" id="ba" name="ba" value="<%=ba%>">
		</form>
	</div>




</body>

<footer class="site-footer">
Last Logged In Time:<%=credentials.getUserLastLogin()%>
</footer>
</html>
<%@page import="com.cnsi.story.constants.UserCredentials"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Business Area</title>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="/StoryProject/resources/js/selectBA.js"></script>
<script type="text/javascript"
	src="/StoryProject/resources/js/logout.js"></script>
<link rel="stylesheet" href="/StoryProject/resources/css/selectBA.css">
<link rel="stylesheet" href="/StoryProject/resources/css/header.css">
<link rel="stylesheet" href="/StoryProject/resources/css/footer.css">

<script type="text/javascript">
	function submitForm(e) {
		var c = e.target.name;
		document.getElementById("baFrmAction").value = c;
		document.forms["baForm"].submit();
	}
</script>
</head>
<%
	List<String> businessAreaList = (List<String>) request
			.getAttribute("businessAreaList");
	UserCredentials credentials = (UserCredentials) request
			.getAttribute("userCredentials");
%>
<div class="header">
	<p>
		Logged in as
		<%=credentials.getUserName().toString()%>
		<a onclick="submitForm(event)" name="Logout">Logout</a></br>

	</p>
</div>
<body>

	<div class="container">
		<form id="baForm" action="/StoryProject/BusinessAreaHandler"
			method="post" name="baForm">

			<div class="wrapper-dropdown-1">
				<select id="selectBA" name="selectBA" class="select-style">
					<%
							for (String itr : businessAreaList) {
						%>
					<option><%=itr.toString().toUpperCase()%></option>
					<%
							}
						%>
				</select> <input type="button" name="Submit" class="button"
					onclick="submitForm(event)" value="Submit">
			</div>
			<input type="hidden" value="<%=credentials.getUserName()%>"
				name="userName"> <input type="hidden"
				value="<%=credentials.getUserBusinessArea()%>" name="userBA">
			<input type="hidden" value="<%=credentials.getUserLastLogin()%>"
				name="userLastLogin"> <input type="hidden" value=""
				name="baFrmAction" id="baFrmAction">

		</form>

	</div>

	<script type="text/javascript"
		src="/StoryProject/resources/js/domstorage-persist.js"></script>
</body>
</div>
<footer class="site-footer"> Last Logged In Time:<%=credentials.getUserLastLogin()%>
</footer>

</html>
<%@page import="java.util.*"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="/StoryProject/resources/css/form.css">
<link rel="stylesheet" href="/StoryProject/resources/css/button.css">
<!-- <script type="text/javascript"
	src="/StoryProject/resources/js/logout.js"></script> -->

</head>
<body>
	<form action="/StoryProject/CreateUserHandler" method="post"
		id="createUserFrm" name="createUserFrm" class="dark-matter">

		<%
			List<String> businessAreaList = (List<String>) request
					.getAttribute("businessAreaList");
		%>
		<table>
		<tr><td><label>User Name : </label></td><td><input type="text" name="usrName"></td></tr>
		<tr><td><label>Password : </label></td><td><input type="password" name="pwd"></td></tr>
		<tr><td><label> E-Mail : </label></td><td><input type="text" name="email" placeholder="Enter Email"></td></tr>
		<tr><td><label>Iteration Name</label></td><td> <select id="selectBA"
			name="selectBA" class="">
			<%
				for (String itr : businessAreaList) {
			%>
			<option><%=itr.toString().toUpperCase()%></option>
			<%
				}
			%>
		</select></td></tr>
		<tr><td><button class="button" name="button" value="create">Create</button></td>
		
		<td><button class="button" name="button" value="back">Back</button></td>
		</tr>
		
		</table>
	</form>
</body>
</html>
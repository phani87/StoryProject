<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="shortcut icon" href="/favicon.ico" />
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/StoryProject/resources/js/login.js"></script>
<link rel="stylesheet" href="/StoryProject/resources/css/login.css">

<title>index</title>
</head>
<body>

	<form action="/StoryProject/LoginRequestHadler" method="post"
		class="login">
		<h2>
			<span class="entypo-login"></span> Login
		</h2>
		<button class="submit" value="submit" name="submit">
			<span class="entypo-lock"></span>
		</button>
		<span class="entypo-user inputUserIcon"></span> 
			<input type="text" class="user" placeholder="ursername" name="username" /> 
		<span class="entypo-key inputPassIcon"></span> 
			<input type="password" class="pass" placeholder="password" name="password" />
		<span class="entypo-user-add inputCreateUserIcon"></span>
			<button class="createUser" style="background-color : rgb(231, 76, 60) ; width: 300px; height: 54px; margin-right: 65px;" name="submit" value="createUser">Create User </button>
	</form>
	
</body>
</html>
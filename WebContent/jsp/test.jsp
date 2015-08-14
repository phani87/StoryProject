<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
	function clickRate(event) {
		var e = event.target.name;
		if (e == ("clickMe")) {
			if (typeof (Storage) !== "undefined") {
				if (localStorage.clickcount) {
					
					localStorage.clickcount = Number(localStorage.clickcount) + 1;
					localStorage.setItem("count", localStorage.clickcount);
					/* //document.forms["testForm"].submit(); */
				} else {
					localStorage.clickcount = 1;
				}
				document.getElementById("myClick").innerHTML = "You are clicked the button"
						+ localStorage.clickcount + "times";
			}
		} else {
			localStorage.clear();
			document.getElementById("myClick").innerHTML = "";
		}

	}
</script>

</head>
<!-- <body>
	<form action="/StoryProject/TestServlet" id="testForm" method="post">
		<button onclick="clickRate(event)" name="clickMe">Click ME</button>
		<button onclick="clickRate(event)" name="Reset">Reset</button>
	<div id="myClick"></div>

		<input type="hidden" id="myClick" name="myClick">
	</form>
</body> -->


<BODY>
    <H2>Client Form
    </H2>
    <br/>
    <form name="clientForm" id="clientForm">
      <table>
        <tr>
          <td>First Name
          </td>
          <td>
            <input type="text" name="firstname"/>
          </td>
        </tr>
        <tr>
          <td>Last Name
          </td>
          <td>
            <input type="text" name="lastname"/>
          </td>
        </tr>
        <tr>
          <td>Date of Birth
          </td>
          <td>
            <input type="text" name="dob"/>
          </td>
        </tr>
        <tr>
          <td>Gender
          </td>
          <td>
            <input name="gender" type="radio" value="M">Male
          </input>
          <input name="gender" type="radio" value="F">Female
        </input>
          </td>
        </tr>
        <tr>
          <td>Designation
          </td>
          <td>
            <select name="designation">
              <option value="0">Software Engineer
              </option>
              <option value="1">Sr. Software Engineer
              </option>
              <option value="2">Technical Architect
              </option>
            </select>
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <br/>
            <input name="submit" type="button" value="Submit"/>
            <input name="reset" type="reset" value="Reset"/>
          </td>
        </tr>
      </table>
    </form>
 
<script type="text/javascript" src="../resources/js/domstorage-persist.js"></script>
 
  </BODY>
</html>
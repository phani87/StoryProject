/*idleTimer = null;
idleState = false;
idleWait = 2000;

(function ($) {

    $(document).ready(function () {
    
        $('*').bind('mousemove keydown scroll', function () {
        
            clearTimeout(idleTimer);
                    
            if (idleState == true) { 
                
                // Reactivated event
            	jAlert("Loggin out");
            	window.location.replace("http://localhost:8080/StoryProject/jsp/index.jsp");
                //$("body").append("<p>Welcome Back.</p>");            
            }
            
            idleState = false;
            
            idleTimer = setTimeout(function () { 
                
                // Idle Event
                $("body").append("<p>You've been idle for " + idleWait/1000 + " seconds.</p>");

                idleState = true; }, idleWait);
        });
        
        $("body").trigger("mousemove");
    
    });
}) (jQuery)*//**
 * 
 */


var clickedDate = new Date();
var idleTime = 1;//

function timerIncrement() {

    var nowDate = new Date();
    var diffMs = (nowDate - clickedDate); //Milliseconds between now & the last time a user clicked somewhere on the page
    var diffMins = Math.round(((diffMs % 86400000) % 3600000) / 60000); //Convert ms to minutes

    if (diffMins >= idleTime) {
        //Redirect user to home page etc...
    	alert("Idle for too long. Logging out");
    	window.location.replace("http://localhost:8080/StoryProject/jsp/index.jsp");
    }
}

$(document).ready(function () {

    var idleInterval = setInterval(timerIncrement, 360000); // 6 minute

    $(this).click(function (e) {
        clickedDate = new Date();
    });

});
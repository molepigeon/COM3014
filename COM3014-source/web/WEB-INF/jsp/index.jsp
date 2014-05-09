<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="google-signin-clientid" content="224915916800-pnk6oijt8djtvfekugd1bbjsivhlulip.apps.googleusercontent.com" />
        <meta name="google-signin-cookiepolicy" content="single_host_origin" />
        <meta name="google-signin-callback" content="signinCallback" />
        <meta name="google-signin-requestvisibleactions" content="https://schemas.google.com/AddActivity" />
        <meta name="google-signin-scope" content="https://www.googleapis.com/auth/plus.login" />
        <meta name="google-state" content="${gState}" />
        
        <title>Surrey Share</title>
        
        <script src="js/jquery.js" type="text/javascript"></script>
        <script src="js/dropzone.js"></script>
        <script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script src="https://apis.google.com/js/client:plusone.js" type="text/javascript"></script>
        
        <script src="js/functions.js" type="text/javascript"></script>
         <script src="js/fileUpload.js" type="text/javascript"></script>
        
        <link href="css/main.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
    <div id="lightbox"></div>
    <div id="uploadBox">
        <form action="fileupload" method="POST" class="dropzone square" id="uploader">
        </form>
    </div>
    <header>
        <h1>Surrey Share</h1>
        <div id="userbox">
            <a id="profilelink" href="">
                <span id="avatar"><img src="" id="smallavatar"/></span>
                <span id="username">Not logged in</span>
            </a>
            <span id="loginbuttons">
                <span id="uploadButton"></span>
                <span id="signinButton">
                    <span class="g-signin"></span>
                </span>
                <span id="signoutButton"></span>
            </span>
        </div>
    </header>
    <div id="dashboard">
        <ul class="content" id="contentList">
        </ul>
    </div>
    </body>
</html>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <link rel="shortcut icon" type="image/x-icon" href="assets/img/cursor.png">
    <title>Admin</title>
    <link href="https://fonts.googleapis.com/css?family=Fira+Sans:400,500,600,700" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="assets/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="assets/css/style.css">
    <!--[if lt IE 9]>
		<script src="assets/js/html5shiv.min.js"></script>
		<script src="assets/js/respond.min.js"></script>
	<![endif]-->
</head>

<body>
    <div class="main-wrapper">
        <div class="account-page">
            <div class="container">
                <h3 class="account-title">Login</h3>
                <div class="account-box">
                    <div class="account-wrapper">
                        <div class="account-logo">
                            <a href="index.php"><img src="assets/img/cursor.png" alt="Preadmin"></a>
                        </div>
                        
                        <s:url var="url_login"  value="/login"/>
                        <f:form action="${url_login}" >
 							<table>
	 							<div class="form-group form-focus">
	                                <label class="control-label">Username or Email</label>
	                                <input name="name" class="form-control floating"/>
	                            </div>
	                            <div class="form-group form-focus">
	                                <label class="control-label">Password</label>
	                                <input password name="password" class="form-control floating" />
	                            </div>
	                            <div class="form-group text-center">
	                                <button class="btn btn-primary btn-block account-btn" >Login</button>
	                            </div>
	                            <div class="text-center">
	                                <a href="forgot-password.php">Forgot your password?</a>
	                            </div>
 							</table>
                        </f:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="assets/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="assets/js/app.js"></script>
</body>

</html>
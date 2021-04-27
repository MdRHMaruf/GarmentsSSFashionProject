<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Admin</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" type="text/css"
	href="assets/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="assets/css/util.css">
<link rel="stylesheet" type="text/css" href="assets/css/main.css">

</head>
<body style="background-color: #666666;">

	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100">
				<s:url var="url_login" value="/login" />
				<%-- <f:form action="${url_login}"> --%>
				<f:form action="${url_login}" class="login100-form validate-form">
					<div class="text-center">
						<a href="index.php"><img src="assets/images/logo2.png"
							alt="Preadmin" class="border border-dark"
							style="border-radius: 20%"></a>
					</div>
					<div class="typewriter">
						<h4>
							<b>Cursor Garments Management System.</b>
						</h4>
					</div>
					<span class="login100-form-title p-b-43 mt-1"> <b>Login
							to continue</b>
					</span>

					<%-- <s:url var="url_login" value="/login" />
					<f:form action="${url_login}"> --%>

					<div class="wrap-input100 validate-input">
						<input class="input100" type="text" name="name" placeholder="User Name">
						<%-- <span class="focus-input100"></span> --%>
						<%-- <span class="label-input100">User Name</span> --%>
					</div>


					<div class="wrap-input100 validate-input"
						data-validate="Password is required">
						<input class="input100" type="password" name="password" placeholder="Password">
						<%-- <span class="focus-input100"></span>
						<span class="label-input100">Password</span> --%>
					</div>

					<div class="flex-sb-m w-full p-t-3 p-b-32">
						<div class="contact100-form-checkbox">
							<!-- <input class="input-checkbox100" id="ckb1" type="checkbox" name="remember-me">
									<label class="label-checkbox100" for="ckb1">
										Remember me
									</label> -->
						</div>

						<div>
							<a href="#" class="txt1"> Forgot Password? </a>
						</div>
					</div>


					<div class="container-login100-form-btn">
						<button class="login100-form-btn">
							<b>Login</b>
						</button>
					</div>
					<%-- </f:form> --%>

					<%-- </form> --%>
				</f:form>
				<!-- <div class="login100-more" style="background-image: url('images/bg-01.jpg');"> -->

				<div class="login100-more">

					<img src="assets/images/HR.png">

				</div>

				<!-- </div> -->
			</div>
		</div>
	</div>

	<script type="text/javascript" src="assets/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="assets/js/app.js"></script>

</body>
</html>
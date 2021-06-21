<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

			
			<!--  loginForm -->
			<div class="login">
				<div class="container" ng-controller="passwordForgottenController">
					<h2>Password Forgotten</h2>
					<div class="login-form-grids animated wow slideInUp" data-wow-delay=".5s">
						<form name="loginForm" ng-submit="passwordForgotten()" novalidate>
							<h6>Insert the email used for registration</h6>
							<input name="email" type="text" placeholder="Email Address" ng-model="email">
							<input type="submit" value="passwordForgotten">
						</form>
					</div>
				</div>
			</div>
			<script src="js/usermanager/passwordForgotten.js"></script>

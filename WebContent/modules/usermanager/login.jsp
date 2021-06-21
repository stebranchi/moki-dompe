<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

			
			<!--  loginForm -->
			<div class="login">
				<div class="container" ng-controller="loginController">
					<h2>Login</h2>
					<div class="login-form-grids animated wow slideInUp" data-wow-delay=".5s">
						<form name="loginForm" ng-submit="login()" novalidate>
							<input name="email" type="text" placeholder="Email Address" ng-model="email">
							<input name="password" type="password" placeholder="Password" ng-model="password">
							<!--
							<div class="forgot">
								<a href="#">Forgot Password?</a>
							</div>
							-->
							<input type="submit" value="Login">
							<div class="text-center forgot-password">
          						<a ng-href='?page=passwordForgotten&module=usermanager' target="_self">Forgot your password?</a>
        					</div>
						</form>
					</div>
				</div>
			</div>
			<script src="js/usermanager/login.js"></script>

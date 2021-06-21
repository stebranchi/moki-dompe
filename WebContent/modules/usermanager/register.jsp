<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

	
			
			
			<!--  registrationForm -->
			
			<div class="register" ng-controller="registerController">
				<div class="container">
					<h2>Register</h2>
					<div class="login-form-grids registerMoki">
						<h5>profile information</h5>
						<form name="registerForm" ng-submit="register()" novalidate>
							<input type="text" name="name" placeholder="Name..." id="name" ng-model="name" required>
						<h6>Login information</h6>
							<input type="text" name="email" placeholder="Email Address" id="email" ng-model="email" ng-pattern="emailPattern" ng-model-options="{updateOn: 'blur'}" required>
								<small class="text-danger" ng-show="registerForm.email.$invalid && registerForm.email.$dirty">
						          Mail must be of the type name@domain
						        </small>
							<input type="password" name="pw1" placeholder="Password" id="pw1" ng-model="pw1" ng-minlength="8" required>
								<small class="text-danger" ng-show="registerForm.pw1.$invalid && registerForm.pw1.$dirty">
						          Password must be composed at least by 8 characters
						        </small>
							<input type="password" name="pw2" placeholder="Password Confirmation" id="pw2" ng-model="pw2" ng-minlength="8" password-verify="{{pw1}}" required>
								<small class="text-danger" ng-show="registerForm.pw2.$invalid && registerForm.pw2.$dirty">
						          Passwords must be equal
						        </small>
							<input type="submit" value="Register" id="go" ng-disabled="registerForm.$invalid">
						</form>
					</div>
				</div>
			</div>
			<script src="js/usermanager/register.js"></script>
			
						

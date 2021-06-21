<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

			
			<div class="register" ng-controller="resetpasswordController">
				<div class="container">
					<h2>Password reset</h2>
					<div class="login-form-grids registerMoki">
						<form name="passwordResetForm" ng-submit="passwordReset()" novalidate>
							<input type="password" name="pw3" placeholder="Password" id="pw3" ng-model="pw3" ng-minlength="8" required>
								<small class="text-danger" ng-show="passwordResetForm.pw3.$invalid && passwordResetForm.pw3.$dirty">
						          Password must be composed at least by 8 characters
						        </small>
							<input type="password" name="pw4" placeholder="Password Confirmation" id="pw4" ng-model="pw4" ng-minlength="8" password-verify="{{pw3}}" required>
								<small class="text-danger" ng-show="passwordResetForm.pw4.$invalid && passwordResetForm.pw4.$dirty">
						          Passwords must be equal
						        </small>
							<input type="submit" value="PasswordReset" id="go" ng-disabled="passwordResetForm.$invalid">
						</form>
					</div>
				</div>
			</div>
			<script src="js/usermanager/resetPassword.js"></script>
			
						

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    <div class="register" ng-controller="roleController">
		<div class="container">
			<h2>Create role</h2>
			<div class="login-form-grids">
				<h5>role name</h5>
				<form name="roleForm" ng-submit="createrole()" novalidate>
					<input type="text" name="name" placeholder="Role name..." id="name" ng-model="name" required>
				<h6>Role rights</h6>
					<div class="checkbox">
					  <label>
					    <input type="checkbox" value="rights" ng-model="read_right">
					    this role has read rights
					  </label>
					</div>
					<div class="checkbox">
					  <label>
					    <input type="checkbox" value="rights" ng-model="write_right">
					    this role has write rights
					  </label>
					</div>
					<div class="checkbox">
					  <label>
					    <input type="checkbox" value="rights" ng-model="update_right">
					    this role has update rights
					  </label>
					</div>
					<div class="checkbox">
					  <label>
					    <input type="checkbox" value="rights" ng-model="delete_right">
					    this role has delete rights
					  </label>
					</div>
					<input type="submit" value="Create" id="go" ng-disabled="roleForm.$invalid">
				</form>
			</div>
		</div>
	</div>
	<script src="js/usermanager/rolePanel.js"></script>
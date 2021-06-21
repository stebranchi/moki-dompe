<%@ page language="java" contentType="text/html; charset=UTF-8"%>
    
    
    
			<!--  notifications -->
			
			<script type="text/ng-template" id="/Notification_manager.html">
				<div class="alert {{type}} alert-dismissible" role="alert">
					<button type="button" class="close" ng-click="close()" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<strong>{{title}}</strong> {{text}}
				</div>
			</script>
			
			<div ng-controller="notificationController">
				<moki-notification ng-show="show"  show="show" title="title" text="text" type="type" trigger="mokiService.notification"></moki-notification>
			</div>
			
			<script src="js/common/notifications.js"></script>
/* this code handles the notification area. 
 * You can create a notification changing the variable "mokiService.notification" or adding the variable "notification" in the GET request (http://.../index.jsp?page...&notification=../).
 */

var app = angular.module("Moki") 
	
	//this directive creates the graphics of the notification
	app.directive("mokiNotification", function() {
	    return {
	        restrict: "E", 
	        templateUrl: "/Notification_manager.html", 
	        scope: {
	        	type: "=",
	            text: "=",
	            title: "=",
	            show: "=",
	            trigger: "="
	        },
	        controller: function($scope, $element){
	        	$scope.close = function() {
	    	   		$scope.show = false;
	    	   		$scope.trigger = "";
	    	    }
	        }
	    }; 
	});

	 app.controller("notificationController",   
		        function($scope, $location, $http, mokiService) {
		 
		 		 //create the notification and show it
				 $scope.put_notification = function (trigger) {
						//get the list of possible notifications
			 			if($location.search().page != undefined && $location.search().module != undefined)
			 				url_not = 'modules/' + $location.search().module + '/notification.json';
			 			else
			 				url_not = 'modules/notification.json';
			 			
			 			$http({
								method: 'get',
				                url: url_not,
				                dataType: 'json',
				                contentType: "application/json"
			 			}).then(function (response) {
			 				var data = response.data;
			 				
			 				//search in the list the values of the notification
			 				for (var i = 0; i < data.length; i++) {
			            		  if (data[i].trigger == trigger) {
			            			  $scope.title = data[i].title;
			            			  $scope.text = data[i].text;
			            			  $scope.type = data[i].type; 
			            			  $scope.show = true;
			            		  }
			            	}
			 			}, 
			 			function() {
			 					
			 			});
				 }
	    	
    			$scope.show = false;
    			$scope.trigger = "";
    			
    			//check notification in the url
    			$scope.trigger = $location.search().notification;
    			$scope.put_notification($scope.trigger);
    			
    			//check notification from another controller
    			$scope.mokiService = mokiService;
    			$scope.notification = mokiService.notification;
    			
    			//show the notification when the variable 'mokiService.notification' is modified
    			$scope.$watch('mokiService.notification', function (newVal, oldVal, scope) {
    			    if(newVal) {
    			    	$scope.put_notification(newVal);
    			    }
    			});
	    			
	    			
	    			
	    			
		        });
	   
	    
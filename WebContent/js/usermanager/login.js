var app = angular.module("Moki") 

	app.controller("loginController",   
        function($scope, $window, $http) {
		
    	   	$scope.login = function() {
    	   		
    	   		req = {
    	   				method: 'POST',
	    	   			url: 'RequestManager',
	    	   			headers: {
	    	   			  'Content-Type': "application/json"
	    	   			},
	    	   			data: { 
		    				"serviceName": "LoginManager",
		    				"locationService": "usermanager",
		    				"data" : {
		    					"mode": "login",
			    				"email": $scope.email,
			    				"password": $scope.password
		    				}
	    	   			}
    	   		}
    	   		
    	   		$http(req)
    	   			.then(function(response){
    	   				$window.location.href = response.data.data;
    	   			}, 
    	   			function(response){
    	   				$window.location.href = 'index.jsp?page=error&module=common';
    	   			});
	        };
	        
	        $scope.passwordForgottenLink = function() {
	        	$window.location.href = '?page=passwordForgotten&module=usermanager';
	        }
            
});
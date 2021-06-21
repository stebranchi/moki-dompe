var app = angular.module("Moki") 

	app.controller("passwordForgottenController",   
        function($scope, $window, $http) {

	        
	        $scope.passwordForgotten = function() {
    	   		
    	   		req = {
    	   				method: 'POST',
	    	   			url: 'RequestManager',
	    	   			headers: {
	    	   			  'Content-Type': "application/json"
	    	   			},
	    	   			data: { 
		    				"serviceName": "PasswordManager",
		    				"locationService": "usermanager",
		    				"data" : {
		    					"mode": "passwordForgotten",
			    				"email": $scope.email
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
            
        });
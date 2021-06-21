var app = angular.module("Moki")

	 app.controller("logoutController",   
		        function($window, $location, $http) {
	    	    		
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
		    		    				"mode": "logout"
		    	    				}
				 	   			}
					   		}
						 
						 $http(req)
		    	   			.then(function(response){
		    	   				data = response.data.data;
		    	   				$window.location.href = data;
		    	   			}, 
		    	   			function(){
		    	   				$window.location.href = 'index.jsp?page=error&module=common';
		    	   			});
		        });
	   
	    
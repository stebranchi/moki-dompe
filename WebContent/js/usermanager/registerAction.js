var app = angular.module("Moki")

	 app.controller("registerActionController",   
		        function($window, $location, $http) {
	    	    		
		 			if($location.search().email != undefined && $location.search().confirmation != undefined) {
		 				
		 				req = {
		    	   				method: 'POST',
			    	   			url: 'RequestManager',
			    	   			headers: {
			    	   			  'Content-Type': "application/json"
			    	   			},
			    	   			data: {
		    	    				"serviceName": "RegisterManager",
		    	    				"locationService": "usermanager",
		    	    				"data" : {
		    		    				"mode": "confirmMail",
		    		    				"email": $location.search().email,
		    		    				"confirmation": $location.search().confirmation
		    	    				}
			    	    		}
		    	   		}
		    	   		
		    	   		$http(req)
		    	   			.then(function(response){
		    	   				$window.location.href = response.data.data;
		    	   			}, 
		    	   			function(){
		    	   				$window.location.href = 'index.jsp?page=error&module=common';
		    	   			});
		 				
		 				/*confirmData = {
    	    				"serviceName": "RegisterManager",
    	    				"locationService": "usermanager",
    	    				"data" : {
    		    				"mode": "confirmMail",
    		    				"email": $location.search().email,
    		    				"confirmation": $location.search().confirmation
    	    				}
	    	    		}
	    	    		
	    	    		$http.post("RequestManager", confirmData) 
	    	                .success(function(data) {
	    	                	$window.location.href = data;
	    	                }) 
	    	                .error(function() { 
	    	                	$window.location.href = 'index.jsp?page=register&module=usermanager&notification=bad';
	    	                }); */
		 			} else {
		 				$window.location.href = 'index.jsp?notification=bad';
		 			}
		        });
	   
	    
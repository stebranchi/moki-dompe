var app = angular.module("Moki")  

app.controller("roleController",   
        function($scope, $window, $http) {
    			
	    	$scope.createrole = function() {
	    		
	    		req = {
    	   				method: 'POST',
	    	   			url: 'RequestManager',
	    	   			headers: {
	    	   			  'Content-Type': "application/json"
	    	   			},
	    	   			data: {
		    				"serviceName": "RoleManager",
		    				"locationService": "usermanager",
		    				"data" : {
			    				"mode": "create",
			    				"name": $scope.name,
			    				"read": $scope.read_right,
			    				"write": $scope.write_right,
			    				"update": $scope.update_right,
			    				"delete": $scope.delete_right
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
	        };
	        
            
        });
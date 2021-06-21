var app = angular.module("Moki") 

//create dinamically input texts when the user click the "edit" button
app.directive('mokiInput', function() {
    return {
        restrict: 'E',
        scope: {
            model: '=',
            type: '@'
        },
        template: '<input type="{{type}}" ng-model="model"></input>',            
    };
});

app.controller("userPanelController",   
        function($scope, $http, $filter, $window) {
			
			//default values
			session_name = "NOME COGNOME";
			session_email = "MAIL";
			session_organization = "ORGANIZATION";
			session_birthdate = "0000-00-00";
			$scope.title_name = "NOME COGNOME";
			$scope.can = "";
			$scope.can_not = "";
	
			//get the data of the user
			req = {
	   				method: 'POST',
    	   			url: 'RequestManager',
    	   			headers: {
    	   			  'Content-Type': "application/json"
    	   			},
    	   			data: {
    					"serviceName": "UserDataManager",
    					"locationService": "usermanager",
    					"data" : {
    						"mode": "getUserData"
    					}
    				}
	   		}
	   		
	   		$http(req)
	   			.then(function(response){
	   				data = response.data.data;
	   				
		        	session_name = data.name;
		        	session_email = data.email;
		        	session_organization = data.organization;
		        	session_birthdate = data.birthdate;
		        	$scope.title_name = session_name;
		        	$scope.role = data.role;
		        	
		        	//don't show input
					$scope.show_inpt = "";
					//when user isn't editing
					$scope.not_editing = "";
					//when user is editing
					$scope.editing = "hidden";
					
					$scope.name = session_name;
					$scope.name_inpt = "";
					
					$scope.email = session_email;
					$scope.email_inpt = "";
					
					$scope.organization = session_organization;
					$scope.organization_inpt = "";
					
					$scope.birthdate = session_birthdate;
					$scope.birthdate_inpt = new Date();
					
					//set role rights
					if(data.read_right)
						$scope.can += "read, ";
					else
						$scope.can_not += "read, ";
					
					if(data.write_right)
						$scope.can += "write, ";
					else
						$scope.can_not += "write, ";
					
					if(data.update_right)
						$scope.can += "update, ";
					else
						$scope.can_not += "update, ";
					
					if(data.delete_right)
						$scope.can += "delete";
					else
						$scope.can_not += "delete";
					
	   			}, 
	   			function(){
	   				$window.location.href = 'index.jsp?page=error&module=common';
	   			});
    	
    	   	$scope.edit = function() {
    	   		//change the buttons visibility
    	   		$scope.editing = "";
    	   		$scope.not_editing = "hidden";
    	   		$scope.show_inpt = "true";
    	   		
    			$scope.name_inpt = $scope.name;
    			$scope.name = "";
    	   		
    			$scope.email_inpt = $scope.email;
    			$scope.email = "";
    			
    			$scope.organization_inpt = $scope.organization;
    			$scope.organization = "";

    			$scope.birthdate_inpt = new Date($scope.birthdate);
    			$scope.birthdate = "";
	        };
	        
	        $scope.save = function() {
	        	session_name = $scope.name_inpt;
	        	session_email = $scope.email_inpt;
	        	session_organization = $scope.organization_inpt;
	        	session_birthdate = $filter('date')($scope.birthdate_inpt, 'yyyy-MM-dd');
	        	
	        	//send the new values of the user
	        	req = {
    	   				method: 'POST',
	    	   			url: 'RequestManager',
	    	   			headers: {
	    	   			  'Content-Type': "application/json"
	    	   			},
	    	   			data: {
		    				"serviceName": "UserDataManager",
		    				"locationService": "usermanager",
		    				"data" : {
			    				"mode": "setUserData",
			    				"name": session_name,
			    				"email": session_email,
			    				"organization": session_organization,
			    				"birthdate": session_birthdate,
			    				"old_password": $scope.oldpw,
			    				"new_password": $scope.newpw
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
	        
	        //reset the editable values
	        $scope.cancel = function() {
	        	$scope.editing = "hidden";
    	   		$scope.not_editing = "";
    	   		$scope.show_inpt = "";
    	   		
    			$scope.name = session_name;
    			$scope.name_inpt = "";
    	   		
    			$scope.email = session_email;
    			$scope.email_inpt = "";
    			
    			$scope.organization = session_organization;
    			$scope.organization_inpt = "";

    			$scope.birthdate = session_birthdate;
    			$scope.birthdate_inpt = "";
	        };
	        
	        
            
        });
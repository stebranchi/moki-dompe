var app = angular.module("Moki") 

//create dinamically input texts when the user click the "edit" button
app.directive('mokiInput', function() {
    return {
        restrict: 'E',
        scope: {
            model: '=',
            type: '@'
        },
        template: '<input type="{{type}}" ng-model="model" id="{{ngModel}}"></input>',            
    };
});

app.controller("adminPanelController",   
        function($scope, $http, $window, $filter, $timeout, mokiService) {
	
		//used by the multiselect dropdown
		$scope.stringSettings = { showCheckAll: false, showUncheckAll: false, template: '{{option}}', smartButtonTextConverter(skip, option) { return option; }, };
			
		$scope.inputs = [];
		$scope.user_per_role = [];
		$scope.roles = [];
	
		//get all users
		req = {
				method: 'POST',
	   			url: 'RequestManager',
	   			headers: {
	   			  'Content-Type': "application/json"
	   			},
	   			data: {
					"serviceName": "AdminDataManager",
					"locationService": "usermanager",
					"data" : {
						"mode": "getData"
					}
				}
			}
		
		$http(req)
			.then(function(response){
   				data = response.data.data;
				$scope.user_per_role.length = 0;
				
				$scope.user_per_role = data;
				
				//create all input variables
				for (var i = 0; i < $scope.user_per_role.length; i++) {
					$scope.inputs.push([]);
					for(var j=0; j < $scope.user_per_role[i].list.length;j++) {
						$scope.inputs[i].push({name: "",
				        	email: "",
				        	email_confirm: "",
				        	organization: "",
				        	birthdate: new Date(),
				        	password: "",
				        	role: [],
				        	show_inpt: ""});
					}
				}
			}, 
			function(){
				$window.location.href = 'index.jsp?page=error&module=common';
			});
			
		//get the list of roles for the multiselect dropdown
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
						"mode": "getRoles"
					}
				}
			}
		
		$http(req)
			.then(function(response){
   				data = response.data.data;
				$scope.roles = data;
			}, 
			function(){
				$window.location.href = 'index.jsp?page=error&module=common';
			});
	
	        //makes the user editable
	        $scope.edit = function(parentIndex, index) {
	        	$scope.inputs[parentIndex][index].show_inpt = "true";
	        	$scope.inputs[parentIndex][index].name = $scope.user_per_role[parentIndex].list[index].name;
	        	$scope.inputs[parentIndex][index].email = $scope.user_per_role[parentIndex].list[index].email;
	        	$scope.inputs[parentIndex][index].email_confirm = $scope.user_per_role[parentIndex].list[index].email_confirm;
	        	$scope.inputs[parentIndex][index].organization = $scope.user_per_role[parentIndex].list[index].organization;
	        	$scope.inputs[parentIndex][index].birthdate = new Date($scope.user_per_role[parentIndex].list[index].birthdate);
	        	$scope.inputs[parentIndex][index].password = $scope.user_per_role[parentIndex].list[index].password;
	        	$scope.inputs[parentIndex][index].role = $scope.user_per_role[parentIndex].list[index].role;
	        	
	        	show_input = '#' + parentIndex + index + '_show';
	        	
	        	if(angular.element(show_input).hasClass("collapsed")) {
	        		angular.element(show_input).trigger('click');
	        	}
	        	
	        
	        }
	        
	        //values of the user return as at the beginning (the user is not editable anymore)
	        $scope.cancel = function(parentIndex, index) {
	        	$scope.inputs[parentIndex][index].show_inpt = "";
	        	
	        	show_input = '#' + parentIndex + index + '_show';
	        	
	        	if(!angular.element(show_input).hasClass("collapsed")) {
	        		angular.element(show_input).trigger('click');
	        	}
	        }
	        
	        //save the new values of the user
	        $scope.save = function(parentIndex, index) {
	        	
	        	//check if the user has at least one role
	        	if($scope.inputs[parentIndex][index].role.length) {
	        		
	        		//send the new values
	        		req = {
	    	   				method: 'POST',
		    	   			url: 'RequestManager',
		    	   			headers: {
		    	   			  'Content-Type': "application/json"
		    	   			},
		    	   			data: {
			    				"serviceName": "AdminDataManager",
			    				"locationService": "usermanager",
			    				"data" : {
				    				"mode": "setUserData",
				    				"name": $scope.inputs[parentIndex][index].name,
				    				"email_old": $scope.user_per_role[parentIndex].list[index].email,
				    				"email": $scope.inputs[parentIndex][index].email,
				    				"email_confirm": $scope.inputs[parentIndex][index].email_confirm,
				    				"organization": $scope.inputs[parentIndex][index].organization,
				    				"birthdate": $filter('date')($scope.inputs[parentIndex][index].birthdate, 'yyyy-MM-dd'),
				    				"password": $scope.inputs[parentIndex][index].password,
				    				"role": $scope.inputs[parentIndex][index].role
			    				}
			    		}
	    	   		}
	    	   		
	    	   		$http(req)
	    	   			.then(function(response){
	    	   				//$window.location.href = response.data.data;
	    	   				
	    	   				//show the new variables in the labels
	    	   				if(response.data.data === "success") {	    	   						
	    	   					
	    	   					//format the values
	    	   					if($scope.inputs[parentIndex][index].organization === "")
	    	   						$scope.inputs[parentIndex][index].organization = '-';
	    	   					
	    	   					if($scope.inputs[parentIndex][index].email_confirm === "")
	    	   						$scope.inputs[parentIndex][index].email_confirm = 'yes';
	    	   					
	    	   					if($scope.inputs[parentIndex][index].birthdate == 'Invalid Date')
	    	   						$scope.inputs[parentIndex][index].birthdate =  new Date('0000-00-00');
	    	   					else 
	    	   						$scope.user_per_role[parentIndex].list[index].birthdate = $filter('date')($scope.inputs[parentIndex][index].birthdate, 'yyyy-MM-dd');
	    	   					
	    	   					$scope.inputs[parentIndex][index].show_inpt = "";
		    		        	$scope.user_per_role[parentIndex].list[index].name = $scope.inputs[parentIndex][index].name;
		    		        	$scope.user_per_role[parentIndex].list[index].email = $scope.inputs[parentIndex][index].email;
		    		        	$scope.user_per_role[parentIndex].list[index].email_confirm = $scope.inputs[parentIndex][index].email_confirm;
		    		        	$scope.user_per_role[parentIndex].list[index].organization = $scope.inputs[parentIndex][index].organization;
		    		        	$scope.user_per_role[parentIndex].list[index].password = $scope.inputs[parentIndex][index].password;
		    		        	$scope.user_per_role[parentIndex].list[index].role = $scope.inputs[parentIndex][index].role;
	    	   				}
	    	   				
	    	   				mokiService.notification = response.data.data;
	    	   				$window.scrollTo(0, 0);
	    	   			}, 
	    	   			function(){
	    	   				$window.location.href = 'index.jsp?page=error&module=common';
	    	   			});
	        	} else {
	        		mokiService.notification = "empty_role";
	        		$window.scrollTo(0, 0);
	        	}
	        	
	        }
	        
	        //ban or unban a user
	        $scope.setActive = function(parentIndex, index) {
	        	
	        	req = {
    	   				method: 'POST',
	    	   			url: 'RequestManager',
	    	   			headers: {
	    	   			  'Content-Type': "application/json"
	    	   			},
	    	   			data: {
		    				"serviceName": "AdminDataManager",
		    				"locationService": "usermanager",
		    				"data" : {
			    				"mode": "setActive",
			    				"email_old": $scope.user_per_role[parentIndex].list[index].email,
			    				"active": $scope.user_per_role[parentIndex].list[index].active
		    				}
		    		}
    	   		}
    	   		
    	   		$http(req)
    	   			.then(function(response) {
    	   				if($scope.user_per_role[parentIndex].list[index].active === '1')
    	   					$scope.user_per_role[parentIndex].list[index].active = '0';
    	   				else
    	   					$scope.user_per_role[parentIndex].list[index].active = '1';
    	   				//$window.location.href = response.data.data;
    	   				mokiService.notification = response.data.data;
    	   				$window.scrollTo(0, 0);
    	   			}, 
    	   			function(){
    	   				$window.location.href = 'index.jsp?page=error&module=common';
    	   			});
	        }
	        
        });






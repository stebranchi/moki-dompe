/* this code creates the navigation menù dinamically, using menu.json and the roles of the user.
 * 
 */

var app = angular.module("Moki") 

app.controller("headerController",   
        function($scope, $http) {
	
			$scope.menu = [];
			
			role = ["not_logged"];
				
			//get the roles of the user
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
	   				if(data.role !== undefined)
	   					role = data.role;
	   				
	   				//get all the possible values of the menu
	   				$http({
	   					method: 'get',
	   	                url: 'modules/common/menu.json',
	   	                dataType: 'json',
	   	                contentType: "application/json"
	   				}).then(function (response) {
	   					var data = response.data;
	   					
	   					//show only the values of the roles of the user
	   					for(i = 0; i < data.length; i++) {
	   						if(data[i].role[0] === "all" || findOne(data[i].role, role)) {
	   							sub_arr = [];
	   							
	   							if(data[i].sub) {
	   								for(j = 0; j < data[i].sub.length; j++) {
	   									if(data[i].sub[j].role[0] === "all" || findOne(data[i].sub[j].role, role)) {
	   										sub_arr.push(data[i].sub[j]);
	   									}
		   							}
	   								
	   								$scope.menu.push({"label": data[i].label,
			   							"link": data[i].link,
			   							"sub": sub_arr});
	   							} else {
	   								$scope.menu.push({"label": data[i].label,
			   							"link": data[i].link,
			   							"sub": "none"});
	   							}
	   						}	
	   					}
	   					
	   				}, 
	   				function() {
	   					
	   				});
	   			}, 
	   			function(){
	   				
	   			});
			
			//search if two arrays have values in common
			var findOne = function (haystack, arr) {
			    return arr.some(function (v) {
			        return haystack.indexOf(v) >= 0;
			    });
			};
	   			
        });
var app = angular.module("Moki")

	

	app.controller("individualController",   
        function($scope, $window, $http) {

		$scope.displayText = function(item) {
	   	  return item.id
	   	}
	   	
	   	$scope.afterSelect = function(item) {
	   	  $scope.someId = item
	   	}
		
		$scope.show = '';
		$scope.show_uri = '';
		$scope.class_type = '';
		
		$scope.model_uri = '';
		
		$scope.object_prop = [];
		$scope.datatype_prop = [];
		$scope.annotation_prop = [];


		$scope.classes_list = [];
	
		req = {
				method: 'POST',
	   			url: 'RequestManager',
	   			headers: {
	   			  'Content-Type': "application/json"
	   			},
	   			data: {
					"serviceName": "ClassesManager",
					"locationService": "listmanager",
					"data" : {
						"mode": "getListOfClass"
					}
				}
		}
		$http(req)
			.then(function(response){
				getdata = response.data.data;
				
	        	//list = data.list;
				$scope.classes_list = getdata.list;
				
				$scope.show_uri = 'true';

				
			}, 
			function(){
				$window.location.href = 'http://pdi-apps.fbk.eu/Moki/index.jsp?notification=bad';
			});
		
		//this function watch the "class type" input and creates dinamically the second part of the page
		//when a right value is inserted.
		$scope.change = function() {
			var newVal = $scope.class_type;
	    	$scope.show = '';
	    	
	    	$scope.object_prop.length = 0;
			$scope.datatype_prop.length = 0;
			$scope.annotation_prop.length = 0;
	    	
	    	//check if it's a valid class
	    	for (var i = 0; i < $scope.classes_list.length; i++) {
	    		if ($scope.classes_list[i].id === newVal) {
	    			req = {
	    	    			method: 'POST',
	    	       			url: 'RequestManager',
	    	       			headers: {
	    	       			  'Content-Type': "application/json"
	    	       			},
	    	       			data: {
	    	    				"serviceName": "ClassesManager",
	    	    				"locationService": "listmanager",
	    	    				"data" : {
	    	    					"mode": "provaIndividuals",
	    	    					"id": $scope.classes_list[i].id
	    	    				}
	    	    			}
	    	    		}
    	        	$http(req)
    	    			.then(function(response){
    	    				getdata = response.data.data;
    	    	        	data = getdata.list;
    	    	        	
    	    	        	data.forEach(function(item, index){
    	    	        		if(item.object !== undefined) {
    	    	        			$scope.object_prop.push({
	    								"label": item.object,
	    								"model": ""
	    							});
    	    	        		}
    	    	        		
    	    	        		if(item.datatype !== undefined) {
    	    	        			$scope.datatype_prop.push({
    	    	        				"label": item.datatype,
    	    	        				"model": ""
    	    	        			});
    	    	        		}
    	    	        		
    	    	        		if(item.annotation !== undefined) {
    	    	        			$scope.annotation_prop.push({
	    								"label": item.annotation,
	    								"model": ""
	    							});
    	    	        		}
    			    			
			    			
	    	        		});
    	    			}, 
    	    			function(){
    	    				$window.location.href = 'http://pdi-apps.fbk.eu/Moki/index.jsp?notification=bad';
    	    			});
	    			
	    			$scope.show = 'true';
	    			break;
	    		}
		    	
		    }
		};
		
	   	
	   	//saves new values of the individual
	   	$scope.save = function() {
	   		
	   		req = {
	   				method: 'POST',
    	   			url: 'RequestManager',
    	   			headers: {
    	   			  'Content-Type': "application/json"
    	   			},
    	   			data: { 
	    				"serviceName": "IndividualManager",
	    				"locationService": "ontologyeditor",
	    				"data" : {
	    					"mode": "new",
		    				"list_obj_prop": $scope.object_prop,
		    				"list_isa": $scope.datatype_prop,
		    				"list_data_prop": $scope.annotation_prop
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
	    }
	   	
	   	//if you click "cancel" button
	   	$scope.cancel = function() {
	   		var x = confirm("Are you sure? You will lose all modifications");
	   		
	   		if(x) {
	   			history.go(-1);
	   		}
	    }
	   	
	});
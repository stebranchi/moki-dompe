var app = angular.module("Moki") 

	

	app.controller("data_prop_Controller",   
        function($scope, $window, $http) {
	
		$scope.model_uri = '';
		$scope.model_domain = [''];
		$scope.model_range = [''];
		$scope.model_mapping = [''];
		$scope.model_labels = [{'label': '', 'lang': 'en'}];
		
		$scope.classes_list = [];
		$scope.datatype_list = [];
		$scope.datatype_values = [];
		$scope.language_list = [];
		
	
		//fetch the lists for autocomplete
		
		//classes
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
							"mode": "getList"
						}
					}
				}
				$http(req)
					.then(function(response){
						getdata = response.data.data;
			        	getdata.list.forEach(function(item, index){
			        		$scope.classes_list.push(item.name);
			         	});
					}, 
					function(){
						$window.location.href = 'index.jsp?page=error&module=common';
					});

		//datatype properties
		req = {
				method: 'POST',
				url: 'RequestManager',
				headers: {
				  'Content-Type': "application/json"
				},
				data: {
				"serviceName": "DatatypePropertiesManager",
				"locationService": "listmanager",
				"data" : {
					"mode": "getList"
				}
			}
		}
		$http(req)
			.then(function(response){
				getdata = response.data.data;
		    	data = getdata.list;
		    	
		    	data.forEach(function(item, index){
		    		$scope.datatype_list.push(item.name);
		     	});
			}, 
			function(){

			});
	   	
	   	$http({
			method: 'get',
            url: 'modules/common/datatype_list.json',
            dataType: 'json',
            contentType: "application/json"
		}).then(function (response) {
			$scope.datatype_values = response.data;
		}, 
		function() {
				
		});
	   	
	   	$http({
			method: 'get',
            url: 'modules/common/list_languages.json',
            dataType: 'json',
            contentType: "application/json"
		}).then(function (response) {
			$scope.language_list = response.data;
			
			for(i = 0; i< $scope.language_list.length; i++) {
				$scope.language_list[i].lang = $scope.language_list[i].code + ' - ' + $scope.language_list[i].lang;
			}
		}, 
		function() {
				
		});
	   	
	   	$scope.save = function() {
	   		req = {
	   				method: 'POST',
    	   			url: 'RequestManager',
    	   			headers: {
    	   			  'Content-Type': "application/json"
    	   			},
    	   			data: { 
	    				"serviceName": "AnnotationManager",
	    				"locationService": "ontologyeditor",
	    				"data" : {
	    					"mode": "new",
	    					"list_domain": $scope.model_domain,
		    				"list_range": $scope.model_range,
		    				"list_mapping": $scope.model_mapping,
		    				"list_labels": $scope.model_labels
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
	   	
	   	$scope.cancel = function() {
	   		var x = confirm("Are you sure? You will lose all modifications");
	   		
	   		if(x) {
	   			history.go(-1);
	   		}
	    }
	   	
	});
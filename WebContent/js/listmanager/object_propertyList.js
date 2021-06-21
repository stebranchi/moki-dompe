var app = angular.module("Moki") 

app.controller("getListController",   
        function($scope, $http, $window, NgTableParams, mokiService) {
	
	$scope.lista = {  title: 'Lista', active: 'active', disabled: false };
	$scope.tabs = [
		
	];
	
	var self = this;
	var data;
	index_active = -1;
	
	//lists for autocomplete
	$scope.classes_list = [];
	$scope.object_list = [];
	$scope.language_list = [];
	
	//fetch the list
	req = {
				method: 'POST',
   			url: 'RequestManager',
   			headers: {
   			  'Content-Type': "application/json"
   			},
   			data: {
				"serviceName": "ObjectPorpertyManager",
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
        	
        	//create the table
        	self.tableParams = new NgTableParams({ count: 10}, { counts: [5, 10, 25], dataset: data});
        	
        	//populates the list of object properties
        	data.forEach(function(item, index){
        		$scope.object_list.push(item.name);
         	});
		}, 
		function(){
			$window.location.href = 'index.jsp?page=error&module=common';
		});
	
	//http requests to get the lists
	
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
	    	data = getdata.list;
	    	
	    	data.forEach(function(item, index){
	    		$scope.classes_list.push(item.name);
	     	});
		}, 
		function(){

		});
   	
	//list of languages
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
	
   	//this function tracks and shows the tab clicked by the user
	$scope.go = function(index) {
		 //index_active is the index of the last tab selected
		 //value "-1" of the index is referred to the table
		 if(index !== index_active) {
			 
			 if(index_active < $scope.tabs.length) {
				 if(index === -1) {
					 $scope.tabs[index_active].active = "";
					 $scope.lista.active = "active";
					 index_active = -1;
				 } else {
					 if(index_active === -1) {
						 $scope.lista.active = "";
					 } else {
						 $scope.tabs[index_active].active = "";
					 }
					
					 $scope.tabs[index].active = "active";
					 index_active = index;
				 }
			 } else {
				 //if the user is closing a tab
				 //(the button "close" fire also this function)
				 
				 for (i = 0; i < $scope.tabs.length; i++) {
			   		$scope.tabs[i].active = "";
			   	 }
				 
				 $scope.lista.active = "active";
				 index_active = -1;
			 }
			 
		 }
     }
	
	//this function close a tab
    $scope.close = function(index) {
    	$scope.tabs.splice(index, 1);
    	
    	if(index === index_active) {
    		$scope.go(-1);
    	} else if(index < index_active) {
    		index_active--;
    	}
    	
    	
    }
    
    //this function create a new tab
    $scope.new_tab = function(e) {
    	
    	isPresent = false;
    	
    	//if the tab is present we don't create a new one
    	for (i = 0; i < $scope.tabs.length; i++) {
    		if($scope.tabs[i].id === e.id) {
    			isPresent = true;
    			//go to the tab selected by the user
    			$scope.go(i);
    			break;
    		}
    	}
    	
    	if(!isPresent) {
    		
    		var domain = [];
    		var range = [];
    		var mapping = [];
    		var labels = [];
    		var uri = '';
        	
    		//fetch and insert data in the new tab
    		
    		//domain
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
    					"mode": "getDomainData",
    					"id": e.id
    				}
    			}
    		}
        	$http(req)
    			.then(function(response){
    				getdata = response.data.data;
    	        	data = getdata.list;
    	        	
    	        	data.forEach(function(item, index){ 
    	        		   domain.push(item.id);
    	        		});
    	        	
    	        	domain.push('');
    			}, 
    			function(){

    			});
        	
        	//range
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
        					"mode": "getRangeData",
        					"id": e.id
        				}
        			}
        		}
        	$http(req)
    			.then(function(response){
    				getdata = response.data.data;
    	        	data = getdata.list;
    	        	
    	        	data.forEach(function(item, index){ 
 	        		   range.push(item.id);
 	        		});
 	        	
    	        	range.push('');
    			}, 
    			function(){

    			});
			
        	//mapping
        	req = {
        			method: 'POST',
           			url: 'RequestManager',
           			headers: {
           			  'Content-Type': "application/json"
           			},
           			data: {
        				"serviceName": "MappingManager",
        				"locationService": "listmanager",
        				"data" : {
        					"mode": "getMappingData",
        					"id": e.id
        				}
        			}
        		}
        	$http(req)
    			.then(function(response){
    				getdata = response.data.data;
    	        	data = getdata.list;
    	        	
    	        	data.forEach(function(item, index){ 
    	        		mapping.push(item.id);
    	        	});
    	        	
    	        	mapping.push('');
    			}, 
    			function(){

    			});
        	
        	//labels
        	req = {
        			method: 'POST',
           			url: 'RequestManager',
           			headers: {
           			  'Content-Type': "application/json"
           			},
           			data: {
        				"serviceName": "LabelsManager",
        				"locationService": "listmanager",
        				"data" : {
        					"mode": "getLabelData",
        					"id": e.id
        				}
        			}
        		}
        	$http(req)
    			.then(function(response){
    				getdata = response.data.data;
    	        	data = getdata.list;
    	        	
    	        	data.forEach(function(item, index){ 
    	        		labels.push({'label': item.label, 'lang': item.language});
	        		});
    	        	
    	        	labels.push({'label': '', 'lang': 'en'});
    			}, 
    			function(){

    			});
        	
        	//URI
        	req = {
        			method: 'POST',
           			url: 'RequestManager',
           			headers: {
           			  'Content-Type': "application/json"
           			},
           			data: {
        				"serviceName": "URIManager",
        				"locationService": "listmanager",
        				"data" : {
        					"mode": "getURIData",
        					"id": e.id
        				}
        			}
        		}
        	$http(req)
    			.then(function(response){
    				getdata = response.data.data;
    	        	data = getdata.list;
    	        	
    	        	uri = data;
    	        	
    			}, 
    			function(){

    			});
        	
        	element = {
        			"title": e.name,
        			'paneId': 'tab' + $scope.tabs.length,
        			"id": e.id,
        			"active": "",
        			"model_domain": domain,
        			"model_range": range,
        			"model_mapping": mapping,
        			"model_labels": labels,
        			"model_uri": uri
        	}
    		
    		$scope.tabs.push(element);
    		$scope.go($scope.tabs.length-1);
    	}
    	
    }
    
    //update data of the ontology
    $scope.edit = function(i) {
    	var model_domain = [];
    	var model_range = [];
    	var model_mapping = [];
    	var model_labels = [];
    	var model_uri = '';
    	
    	//remove blanks
    	$scope.tabs[i].model_domain.forEach(function(item, index){
 		   if(item !== '') {
 			  model_domain.push(item);
 		   }
     	});
    	$scope.tabs[i].model_range.forEach(function(item, index){
  		   if(item !== '') {
  			 model_range.push(item);
  		   }
      	});
		$scope.tabs[i].model_mapping.forEach(function(item, index){ 
			if(item !== '') {
 			   model_mapping.push(item);
     		}
     	});
		$scope.tabs[i].model_labels.forEach(function(item, index){
			if(item.label !== '') {
 			   model_labels.push(item);
     		}
     	});
		
		$scope.tabs[i].model_domain = model_domain;
		$scope.tabs[i].model_range = model_range;
		$scope.tabs[i].model_mapping = model_mapping;
		$scope.tabs[i].model_labels = model_labels;
		$scope.tabs[i].model_uri = model_uri;
		
		//update data
    	req = {
    			method: 'POST',
       			url: 'RequestManager',
       			headers: {
       			  'Content-Type': "application/json"
       			},
       			data: {
    				"serviceName": "ObjectPorpertyManager",
    				"locationService": "listmanager",
    				"data" : {
    					"mode": "edit",
    					"id": $scope.tabs[i].id,
    					"list": [
    						$scope.tabs[i].model_uri,
    						$scope.tabs[i].model_domain,
    						$scope.tabs[i].model_range,
    						$scope.tabs[i].model_mapping,
    						$scope.tabs[i].model_labels
    					]
    				}
    			}
    		}
        	$http(req)
    			.then(function(response){
    				
    				$scope.tabs[i].model_domain.push('');
    				$scope.tabs[i].model_domain.push('');
					$scope.tabs[i].model_mapping.push('');
					$scope.tabs[i].model_labels.push({'label': '', 'lang': 'en'});
    				
    				mokiService.notification = "edit";
    				$window.scrollTo(0, 0);
    			}, 
    			function(){
    				$window.location.href = 'index.jsp?page=error&module=common';
    			});
    	
    	
    	
    }
      
            
});
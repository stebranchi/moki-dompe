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
	$scope.individual_list = [];
	
	//fetch the list
	req = {
				method: 'POST',
   			url: 'RequestManager',
   			headers: {
   			  'Content-Type': "application/json"
   			},
   			data: {
				"serviceName": "IndividualManager",
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
        	
        	//populates the list of individuals
        	data.forEach(function(item, index){
        		$scope.individual_list.push(item.name);
         	});
		}, 
		function(){
			$window.location.href = 'index.jsp?page=error&module=common';
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
    		
    		var object_prop = [];
    		var datatype_prop = [];
    		var annotation_prop = [];
        	
    		//fetch and insert data in the new tab
    		
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
	    					"id": e.id
	    				}
	    			}
	    		}
        	$http(req)
    			.then(function(response){
    				getdata = response.data.data;
    	        	data = getdata.list;
    	        	
    	        	data.forEach(function(item, index){
    	        		if(item.object !== undefined) {
    	        			object_prop.push({
								"label": item.object,
								"model": item.value
							});
    	        		}
    	        		
    	        		if(item.datatype !== undefined) {
    	        			datatype_prop.push({
    	        				"label": item.datatype,
    	        				"model": item.value
    	        			});
    	        		}
    	        		
    	        		if(item.annotation !== undefined) {
    	        			annotation_prop.push({
								"label": item.annotation,
								"model": item.value
							});
    	        		}
		    			
	    			
	        		});
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
        	
        	var object_prop = [];
    		var datatype_prop = [];
    		var annotation_prop = [];
        	
        	element = {
        			"title": e.name,
        			'paneId': 'tab' + $scope.tabs.length,
        			"id": e.id,
        			"active": "",
        			"object_prop": object_prop,
        			"datatype_prop": datatype_prop,
        			"annotation_prop": annotation_prop,
        			"model_uri": uri
        	}
    		
    		$scope.tabs.push(element);
    		$scope.go($scope.tabs.length-1);
    	}
    	
    }
    
    //update data of the ontology
    $scope.edit = function(i) {
		
		//update data
    	req = {
    			method: 'POST',
       			url: 'RequestManager',
       			headers: {
       			  'Content-Type': "application/json"
       			},
       			data: {
    				"serviceName": "IndividualManager",
    				"locationService": "listmanager",
    				"data" : {
    					"mode": "edit",
    					"id": $scope.tabs[i].id,
    					"list": [
    						$scope.tabs[i].model_uri,
    						$scope.tabs[i].object_prop,
    						$scope.tabs[i].datatype_prop,
    						$scope.tabs[i].annotation_prop
    					]
    				}
    			}
    		}
        	$http(req)
    			.then(function(response){
    				
    				mokiService.notification = "edit";
    				$window.scrollTo(0, 0);
    			}, 
    			function(){
    				$window.location.href = 'index.jsp?page=error&module=common';
    			});
    	
    	
    	
    }
      
            
});
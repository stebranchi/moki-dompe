var app = angular.module("Moki") 

app.filter('unsafe', function($sce) {
    return function(val) {
        return $sce.trustAsHtml(val);
    };
});

app.controller("getListController",   
        function($scope, $http, $window, NgTableParams, mokiService) {
	
	$scope.lista = {  title: 'Lista', active: 'active', disabled: false };
	$scope.tabs = [
		
	];
	var search;
	var self = this;
	var data;
	$scope.results = -1;
	$scope.type_advanced = "All";
	$scope.type_search = "All";
	index_active = -1;
	
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
				if(response.data.data.email == null)
					{
					$window.location.href='index.jsp?page=login&module=usermanager';
					}
			});
			
	$('input').on('click', function() {
	    $(this).attr('placeholder',$(this).val());
	  $(this).val('');
	});
	$('input').on('mouseleave', function() {
	  if ($(this).val() == '') {
	    $(this).val($(this).attr('placeholder'));
	  }
	});
	
	
	getAll();
	//IT calls the "Getlist" method from ClassesManager that send back the first n SOPS
//	cause it's too long to take all the SOPS
	function getAll () {
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
        	
        	//create the table
        	self.tableParams = new NgTableParams({ count: 10}, { counts: [5, 10, 25], dataset: data});

        	//populates the list of classes
        	data.forEach(function(item, index){
        		$scope.classes_list.push(item);
         	});
		}, 
		function(){
			$window.location.href = 'index.jsp?page=error&module=common';
		});
	}
//	Function for searching as a free text in 1 or more specific section
	$scope.AdvancedSearch = function(){
		search = $scope.sectiontext;
			req = {
					method: 'POST',
					url: 'RequestManager',
					headers: {
					  'Content-Type': "multipart/form-data"
					},
					data: {
					"serviceName": "ClassesManager",
					"locationService": "listmanager",
					"data" : {
						"mode": "advancedSearch",
						"title": $scope.title,
						"type": $scope.type_advanced,
						"author": $scope.author,
						"date": $scope.date,
						"cro": $scope.cro,
						"material": $scope.material,
						"abstrac": $scope.abstract,
						"numrel": $scope.numrel,
						"documentno": $scope.documentno,
						"project": $scope.project,
						"glp": $scope.glp,
						"pages": $scope.pages,
						"journal": $scope.journal,
						"isbn": $scope.isbn,
						"doi": $scope.doi,
						"requester": $scope.requester,
						"pmcid": $scope.pmcid,
						"saggio": $scope.saggio,
						"administration": $scope.administration,
						"location": $scope.location,
						"notes": $scope.notes,
						"author_address": $scope.author_address,
						"keywords": $scope.keywords,
						"language": $scope.language
					}
				}
			}
		
	$http(req)
		.then(function(response){
			getdata = response.data.data;
        	data = getdata.list;
        	$scope.results = data.length;
        	//create the table
        	self.tableParams = new NgTableParams({ count: 10}, { counts: [5, 10, 25], dataset: data});
        	console.log(data);
		}, 
		function(){
			$window.location.href = 'index.jsp?page=error&module=common';
		});
	}
//	General wrap function for the search of SOP
//	It checks which field is used when clicked the search taking the first available
//	Searchname: it calls the "Search" function of the LuceneTester module for the free text search 
//	SearchRole: Same as searchname but with a name taken from a list of roles 
//	SearchId: it calls the function "searchById" to retrieve the single SOP
//	And the response is given to the list to populate it
	$scope.searchByName = function(){
		if(typeof $scope.searchname !== "undefined" && $scope.searchname.length > 0) {
			search = $scope.searchname;
			reqName = {
					method: 'POST',
					url: 'RequestManager',
					headers: {
					  'Content-Type': "application/json"
					},
					data: {
						"serviceName": "ClassesManager",
						"locationService": "listmanager",
						"data" : {
						"mode": "searchName",
						"name": $scope.searchname,
						"type": $scope.type_search,
					}
				}
			}
		
			$http(reqName)
			.then(function(response){
				startTime = new Date();
				getdata = response.data.data;
	        	data = getdata.list;
	        	$scope.results = data.length;
	        	//create the table
	        	
	        	self.tableParams = new NgTableParams({ count: 10}, { counts: [5, 10, 25], dataset: data});
			})
			.catch(console.error);
		}else {
			getAll();
		}
	}
	
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
	
	$scope.changeValue= function(param) {  
	    $scope.details = param;
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
    
   //this function opens attachment on new internet tab
    $scope.show_pdf = function(e){
    	path = e.replace("internal-pdf://", "");
    	$window.open('../pdfs/' + path);
    }
    
    //this function create a new tab
    $scope.new_tab = function(e) {
    	
    	isPresent = false;
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
					"mode": "saveLog",
					"name": search,
					"id": e.sopId
				}
			}
		}
	
		$http(req)
		.then(function(response){
		})
		.catch(console.error);
    	
    	//if the tab is present we don't create a new one
    	for (i = 0; i < $scope.tabs.length; i++) {
    		if($scope.tabs[i].title === e.title) {
    			isPresent = true;
    			//go to the tab selected by the user
    			$scope.go(i);
    			break;
    		}
    	}
    	
    	if(!isPresent) {
    		
    		var urlsArray = e.urls.split(",");
    		for (i = 0; i < urlsArray.length; i++){
    			urlsArray[i] = urlsArray[i].trim();
    		}
    		var authors = e.author.split(" - ");
    	
	    	element = {
	    			"title": e.title,
	    			"date": e.date,
	    			"date_upload": e.date_upload,
	    			'paneId': 'tab' + $scope.tabs.length,
	    			"isbn": e.isbn,
	    			"active": "",
	    			"author": authors,
	    			"abstract": e.abstrac,
	    			"urls": urlsArray,
	    			"material": e.material,
	    			"pages": e.pages,
	    			"journal": e.journal,
	    			"numrel": e.numrel,
	    			"doi": e.doi,
	    			"requester": e.requester,
	    			"pmcid": e.pmcid,
	    			"cro": e.cro,
	    			"documentno": e.documentno,
	    			"project": e.project,
	    			"glp": e.glp,
	    			"saggio": e.saggio,
	    			"administration": e.administration,
	    			"location": e.location,
	    			"author_address": e.author_address,
	    			"notes": e.notes,
	    			"type": e.type,
	    			"desc_material": e.desc_material,
	    			"data_arch": e.data_arch,
	    			"num_lotto": e.num_lotto,
	    			"prodotto": e.prodotto,
	    			"container": e.container,
	    			"formula": e.formula,
	    			"keywords": e.keywords,
	    			"tipo_studio": e.tipo_studio,
	    	}
			
	    	
	    	
	    	
			$scope.tabs.push(element);
			$scope.go($scope.tabs.length-1);
    	}
    	
    }
    
    //update data of the ontology
    $scope.edit = function(i) {
    	var model_isa = [];
    	var model_obj_prop = [];
    	var model_data_prop = [];
    	var model_annotations = [];
    	var model_mapping = [];
    	var model_labels = [];
    	var model_uri = '';
    	
    	//remove blanks
    	$scope.tabs[i].model_isa.forEach(function(item, index){
 		   if(item !== '') {
		   	   model_isa.push(item);
 		   }
     	});
		$scope.tabs[i].model_obj_prop.forEach(function(item, index){ 
			if(item.name !== '' && item.range !== '') {
 			   model_obj_prop.push(item);
     		}
     	});
		$scope.tabs[i].model_data_prop.forEach(function(item, index){ 
			if(item !== '') {
 			   model_data_prop.push(item);
     		}
     	});
		$scope.tabs[i].model_annotations.forEach(function(item, index){ 
			if(item !== '') {
 			   model_annotations.push(item);
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
		
		$scope.tabs[i].model_isa = model_isa;
		$scope.tabs[i].model_obj_prop = model_obj_prop;
		$scope.tabs[i].model_data_prop = model_data_prop;
		$scope.tabs[i].model_annotations = model_annotations;
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
    				"serviceName": "ClassesManager",
    				"locationService": "listmanager",
    				"data" : {
    					"mode": "edit",
    					"id": $scope.tabs[i].id,
    					"list": [
    						$scope.tabs[i].model_uri,
    						$scope.tabs[i].model_isa,
    						$scope.tabs[i].model_obj_prop,
    						$scope.tabs[i].model_data_prop,
    						$scope.tabs[i].model_annotations,
    						$scope.tabs[i].model_mapping,
    						$scope.tabs[i].model_labels
    					]
    				}
    			}
    		}
        	$http(req)
    			.then(function(response){
    				
    				$scope.tabs[i].model_isa.push('');
					$scope.tabs[i].model_obj_prop.push({'name': '', 'range': ''});
					$scope.tabs[i].model_data_prop.push('');
					$scope.tabs[i].model_annotations.push('');
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
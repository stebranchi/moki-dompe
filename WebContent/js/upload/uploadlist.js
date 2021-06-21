var app = angular.module("Moki") 

app.controller("GetUploadListController",   
        function($scope, $http, $window, NgTableParams, mokiService) {

	var data = [{"user":"Stefano Branchi","title":"Prova","date":"2021-05-26 15:15:15"},{"user":"Stefano Branchi","title":"Prova2","date":"2021-05-27 14:13:15"}];
	var self = this;
	var req = {
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
	
	
	var req1 = {
			method: 'POST',
			url: 'RequestManager',
			headers: {
			  'Content-Type': "application/json"
			},
			data: {
				"serviceName": "Upload",
				"locationService": "listmanager",
				"data" : {
					"mode": "getUploadHistory"
			}
		}
	}
	
	$http(req1)
		.then(function(response){
			data = response.data.data;
			$scope.count = data[0].count;
        	//create the table
        	self.tableParams = new NgTableParams({ count: 10}, { counts: [5, 10, 25], dataset: data});
        	self.tableParams.reload();
		}, 
		function(){
			$window.location.href = 'index.jsp?page=error&module=common';
		});
})
	
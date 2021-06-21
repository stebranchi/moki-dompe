var app = angular.module("Moki") 

app.controller("getSimilarityController",   
        function($scope, $http, $window, NgTableParams, mokiService) {
	
	$scope.lista = {  title: 'Tabella', active: 'active', disabled: false };
	var self = this;
	var datatable = new Array();
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
	
//	Values of similarity are taken from similarity.csv and parse it in rows for the table
	d3.text("modules/similaritytab/similaritiesv2.1.csv", function(data) {
		var rows = d3.csvParseRows(data);
		rows.forEach(function(row){
			if(row.length == 8){
				if (row[7] >= 0.9){
					var element = new Object();
					element.sop1 = row[0];
					element.sop2 = row[1];
					element.totalsim = "";
					if (row[2] > 0.5)	{
						element.totalsim += "Le SOP si applicano all'interno della stessa sede\n";
					}
					if (row[3] > 0.5)	{
						element.totalsim += "Le SOP si applicano all'interno della stessa area\n";
					}
					if(row[4] >=0 && row[4] < 0.40){
						element.totalsim += "I ruoli delle SOP non sono simili\n";
					}else if(row[4] >=0.40 && row[4] < 0.75){
						element.totalsim += "I ruoli delle SOP sono abbastanza simili\n"
					}else if(row[4] >=0.75 && row[4] < 1){
						element.totalsim += "I ruoli delle SOP sono molto simili\n"
					}
					if(row[5] >=0 && row[5] < 0.40){
						element.totalsim += "I link delle SOP non sono simili\n";
					}else if(row[5] >=0.40 && row[5] < 0.75){
						element.totalsim += "I link delle SOP sono abbastanza simili\n"
					}else if(row[5] >=0.75 && row[5] < 1){
						element.totalsim += "I link delle SOP sono molto simili\n"
					}
					if(row[6] >=0 && row[6] < 0.40){
						element.totalsim += "I contesti delle SOP non sono simili";
					}else if(row[6] >=0.05 && row[6] < 0.1){
						element.totalsim += "I contesti delle SOP sono abbastanza simili"
					}else if(row[6] >=0.1 && row[6] < 1){
						element.totalsim += "I contesti delle SOP sono molto simili"
					}
					
					element.osim = row[7];
					datatable.push(element);
				}
			}
		});
		datatable.sort(compare);
		console.log(datatable);
		console.log(datatable.length);
		self.tableParams = new NgTableParams({ count: 10}, { counts: [5, 10, 25], dataset: datatable});
    });
	
	function compare(a,b){
		  var comparison = 0;
		  if (parseFloat(a.osim) >= parseFloat(b.osim)) {
		    comparison = -1;
		  } else if (parseFloat(a.osim) < parseFloat(b.osim)) {
		    comparison = 1;
		  }
		  return comparison;
		}

	
});
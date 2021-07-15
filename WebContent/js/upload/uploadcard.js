var app = angular.module("Moki") 

app.controller("GetUploadController",   
        function($scope, $http, $window, NgTableParams, mokiService) {
	$scope.empty=true;
	
	$scope.notempty = function(){
		$scope.empty = false;
		console.log("change");
	}
	
	
	$scope.type_upload = "Paper";
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
	
	$scope.uploadFile = function(){
			req = {
					method: 'POST',
					url: 'RequestManager',
					headers: {
					  'Content-Type': "multipart/form-data"
					},
					data: {
					"serviceName": "Upload",
					"locationService": "listmanager",
					"data" : {
						"mode": "uploadFile",
						"title": $scope.title,
						"xml": $scope.xml,
						"type": $scope.type_upload,
						"author": $scope.author,
						"date": $scope.date,
						"cro": $scope.cro,
						"abstrac": $scope.abstract,
						"material": $scope.material,
						"documentno": $scope.documentno,
						"project": $scope.project,
						"numrel": $scope.numrel,
						"glp": $scope.glp,
						"saggio": $scope.saggio,
						"administration": $scope.administration,
						"location": $scope.location,
						"desc_material": $scope.desc_material,
						"data_arch": $scope.data_arch,
						"num_lotto": $scope.num_lotto,
						"prodotto": $scope.prodotto,
						"container": $scope.container,
						"formula": $scope.formula,
						"tipo_studio": $scope.tipo_studio,
						"pages": $scope.pages,
						"journal": $scope.journal,
						"isbn": $scope.isbn,
						"doi": $scope.doi,
						"requester": $scope.requester,
						"pmcid": $scope.pmcid,
						"notes": $scope.notes,
						"author_address": $scope.author_address,
						"keywords": $scope.keywords,
						"language": $scope.language,
						"pdf": $scope.pdf
					}
				}
			}
		
		
		$http(req)
		.then(function(response){
			$window.location.href = response.data.data;
		})
		.catch(console.error);
	};
	

})
.directive("fileread", [function () {
    return {
        scope: {
            fileread: "="
        },
        link: function (scope, element, attributes) {
            element.bind("change", function (changeEvent) {
                var reader = new FileReader();
                reader.onload = function (loadEvent) {
                    scope.$apply(function () {
                        scope.fileread = loadEvent.target.result;
                    });
                }
                reader.readAsDataURL(changeEvent.target.files[0]);
            });
        }
    }
}]);
app.directive('customOnChange', function() {
	  return {
	    restrict: 'A',
	    link: function (scope, element, attrs) {
	      var onChangeHandler = scope.$eval(attrs.customOnChange);
	      element.on('change', onChangeHandler);
	      element.on('$destroy', function() {
	        element.off();
	      });

	    }
	  };
});
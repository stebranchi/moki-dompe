var app = angular.module("Moki") 

app.controller("GetUploadController",   
        function($scope, $http, $window, NgTableParams, mokiService) {
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
		if ($scope.type === "Paper"){
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
						"type": $scope.type,
						"author": $scope.author,
						"date": $scope.date,
						"pages": $scope.pages,
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
		}
		else{
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
						"type": $scope.type,
						"author": $scope.author,
						"date": $scope.date,
						"cro": $scope.cro,
						"material": $scope.material,
						"documentno": $scope.documentno,
						"project": $scope.project,
						"glp": $scope.glp,
						"essay": $scope.essay,
						"administration": $scope.administration,
						"location": $scope.location,
						"notes": $scope.notes,
						"author_address": $scope.author_address,
						"keywords": $scope.keywords,
						"language": $scope.language,
						"pdf": $scope.pdf
					}
				}
			}
		}
		
		
		
		};
		$http(req)
		.then(function(response){
			$window.location.href = response.data.data;
		})
		.catch(console.error);
	}
	

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
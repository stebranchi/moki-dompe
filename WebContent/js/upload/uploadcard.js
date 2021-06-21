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
					"filename": $scope.filename,
					"type": $scope.type,
					"xml": $scope.xml,
					"pdf": $scope.pdf
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
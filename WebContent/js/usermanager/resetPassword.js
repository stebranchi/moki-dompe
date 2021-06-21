var app = angular.module("Moki") 

app.directive("passwordVerify", function() {
    return {
        restrict: 'A', // only activate on element attribute
        require: '?ngModel', // get a hold of NgModelController
        link: function(scope, elem, attrs, ngModel) {
          if (!ngModel) return; // do nothing if no ng-model

          // watch own value and re-validate on change
          scope.$watch(attrs.ngModel, function() {
            validate();
          });

          // observe the other value and re-validate on change
          attrs.$observe('passwordVerify', function(val) {
            validate();
          });

          var validate = function() {
            // values
            var val1 = ngModel.$viewValue;
            var val2 = attrs.passwordVerify;

            // set validity
            ngModel.$setValidity('passwordVerify', val1 === val2);
          };
        }
      };
});

	app.controller("resetpasswordController",   
        function($scope, $window, $http, $location) {
		
	        $scope.passwordReset = function() {
    	   		
    	   		req = {
    	   				method: 'POST',
	    	   			url: 'RequestManager',
	    	   			headers: {
	    	   			  'Content-Type': "application/json"
	    	   			},
	    	   			data: { 
		    				"serviceName": "PasswordManager",
		    				"locationService": "usermanager",
		    				"data" : {
		    					"mode": "resetPassword",
			    				"email": $location.search().email,
			    				"confirmation": $location.search().token,
			    				"password": $scope.pw3,
			    				"password_confirm": $scope.pw4,
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
	        };
            
        });
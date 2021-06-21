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
    		    
    app.controller("registerController",   
        function($scope, $window, $http) {
    	
    	$scope.emailPattern = /^([a-zA-Z0-9])+([a-zA-Z0-9._%+-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+$/;
    			
	    	$scope.register = function() {
	    		
	    		req = {
    	   				method: 'POST',
	    	   			url: 'RequestManager',
	    	   			headers: {
	    	   			  'Content-Type': "application/json"
	    	   			},
	    	   			data: {
		    				"serviceName": "RegisterManager",
		    				"locationService": "usermanager",
		    				"data" : {
			    				"mode": "register",
			    				"name": $scope.name,
			    				"email": $scope.email,
			    				"password": $scope.pw1,
			    				"password_confirm": $scope.pw2,
		    				}
		    		}
    	   		}
    	   		
    	   		$http(req)
    	   			.then(function(response){
    	   				$window.location.href = response.data.data;
    	   			}, 
    	   			function(){
    	   				$window.location.href = 'index.jsp?page=error&module=common';
    	   			});
	    		
	    		/*registerData = {
	    				"serviceName": "RegisterManager",
	    				"locationService": "usermanager",
	    				"data" : {
		    				"mode": "register",
		    				"firstname": $scope.firstname,
		    				"lastname": $scope.lastname,
		    				"email": $scope.email,
		    				"password": $scope.pw1,
		    				"password_confirm": $scope.pw2,
	    				}
	    		}
	    		
	    		$http.post("RequestManager", registerData) 
	                .success(function(data) {
	                	$window.location.href = data;
	                }) 
	                .error(function() { 
	                	$window.location.href = 'index.jsp?page=register&module=usermanager&notification=bad';
	                }); */
	        };
	        
            
        });
    
   
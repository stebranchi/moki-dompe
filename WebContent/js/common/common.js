/* file javascript shared through the application, (here we initialize AnguarJS)
 * 
 */



//initialize the module "Moki"
var app = angular.module("Moki", ['angularjs-dropdown-multiselect', 'bootstrap3-typeahead', 'ngTable', 'mj.scrollingTabs']);

	//"$window.location.href" can't work without this
	app.config(['$locationProvider', function($locationProvider) {
	    $locationProvider.html5Mode({
	    	  enabled: true,
	    	  requireBase: false
	    	});
	}]);
	
	//you can use this AngularJS filter to show the content of an array in a label
	app.filter("array",   
	        function(){ 
	    return function(arr) {
	    	if(arr == undefined)
	    		return "";
	    	var result = arr[0];
	    	
	        for(i=1;i < arr.length;i++) {
	        	result += ", " + arr[i];
	        }
	    	
	        return result; 
	    } 
	});
	
	//used to create notifications dinamically
	app.factory("mokiService", function() { 
        var data = {
        				notification: ""
        		   }; 
        
        return data;
    });
	
	//components used in ontologyeditor
	app.directive("mokiComponent1", function() {
		return {
			restrict: "E", 
			templateUrl: "modules/ontologyeditor/components/component_type1.html",
			scope: {
				list_model: "=model",
				list: "=",
				legend: "@",
				label: "@",
				addLabel: "@"
	        },
	        controller: function($scope, $element){
	        	$scope.delete = function(index) {
	    	   		$scope.list_model.splice(index, 1);
	    	    }
	    	   	
	    	   	$scope.new_tab = function() {
	    	   		$scope.list_model.push('');
	      	   	}
	        	
	        	$scope.displayText = function(item) {
	      	   	  return item
	      	   	}
	      	   	
	      	   	$scope.afterSelect = function(item) {
	      	   	  $scope.someId = item
	      	   	}
	        }
		 };
	});
	
	app.directive("mokiIsa", function() {
		return {
			restrict: "E", 
			templateUrl: "modules/ontologyeditor/components/isa.html",
			scope: {
				list_model: "=model",
				list_class: "=classList"
	        }
		 };
	});
	
	app.directive("mokiDatatypeProperties", function() {
		return {
			restrict: "E", 
			templateUrl: "modules/ontologyeditor/components/datatype_properties.html",
			scope: {
				list_model: "=model",
				list_datatype: "=datatypeList"
	        }
		 };
	});
	
	app.directive("mokiAnnotations", function() {
		return {
			restrict: "E", 
			templateUrl: "modules/ontologyeditor/components/annotations.html",
			scope: {
				list_model: "=model",
				list_annotation: "=annotationList"
	        }
		 };
	});
	
	app.directive("mokiMapping", function() {
		return {
			restrict: "E", 
			templateUrl: "modules/ontologyeditor/components/mapping.html",
			scope: {
				list_model: "=model",
				list_class: "=classList"
	        }
		 };
	});
	
	app.directive("mokiObjectProperties", function() {
		return {
			restrict: "E", 
			templateUrl: "modules/ontologyeditor/components/object_properties.html",
			scope: {
				list_model: "=model",
				list_class: "=classList",
				list_object: "=objectList"
	        },
	        controller: function($scope, $element){
	        	$scope.delete = function(index) {
	    	   		$scope.list_model.splice(index, 1);
	    	    }
	    	   	
	    	   	$scope.new_tab = function() {
	    	   		$scope.list_model.push({'name': '', 'range': ''});
	      	   	}
	        	
	        	$scope.displayText = function(item) {
	      	   	  return item
	      	   	}
	      	   	
	      	   	$scope.afterSelect = function(item) {
	      	   	  $scope.someId = item
	      	   	}
	        }
		 };
	});
	
	app.directive("mokiLabels", function() {
		return {
			restrict: "E", 
			templateUrl: "modules/ontologyeditor/components/labels.html",
			scope: {
				list_model: "=model",
				list_languages: "=languageList"
	        },
	        controller: function($scope, $element){
	        	$scope.delete = function(index) {
	    	   		$scope.list_model.splice(index, 1);
	    	    }
	    	   	
	    	   	$scope.new_tab = function() {
	    	   		$scope.list_model.push({'label': '', 'lang': 'en'});
	      	   	}
	        }
		 };
	});
	
	app.directive("mokiRangeDatatype", function() {
		return {
			restrict: "E", 
			templateUrl: "modules/ontologyeditor/components/range_datatype.html",
			scope: {
				list_model: "=model",
				list_datatype: "=datatypeList"
	        },
	        controller: function($scope, $element){
	        	$scope.delete = function(index) {
	    	   		$scope.list_model.splice(index, 1);
	    	    }
	    	   	
	    	   	$scope.new_tab = function() {
	    	   		$scope.list_model.push('');
	      	   	}
	        }
		 };
	});
	
	app.directive("mokiDomain", function() {
		return {
			restrict: "E", 
			templateUrl: "modules/ontologyeditor/components/domain.html",
			scope: {
				list_model: "=model",
				list_class: "=classList"
	        }
		 };
	});
	
	app.directive("mokiRange", function() {
		return {
			restrict: "E", 
			templateUrl: "modules/ontologyeditor/components/range.html",
			scope: {
				list_model: "=model",
				list_class: "=classList"
	        }
		 };
	});
	
	app.directive("mokiButtons", function() {
		return {
			restrict: "E", 
			templateUrl: "modules/ontologyeditor/components/buttons.html"
		 };
	});
	
	app.directive("mokiUri", function() {
		return {
			restrict: "E", 
			templateUrl: "modules/ontologyeditor/components/uri.html",
			scope: {
				model: "=",
				list_class: "=classList",
				label: "@"
	        },
	        controller: function($scope, $element){
	        	
	        	$scope.displayText = function(item) {
	      	   	  return item.name
	      	   	}
	      	   	
	      	   	$scope.afterSelect = function(item) {
	      	   	  $scope.someId = item
	      	   	}
	        }
		 };
	});
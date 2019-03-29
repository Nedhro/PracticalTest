(function () {
    'use strict';
 
    angular
        .module('PracticalTest')
        .controller('EmployeeReportController', RegisterController);
 
    function RegisterController(UserService,$localStorage, $stateParams, $location, $rootScope,$scope,$window,$uibModal) {
        var vm = this;

        UserService.getData("/api/v1/employee/gender")
	    .then(function (response) {         	
	    	$scope.genders =response.data;
	    	vm.dataLoading =false;
	    	console.log($scope.genders);
          }, function(result){
        	  console.log(result)
          });  
        
    }
 
})();
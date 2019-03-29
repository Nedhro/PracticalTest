(function () {
    'use strict';
 
    angular
        .module('PracticalTest')
        .controller('EmployeeController', RegisterController);
 
    function RegisterController(UserService,$localStorage,jwtHelper, $stateParams, $location, $rootScope,$scope,$window,$uibModal) {
        var vm = this; 
        vm.addNewEmployee = addNewEmployee;
        
        if(typeof $stateParams.id !== 'undefined'){
        	console.log($stateParams.id);
        	
        	UserService.GetEmployeeById($stateParams.id)
	        .then(function (response) {
	            if (response.status==="success") {               
	            	vm.user=response.data;	
	            	console.log(vm.user)
	            }
	        });
        }
        
        function addNewEmployee() {
        	$scope.submitted = true;        	
        	if ($scope.userForm.$valid && $scope.isvalidImage) {
        		$scope.submitted = false;
        		vm.dataLoading = true;
        		vm.user.profile = $scope.myFile;
                UserService.Create(vm.user)
                    .then(function (response) {
                    	vm.dataLoading = false;
                    	if (response.status === "success") {
                    		$location.path('/home');
                        } else { 
                        	vm.error = response.message;
                        }
                    });
				}                  	
        }
        
        $scope.isvalidImage = true;
        $scope.checkFileType = function(evt) {
			var filename = evt[0].name;
			var fileExt = filename.substr(filename.indexOf('.') + 1);
			
			if (!fileExt == "gif" || !fileExt == "png" || !fileExt == "bmp"
                || !fileExt == "jpeg" || !fileExt == "jpg" ) {
				$scope.errType = "Image must be gif|png|bmp|jpeg|jpg";
				$scope.isvalidImage=false;
			}else if(evt[0].size>948576){
				$scope.errType = "Image size too large";
				$scope.isvalidImage = false;
				$scope.userForm.$valid=false;
			} else{			
				$scope.errType = "";
				$scope.isvalidImage = true;
				$scope.userForm.$valid=true;
			}

		};
        
    }
 
})();
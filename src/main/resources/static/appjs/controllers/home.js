(function () {
    'use strict';

    angular
        .module('PracticalTest')
        .controller('Home.IndexController', Controller);

    function Controller(UserService,$localStorage,$scope,jwtHelper,AuthenticationService,$location,$uibModal) {
        var vm = this;
        vm.deleteUser = DeleteUser;
        vm.search = search;
        vm.searchModel = {
        		name:'',
        		age:''
        }
        
        $scope.currentPage = 1;
        $scope.itemsPerPage = 2; 
        
        loadEmployee(0,2,vm.searchModel);
	    
	    $scope.pageChanged = function () {
	    	loadEmployee($scope.currentPage - 1,2,vm.searchModel);
        }
	    
	    function search(page,size,model){
	    	loadEmployee($scope.currentPage - 1,2,vm.searchModel);
	    } 
	    
	    function loadEmployee(page,size,model){
	    	UserService.SearchEmployee(page,size,model)
	        .then(function (response) {
	            if (response.status==="success") {
	            	$scope.users=response.data.content;
	            	$scope.totalItems = response.data.totalElements;
	            }
	        });
	    }
	    
	   function DeleteUser(id,index){
            var modalInstance;
            modalInstance = $uibModal.open({
                templateUrl: "views/delete_confirmation.html",
                controller: function($scope){                               
                    $scope.ok = function()
                    {
                    	UserService.DeleteData('/api/v1/employee/delete/'+id)
	      		            .then(function (response) {
	      		            	if (response.status==="success") {
	      		            		$scope.users.splice(index,1);
	      		            		modalInstance.dismiss("cancel");
	      		            	}
	      		            }, function(result){
	      		            	$rootScope.ShowAlertMessage('alert-danger', result.message );
	      		            });
                    }, 
                    $scope.cancel = function() {
                        modalInstance.dismiss("cancel");
                    };
                },
                scope: $scope,
                resolve: {
                    
                    }
            	});     
	   	}
        
    }

})();
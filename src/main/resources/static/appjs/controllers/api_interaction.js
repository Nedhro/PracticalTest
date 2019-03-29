(function () {
    'use strict';

    angular
        .module('PracticalTest')
        .controller('ApiInteractionController', Controller);

    function Controller(UserService,$scope) {
        var vm = this;
        vm.dataLoading =true;
        $scope.currentPage = 1;
        $scope.itemsPerPage = 5; 
        UserService.getData("/api/interaction")
	    .then(function (response) {         	
	    	$scope.interactionTypeGroup =response.interactionTypeGroup.interactionTypeGroup;
	    	$scope.interactionPair = $scope.interactionTypeGroup[0].interactionType[0].interactionPair;
	    	vm.dataLoading =false;
	    	//$scope.totalItems = $scope.interactionPair.length;
	    	console.dir($scope.interactionTypeGroup[0].interactionType[0].interactionPair);

          }, function(result){
        	  console.log(result)
          });
    }

})();
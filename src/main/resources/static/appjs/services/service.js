(function () {
    'use strict';
 
    angular
        .module('PracticalTest')
        .factory('UserService', UserService);
 
    UserService.$inject = ['$timeout', '$filter', '$q','$http'];
    function UserService($timeout, $filter, $q,$http) {
 
        var service = {};
 
        service.GetAllEmployee = GetAllEmployee;
        service.SearchEmployee = SearchEmployee;
        service.GetEmployeeById = GetEmployeeById;
        service.Create = Create;
        service.DeleteData = DeleteData;
        service.getData = getData;
 
        return service;
        
        var config = {
                headers : {
                    'Content-Type': 'application/json'
                }
        }
        
        
        function GetAllEmployee(pageNumber,pageSize) {        	
            var defer = $q.defer();          	
            $http.get('/api/v1/employee?page='+pageNumber+"&size="+pageSize).then(function(response) {            	
              defer.resolve(response.data);             
            }, function(response) {           	
              defer.reject(response);
            });
            return defer.promise;
        };
        
        function GetEmployeeById(id) {       	
            var defer = $q.defer();           	
            $http.get("/api/v1/employee/"+id).then(function(response) {           	
              defer.resolve(response.data);              
            }, function(response) {            	
              defer.reject(response);
            });
            return defer.promise;
        };
        
        function SearchEmployee(page,size,searchModel) {       	
            var defer = $q.defer();           	
            $http.post("/api/employee/search?page="+page+"&size="+size,searchModel)
	            .then(function(response) {           	
	              defer.resolve(response.data);              
	            }, function(response) {            	
	              defer.reject(response);
	            });
            return defer.promise;
        };

        function Create(user){       	
        	 var defer = $q.defer();  
        	 var fd = new FormData();       	 
             fd.append('name', user.name);
             fd.append('dob', user.dob);
             fd.append('gender', user.gender);
             if(typeof user.id != 'undefined'){
        		 fd.append('id', user.id);
        	 }        	 
        	 if(typeof user.profile != 'undefined'){
        		 fd.append('file', user.profile);
        	 }
             if(typeof user.note != 'undefined'){
            	 fd.append('note', user.note);
             }
	         $http({	        		 
	        		 method: 'POST',	         
	                 url: '/employee/registration',
	                 transformRequest: angular.identity,
	                 headers: {'Content-Type': undefined},
	                 data: fd
	                 })
	         .then(
		       function(response){
		    	   defer.resolve(response.data);
		       }, 
		       function(response){
		    	   defer.resolve(response.data);
		       })	             
        	return defer.promise;
        }
 
        function DeleteData(url){
		    var defer = $q.defer();
		    $http.delete(url)
		    	.then(function(response) {		    		
		    		defer.resolve(response.data);		    		
		    	},function(response) {
		    		defer.resolve(response.data);
		    	});
		    return defer.promise;
		}
        
        function getData(url){
		    var defer = $q.defer();
		    $http.get(url)
		    	.then(function(response) {		    		
		    		defer.resolve(response.data);		    		
		    	},function(response) {
		    		defer.resolve(response.data);
		    	});
		    return defer.promise;
		}
 
    }
})();
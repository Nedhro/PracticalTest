(function () {
    'use strict';

    angular
        .module('PracticalTest')
        .factory('AuthenticationService', Service);

    function Service($http, $localStorage,jwtHelper,$rootScope) {
        var service = {};
        service.Login = Login;

        return service;

        function Login(username, password, callback) {
            $http.post('/auth', { username: username, password: password })
                .success(function (response) {
                    // login successful if there's a token in the response                	
                    if (response.data) {
                        // store username and token in local storage to keep user logged in between page refreshes
                        $localStorage.currentUser = { username: username, token: response.data.accessToken };                       
                        $rootScope.tokenPayload = jwtHelper.decodeToken($localStorage.currentUser.token);
                        // add jwt token to auth header for all requests made by the $http service
                        $http.defaults.headers.common.Authorization = response.data.accessToken;
                        // execute callback with true to indicate successful login
                        callback(true);
                    } else {
                        // execute callback with false to indicate failed login
                        callback(false);
                    }
                })
            .error(function (response) {
            	callback(false);   	
            });
        }
    }
})();
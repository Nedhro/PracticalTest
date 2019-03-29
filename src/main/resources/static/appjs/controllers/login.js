(function () {
    'use strict';

    angular
        .module('PracticalTest')
        .controller('Login.IndexController', Controller);

    function Controller($location,AuthenticationService,$localStorage,$scope) {
        var vm = this;
        vm.login = login;

        function login() {
            vm.loading = true;
            AuthenticationService.Login(vm.username, vm.password, function (result) {
                if (result === true) {
                    $location.path('/home');
                } else {
                    vm.error = 'Username or password is incorrect';
                    vm.loading = false;                  
                }
            });
        };
    }

})();
(function () {
    'use strict';

    angular
        .module('PracticalTest', ['ui.router','ui.bootstrap', 'ngMessages', 'ngStorage','angular-jwt'])
        .config(config)
        .run(run);

    function config($stateProvider, $urlRouterProvider,jwtOptionsProvider, $httpProvider) {
        // default route
        $urlRouterProvider.otherwise("/login");
      
	
        $httpProvider.interceptors.push('jwtInterceptor');

        // app routes
        $stateProvider
            .state('home', {
                url: '/home',
                templateUrl: 'views/employee_list.html',
                controller: 'Home.IndexController',
                controllerAs: 'vm'
            })            
            .state('login', {
                url: '/login',
                templateUrl: 'views/login.html',
                controller: 'Login.IndexController',
                controllerAs: 'vm'
            })
            .state('/register', {
            	url: '/register',
            	title: 'Add Employee',
                templateUrl: 'views/add_employee.html',
                controller: 'EmployeeController',
                controllerAs: 'vm'
            })
            .state('/registerEdit', {
            	url: '/register/:id',
            	title: 'Edit Employee',
                templateUrl: 'views/add_employee.html',
                controller: 'EmployeeController',
                controllerAs: 'vm'
            })
            .state('/apiinteraction', {
            	url: '/apiinteraction',
            	title: 'api Intercation',
                templateUrl: 'views/api_interaction.html',
                controller: 'ApiInteractionController',
                controllerAs: 'vm'
            })
            .state('/reportGenderCount', {
            	url: '/genderWiseEmployees',
            	title: 'Report Gender Count',
                templateUrl: 'views/gender_count_report.html',
                controller: 'EmployeeReportController',
                controllerAs: 'vm'
            })
        	
    }

    function run($rootScope, $http, $location, $localStorage,authManager,jwtHelper,$window) {
    	
    	authManager.redirectWhenUnauthenticated(); 	
    	
        // keep user logged in after page refresh
        if ($localStorage.currentUser) {
        	$rootScope.tokenPayload = jwtHelper.decodeToken($localStorage.currentUser.token);
            $http.defaults.headers.common.Authorization = $localStorage.currentUser.token;
        }
        
        // redirect to login page if not logged in and trying to access a restricted page
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
        	$rootScope.currentmenu = $location.path();
            var publicPages = ['/login'];
            var restrictedPage = publicPages.indexOf($location.path()) === -1;
            if (restrictedPage && !$localStorage.currentUser) {
                $location.path('/login');
            }
        });
        
        $rootScope.logout = function () {
        	delete $localStorage.currentUser;
        	$rootScope.tokenPayload='';
            $http.defaults.headers.common.Authorization = '';
            $window.location.reload();
        }
        
    }
    
})();
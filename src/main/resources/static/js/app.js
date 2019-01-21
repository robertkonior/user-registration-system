var app = angular.module('userregistrationsystem', ['ngRoute', 'ngResource']);

app.config(function ($routeProvider) {
    $routeProvider
        .when('/list-all-users', {
            templateUrl: '/template/listuser.html',
            controller: 'listUserController'
        })
        .when('/register-new-user', {
            templateUrl : '/template/userregistration.html',
            controller: 'registrationUserController'
        })
        .when('/update-user/:id', {
            templateUrl: '/template/userupdation.html',
            controller: 'userDetailsController'
        })
        .otherwise({
            redirectTo: '/home',
            templateUrl: '/template/home.html'
        });
});

app.config(['$httpProvider',function ($httpProvider) {
    $httpProvider.interceptors.push('AuthInterceptor');
}]);
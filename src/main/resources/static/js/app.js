var app = angular.module('userregistrationsystem', ['ngRoute', 'ngResource']);

app.config(function ($routeProvider) {
    $routeProvider
        .when('/list-all-users', {
            templateUrl: '/template/listuser.html',
            controller: 'listUserController'
        })
        .when('/', {
            templateUrl: '/template/home.html',
            controller: 'homeController'
        })
        .when('/register-new-user', {
            templateUrl: '/template/userregistration.html',
            controller: 'registrationUserController'
        })
        .when('/update-user/:id', {
            templateUrl: '/template/userupdation.html',
            controller: 'userDetailsController'
        })
        .when('/login', {
            templateUrl: '/template/login.html',
            controller: 'loginController'
        })
        .when('/logout', {
            templateUrl: '/template/logout.html',
            controller: 'logoutController'
        })
        .otherwise({
            redirectTo: '/login'
        });
});

app.config(['$httpProvider', function ($httpProvider) {
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
}]);
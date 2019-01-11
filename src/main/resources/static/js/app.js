var app = angular.module('userregistrationsystem', ['ngRoute', 'ngResource']);

app.config(function ($routeProvider) {
    $routeProvider
        .when('list-all-users', {
            templateUrl: '/template/listuser.html',
            controller: 'listUSerController'
        })
        .when('register-new-user', {
            template: '/template/userregistartion.html',
            controller: 'registrationUserController'
        })
        .when('/update-user/:id', {
            templateUrl: '/template/userupdation.html',
            controller: 'userDetailsController'
        })
        .otherwise({
            redirectTo: '/home',
            templateUrl: '/template/home.html'
        })
})
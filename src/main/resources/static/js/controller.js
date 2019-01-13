app.controller('registrationUserController', function ($scope, $http, $window, $location, $route) {

    $scope.submitUserForm = function () {
        $http({
            method: 'POST',
            url: 'http://localhost:8080/api/users/',
            data: $scope.user,
        }).then(function () {
                $window.alert("User registered successfully");
                $location.path("list-all-users");
                $route.reload();
        }, function (errResponse) {
            $scope.errorMessage = errResponse.data.errorMessage;
        });
    };

    $scope.resetForm = function () {
        $scope.user = null;
    };
});
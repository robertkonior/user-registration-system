app.controller('registrationUserController', function ($scope, $http, $window, $location, $route) {

    $scope.submitUserForm = function () {
        $http({
            method: 'POST',
            url: 'http://localhost:8080/api/users/',
            data: $scope.user
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

app.controller('listUserController', function ($scope, $http, $location, $route) {
    $http({
        method: 'GET',
        url: 'http://localhost:8080/api/users/'
    }).then(function (response) {
        $scope.users = response.data;
    });

    $scope.editUser = function (userId) {
        $location.path("/update-users/" + userId);
    };

    $scope.deleteUser = function (userId) {
        $http({
            method: 'DELETE',
            url: 'http://localhost:8080/api/users/' + userId
        }).then(
            function () {
                $location.path("/list-all-users");
                $route.reload();
            });
    }
});


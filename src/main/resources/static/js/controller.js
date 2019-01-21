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
        $location.path("/update-user/" + userId);
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

app.controller('userDetailsController', function ($scope, $http, $location, $routeParams, $route, $window) {
    $scope.userId = $routeParams.id;

    $http({
        method: 'GET',
        url: 'http://localhost:8080/api/users/' + $scope.userId
    }).then(function (response) {
        $scope.user = response.data;
    });

    $scope.submitUserForm = function () {
        $http({
            method: 'PUT',
            url:  'http://localhost:8080/api/users/' + $scope.userId,
            data: $scope.user
        }).then(  function () {
                $window.alert("User updated successfully");
                $location.path("list-all-users");
                $route.reload();
            },  function (errResponse) {
                $scope.errorMessage = "Error while updating User - Error Message: "
                    + errResponse.data.errorMessage;
            });
    };
});

app.controller('homeController', function ($rootScope, $scope, $http, $location, $route) {

    if ($rootScope.authenticated) {
        $location.path("/");
        $scope.loginerror = false;
    } else {
        $location.path("/login");
        $scope.loginerror = true;
    }
});



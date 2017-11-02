angular.module('planty', [])
    .controller('plant', function($scope, $http) {
        $http.get('http://localhost:9999/plant/0').
        then(function(response) {
            $scope.plant = response.data;
        });
    });
(function() {
    'use strict';

    angular
        .module('baloncestoseedApp')
        .controller('CityDetailController', CityDetailController);

    CityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'City', 'Player', 'Team'];

    function CityDetailController($scope, $rootScope, $stateParams, previousState, entity, City, Player, Team) {
        var vm = this;

        vm.city = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('baloncestoseedApp:cityUpdate', function(event, result) {
            vm.city = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

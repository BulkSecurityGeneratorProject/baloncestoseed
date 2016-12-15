(function() {
    'use strict';

    angular
        .module('baloncestoseedApp')
        .controller('TeamDetailController', TeamDetailController);

    TeamDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Team', 'City', 'Player', 'Game'];

    function TeamDetailController($scope, $rootScope, $stateParams, previousState, entity, Team, City, Player, Game) {
        var vm = this;

        vm.team = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('baloncestoseedApp:teamUpdate', function(event, result) {
            vm.team = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

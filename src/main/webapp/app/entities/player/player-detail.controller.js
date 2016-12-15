(function() {
    'use strict';

    angular
        .module('baloncestoseedApp')
        .controller('PlayerDetailController', PlayerDetailController);

    PlayerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Player', 'City', 'Team', 'UserPlayerFavorite'];

    function PlayerDetailController($scope, $rootScope, $stateParams, previousState, entity, Player, City, Team, UserPlayerFavorite) {
        var vm = this;

        vm.player = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('baloncestoseedApp:playerUpdate', function(event, result) {
            vm.player = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

(function() {
    'use strict';

    angular
        .module('baloncestoseedApp')
        .controller('GameDetailController', GameDetailController);

    GameDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Game', 'Team', 'GameUserRating'];

    function GameDetailController($scope, $rootScope, $stateParams, previousState, entity, Game, Team, GameUserRating) {
        var vm = this;

        vm.game = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('baloncestoseedApp:gameUpdate', function(event, result) {
            vm.game = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

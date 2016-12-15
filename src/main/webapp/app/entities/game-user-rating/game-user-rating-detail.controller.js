(function() {
    'use strict';

    angular
        .module('baloncestoseedApp')
        .controller('GameUserRatingDetailController', GameUserRatingDetailController);

    GameUserRatingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'GameUserRating', 'Game', 'User'];

    function GameUserRatingDetailController($scope, $rootScope, $stateParams, previousState, entity, GameUserRating, Game, User) {
        var vm = this;

        vm.gameUserRating = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('baloncestoseedApp:gameUserRatingUpdate', function(event, result) {
            vm.gameUserRating = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

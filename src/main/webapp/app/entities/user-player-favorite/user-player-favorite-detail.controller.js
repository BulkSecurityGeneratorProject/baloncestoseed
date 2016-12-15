(function() {
    'use strict';

    angular
        .module('baloncestoseedApp')
        .controller('UserPlayerFavoriteDetailController', UserPlayerFavoriteDetailController);

    UserPlayerFavoriteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserPlayerFavorite', 'User', 'Player'];

    function UserPlayerFavoriteDetailController($scope, $rootScope, $stateParams, previousState, entity, UserPlayerFavorite, User, Player) {
        var vm = this;

        vm.userPlayerFavorite = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('baloncestoseedApp:userPlayerFavoriteUpdate', function(event, result) {
            vm.userPlayerFavorite = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

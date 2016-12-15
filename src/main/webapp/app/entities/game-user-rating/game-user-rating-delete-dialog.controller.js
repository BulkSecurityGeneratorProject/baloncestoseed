(function() {
    'use strict';

    angular
        .module('baloncestoseedApp')
        .controller('GameUserRatingDeleteController',GameUserRatingDeleteController);

    GameUserRatingDeleteController.$inject = ['$uibModalInstance', 'entity', 'GameUserRating'];

    function GameUserRatingDeleteController($uibModalInstance, entity, GameUserRating) {
        var vm = this;

        vm.gameUserRating = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            GameUserRating.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

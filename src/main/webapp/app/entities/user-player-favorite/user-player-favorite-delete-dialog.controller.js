(function() {
    'use strict';

    angular
        .module('baloncestoseedApp')
        .controller('UserPlayerFavoriteDeleteController',UserPlayerFavoriteDeleteController);

    UserPlayerFavoriteDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserPlayerFavorite'];

    function UserPlayerFavoriteDeleteController($uibModalInstance, entity, UserPlayerFavorite) {
        var vm = this;

        vm.userPlayerFavorite = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserPlayerFavorite.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

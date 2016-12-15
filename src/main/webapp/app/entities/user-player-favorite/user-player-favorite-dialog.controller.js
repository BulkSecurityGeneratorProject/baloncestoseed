(function() {
    'use strict';

    angular
        .module('baloncestoseedApp')
        .controller('UserPlayerFavoriteDialogController', UserPlayerFavoriteDialogController);

    UserPlayerFavoriteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserPlayerFavorite', 'User', 'Player'];

    function UserPlayerFavoriteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserPlayerFavorite, User, Player) {
        var vm = this;

        vm.userPlayerFavorite = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.users = User.query();
        vm.players = Player.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userPlayerFavorite.id !== null) {
                UserPlayerFavorite.update(vm.userPlayerFavorite, onSaveSuccess, onSaveError);
            } else {
                UserPlayerFavorite.save(vm.userPlayerFavorite, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('baloncestoseedApp:userPlayerFavoriteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.timestamp = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

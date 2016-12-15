(function() {
    'use strict';

    angular
        .module('baloncestoseedApp')
        .controller('GameUserRatingDialogController', GameUserRatingDialogController);

    GameUserRatingDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'GameUserRating', 'Game', 'User'];

    function GameUserRatingDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, GameUserRating, Game, User) {
        var vm = this;

        vm.gameUserRating = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.games = Game.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.gameUserRating.id !== null) {
                GameUserRating.update(vm.gameUserRating, onSaveSuccess, onSaveError);
            } else {
                GameUserRating.save(vm.gameUserRating, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('baloncestoseedApp:gameUserRatingUpdate', result);
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

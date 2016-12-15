(function() {
    'use strict';

    angular
        .module('baloncestoseedApp')
        .controller('PlayerDialogController', PlayerDialogController);

    PlayerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Player', 'City', 'Team', 'UserPlayerFavorite'];

    function PlayerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Player, City, Team, UserPlayerFavorite) {
        var vm = this;

        vm.player = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.cities = City.query({filter: 'player-is-null'});
        $q.all([vm.player.$promise, vm.cities.$promise]).then(function() {
            if (!vm.player.city || !vm.player.city.id) {
                return $q.reject();
            }
            return City.get({id : vm.player.city.id}).$promise;
        }).then(function(city) {
            vm.cities.push(city);
        });
        vm.teams = Team.query();
        vm.userplayerfavorites = UserPlayerFavorite.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.player.id !== null) {
                Player.update(vm.player, onSaveSuccess, onSaveError);
            } else {
                Player.save(vm.player, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('baloncestoseedApp:playerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.birthdate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('baloncestoseedApp')
        .controller('TeamDialogController', TeamDialogController);

    TeamDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Team', 'City', 'Player', 'Game'];

    function TeamDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Team, City, Player, Game) {
        var vm = this;

        vm.team = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.cities = City.query({filter: 'team-is-null'});
        $q.all([vm.team.$promise, vm.cities.$promise]).then(function() {
            if (!vm.team.city || !vm.team.city.id) {
                return $q.reject();
            }
            return City.get({id : vm.team.city.id}).$promise;
        }).then(function(city) {
            vm.cities.push(city);
        });
        vm.players = Player.query();
        vm.games = Game.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.team.id !== null) {
                Team.update(vm.team, onSaveSuccess, onSaveError);
            } else {
                Team.save(vm.team, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('baloncestoseedApp:teamUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fundationDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

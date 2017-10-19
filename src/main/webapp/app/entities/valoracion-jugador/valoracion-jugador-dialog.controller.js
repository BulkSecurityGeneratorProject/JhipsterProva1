(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('ValoracionJugadorDialogController', ValoracionJugadorDialogController);

    ValoracionJugadorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ValoracionJugador', 'Jugador', 'User'];

    function ValoracionJugadorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ValoracionJugador, Jugador, User) {
        var vm = this;

        vm.valoracionJugador = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.jugadors = Jugador.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.valoracionJugador.id !== null) {
                ValoracionJugador.update(vm.valoracionJugador, onSaveSuccess, onSaveError);
            } else {
                ValoracionJugador.save(vm.valoracionJugador, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterProva1App:valoracionJugadorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.momento = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

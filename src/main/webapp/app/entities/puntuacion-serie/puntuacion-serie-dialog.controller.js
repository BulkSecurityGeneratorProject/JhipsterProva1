(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('PuntuacionSerieDialogController', PuntuacionSerieDialogController);

    PuntuacionSerieDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PuntuacionSerie', 'Serie', 'User'];

    function PuntuacionSerieDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PuntuacionSerie, Serie, User) {
        var vm = this;

        vm.puntuacionSerie = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.series = Serie.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.puntuacionSerie.id !== null) {
                PuntuacionSerie.update(vm.puntuacionSerie, onSaveSuccess, onSaveError);
            } else {
                PuntuacionSerie.save(vm.puntuacionSerie, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterProva1App:puntuacionSerieUpdate', result);
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

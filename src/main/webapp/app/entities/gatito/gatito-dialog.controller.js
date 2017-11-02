(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('GatitoDialogController', GatitoDialogController);

    GatitoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Gatito'];

    function GatitoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Gatito) {
        var vm = this;

        vm.gatito = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.gatito.id !== null) {
                Gatito.update(vm.gatito, onSaveSuccess, onSaveError);
            } else {
                Gatito.save(vm.gatito, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterProva1App:gatitoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.anoCreacion = false;

        vm.setFoto = function ($file, gatito) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        gatito.foto = base64Data;
                        gatito.fotoContentType = $file.type;
                    });
                });
            }
        };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

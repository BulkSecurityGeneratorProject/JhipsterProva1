(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('PruebaDialogController', PruebaDialogController);

    PruebaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Prueba'];

    function PruebaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Prueba) {
        var vm = this;

        vm.prueba = entity;
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
            if (vm.prueba.id !== null) {
                Prueba.update(vm.prueba, onSaveSuccess, onSaveError);
            } else {
                Prueba.save(vm.prueba, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterProva1App:pruebaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.anoCreacion = false;

        vm.setFoto = function ($file, prueba) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        prueba.foto = base64Data;
                        prueba.fotoContentType = $file.type;
                    });
                });
            }
        };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

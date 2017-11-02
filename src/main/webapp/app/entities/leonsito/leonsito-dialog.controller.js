(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('LeonsitoDialogController', LeonsitoDialogController);

    LeonsitoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Leonsito'];

    function LeonsitoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Leonsito) {
        var vm = this;

        vm.leonsito = entity;
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
            if (vm.leonsito.id !== null) {
                Leonsito.update(vm.leonsito, onSaveSuccess, onSaveError);
            } else {
                Leonsito.save(vm.leonsito, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterProva1App:leonsitoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.anoCreacion = false;

        vm.setFoto = function ($file, leonsito) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        leonsito.foto = base64Data;
                        leonsito.fotoContentType = $file.type;
                    });
                });
            }
        };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

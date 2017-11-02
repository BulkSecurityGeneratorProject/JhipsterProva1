(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('JaguarDialogController', JaguarDialogController);

    JaguarDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Jaguar'];

    function JaguarDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Jaguar) {
        var vm = this;

        vm.jaguar = entity;
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
            if (vm.jaguar.id !== null) {
                Jaguar.update(vm.jaguar, onSaveSuccess, onSaveError);
            } else {
                Jaguar.save(vm.jaguar, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterProva1App:jaguarUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.anoCreacion = false;

        vm.setFoto = function ($file, jaguar) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        jaguar.foto = base64Data;
                        jaguar.fotoContentType = $file.type;
                    });
                });
            }
        };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

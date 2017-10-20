(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('GeneroDialogController', GeneroDialogController);

    GeneroDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Genero'];

    function GeneroDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Genero) {
        var vm = this;

        vm.genero = entity;
        vm.clear = clear;
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
            if (vm.genero.id !== null) {
                Genero.update(vm.genero, onSaveSuccess, onSaveError);
            } else {
                Genero.save(vm.genero, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterProva1App:generoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setFoto = function ($file, genero) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        genero.foto = base64Data;
                        genero.fotoContentType = $file.type;
                    });
                });
            }
        };

    }
})();

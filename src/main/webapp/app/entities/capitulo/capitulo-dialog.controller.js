(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('CapituloDialogController', CapituloDialogController);

    CapituloDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Capitulo', 'Serie'];

    function CapituloDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Capitulo, Serie) {
        var vm = this;

        vm.capitulo = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.series = Serie.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.capitulo.id !== null) {
                Capitulo.update(vm.capitulo, onSaveSuccess, onSaveError);
            } else {
                Capitulo.save(vm.capitulo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterProva1App:capituloUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setFoto = function ($file, capitulo) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        capitulo.foto = base64Data;
                        capitulo.fotoContentType = $file.type;
                    });
                });
            }
        };

    }
})();

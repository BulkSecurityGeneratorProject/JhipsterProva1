(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('ProductoraDialogController', ProductoraDialogController);

    ProductoraDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Productora'];

    function ProductoraDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Productora) {
        var vm = this;

        vm.productora = entity;
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
            if (vm.productora.id !== null) {
                Productora.update(vm.productora, onSaveSuccess, onSaveError);
            } else {
                Productora.save(vm.productora, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterProva1App:productoraUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setFoto = function ($file, productora) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        productora.foto = base64Data;
                        productora.fotoContentType = $file.type;
                    });
                });
            }
        };

    }
})();

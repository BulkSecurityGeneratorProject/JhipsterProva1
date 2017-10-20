(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('SerieDialogController', SerieDialogController);

    SerieDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Serie', 'Productora', 'Actor', 'Genero'];

    function SerieDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Serie, Productora, Actor, Genero) {
        var vm = this;

        vm.serie = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.productoras = Productora.query();
        vm.actors = Actor.query();
        vm.generos = Genero.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.serie.id !== null) {
                Serie.update(vm.serie, onSaveSuccess, onSaveError);
            } else {
                Serie.save(vm.serie, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterProva1App:serieUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.anoCreacion = false;

        vm.setFoto = function ($file, serie) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        serie.foto = base64Data;
                        serie.fotoContentType = $file.type;
                    });
                });
            }
        };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

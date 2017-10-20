(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('PuntuacionSerieDeleteController',PuntuacionSerieDeleteController);

    PuntuacionSerieDeleteController.$inject = ['$uibModalInstance', 'entity', 'PuntuacionSerie'];

    function PuntuacionSerieDeleteController($uibModalInstance, entity, PuntuacionSerie) {
        var vm = this;

        vm.puntuacionSerie = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PuntuacionSerie.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

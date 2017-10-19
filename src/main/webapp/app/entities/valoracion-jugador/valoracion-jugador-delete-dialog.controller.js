(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('ValoracionJugadorDeleteController',ValoracionJugadorDeleteController);

    ValoracionJugadorDeleteController.$inject = ['$uibModalInstance', 'entity', 'ValoracionJugador'];

    function ValoracionJugadorDeleteController($uibModalInstance, entity, ValoracionJugador) {
        var vm = this;

        vm.valoracionJugador = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ValoracionJugador.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

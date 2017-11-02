(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('PruebaDeleteController',PruebaDeleteController);

    PruebaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Prueba'];

    function PruebaDeleteController($uibModalInstance, entity, Prueba) {
        var vm = this;

        vm.prueba = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Prueba.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('GatitoDeleteController',GatitoDeleteController);

    GatitoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Gatito'];

    function GatitoDeleteController($uibModalInstance, entity, Gatito) {
        var vm = this;

        vm.gatito = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Gatito.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

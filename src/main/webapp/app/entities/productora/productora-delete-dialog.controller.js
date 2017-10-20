(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('ProductoraDeleteController',ProductoraDeleteController);

    ProductoraDeleteController.$inject = ['$uibModalInstance', 'entity', 'Productora'];

    function ProductoraDeleteController($uibModalInstance, entity, Productora) {
        var vm = this;

        vm.productora = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Productora.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

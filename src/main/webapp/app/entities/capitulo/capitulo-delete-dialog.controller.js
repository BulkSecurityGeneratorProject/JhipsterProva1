(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('CapituloDeleteController',CapituloDeleteController);

    CapituloDeleteController.$inject = ['$uibModalInstance', 'entity', 'Capitulo'];

    function CapituloDeleteController($uibModalInstance, entity, Capitulo) {
        var vm = this;

        vm.capitulo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Capitulo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

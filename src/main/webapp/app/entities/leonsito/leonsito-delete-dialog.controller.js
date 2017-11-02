(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('LeonsitoDeleteController',LeonsitoDeleteController);

    LeonsitoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Leonsito'];

    function LeonsitoDeleteController($uibModalInstance, entity, Leonsito) {
        var vm = this;

        vm.leonsito = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Leonsito.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

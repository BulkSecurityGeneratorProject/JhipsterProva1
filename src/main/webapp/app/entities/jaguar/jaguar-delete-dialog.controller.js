(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('JaguarDeleteController',JaguarDeleteController);

    JaguarDeleteController.$inject = ['$uibModalInstance', 'entity', 'Jaguar'];

    function JaguarDeleteController($uibModalInstance, entity, Jaguar) {
        var vm = this;

        vm.jaguar = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Jaguar.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

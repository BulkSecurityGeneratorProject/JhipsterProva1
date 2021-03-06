(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('ActorDeleteController',ActorDeleteController);

    ActorDeleteController.$inject = ['$uibModalInstance', 'entity', 'Actor'];

    function ActorDeleteController($uibModalInstance, entity, Actor) {
        var vm = this;

        vm.actor = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Actor.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

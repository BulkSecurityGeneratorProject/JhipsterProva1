(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('ActorController', ActorController);

    ActorController.$inject = ['DataUtils', 'Actor'];

    function ActorController(DataUtils, Actor) {

        var vm = this;

        vm.actors = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Actor.query(function(result) {
                vm.actors = result;
                vm.searchQuery = null;
            });
        }
    }
})();

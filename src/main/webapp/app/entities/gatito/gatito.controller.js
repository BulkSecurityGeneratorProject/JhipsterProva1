(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('GatitoController', GatitoController);

    GatitoController.$inject = ['DataUtils', 'Gatito'];

    function GatitoController(DataUtils, Gatito) {

        var vm = this;

        vm.gatitos = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Gatito.query(function(result) {
                vm.gatitos = result;
                vm.searchQuery = null;
            });
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('PruebaController', PruebaController);

    PruebaController.$inject = ['DataUtils', 'Prueba'];

    function PruebaController(DataUtils, Prueba) {

        var vm = this;

        vm.pruebas = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Prueba.query(function(result) {
                vm.pruebas = result;
                vm.searchQuery = null;
            });
        }
    }
})();

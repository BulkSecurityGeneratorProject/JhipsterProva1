(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('CapituloController', CapituloController);

    CapituloController.$inject = ['DataUtils', 'Capitulo'];

    function CapituloController(DataUtils, Capitulo) {

        var vm = this;

        vm.capitulos = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Capitulo.query(function(result) {
                vm.capitulos = result;
                vm.searchQuery = null;
            });
        }
    }
})();

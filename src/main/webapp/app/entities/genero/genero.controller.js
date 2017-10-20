(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('GeneroController', GeneroController);

    GeneroController.$inject = ['DataUtils', 'Genero'];

    function GeneroController(DataUtils, Genero) {

        var vm = this;

        vm.generos = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Genero.query(function(result) {
                vm.generos = result;
                vm.searchQuery = null;
            });
        }
    }
})();

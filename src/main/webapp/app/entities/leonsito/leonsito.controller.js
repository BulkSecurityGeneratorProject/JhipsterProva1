(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('LeonsitoController', LeonsitoController);

    LeonsitoController.$inject = ['DataUtils', 'Leonsito'];

    function LeonsitoController(DataUtils, Leonsito) {

        var vm = this;

        vm.leonsitos = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Leonsito.query(function(result) {
                vm.leonsitos = result;
                vm.searchQuery = null;
            });
        }
    }
})();

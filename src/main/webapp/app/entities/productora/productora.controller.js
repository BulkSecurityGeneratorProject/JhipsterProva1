(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('ProductoraController', ProductoraController);

    ProductoraController.$inject = ['DataUtils', 'Productora'];

    function ProductoraController(DataUtils, Productora) {

        var vm = this;

        vm.productoras = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Productora.query(function(result) {
                vm.productoras = result;
                vm.searchQuery = null;
            });
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('JaguarController', JaguarController);

    JaguarController.$inject = ['DataUtils', 'Jaguar'];

    function JaguarController(DataUtils, Jaguar) {

        var vm = this;

        vm.jaguars = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Jaguar.query(function(result) {
                vm.jaguars = result;
                vm.searchQuery = null;
            });
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('SerieController', SerieController);

    SerieController.$inject = ['DataUtils', 'Serie'];

    function SerieController(DataUtils, Serie) {

        var vm = this;

        vm.series = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Serie.query(function(result) {
                vm.series = result;
                vm.searchQuery = null;
            });
        }
    }
})();

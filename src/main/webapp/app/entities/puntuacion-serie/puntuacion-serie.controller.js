(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('PuntuacionSerieController', PuntuacionSerieController);

    PuntuacionSerieController.$inject = ['PuntuacionSerie'];

    function PuntuacionSerieController(PuntuacionSerie) {

        var vm = this;

        vm.puntuacionSeries = [];

        loadAll();

        function loadAll() {
            PuntuacionSerie.query(function(result) {
                vm.puntuacionSeries = result;
                vm.searchQuery = null;
            });
        }
    }
})();

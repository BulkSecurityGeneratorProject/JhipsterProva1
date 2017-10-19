(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('ValoracionJugadorController', ValoracionJugadorController);

    ValoracionJugadorController.$inject = ['ValoracionJugador'];

    function ValoracionJugadorController(ValoracionJugador) {

        var vm = this;

        vm.valoracionJugadors = [];

        loadAll();

        function loadAll() {
            ValoracionJugador.query(function(result) {
                vm.valoracionJugadors = result;
                vm.searchQuery = null;
            });
        }
    }
})();

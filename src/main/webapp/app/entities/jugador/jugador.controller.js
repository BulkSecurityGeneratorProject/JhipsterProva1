(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('JugadorController', JugadorController);

    JugadorController.$inject = ['Jugador'];

    function JugadorController(Jugador) {

        var vm = this;

        vm.jugadors = [];

        loadAll();

        function loadAll() {
            Jugador.query(function(result) {
                vm.jugadors = result;
                vm.searchQuery = null;
            });
        }
    }
})();

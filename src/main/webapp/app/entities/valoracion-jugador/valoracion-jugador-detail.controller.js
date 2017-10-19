(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('ValoracionJugadorDetailController', ValoracionJugadorDetailController);

    ValoracionJugadorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ValoracionJugador', 'Jugador', 'User'];

    function ValoracionJugadorDetailController($scope, $rootScope, $stateParams, previousState, entity, ValoracionJugador, Jugador, User) {
        var vm = this;

        vm.valoracionJugador = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipsterProva1App:valoracionJugadorUpdate', function(event, result) {
            vm.valoracionJugador = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

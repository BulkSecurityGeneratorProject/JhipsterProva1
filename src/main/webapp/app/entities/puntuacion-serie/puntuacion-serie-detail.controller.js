(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('PuntuacionSerieDetailController', PuntuacionSerieDetailController);

    PuntuacionSerieDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PuntuacionSerie', 'Serie', 'User'];

    function PuntuacionSerieDetailController($scope, $rootScope, $stateParams, previousState, entity, PuntuacionSerie, Serie, User) {
        var vm = this;

        vm.puntuacionSerie = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipsterProva1App:puntuacionSerieUpdate', function(event, result) {
            vm.puntuacionSerie = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

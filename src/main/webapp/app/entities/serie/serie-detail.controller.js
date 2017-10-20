(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('SerieDetailController', SerieDetailController);

    SerieDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Serie', 'Productora', 'Actor', 'Genero'];

    function SerieDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Serie, Productora, Actor, Genero) {
        var vm = this;

        vm.serie = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('jhipsterProva1App:serieUpdate', function(event, result) {
            vm.serie = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

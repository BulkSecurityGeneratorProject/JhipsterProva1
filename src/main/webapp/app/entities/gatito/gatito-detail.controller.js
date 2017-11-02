(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('GatitoDetailController', GatitoDetailController);

    GatitoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Gatito'];

    function GatitoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Gatito) {
        var vm = this;

        vm.gatito = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('jhipsterProva1App:gatitoUpdate', function(event, result) {
            vm.gatito = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

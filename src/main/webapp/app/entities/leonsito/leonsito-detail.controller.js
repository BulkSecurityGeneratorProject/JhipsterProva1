(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('LeonsitoDetailController', LeonsitoDetailController);

    LeonsitoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Leonsito'];

    function LeonsitoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Leonsito) {
        var vm = this;

        vm.leonsito = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('jhipsterProva1App:leonsitoUpdate', function(event, result) {
            vm.leonsito = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

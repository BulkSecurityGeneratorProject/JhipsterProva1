(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('CapituloDetailController', CapituloDetailController);

    CapituloDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Capitulo', 'Serie'];

    function CapituloDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Capitulo, Serie) {
        var vm = this;

        vm.capitulo = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('jhipsterProva1App:capituloUpdate', function(event, result) {
            vm.capitulo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

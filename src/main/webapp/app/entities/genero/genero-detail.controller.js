(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('GeneroDetailController', GeneroDetailController);

    GeneroDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Genero'];

    function GeneroDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Genero) {
        var vm = this;

        vm.genero = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('jhipsterProva1App:generoUpdate', function(event, result) {
            vm.genero = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('JaguarDetailController', JaguarDetailController);

    JaguarDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Jaguar'];

    function JaguarDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Jaguar) {
        var vm = this;

        vm.jaguar = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('jhipsterProva1App:jaguarUpdate', function(event, result) {
            vm.jaguar = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

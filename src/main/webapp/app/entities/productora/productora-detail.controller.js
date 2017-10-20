(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .controller('ProductoraDetailController', ProductoraDetailController);

    ProductoraDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Productora'];

    function ProductoraDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Productora) {
        var vm = this;

        vm.productora = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('jhipsterProva1App:productoraUpdate', function(event, result) {
            vm.productora = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

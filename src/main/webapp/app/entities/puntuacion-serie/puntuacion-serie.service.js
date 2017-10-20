(function() {
    'use strict';
    angular
        .module('jhipsterProva1App')
        .factory('PuntuacionSerie', PuntuacionSerie);

    PuntuacionSerie.$inject = ['$resource', 'DateUtils'];

    function PuntuacionSerie ($resource, DateUtils) {
        var resourceUrl =  'api/puntuacion-series/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.momento = DateUtils.convertDateTimeFromServer(data.momento);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

(function() {
    'use strict';
    angular
        .module('jhipsterProva1App')
        .factory('ValoracionJugador', ValoracionJugador);

    ValoracionJugador.$inject = ['$resource', 'DateUtils'];

    function ValoracionJugador ($resource, DateUtils) {
        var resourceUrl =  'api/valoracion-jugadors/:id';

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

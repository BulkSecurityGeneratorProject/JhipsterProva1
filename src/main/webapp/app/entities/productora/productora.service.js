(function() {
    'use strict';
    angular
        .module('jhipsterProva1App')
        .factory('Productora', Productora);

    Productora.$inject = ['$resource'];

    function Productora ($resource) {
        var resourceUrl =  'api/productoras/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

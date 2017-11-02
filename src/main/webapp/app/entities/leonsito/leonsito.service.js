(function() {
    'use strict';
    angular
        .module('jhipsterProva1App')
        .factory('Leonsito', Leonsito);

    Leonsito.$inject = ['$resource', 'DateUtils'];

    function Leonsito ($resource, DateUtils) {
        var resourceUrl =  'api/leonsitos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.anoCreacion = DateUtils.convertLocalDateFromServer(data.anoCreacion);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.anoCreacion = DateUtils.convertLocalDateToServer(copy.anoCreacion);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.anoCreacion = DateUtils.convertLocalDateToServer(copy.anoCreacion);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();

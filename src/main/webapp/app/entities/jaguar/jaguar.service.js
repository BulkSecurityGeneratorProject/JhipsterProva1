(function() {
    'use strict';
    angular
        .module('jhipsterProva1App')
        .factory('Jaguar', Jaguar);

    Jaguar.$inject = ['$resource', 'DateUtils'];

    function Jaguar ($resource, DateUtils) {
        var resourceUrl =  'api/jaguars/:id';

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

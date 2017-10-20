(function() {
    'use strict';
    angular
        .module('jhipsterProva1App')
        .factory('Actor', Actor);

    Actor.$inject = ['$resource', 'DateUtils'];

    function Actor ($resource, DateUtils) {
        var resourceUrl =  'api/actors/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.anoNacimiento = DateUtils.convertLocalDateFromServer(data.anoNacimiento);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.anoNacimiento = DateUtils.convertLocalDateToServer(copy.anoNacimiento);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.anoNacimiento = DateUtils.convertLocalDateToServer(copy.anoNacimiento);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();

(function () {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();

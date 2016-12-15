(function () {
    'use strict';

    angular
        .module('baloncestoseedApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();

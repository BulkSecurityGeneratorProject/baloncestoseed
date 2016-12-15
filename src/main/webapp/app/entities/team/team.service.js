(function() {
    'use strict';
    angular
        .module('baloncestoseedApp')
        .factory('Team', Team);

    Team.$inject = ['$resource', 'DateUtils'];

    function Team ($resource, DateUtils) {
        var resourceUrl =  'api/teams/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fundationDate = DateUtils.convertLocalDateFromServer(data.fundationDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fundationDate = DateUtils.convertLocalDateToServer(copy.fundationDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fundationDate = DateUtils.convertLocalDateToServer(copy.fundationDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();

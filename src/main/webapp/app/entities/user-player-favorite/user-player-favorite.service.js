(function() {
    'use strict';
    angular
        .module('baloncestoseedApp')
        .factory('UserPlayerFavorite', UserPlayerFavorite);

    UserPlayerFavorite.$inject = ['$resource', 'DateUtils'];

    function UserPlayerFavorite ($resource, DateUtils) {
        var resourceUrl =  'api/user-player-favorites/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.timestamp = DateUtils.convertDateTimeFromServer(data.timestamp);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

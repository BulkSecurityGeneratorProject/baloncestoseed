(function() {
    'use strict';
    angular
        .module('baloncestoseedApp')
        .factory('GameUserRating', GameUserRating);

    GameUserRating.$inject = ['$resource', 'DateUtils'];

    function GameUserRating ($resource, DateUtils) {
        var resourceUrl =  'api/game-user-ratings/:id';

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

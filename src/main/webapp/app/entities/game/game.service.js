(function() {
    'use strict';
    angular
        .module('baloncestoseedApp')
        .factory('Game', Game);

    Game.$inject = ['$resource', 'DateUtils'];

    function Game ($resource, DateUtils) {
        var resourceUrl =  'api/games/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.timeStart = DateUtils.convertDateTimeFromServer(data.timeStart);
                        data.timeFinish = DateUtils.convertDateTimeFromServer(data.timeFinish);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

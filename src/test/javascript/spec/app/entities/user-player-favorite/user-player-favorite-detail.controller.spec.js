'use strict';

describe('Controller Tests', function() {

    describe('UserPlayerFavorite Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUserPlayerFavorite, MockUser, MockPlayer;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUserPlayerFavorite = jasmine.createSpy('MockUserPlayerFavorite');
            MockUser = jasmine.createSpy('MockUser');
            MockPlayer = jasmine.createSpy('MockPlayer');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UserPlayerFavorite': MockUserPlayerFavorite,
                'User': MockUser,
                'Player': MockPlayer
            };
            createController = function() {
                $injector.get('$controller')("UserPlayerFavoriteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'baloncestoseedApp:userPlayerFavoriteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

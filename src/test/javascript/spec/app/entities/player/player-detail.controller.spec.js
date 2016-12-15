'use strict';

describe('Controller Tests', function() {

    describe('Player Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPlayer, MockCity, MockTeam, MockUserPlayerFavorite;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPlayer = jasmine.createSpy('MockPlayer');
            MockCity = jasmine.createSpy('MockCity');
            MockTeam = jasmine.createSpy('MockTeam');
            MockUserPlayerFavorite = jasmine.createSpy('MockUserPlayerFavorite');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Player': MockPlayer,
                'City': MockCity,
                'Team': MockTeam,
                'UserPlayerFavorite': MockUserPlayerFavorite
            };
            createController = function() {
                $injector.get('$controller')("PlayerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'baloncestoseedApp:playerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

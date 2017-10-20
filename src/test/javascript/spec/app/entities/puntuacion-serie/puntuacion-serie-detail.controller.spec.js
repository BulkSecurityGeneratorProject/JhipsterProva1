'use strict';

describe('Controller Tests', function() {

    describe('PuntuacionSerie Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPuntuacionSerie, MockSerie, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPuntuacionSerie = jasmine.createSpy('MockPuntuacionSerie');
            MockSerie = jasmine.createSpy('MockSerie');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PuntuacionSerie': MockPuntuacionSerie,
                'Serie': MockSerie,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("PuntuacionSerieDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterProva1App:puntuacionSerieUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

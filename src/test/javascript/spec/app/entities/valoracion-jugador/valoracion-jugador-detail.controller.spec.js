'use strict';

describe('Controller Tests', function() {

    describe('ValoracionJugador Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockValoracionJugador, MockJugador, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockValoracionJugador = jasmine.createSpy('MockValoracionJugador');
            MockJugador = jasmine.createSpy('MockJugador');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ValoracionJugador': MockValoracionJugador,
                'Jugador': MockJugador,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("ValoracionJugadorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterProva1App:valoracionJugadorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

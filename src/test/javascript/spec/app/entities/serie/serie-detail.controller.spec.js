'use strict';

describe('Controller Tests', function() {

    describe('Serie Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSerie, MockProductora, MockActor, MockGenero;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSerie = jasmine.createSpy('MockSerie');
            MockProductora = jasmine.createSpy('MockProductora');
            MockActor = jasmine.createSpy('MockActor');
            MockGenero = jasmine.createSpy('MockGenero');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Serie': MockSerie,
                'Productora': MockProductora,
                'Actor': MockActor,
                'Genero': MockGenero
            };
            createController = function() {
                $injector.get('$controller')("SerieDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jhipsterProva1App:serieUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

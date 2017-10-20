(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('puntuacion-serie', {
            parent: 'entity',
            url: '/puntuacion-serie',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterProva1App.puntuacionSerie.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/puntuacion-serie/puntuacion-series.html',
                    controller: 'PuntuacionSerieController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('puntuacionSerie');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('puntuacion-serie-detail', {
            parent: 'puntuacion-serie',
            url: '/puntuacion-serie/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterProva1App.puntuacionSerie.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/puntuacion-serie/puntuacion-serie-detail.html',
                    controller: 'PuntuacionSerieDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('puntuacionSerie');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PuntuacionSerie', function($stateParams, PuntuacionSerie) {
                    return PuntuacionSerie.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'puntuacion-serie',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('puntuacion-serie-detail.edit', {
            parent: 'puntuacion-serie-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/puntuacion-serie/puntuacion-serie-dialog.html',
                    controller: 'PuntuacionSerieDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PuntuacionSerie', function(PuntuacionSerie) {
                            return PuntuacionSerie.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('puntuacion-serie.new', {
            parent: 'puntuacion-serie',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/puntuacion-serie/puntuacion-serie-dialog.html',
                    controller: 'PuntuacionSerieDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                puntuacion: null,
                                momento: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('puntuacion-serie', null, { reload: 'puntuacion-serie' });
                }, function() {
                    $state.go('puntuacion-serie');
                });
            }]
        })
        .state('puntuacion-serie.edit', {
            parent: 'puntuacion-serie',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/puntuacion-serie/puntuacion-serie-dialog.html',
                    controller: 'PuntuacionSerieDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PuntuacionSerie', function(PuntuacionSerie) {
                            return PuntuacionSerie.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('puntuacion-serie', null, { reload: 'puntuacion-serie' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('puntuacion-serie.delete', {
            parent: 'puntuacion-serie',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/puntuacion-serie/puntuacion-serie-delete-dialog.html',
                    controller: 'PuntuacionSerieDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PuntuacionSerie', function(PuntuacionSerie) {
                            return PuntuacionSerie.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('puntuacion-serie', null, { reload: 'puntuacion-serie' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

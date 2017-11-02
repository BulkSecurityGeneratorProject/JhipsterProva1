(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('gatito', {
            parent: 'entity',
            url: '/gatito',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterProva1App.gatito.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gatito/gatitos.html',
                    controller: 'GatitoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('gatito');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('gatito-detail', {
            parent: 'gatito',
            url: '/gatito/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterProva1App.gatito.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/gatito/gatito-detail.html',
                    controller: 'GatitoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('gatito');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Gatito', function($stateParams, Gatito) {
                    return Gatito.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'gatito',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('gatito-detail.edit', {
            parent: 'gatito-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gatito/gatito-dialog.html',
                    controller: 'GatitoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Gatito', function(Gatito) {
                            return Gatito.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gatito.new', {
            parent: 'gatito',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gatito/gatito-dialog.html',
                    controller: 'GatitoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                anoCreacion: null,
                                descripcion: null,
                                foto: null,
                                fotoContentType: null,
                                goodCat: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('gatito', null, { reload: 'gatito' });
                }, function() {
                    $state.go('gatito');
                });
            }]
        })
        .state('gatito.edit', {
            parent: 'gatito',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gatito/gatito-dialog.html',
                    controller: 'GatitoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Gatito', function(Gatito) {
                            return Gatito.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gatito', null, { reload: 'gatito' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gatito.delete', {
            parent: 'gatito',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/gatito/gatito-delete-dialog.html',
                    controller: 'GatitoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Gatito', function(Gatito) {
                            return Gatito.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gatito', null, { reload: 'gatito' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

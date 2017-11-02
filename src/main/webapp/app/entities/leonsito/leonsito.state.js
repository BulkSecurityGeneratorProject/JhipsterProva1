(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('leonsito', {
            parent: 'entity',
            url: '/leonsito',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterProva1App.leonsito.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/leonsito/leonsitos.html',
                    controller: 'LeonsitoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('leonsito');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('leonsito-detail', {
            parent: 'leonsito',
            url: '/leonsito/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterProva1App.leonsito.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/leonsito/leonsito-detail.html',
                    controller: 'LeonsitoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('leonsito');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Leonsito', function($stateParams, Leonsito) {
                    return Leonsito.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'leonsito',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('leonsito-detail.edit', {
            parent: 'leonsito-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/leonsito/leonsito-dialog.html',
                    controller: 'LeonsitoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Leonsito', function(Leonsito) {
                            return Leonsito.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('leonsito.new', {
            parent: 'leonsito',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/leonsito/leonsito-dialog.html',
                    controller: 'LeonsitoDialogController',
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
                    $state.go('leonsito', null, { reload: 'leonsito' });
                }, function() {
                    $state.go('leonsito');
                });
            }]
        })
        .state('leonsito.edit', {
            parent: 'leonsito',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/leonsito/leonsito-dialog.html',
                    controller: 'LeonsitoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Leonsito', function(Leonsito) {
                            return Leonsito.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('leonsito', null, { reload: 'leonsito' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('leonsito.delete', {
            parent: 'leonsito',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/leonsito/leonsito-delete-dialog.html',
                    controller: 'LeonsitoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Leonsito', function(Leonsito) {
                            return Leonsito.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('leonsito', null, { reload: 'leonsito' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

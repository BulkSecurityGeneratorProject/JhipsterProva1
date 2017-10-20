(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('productora', {
            parent: 'entity',
            url: '/productora',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterProva1App.productora.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/productora/productoras.html',
                    controller: 'ProductoraController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('productora');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('productora-detail', {
            parent: 'productora',
            url: '/productora/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterProva1App.productora.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/productora/productora-detail.html',
                    controller: 'ProductoraDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('productora');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Productora', function($stateParams, Productora) {
                    return Productora.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'productora',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('productora-detail.edit', {
            parent: 'productora-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/productora/productora-dialog.html',
                    controller: 'ProductoraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Productora', function(Productora) {
                            return Productora.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('productora.new', {
            parent: 'productora',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/productora/productora-dialog.html',
                    controller: 'ProductoraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                pais: null,
                                foto: null,
                                fotoContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('productora', null, { reload: 'productora' });
                }, function() {
                    $state.go('productora');
                });
            }]
        })
        .state('productora.edit', {
            parent: 'productora',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/productora/productora-dialog.html',
                    controller: 'ProductoraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Productora', function(Productora) {
                            return Productora.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('productora', null, { reload: 'productora' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('productora.delete', {
            parent: 'productora',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/productora/productora-delete-dialog.html',
                    controller: 'ProductoraDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Productora', function(Productora) {
                            return Productora.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('productora', null, { reload: 'productora' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

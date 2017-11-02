(function() {
    'use strict';

    angular
        .module('jhipsterProva1App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('jaguar', {
            parent: 'entity',
            url: '/jaguar',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterProva1App.jaguar.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/jaguar/jaguars.html',
                    controller: 'JaguarController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('jaguar');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('jaguar-detail', {
            parent: 'jaguar',
            url: '/jaguar/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'jhipsterProva1App.jaguar.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/jaguar/jaguar-detail.html',
                    controller: 'JaguarDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('jaguar');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Jaguar', function($stateParams, Jaguar) {
                    return Jaguar.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'jaguar',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('jaguar-detail.edit', {
            parent: 'jaguar-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jaguar/jaguar-dialog.html',
                    controller: 'JaguarDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Jaguar', function(Jaguar) {
                            return Jaguar.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('jaguar.new', {
            parent: 'jaguar',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jaguar/jaguar-dialog.html',
                    controller: 'JaguarDialogController',
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
                    $state.go('jaguar', null, { reload: 'jaguar' });
                }, function() {
                    $state.go('jaguar');
                });
            }]
        })
        .state('jaguar.edit', {
            parent: 'jaguar',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jaguar/jaguar-dialog.html',
                    controller: 'JaguarDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Jaguar', function(Jaguar) {
                            return Jaguar.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('jaguar', null, { reload: 'jaguar' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('jaguar.delete', {
            parent: 'jaguar',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/jaguar/jaguar-delete-dialog.html',
                    controller: 'JaguarDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Jaguar', function(Jaguar) {
                            return Jaguar.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('jaguar', null, { reload: 'jaguar' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

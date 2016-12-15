(function() {
    'use strict';

    angular
        .module('baloncestoseedApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-player-favorite', {
            parent: 'entity',
            url: '/user-player-favorite?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'baloncestoseedApp.userPlayerFavorite.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-player-favorite/user-player-favorites.html',
                    controller: 'UserPlayerFavoriteController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userPlayerFavorite');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('user-player-favorite-detail', {
            parent: 'entity',
            url: '/user-player-favorite/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'baloncestoseedApp.userPlayerFavorite.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-player-favorite/user-player-favorite-detail.html',
                    controller: 'UserPlayerFavoriteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userPlayerFavorite');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UserPlayerFavorite', function($stateParams, UserPlayerFavorite) {
                    return UserPlayerFavorite.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-player-favorite',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-player-favorite-detail.edit', {
            parent: 'user-player-favorite-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-player-favorite/user-player-favorite-dialog.html',
                    controller: 'UserPlayerFavoriteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserPlayerFavorite', function(UserPlayerFavorite) {
                            return UserPlayerFavorite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-player-favorite.new', {
            parent: 'user-player-favorite',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-player-favorite/user-player-favorite-dialog.html',
                    controller: 'UserPlayerFavoriteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                liked: null,
                                timestamp: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-player-favorite', null, { reload: 'user-player-favorite' });
                }, function() {
                    $state.go('user-player-favorite');
                });
            }]
        })
        .state('user-player-favorite.edit', {
            parent: 'user-player-favorite',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-player-favorite/user-player-favorite-dialog.html',
                    controller: 'UserPlayerFavoriteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserPlayerFavorite', function(UserPlayerFavorite) {
                            return UserPlayerFavorite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-player-favorite', null, { reload: 'user-player-favorite' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-player-favorite.delete', {
            parent: 'user-player-favorite',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-player-favorite/user-player-favorite-delete-dialog.html',
                    controller: 'UserPlayerFavoriteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserPlayerFavorite', function(UserPlayerFavorite) {
                            return UserPlayerFavorite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-player-favorite', null, { reload: 'user-player-favorite' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

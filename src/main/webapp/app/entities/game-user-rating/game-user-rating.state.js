(function() {
    'use strict';

    angular
        .module('baloncestoseedApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('game-user-rating', {
            parent: 'entity',
            url: '/game-user-rating?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'baloncestoseedApp.gameUserRating.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/game-user-rating/game-user-ratings.html',
                    controller: 'GameUserRatingController',
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
                    $translatePartialLoader.addPart('gameUserRating');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('game-user-rating-detail', {
            parent: 'entity',
            url: '/game-user-rating/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'baloncestoseedApp.gameUserRating.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/game-user-rating/game-user-rating-detail.html',
                    controller: 'GameUserRatingDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('gameUserRating');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'GameUserRating', function($stateParams, GameUserRating) {
                    return GameUserRating.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'game-user-rating',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('game-user-rating-detail.edit', {
            parent: 'game-user-rating-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/game-user-rating/game-user-rating-dialog.html',
                    controller: 'GameUserRatingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GameUserRating', function(GameUserRating) {
                            return GameUserRating.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('game-user-rating.new', {
            parent: 'game-user-rating',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/game-user-rating/game-user-rating-dialog.html',
                    controller: 'GameUserRatingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                rating: null,
                                timestamp: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('game-user-rating', null, { reload: 'game-user-rating' });
                }, function() {
                    $state.go('game-user-rating');
                });
            }]
        })
        .state('game-user-rating.edit', {
            parent: 'game-user-rating',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/game-user-rating/game-user-rating-dialog.html',
                    controller: 'GameUserRatingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GameUserRating', function(GameUserRating) {
                            return GameUserRating.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('game-user-rating', null, { reload: 'game-user-rating' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('game-user-rating.delete', {
            parent: 'game-user-rating',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/game-user-rating/game-user-rating-delete-dialog.html',
                    controller: 'GameUserRatingDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['GameUserRating', function(GameUserRating) {
                            return GameUserRating.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('game-user-rating', null, { reload: 'game-user-rating' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

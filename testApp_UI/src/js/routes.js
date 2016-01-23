'use strict';

/**
 * Route configuration for the RDash module.
 */
angular.module('RDash').config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        // For unmatched routes
        $urlRouterProvider.otherwise('/');

        // Application routes
        $stateProvider
            .state('index', {
                url: '/',
                templateUrl: 'templates/dashboard.html'
            })
            .state('tables', {
                url: '/tables',
                templateUrl: 'templates/tables.html'
            })
            .state('addInvoice', {
                url: '/addInvoice',
                templateUrl: 'templates/addInvoice.html'
            })
            .state('editInvoice', {
                url: '/editInvoice',
                templateUrl: 'templates/editInvoice.html'
            });
    }
]);
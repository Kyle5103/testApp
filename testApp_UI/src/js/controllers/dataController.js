/**
 * Created by Kyle on 1/22/16.
 */
angular.module('RDash')
    .controller('Hello', ["$scope", '$http', function ($scope, $http) {
        $scope.getInvoices = function () {

            $http.get('http://localhost:8080/invoices').
                success(function (data) {
                    console.log(data);
                    $scope.invoices = data;
                }).error(function (err) {
                    console.log("Could not retrieve invoices");
                });
            ;
        };

        $scope.createInvoice = function (invoice) {

            url = 'http://localhost:8080/invoices';
            $http.post(url, invoice).success(function (inv) {
                console.log("Successfully create invoice with id: " + inv.invoiceId)
            }).error(function (err) {
                console.log("Could not create invoice");
                console.log(err)
            });
        }

        $scope.updateInvoice = function (invoice) {
            url = 'http://localhost:8080/invoices/' + invoice.invoiceNo;
            $http.put(url, invoice).success(function (inv) {
                console.log("Successfully updated invoice with id: " + inv.invoiceId)
            }).error(function (err) {
                console.log("Could not update invoice");
                console.log(err)
            });
        }

        $scope.getInvoices();


    }]);

'use strict';
 
angular.module('Authentication')
 
.factory('AuthenticationService',
    ['$http', '$cookieStore', '$rootScope', '$timeout',
    function ( $http, $cookieStore, $rootScope, $timeout) {
        var service = {};

        service.Login = function (username, password,isadminchkbox, callback) {

        	if(isadminchkbox){
        		 $http.post('/mycafeteria/webapi/admin/authenticate', { username: username, password: password })
                 .success(function (response) {
              	   if(!response.success){
              		   response.message = 'Username or password is incorrect';  
              	   }
                     callback(response);
                 });
        	}else{
        		 $http.post('/mycafeteria/webapi/vendor/authenticate', { username: username, password: password })
                 .success(function (response) {
              	   if(!response.success){
              		   response.message = 'Username or password is incorrect';  
              	   }
                     callback(response);
                 });
        	}
        	  

        };
 
        service.SetCredentials = function (username, password,isAdmin) {
         
            $rootScope.globals = {
                currentUser: {
                    username: username,
                    isadmin: isAdmin
                
                }
            };
 
        };
 
        service.ClearCredentials = function () {
            $rootScope.globals = {};
          
        };
 
        return service;
    }])
 
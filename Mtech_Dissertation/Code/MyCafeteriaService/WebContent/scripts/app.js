'use strict';

// declare modules
angular.module('Authentication', []);
angular.module('Home', []);
angular.module('Vendor', []);
/*if (!String.prototype.startsWith) {
	  String.prototype.startsWith = function(searchString, position) {
	    position = position || 0;
	    return this.indexOf(searchString, position) === position;
	  };
	}*/
if ( typeof String.prototype.startsWith != 'function' ) {
	  String.prototype.startsWith = function( str ) {
	    return str.length > 0 && this.substring( 0, str.length ) === str;
	  }
	};
angular.module('MyCafeteria', [
    'Authentication',
    'Vendor',
    'Home',
    'ngRoute',
    'ngCookies','ui.bootstrap.tpls','ui.bootstrap.modal',
    'ui.grid',
    'ui.grid.edit', 
    'ui.grid.rowEdit', 
    'ui.grid.resizeColumns',
    'ui.grid.pagination','ui.grid.moveColumns',
    'ui.grid.expandable',  'ngAnimate'
])
 
.config(['$routeProvider', function ($routeProvider) {

    $routeProvider
        .when('/login', {
            controller: 'LoginController',
            templateUrl: 'modules/authentication/views/login.html',
            hideMenus: true
        })
 
        .when('/admin', {
            controller: 'HomeController',
            templateUrl: 'modules/home/views/home.html'
        })
        .when('/category', {
            controller: 'HomeController',
            templateUrl: 'modules/home/views/category.html'
        })
         .when('/location', {
            controller: 'HomeController',
            templateUrl: 'modules/home/views/location.html'
        })
        
        .when('/newvendor', {
            controller: 'HomeController',
            templateUrl: 'modules/home/views/newvendor.html'
           
        })
        .when('/editvendor/:param', {
            controller: 'HomeController',
            templateUrl: 'modules/home/views/vendoredit.html'
        })
         .when('/edititem/:param', {
            controller: 'VendorController',
            templateUrl: 'modules/vendor/views/itemedit.html'
        })
    
          .when('/newitem', {
            controller: 'VendorController',
            templateUrl: 'modules/vendor/views/newitem.html'
        })
          .when('/vendor', {
            controller: 'VendorController',
            templateUrl: 'modules/vendor/views/home.html'
        })
        .when('/order', {
            controller: 'VendorController',
            templateUrl: 'modules/vendor/views/order.html'
        })
         .when('/settings', {
            controller: 'VendorController',
            templateUrl: 'modules/vendor/views/settings.html'
        })
        .otherwise({ redirectTo: '/login' });
}])
 
.run(['$rootScope', '$location', '$cookieStore', '$http',
    function ($rootScope, $location, $cookieStore, $http) {
        
        var routesvendor = ['/edititem','/newitem','/vendor','/order','/settings'];
        var routesadmin = ['/admin','/category','/location','/newvendor','/editvendor'];
        // check if current location matches route  
        var routevendorchk = function (route) {
          return _.find(routesvendor,
           function (AuthRoute) {
              return route.startsWith(AuthRoute); 
            });
        };
        var routeadminchk = function (route) {
            return _.find(routesadmin,
             function (AuthRoute) {
                return route.startsWith(AuthRoute);
              });
          };
 
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in
        	var currentpath=$location.path();
            if (currentpath !== '/login' && !$rootScope.globals.currentUser) {
                $location.path('/login');
            }else   if (currentpath !== '/login' && $rootScope.globals.currentUser) {{
            	//user is present chk for roles
            	if(!$rootScope.globals.currentUser.isadmin){
            		//vendor
            		
            		if(routevendorchk(currentpath)){
            			  $('#mydiv').show();
            			 $location.path(currentpath);
            		}else{
            			//beware of admin path 
            			alert('Not authorised to access Admin pages')
            			 $location.path('/login');
            			 $('#logout_btn').css('visibility','hidden');
            		}
            	}else{
            		//admin
            		if(routeadminchk(currentpath)){
            			  $('#mydiv').show();
           			 $location.path(currentpath);
           		}else{
           			//beware of admin path 
           			alert('Not authorised to access Vendor pages')
           			 $location.path('/login');
           		 $('#logout_btn').css('visibility','hidden');
           		}
            	}
            	}
            }
            
            
        });
    }]);
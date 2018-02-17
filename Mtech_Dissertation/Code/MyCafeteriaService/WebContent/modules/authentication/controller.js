'use strict';
 
angular.module('Authentication')

.controller('LoginController',
    ['$scope', '$rootScope', '$location', 'AuthenticationService','$modal','$http',
    function ($scope, $rootScope, $location, AuthenticationService,$modal,$http) {
        // reset login status
        AuthenticationService.ClearCredentials();
        
        $scope.forgotpwd=function(){
      
        	  $scope.forgotpass= $modal.open({
    			  scope: $scope,
    		      templateUrl: 'modules/authentication/views/fgtpwdmodal.html',	      
    		      controller: 'LoginController',
    		      size:'sm'	     
    			  });
        }
        $scope.submitregmailmodal=function(){
        	 $scope.status="Sending Mail...";
        	 if($scope.regmailid==undefined){
        		 $scope.status="";
        		 alert("MailID cannot be empty or Invalid");
        		 return;
        	 }
        	 $http.post('/mycafeteria/webapi/vendor/forgotpwd',{"mailid":$scope.regmailid})
				.success(function(data3) {
					 $scope.status="";
					 alert(data3);
					 $scope.forgotpass.dismiss('cancel');
					
				});
        	
        }
        
        
        $scope.closeregmailmodal=function(){
        	 $scope.forgotpass.dismiss('cancel');
        }
        $scope.login = function () {
            $scope.dataLoading = true;
            $('#mydiv').show();
            AuthenticationService.Login($scope.username, $scope.password, $scope.isadminchkbox,function(response) {
                if(response.success) {
                	var isAdmin=response.success.isadmin;
                	
                    AuthenticationService.SetCredentials($scope.username, $scope.password,isAdmin);
                    if(isAdmin==true){
                    	 $location.path('/admin');
                    }else{
                    	$rootScope.vendorid=response.success.id;
                    	$rootScope.vendorname=response.success.name;
                    	$rootScope.vendorlocation=response.success.location;
                    	 $location.path('/vendor');
                    }
                    $('#logout_btn').css('visibility','visible');
             
                } else {
                    $scope.error = response.message;
                    $scope.dataLoading = false;
                }
                $('#mydiv').hide();
            });
         
        };
        $scope.logout=function(){
        	
        	 $location.path('/login');
        	 $('#logout_btn').css('visibility','hidden');
        }
    }]);
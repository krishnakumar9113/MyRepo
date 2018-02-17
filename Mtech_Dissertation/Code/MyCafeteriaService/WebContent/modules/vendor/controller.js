'use strict';
 
angular.module('Vendor')
 
.controller('VendorController',
    ['$scope','$http','$rootScope','uiGridConstants','$location','$routeParams','$modal','$window',
    function ($scope,$http,$rootScope,uiGridConstants,$location,$routeParams,$modal,$window) {
    	$scope.abs = $window.Math.abs;
    	$scope.editItem=function(id){
    		$('#mydiv').show();
   		 $location.path('/edititem/'+id);
   		$('#mydiv').hide();
   	}
    	$scope.categorydata = [{
  		  id: 1,
  		  name: 'Snacks'
  		
  		}, {
  		  id: 2,
  		  name: 'Breakfast'
  		 
  		}];
    	$scope.cancel=function(){
    		$('#mydiv').show();
      		 $location.path('/vendor');
      		$('#mydiv').hide();
      	}
    //	$scope.vendorid=1;
    	$scope.loadcreateitem=function(){
    		$('#mydiv').show();
			 $http.get('/mycafeteria/webapi/category/').success(function(data){
				$scope.categorydata= data;
				$scope.selectedcat=$scope.categorydata[0];
				
		});
			 $('#mydiv').hide();
	}
    	$scope.changepwd=function(){
    		 if($scope.newpwdfrm==undefined||$scope.currentpwd==undefined||$scope.newpwd==undefined||$scope.renewpwd==undefined){
    			 alert("Password details cannot be Invalid or empty");
    			   return;
    		}else if($scope.newpwd!=$scope.renewpwd){
    			alert('New password an ReType new password are not same');
    			return;
    		}else{
    			$http.post('/mycafeteria/webapi/vendor/changepwd',{"vendorid":$scope.vendorid,"currentpwd":$scope.currentpwd,"newpwd":$scope.newpwd})
    			.success(function(data3) {
    				alert(data3);
    				 $location.path('/vendor');
    			});
    		}
    		
    	}
    	$scope.createItem=function(){
    		if($scope.itemnew.$invalid||$scope.itemcreate.name==undefined||$scope.itemcreate.price==undefined||$scope.itemcreate.count==undefined){
  			   alert("Item details cannot be Invalid or empty");
  			   return;
  		   }else{
  			 $scope.itemcreate.categoryID=$scope.selectedcat.id;
     		$scope.itemcreate.vendorID=$scope.vendorid;
     		$scope.itemcreate.isActive=true;
 				$http.post('/mycafeteria/webapi/item',$scope.itemcreate)
 				.success(function(data3) {
 					alert('Saved successfully');
 					 $location.path('/vendor');
 				}); 
  		   }
    		
	    	
	}
    	$scope.loadforitemedit=function(){
    		$('#mydiv').show();
    		var id = $routeParams.param;
    		
    		$http.get('/mycafeteria/webapi/item/'+id)
  			.success(function(data3) {
  				$scope.itemedit=data3;
  				 $http.get('/mycafeteria/webapi/category').success(function(data){
  					$scope.categorydata= data;
  					for(var i=0;i<$scope.categorydata.length;i++){
  						if($scope.itemedit.categoryID==$scope.categorydata[i].id){
  							$scope.selectedcat=$scope.categorydata[i];
  							break;
  						}
  					}
  					
  				 });
  				// alert($scope.selectededitVendor.locationID);
  				$('#mydiv').hide();
  				});
    	}
    		$scope.saveItem=function(){
    	    	//	alert(	$scope.selectedloc);
    	        //	alert($scope.selectededitVendor);
    			 if($scope.itemeditfrm.$invalid||$scope.itemedit.name==undefined||$scope.itemedit.price==undefined|| $scope.itemedit.count==undefined){
    				   alert("Item details cannot be Invalid / Empty");
    			   }else{
    				   $('#mydiv').show();
    					$http.put('/mycafeteria/webapi/item',{"id":$scope.itemedit.id,"name": $scope.itemedit.name,"price": $scope.itemedit.price,"count": $scope.itemedit.count,"categoryID":$scope.selectedcat.id})
        				.success(function(data3) {
        					 $('#mydiv').hide();
        					alert(data3);
        					 $location.path('/vendor');
        					
        				});
        	    	
    			   }
    	    	
    	      	}
    		
    		
    	
    	$scope.deleteitem= function(id){
  		  $http.delete('/mycafeteria/webapi/item/'+id)
  			.success(function(data3) {
  				//alert(data3);
  				
  				alert(data3);
  				$scope.refreshList();
  			});
  		
  	  }
    	$scope.markallorders= function(){
    		 $('#mydiv').show();
				$http.put('/mycafeteria/webapi/order/vendororderexpire/',$rootScope.vendorid)
				.success(function(data3) {
					 $('#mydiv').hide();
					alert(data3);
					 $http.get('/mycafeteria/webapi/order/vendorlist/'+$rootScope.vendorid)
	    				.success(function(data3) {
	    					$('#mydiv').show();
	    					 $scope.ordergridOptionsComplex.data=data3;
	    					 $('#mydiv').hide();
	    				});
					
				});
    		
    	}
    	$scope.markorderasExpired= function(id){
   		 $('#mydiv').show();
				$http.put('/mycafeteria/webapi/order/orderexpire/',id)
				.success(function(data3) {
					 $('#mydiv').hide();
					alert(data3);
					 $http.get('/mycafeteria/webapi/order/vendorlist/'+$rootScope.vendorid)
	    				.success(function(data3) {
	    					$('#mydiv').show();
	    					 $scope.ordergridOptionsComplex.data=data3;
	    					 $('#mydiv').hide();
	    				});
					
				});
   		
   	}
    		$scope.loadItemDetails=function(){
    			$('#mydiv').show();
    	  $scope.gridOptionsComplex = {
    			  enableFiltering : true,
					enableGridMenu : true,
					paginationPageSizes : [ 8, 16, 25 ],
					paginationPageSize : 8,
    		        columnDefs: [{name:'id',field:'id',displayName:'Item ID' ,width:'10%' ,enableHiding: false,enableFiltering: false},
    		                     {name : 'name',field : 'name',displayName : 'Item Name', enableCellEdit: false,enableHiding : false,width:'20%',
    		        	cellTemplate : '<div class="editNameClass" ng-click="grid.appScope.editItem(row.entity.id)">{{row.entity.name}}</div>'
    		        },
    		            		       //   { name: 'name', aggregationType: uiGridConstants.aggregationTypes.count, width: 150 },
    		            		         
    		            		          { name: 'price',displayName:'Price', enableCellEdit: false, width: '20%' },
    		            		          { name: 'categoryName',displayName:'Category',  enableCellEdit: false, enableFiltering: true, width: '15%'},
    		            		          { name: 'count',displayName:'Count',   enableCellEdit: false, enableFiltering: true, width: '15%'}, 
    		            		          {name : 'delete',displayName : 'Delete',enableCellEdit: false,enableHiding : false,enableFiltering : false,enableSorting:false,width:'10%',
    		  								cellTemplate : '<div ng-click="grid.appScope.deleteitem(row.entity.id)"><img style="cursor:pointer;margin-left: 20px;" src="img/Delete1.png" alt="Delete" title="Delete Project"/></div>'
    		  							},{name : 'isActive',field : 'isActive',displayName : 'Active',enableFiltering: false,enableSorting:false,width:'10%',
    		                                cellTemplate : '<div ng-click="grid.appScope.itemisact(row.entity.id,row.entity.isActive)">a</div>',
    		                                cellClass : function(grid, row,col, rowRenderIndex,colRenderIndex) {
    		                                      
    		                                       if (grid.getCellValue(row,col) == true)
    		                                            
    		                                             return 'boolStatusMsgTrue';
    		                                       else
    		                                             return 'boolStatusMsgFalse';
    		                                }
    		                         }
    		            		        	    ]
    		        	
    		      };
    	  
    	  $http.get('/mycafeteria/webapi/item/vendor/'+$rootScope.vendorid)
			.success(function(data3) {
			
				 $scope.gridOptionsComplex.data=data3;
				 $('#mydiv').hide();
			});
    		}
    		$scope.itemisact=function(id,name){
    			$('#mydiv').show();
        		$http.put('/mycafeteria/webapi/item/isactive',{"itemid":id,"isactive": !name})
    			.success(function(data2) {
    				 $('#mydiv').hide();
    				 $http.get('/mycafeteria/webapi/item/vendor/'+$rootScope.vendorid)
    					.success(function(data3) {
    						
    						 $scope.gridOptionsComplex.data=data3;
    						
    					});
    				alert(data2);
    			});
        		
        	}
    		$scope.refreshList = function () {	
    			$('#mydiv').show();
    			$http.get('/mycafeteria/webapi/item/vendor/'+$rootScope.vendorid)
    			.success(function(data3) {
    				
    				 $scope.gridOptionsComplex.data=data3;
    				 $('#mydiv').hide();
    			});
   			 
   		}
    		
    		$scope.settinginit=function(){
    			 $('#mydiv').hide();
    		}
    		$scope.loadorderdetails=function(){
    			$('#mydiv').show();
    	    	  $scope.ordergridOptionsComplex = {
    	    			  enableFiltering : true,
    	    			  enableRowSelection: true,
    						enableGridMenu : true,
    						paginationPageSizes : [ 8, 16, 25 ],
    						paginationPageSize : 8,
    	    		        columnDefs: [{name:'orderId',field:'orderId',displayName:'Order ID' ,width:'10%' ,enableHiding: false,enableFiltering: false},
    	    		                     {name : 'userName',field : 'userName',displayName : 'User Name', enableCellEdit: false,enableHiding : false,width:'25%' },
    	    		            		       //   { name: 'name', aggregationType: uiGridConstants.aggregationTypes.count, width: 150 },
    	    		            		         
    	    		            		          { name: 'amount',displayName:'Total Amount', enableCellEdit: false, width: '10%' },
    	    		            		          { name: 'orderStatus',displayName:'Order Status',  enableCellEdit: false, enableFiltering: true, width: '10%'},
    	    		            		          {name : 'expire',displayName : 'Mark order status',enableCellEdit: false,enableHiding : false,enableFiltering : false,enableSorting:false,width:'15%',
      	    		  								cellTemplate : '<a><div  style="margin-left: 10px;margin-top: 5px;" ng-click="grid.appScope.markorderasExpired(row.entity.orderId)"> Mark as Expired </div></a>'
      	    		  							},
    	    		            		          { name: 'timeStamp',displayName:'TimeStamp',   enableCellEdit: false, enableFiltering: true, width: '15%'}, 
    	    		            		          {name : 'details',displayName : 'Details',enableCellEdit: false,enableHiding : false,enableFiltering : false,enableSorting:false,width:'15%',
    	    		  								cellTemplate : '<a><div  style="margin-left: 10px;margin-top: 5px;" ng-click="grid.appScope.itemdetailmodalInstance(row.entity.orderId)"> View Item Details</div></a>'
    	    		  							}
    	    		            		        	    ]
    	    		        	
    	    		      };
    	    	  $http.get('/mycafeteria/webapi/order/vendorlist/'+$rootScope.vendorid)
    				.success(function(data3) {
    					
    					 $scope.ordergridOptionsComplex.data=data3;
    					 $('#mydiv').hide();
    				});
    	    		}
    		$scope.refreshorderList = function () {	
    			$('#mydiv').show();
    			  $http.get('/mycafeteria/webapi/order/vendorlist/'+$rootScope.vendorid)
  				.success(function(data3) {
  					
  					 $scope.ordergridOptionsComplex.data=data3;
  					 $('#mydiv').hide();
  				});
   			 
   		}
    	
    		$scope.itemdetailmodalInstance= function(id){
        		//  alert('hit');
    			 $http.get('/mycafeteria/webapi/order/itemlist/'+id)
    				.success(function(data3) {
    					
    					 $scope.itemdetailsgrid=data3;
    					
    				});
    			
        		  $scope.itemdetmodal= $modal.open({
        			  scope: $scope,
        		      templateUrl: 'modules/vendor/views/itemdetmodal.html',	      
        		      controller: 'VendorController',
        		      size:'sm'	     
        			  });
        	  }
    		$scope.closeitemgridmodal=function(){
    			 $scope.itemdetmodal.dismiss('cancel');
    		}
    }]);
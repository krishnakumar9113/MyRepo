'use strict';
 
angular.module('Home',['ui.grid.edit','ui.grid.rowEdit','ui.grid.cellNav', 'ui.grid.pagination','ui.grid.moveColumns','ui.grid.resizeColumns'])
 
.controller('HomeController',
    ['$scope','$http','$rootScope','uiGridConstants','$location','$routeParams','$modal',
    function ($scope,$http,$rootScope,uiGridConstants,$location,$routeParams,$modal) {
      
    	$scope.editVendor=function(entity){
    		
    		 $location.path('/editvendor/'+entity.id);
    	}
    	
    	$scope.saveVendor=function(){
   if($scope.vendoredit.$invalid||$scope.selectededitVendor.name==undefined||$scope.selectededitVendor.mail==undefined){
	   alert("Vendor details cannot be Invalid / Empty");
   }else{
	   $('#mydiv').show();
	   $http.put('/mycafeteria/webapi/vendor',{"id":$scope.selectededitVendor.id,"name": $scope.selectededitVendor.name,"locationID":$scope.selectedloc.id})
		.success(function(data3) {
			$('#mydiv').hide();
			alert(data3);
			
			 $location.path('/admin');
		},function(err){
			alert(err);
		});
   }
    		
    	
      	}
    	
    	$scope.cancel=function(){
    		
   		 $location.path('/admin');
   	}
    	/*$scope.locationdata = [{
    		  id: 1,
    		  location: 'A'
    		
    		}];*/
    	$scope.loadforvendoredit=function(){
    		$('#mydiv').show();
    		var id = $routeParams.param;	
    		
    		 $http.get('/mycafeteria/webapi/vendor/'+id)
  			.success(function(data3) {
  				$scope.selectededitVendor=data3;
  				 $http.get('/mycafeteria/webapi/location/').success(function(data){
  					$scope.locationdata= data;
  					for(var i=0;i<$scope.locationdata.length;i++){
  						if($scope.selectededitVendor.locationID==$scope.locationdata[i].id){
  							$scope.selectedloc=$scope.locationdata[i];
  							break;
  						}
  					}
  					
  				
  				 });
  				$('#mydiv').hide();
  			});
    		
    	}
    	/*  $scope.deletevendor= function(id){
    		  $http.post('/mycafeteria/webapi/vendor/'+id)
    			.success(function(data3) {
    				//alert(data3);
    				alert('Deleted successfully');
    			});
    	  }
    	*/
    	$scope.loadcreatevendor=function(){
    		$('#mydiv').show();
				 $http.get('/mycafeteria/webapi/location/').success(function(data){
					$scope.locationdata= data;
					$scope.selectedloc=$scope.locationdata[0];
					$('#mydiv').hide();
			});
    	}
    	$scope.createVendor=function(){
    		if($scope.vendorcreateform.$invalid||$scope.selectedcreateVendor==undefined||$scope.selectedcreateVendor.name==undefined||$scope.selectedcreateVendor.mail==undefined){
 			   alert("Vendor details cannot be Invalid or empty");
 			   return;
 		   }
    		else{
    			$('#mydiv').show();
    			$http.post('/mycafeteria/webapi/vendor',{"id":1,"name": $scope.selectedcreateVendor.name,"password":"vendor","mail": $scope.selectedcreateVendor.mail,"locationID":$scope.selectedloc.id,"isActive":true})
				.success(function(data3) {
					
					$('#mydiv').hide();
					alert(data3);
					 $location.path('/admin');
				});
    		}
  				
		    	
    	}
    	
    	  //location
    /*	  $scope.editlocationmodalInstance= function(entity){
    		  $scope.selectededitloc=entity;
    		//  $scope.selectedid=entity.id;
    		//  $scope.selectedlocname=entity.name;
    			// alert($scope.selectedlocentity.name);
    		  $scope.modal= $modal.open({
    			  scope: $scope,
    		      templateUrl: 'modules/home/views/editlocmodal.html',	      
    		      controller: 'HomeController',
    		      size:'sm'	     
    			  });
    	  }  */
    	  
    	
    	  
    	  $scope.savelocation= function(entity){
    		  //post the location
    			//alert("htm"+entity);
    	//	alert($scope.selectededitloc.location+$scope.selectededitloc.id);
    		$http.put('/mycafeteria/webapi/location',entity)
			.success(function(data3) {
				alert(data3);
			});
	
        	  $scope.modal.dismiss('cancel');
        	  }
    	/*  $scope.closeeditlocationmodal= function(){
    		  $scope.modal.dismiss('cancel');
    	  }*/
    	  $scope.newlocationmodalInstance= function(){
      		//  alert('hit');
      		//  $scope.showModal = true;
      		  $scope.modal= $modal.open({
      			  scope: $scope,
      		      templateUrl: 'modules/home/views/newlocationmodal.html',	      
      		      controller: 'HomeController',
      		      size:'sm'	     
      			  });
      	  }
    	  $scope.submitnewlocationmodal= function(){
    		  //post the location
    	//	alert($scope.newlocationname);
    		  if($scope.newlocationname==undefined||$scope.newlocationcode==undefined){
    			  alert("Location details cannot be empty");
    		  }else{
    				$http.post('/mycafeteria/webapi/location',{"id":1,"location":$scope.newlocationname,"code":$scope.newlocationcode})
    	  			.success(function(data3) {
    	  				 $scope.modal.dismiss('cancel');
    	  				 
    	  			  $http.get('/mycafeteria/webapi/location')
  		 			.success(function(dataload) {
  		 				  $('#mydiv').show();
  		 				 $scope.locationgridOptionsComplex.data=dataload;
  		 				  $('#mydiv').hide();
  		 				alert(data3);
  		 			});
    	  				
    	  			});
    	        	 
    	        	
    		  }
    	
        	  }
    	  
    	  //Category

    	  
    /*	  $scope.editcategory= function(entity){
    		  $scope.selectededitcat=entity;
    		//  $scope.selectedid=entity.id;
    		//  $scope.selectedlocname=entity.name;
    			// alert($scope.selectedlocentity.name);
    		  $scope.modal= $modal.open({
    			  scope: $scope,
    		      templateUrl: 'modules/home/views/editcatmodal.html',	      
    		      controller: 'HomeController',
    		      size:'sm'	     
    			  });
    	  }  */
    	  $scope.savecategory= function(entity){
    		  $('#mydiv').show();
    		  //post the location
    			//alert("htm"+entity);
    	//	alert($scope.selectededitcat.categoryName+$scope.selectededitcat.id);
    		$http.put('/mycafeteria/webapi/category',entity)
			.success(function(data3) {
				$('#mydiv').hide();
				alert(data3);
			});
	
        	 // $scope.modal.dismiss('cancel');
        	  }
    	/*  $scope.closeeditlocationmodal= function(){
    		  $scope.modal.dismiss('cancel');
    	  }*/
    	  $scope.newcategorymodalInstance= function(){
      		//  alert('hit');
      		//  $scope.showModal = true;
      		  $scope.modal= $modal.open({
      			  scope: $scope,
      		      templateUrl: 'modules/home/views/newcategorymodal.html',	      
      		      controller: 'HomeController',
      		      size:'sm'	     
      			  });
      	  }
    	  $scope.submitnewcategorymodal= function(){
    		  //post the location
    	//	alert($scope.newcategoryname);
    		  if($scope.newcategoryname==undefined){
    			  alert("Category Name cannot be empty");
    		  }else{
    			  $http.post('/mycafeteria/webapi/category',{"id":1,"categoryName":$scope.newcategoryname})
    	  			.success(function(datacr) {
    	  				//alert(data3);
    	  				 $scope.modal.dismiss('cancel');
    	  				 $http.get('/mycafeteria/webapi/category')
     		  			.success(function(data3) {
     		  				$('#mydiv').show();
     		  				 $scope.categorygridOptionsComplex.data=data3;
     		  				$('#mydiv').hide();
     		  				alert(datacr);
     		  				 
     		  			});
    	  			});
    	        	 
    	        	 
    		  }
    		
        	  }
    	  
    	  
    	  
    	  
    	  $scope.closemodal= function(){
    		  $scope.modal.dismiss('cancel');
    	  }
    		/* */
    	  $scope.deletevendor= function(id){
    			
    		  $http.delete('/mycafeteria/webapi/vendor/'+id)
    			.success(function(data3) {
    				alert(data3);
    				
    				//alert('Deleted Successfully');
    				 $http.get('/mycafeteria/webapi/vendor')
     	  			.success(function(data3) {
     	  				$('#mydiv').show();
     	  				 $scope.gridOptionsComplex.data=data3;
     	  				$('#mydiv').hide();
     	  			});
    			});
    	  }
    	  $scope.deletelocation= function(id){
    		 
    		  $http.delete('/mycafeteria/webapi/location/'+id)
    			.success(function(data3) {
    				//alert(data3);
    				alert(data3);
    				 $http.get('/mycafeteria/webapi/location')
    		 			.success(function(data3) {
    		 				 $('#mydiv').show();
    		 				 $scope.locationgridOptionsComplex.data=data3;
    		 				$('#mydiv').hide();
    		 			});
    			});
    	  }
    	  $scope.deletecategory= function(id){
    		 
    		  $http.delete('/mycafeteria/webapi/category/'+id)
    			.success(function(data3) {
    				alert(data3);
    			//	alert('Deleted Successfully');
    				 $http.get('/mycafeteria/webapi/category')
    		  			.success(function(data3) {
    		  				 $('#mydiv').show();
    		  				 $scope.categorygridOptionsComplex.data=data3;
    		  				$('#mydiv').hide();
    		  			});
    			});
    	  }
    	  
    	  
    	$scope.loadVendorDetails=function(){
    		$('#mydiv').show();
    		  $scope.gridOptionsComplex = {
        			  enableFiltering : true,
    					enableGridMenu : true,
    					paginationPageSizes : [ 8, 16, 25 ],
    					paginationPageSize : 8,
        		        columnDefs: [
        		                 	{name:'id',field:'id',displayName:'Vendor ID' ,width:'10%' ,enableCellEdit: false,enableHiding: false,enableFiltering: false},
    {name : 'name',field : 'name',displayName : 'Vendor Name', enableCellEdit: false,enableHiding : false,width:'30%',
    	cellTemplate : '<div class="editNameClass" ng-click="grid.appScope.editVendor(row.entity)">{{row.entity.name}}</div>'
    },
        		       //   { name: 'name', aggregationType: uiGridConstants.aggregationTypes.count, width: 150 },
        		         
        		          { name: 'mail', displayName : 'Mail ID', enableCellEdit: false, aggregationType: uiGridConstants.aggregationTypes.avg, width: '25%' },
        		          { name: 'locationName', displayName : 'Location Name', enableCellEdit: false, enableFiltering: true, width: '20%'},
        		          {name : 'delete',displayName : 'Delete',enableCellEdit: false,enableHiding : false,enableFiltering : false,enableSorting:false,width:'7%',
								cellTemplate : '<div ng-click="grid.appScope.deletevendor(row.entity.id)"><img style="cursor:pointer;margin-left: 20px;" src="img/Delete1.png" alt="Delete" title="Delete Vendor"/></div>'
							},{name : 'isActive',field : 'isActive',displayName : 'Active',enableFiltering: false,enableSorting:false,width:'8%',
                                cellTemplate : '<div ng-click="grid.appScope.vendorisact(row.entity.id,row.entity.isActive)">a</div>',
                                cellClass : function(grid, row,col, rowRenderIndex,colRenderIndex) {
                                      
                                       if (grid.getCellValue(row,col) == true)
                                            
                                             return 'boolStatusMsgTrue';
                                       else
                                             return 'boolStatusMsgFalse';
                                }
                         }
							/*{name : 'edit',displayName : 'Edit',enableHiding : false,enableFiltering : false,enableCellEdit: false,width:'10%',
 								cellTemplate : '<a class="editNameClass" ng-click="grid.appScope.editVendor(row.entity)">Edit</a></div>'
								} */
        		        	    ]
    		  /*,
        		        	    
        		        data: [
        		          {"id":"1","name": "Hotel Saravana Bhavan","code": "HSB","Location": "SRISEZ"},
        		          {"id":"2","name": "FOOD EXO","code": "FEX","Location": "TIDEL"},
        		          {"id":"3","name": "SN SNACKS","code": "SNN","Location": "DLF"},
        		          {"id":"4","name": "JUGO Juices","code": "JUGO","Location": "SRISEZ"},
        		          {"id":"5","name": "SK Snacks","code": "SKS","Location": "ASV"},
        		         
        		        ]*/
        		      }
    			 $http.get('/mycafeteria/webapi/vendor')
    	  			.success(function(data3) {
    	  				
    	  				 $scope.gridOptionsComplex.data=data3;
    	  				$('#mydiv').hide();
    	  			});
    	     		
    		  //end of gridoptioncomplex
    		 
        	
    	}
    	
    	$scope.vendorisact=function(id,name){
    		
    		$http.put('/mycafeteria/webapi/vendor/isactive',{"vendorid":id,"isactive": !name})
			.success(function(data2) {
				
				$http.get('/mycafeteria/webapi/vendor')
	  			.success(function(data3) {
	  				$('#mydiv').show();
	  				 $scope.gridOptionsComplex.data=data3;
	  				$('#mydiv').hide();
	  			});
				alert(data2);
			});
    		
    	}
    	$scope.loadcategorydetails=function(){
    		$('#mydiv').show();
    		 $scope.categorygridOptionsComplex = {
       			  enableFiltering : true,
   					enableGridMenu : true,
   					paginationPageSizes : [ 8, 16, 25 ],
   					paginationPageSize : 8,
       		        columnDefs: [
       		                 	{name:'id',field:'id',displayName:'Category ID' ,width:'25%' ,enableHiding: false,enableCellEdit: false,enableFiltering: false},
   {name : 'categoryName',field : 'categoryName',displayName : 'Category Name', enableCellEdit: true,enableHiding : false,width:'50%'  	
   } ,{name : 'delete',displayName : 'Delete',enableHiding : false,enableFiltering : false,enableCellEdit: false,enableSorting:false,width:'10%',
		cellTemplate : '<div ng-click="grid.appScope.deletecategory(row.entity.id)"><img style="cursor:pointer;margin-left: 20px;" src="img/Delete1.png" alt="Delete" title="Delete Project"/></div>'
	},{name : 'Save',displayName : 'Edit',enableHiding : false,enableFiltering : false,enableCellEdit: false,width:'15%',
			cellTemplate : '<a class="editNameClass" ng-click="grid.appScope.savecategory(row.entity)">save</a></div>'
		} ] /*,
       		        data: [
       		          {"id":"1","name": "Snacks"},
       		          {"id":"2","name": "Breakfast"},
       		          {"id":"3","name": "Others"}
       		         
       		        ]*/
    	}
    		 $http.get('/mycafeteria/webapi/category')
  			.success(function(data3) {
  				
  				 $scope.categorygridOptionsComplex.data=data3;
  				$('#mydiv').hide();
  			});
     		
    		 
    	
    	}
    	
    	
       	
    	$scope.loadlocationdetails=function(){
    		$('#mydiv').show();
    		 $scope.locationgridOptionsComplex = {
       			  enableFiltering : true,
   					enableGridMenu : true,
   					paginationPageSizes : [ 8, 16, 25 ],
   					paginationPageSize : 8,
       		        columnDefs: [
       		                 	{name:'id',field:'id',displayName:'Location ID' ,width:'25%' ,enableHiding: false,enableCellEdit: false,enableFiltering: false},
   {name : 'location',field : 'location',displayName : 'Location Name', enableCellEdit: true,enableHiding : false,width:'30%'  	
   } ,{name : 'code',field : 'code',displayName : 'Code', enableCellEdit: false,enableHiding : false,width:'20%'  	
   },
       		                 {name : 'delete',displayName : 'Delete',enableHiding : false,enableCellEdit: false,enableFiltering : false,enableSorting:false,width:'10%',
 								cellTemplate : '<div ng-click="grid.appScope.deletelocation(row.entity.id)"><img style="cursor:pointer;margin-left: 20px;" src="img/Delete1.png" alt="Delete" title="Delete Project"/></div>'
 							},
 							{name : 'save',displayName : 'Save',enableHiding : false,enableFiltering : false,enableCellEdit: false,width:'15%',
 								cellTemplate : '<a class="editNameClass" ng-click="grid.appScope.savelocation(row.entity)">Save</a></div>'
 													} ]
    		
    	}
    		 $http.get('/mycafeteria/webapi/location')
 			.success(function(data3) {
 				
 				 $scope.locationgridOptionsComplex.data=data3;
 				$('#mydiv').hide();
 			});
    		
    		 
    	}
    }]);
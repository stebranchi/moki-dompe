<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<link rel="stylesheet" href="css/scrolling-tabs.css">
<br><br>
<div ng-controller="getListController as demo" class="container">
		  <div scrolling-tabs-wrapper watch-tabs="tabs">
            <!-- Standard Bootstrap ul.nav-tabs -->
            <ul class="nav nav-tabs" role="tablist">
            	<li ng-class="{ 'active': lista.active, 'disabled': lista.disabled }"><a role="tab" data-toggle="tab" ng-click="go(-1)">Lista</a></li>
            	<li ng-repeat="tab in tabs" ng-class="tab.active"><a role="tab" data-toggle="tab" ng-click="go($index)"><span ng-bind="tab.title"></span>&nbsp; <button ng-click="close($index)" class="close close-sm"><span class="glyphicon glyphicon-remove-circle black"></span></button></a></li>
            </ul>
          </div>
    
          <!-- Tab panes -->
          
	 <!-- Tab of the table -->
	 <div class="tab-content clearfix">	 
		<div class="tab-pane" ng-class="{ 'active': lista.active }">
		
			<table ng-table="demo.tableParams" class="table table-condensed table-bordered table-striped" show-filter="true">
			    <tr ng-repeat="element in $data">
			        <td title="'Name'" filter="{ name: 'text'}" sortable="'name'">
			            <span ng-bind="element.name"></span></td>
			        <td title="'Id'" filter="{ id: 'text'}" sortable="'id'">
			            <span ng-bind="element.id"></span></td>
			        <td>
			        	<button ng-click="new_tab(element)" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-pencil"></span></button>
			        </td>
			    </tr>
			</table>
			
		</div>
		
		<!-- Repeat for the other panes -->
		<div ng-repeat="tab in tabs" class="tab-pane" ng-class="tab.active">
			<br>
			<div class="row form-group">
				<div class="col-xs-12">
			        <div class="input-group input-group">
			                <span class="input-group-addon">Class URI:</span>
			                <input type="text" class="form-control" placeholder="Class URI... " ng-model="tav.model_uri">
			        </div>
				</div>
			</div>
			<div class="clearfix"></div>
			<br/>
			
			
			<h5 ng-show="tab.object_prop.length">OBJECT PROPERTIES</h5><br ng-show="tab.object_prop.length">
			<div class="row form-group" ng-repeat="elem in tab.object_prop track by $index">
				<div class="col-xs-12">
			        <div class="input-group input-group">
			                <span class="input-group-addon" ng-bind="elem.label + ':'"></span>
			                <input type="text" class="form-control" placeholder="{{elem.label}}... " ng-model="elem.model">
			        </div>
				</div>
			</div>
			<div class="clearfix"></div>
			<hr ng-show="tab.object_prop.length">
			<h5 ng-show="tab.datatype_prop.length">DATATYPE PROPERTIES</h5><br ng-show="tab.datatype_prop.length">
			<div class="row form-group" ng-repeat="elem in tab.datatype_prop track by $index">
				<div class="col-xs-12">
			        <div class="input-group input-group">
			                <span class="input-group-addon" ng-bind="elem.label + ':'"></span>
			                <input type="text" class="form-control" placeholder="{{elem.label}}... " ng-model="elem.model">
			        </div>
				</div>
			</div>
			<div class="clearfix"></div>
			<hr ng-show="tab.datatype_prop.length">
			<h5 ng-show="tab.annotation_prop.length">ANNOTATIONS</h5><br ng-show="tab.annotation_prop.length">
			<div class="row form-group" ng-repeat="elem in tab.annotation_prop track by $index">
				<div class="col-xs-12">
			        <div class="input-group input-group">
			                <span class="input-group-addon" ng-bind="elem.label + ':'"></span>
			                <input type="text" class="form-control" placeholder="Insert annotation... " ng-model="elem.model">
			        </div>
				</div>
			</div>
			<div class="clearfix"></div>
			<hr ng-show="tab.annotation_prop.length">
		
			<div class="container">
			 	<div class="col-md-2 col-md-offset-4 col-sm-3 col-sm-offset-3 col-xs-offset-2 col-xs-4">
			 		<div class="col-md-10">
						<button type="button" class="btn btn-block btn-info btn-lg" ng-click="edit($index)">Edit</button>
					</div>
				</div>
				<div class="col-md-2 col-sm-3 col-xs-4">
					<div class="col-md-10">
						<button type="button" class="btn btn-block btn-cancel btn-lg" ng-click="close($index)">Cancel</button>
					</div>
				</div>
			</div>
	 	</div>
	 	
	</div> 
	
</div>



<script src="js/listmanager/individualList.js"></script>


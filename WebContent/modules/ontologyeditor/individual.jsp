<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<div class="top-title" ng-controller="individualController">
    <div class="container">
	    <br/>
	    <h2>Individual</h2>
		<br/>
		
		<div class="row form-group">
			<div class="col-xs-12">
		        <div class="input-group input-group">
		                <span class="input-group-addon">Class URI:</span>
		                <input type="text" class="form-control" placeholder="Class URI... " ng-model="model_uri">
		        </div>
			</div>
		</div>
		<div class="clearfix"></div>
		<br/>
		
		
		
		<div class="row form-group" ng-if="show_uri">
			<div class="col-xs-12">
		        <div class="input-group input-group">
		                <span class="input-group-addon" ng-bind="'Class Type:'"></span>
		                <input type="text" class="form-control" placeholder="Class Type... " ng-change="change()" bs3-typeahead ng-model="$parent.class_type" bs3-source="classes_list" bs3-displayText="displayText" bs3-afterSelect="afterSelect">
		        </div>
			</div>
		</div>
		<div class="clearfix"></div>
		<br/>
		<hr>
		
		<div ng-if="show">
			<h5 ng-show="$parent.object_prop.length">OBJECT PROPERTIES</h5><br ng-show="$parent.object_prop.length">
			<div class="row form-group" ng-repeat="elem in $parent.object_prop track by $index">
				<div class="col-xs-12">
			        <div class="input-group input-group">
			                <span class="input-group-addon" ng-bind="elem.label + ':'"></span>
			                <input type="text" class="form-control" placeholder="{{elem.label}}... " ng-model="elem.model">
			        </div>
				</div>
			</div>
			<div class="clearfix"></div>
			<hr ng-show="$parent.object_prop.length">
			<h5 ng-show="$parent.datatype_prop.length">DATATYPE PROPERTIES</h5><br ng-show="$parent.datatype_prop.length">
			<div class="row form-group" ng-repeat="elem in $parent.datatype_prop track by $index">
				<div class="col-xs-12">
			        <div class="input-group input-group">
			                <span class="input-group-addon" ng-bind="elem.label + ':'"></span>
			                <input type="text" class="form-control" placeholder="{{elem.label}}... " ng-model="elem.model">
			        </div>
				</div>
			</div>
			<div class="clearfix"></div>
			<hr ng-show="$parent.datatype_prop.length">
			<h5 ng-show="$parent.annotation_prop.length">ANNOTATIONS</h5><br ng-show="$parent.annotation_prop.length">
			<div class="row form-group" ng-repeat="elem in $parent.annotation_prop track by $index">
				<div class="col-xs-12">
			        <div class="input-group input-group">
			                <span class="input-group-addon" ng-bind="elem.label + ':'"></span>
			                <input type="text" class="form-control" placeholder="Insert annotation... " ng-model="elem.model">
			        </div>
				</div>
			</div>
			<div class="clearfix"></div>
			<hr ng-show="$parent.annotation_prop.length">
			<moki-buttons></moki-buttons> 
		</div>
		
	</div>
</div>
<script src="js/ontologyeditor/individual.js"></script>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<div class="top-title" ng-controller="obj_prop_Controller">
    <div class="container">
	    <br/>
	    <h2>Object Property</h2>
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

		<moki-domain model="model_domain" class-list="classes_list"></moki-domain>
		<moki-range model="model_range" class-list="classes_list"></moki-range>
		<moki-mapping model="model_mapping" class-list="object_list"></moki-mapping>
		<moki-labels model="model_labels" language-list="language_list"></moki-labels>
		
		<moki-buttons></moki-buttons> 
		
		
	</div>
</div>
<script src="js/ontologyeditor/object_property.js"></script>
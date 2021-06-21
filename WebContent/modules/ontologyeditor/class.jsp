<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<div class="top-title" ng-controller="classController">
    <div class="container">
	    <br/>
	    <h2>Class</h2>
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

		<moki-isa model="model_isa" class-list="classes_list"></moki-isa>
		<moki-object-properties model="model_obj_prop" class-list="classes_list" object-list="object_list"></moki-object-properties>
		<moki-datatype-properties model="model_data_prop" datatype-list="datatype_list"></moki-datatype-properties>
		<moki-annotations model="model_annotations" annotation-list="annotations_list"></moki-annotations>
		<moki-mapping model="model_mapping" class-list="classes_list"></moki-mapping>
		<moki-labels model="model_labels" language-list="language_list"></moki-labels>
		
		<moki-buttons></moki-buttons> 
		
	</div>
</div>
<script src="js/ontologyeditor/class.js"></script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
<style>
.search{
width: 100%;
display:flex;
justify-content: center;
}

.label-search{
margin-left: 20%;
}

.box {
padding-top: 10px;
padding-bottom: 10px;
width: 60%;
background-color:#9cc3f0;
align-items: center;
border-radius: 10px;
}

.container{
margin-top: 20px;
width: auto;
}

.fa-info-circle {
color: #8989C5 !important;
}

.select {
-webkit-appearance: menulist-button;
height: 42px;
border-color: #cccccc;
color: #8c8c8c;
}

.form {
float: left;
}

.w3l_search input[type="search"] {
width: 45%;
background-color: white;
margin-right:10px
}

.w3l_search button[type="submit"] {
width: 40px;
float: left;
margin-right:10px
}

.row{
display: flex;
flex-direction: row;
justify-content: space-around;
}

.filters{
width: 30%;
display: flex;
flex-direction: row;
margin-top: 10px;
}

label {
   margin:0px 10px 0px 10px;
}
</style>
<link rel="stylesheet" href="css/scrolling-tabs.css">
<br><br>
<div ng-controller="getListController as demo" class="container">
		<label class="label-search">Free text search</label>
		<div class="row">
		<div class="box">
		<div class="w3l_search search">
				<form class="form" name="Search" ng-submit="searchByName()" novalidate style="width:80%; margin-right:20px">				
					<input class = "input" type="search" name="searchname" placeholder="Free Text Search" ng-model="searchname">
					<button type="submit" class="btn btn-default search" aria-label="Left Align">
						<i class="fa fa-search" aria-hidden="true"> </i>
					</button>
					<br/>
					<label>Show only:</label>
					<input type="radio" name="type_search" value="All" ng-model="type_search">
			    	<label>All Documents</label>
					<input type="radio" name="type_search" value="Paper" ng-model="type_search">
			    	<label>Scientific Documents</label>
			    	<input type="radio" name="type_search" value="Preclinical_Trial" ng-model="type_search">
			    	<label>Preclinical Trials</label>
			    	<input type="radio" name="type_search" value="Clinical_Trial" ng-model="type_search">
			    	<label>Clinical Trials</label>
					<!-- <input autoComplete="on" list="attività" class = "input" type="search" name="searchactivity" placeholder="Search For Activity" ng-model="searchactivity"> -->
					
					<div class="clearfix"></div>
				</form>
		</div>
		</div>
		</div>
		<a ng-model="collapsed" ng-click="collapsed=!collapsed" style="margin-left:20%;">Toggle Advanced Search</a>
		<br/>
		<br/>
		<label class="label-search" ng-show="collapsed">Advanced Search</label>
		<div class="row" ng-show="collapsed">
		<div class="box">
		<div class="w3l_search search">
			<form class="form" name="Search" ng-submit="AdvancedSearch()" novalidate style="width:80%; margin-right:20px">
				<input type="radio" name="type_advanced" value="All" ng-model="type_advanced">
		    	<label>All Documents</label>
		    	<input type="radio" name="type_advanced" value="Paper" ng-model="type_advanced">
		    	<label>Scientific Document</label>
		    	<input type="radio" name="type_advanced" value="Preclinical_Trial" ng-model="type_advanced" >
		    	<label>Trial Preclinico</label>
		    	<input type="radio" name="type_advanced" value="Clinical_Trial" ng-model="type_advanced">
		    	<label>Trial Clinico</label>
		    	<button type="submit" class="btn btn-default search" aria-label="Left Align">
					<i class="fa fa-search" aria-hidden="true"> </i>
				</button>
		    	<br/>
		    	<br/>
		    	<label>Title:</label>
		    	<input type="text" name="title" ng-model="title" placeholder="Enter the title of the document" required/>
		    	<label>Authors:</label>
			    <input type="text" name="author" ng-model="author" placeholder="Enter authors of the document"/>
			    <br/>
			    <br/>
			    <label for="date">Date:</label>
				<input type="text" id="date" name="date" ng-model="date" placeholder="Enter date of the document">
				<br/>
				<br/>
				<label for="abstract">Abstract:</label>
				<input type="text" id="abstract" name="abstract" ng-model= "abstract" placeholder="Enter Abstract of the article">
				<br/>
				<br/>
				<div ng-show="type_advanced === 'Paper' || type_advanced === 'All'">
					<label>Pages:</label>
					<input type="text" name="pages" ng-model="pages" placeholder="Enter number of pages of the article"/>
					<label>ISBN:</label>
					<input type="text" name="isbn" ng-model="isbn" placeholder="Enter ISBN/ISSN of the article"/>
					<br/>
					<br/>
					<label>Author Address:</label>
					<input type="text" name="author_address" ng-model="author_address" placeholder="Enter optional institutions references"/>
					<br/>
					<br/>
					<label>Journal:</label>
					<input type="text" name="journal" ng-model="journal" placeholder="Enter journal of the article"/>
					<label>DOI:</label>
					<input type="text" name="doi" ng-model="doi" placeholder="Enter DOI of the article"/>
					<br/>
					<br/>
					<label>Requester:</label>
					<input type="text" name="requester" ng-model="requester" placeholder="Enter Requester of the article"/>
					<label>PMCID:</label>
					<input type="text" name="pmcid" ng-model="pmcid" placeholder="Enter PMCID of the article"/>
					<br/>
					<br/>
				</div>
				<div ng-show="type_advanced !== 'Paper'">
					<label>Relation Number:</label>
					<input type="text" name="numrel" ng-model="numrel" placeholder="Enter relation number of the document"/>
					<br/>
					<br/>
					<label>CRO:</label>
					<input type="text" name="cro" ng-model="cro" placeholder="Enter Institution/CRO name"/>
					<label>Material:</label>
					<input type="text" name="material" ng-model="material" placeholder="Enter material"/>
					<br/>
					<br/>
					<label>Document Number:</label>
					<input type="text" name="documentno" ng-model="documentno" placeholder="Enter document's number"/>
					<br/>
					<br/>
					<label>Project/Product:</label>
					<input type="text" name="project" ng-model="project" placeholder="Enter Project/Product"/>
					<br/>
					<br/>
					<label>GLP/GCP:</label>
					<input type="text" name="glp" ng-model="glp" placeholder="Enter GLP/GCP code"/>
					<label>Saggio:</label>
					<input type="text" name="essay" ng-model="saggio" placeholder="Enter a saggio of the document"/>
					<br/>
					<br/>
					<label>Administration Type:</label>
					<input type="text" name="administration" ng-model="administration" placeholder="Enter Administration type"/>
					<br/>
					<br/>
					<label>Location:</label>
					<input type="text" name="location" ng-model="location" placeholder="Enter alphanumeric code for locating the document"/>
					<br/>
					<br/>
				</div>
				<label>Notes:</label>
				<input type="text" name="notes" ng-model="notes" placeholder="Enter Notes"/>
				<br/>
				<br/>
				<label>Keywords:</label>
				<input type="text" name="keywords" ng-model="keywords" placeholder="Enter keywords of the article"/>
				<label>Language:</label>
				<input type="text" name="language" ng-model="language" placeholder="Enter the language of the document's content"/>
			</form>
		</div>  
		</div>  
	</div>
	<div ng-if="results != -1">
	 <span>
	 The number of found documents is </span><span style="color:black"ng-bind="results"></span>
	 </div>

		  <div scrolling-tabs-wrapper watch-tabs="tabs">
            <!-- Standard Bootstrap ul.nav-tabs -->
            <ul class="nav nav-tabs" role="tablist">
            	<li ng-class="{ 'active': lista.active, 'disabled': lista.disabled }"><a role="tab" data-toggle="tab" ng-click="go(-1)">Lista</a></li>
            	<li ng-repeat="tab in tabs" ng-class="tab.active"><a role="tab" data-toggle="tab" ng-click="go($index)"><span ng-bind="tab.paneId"></span>&nbsp; <button ng-click="close($index)" class="close close-sm"><span class="glyphicon glyphicon-remove-circle black"></span></button></a></li>
            </ul>
          </div>
    
          <!-- Tab panes -->
          
	 <!-- Tab of the table -->
	 
	 <div class="tab-content clearfix">	 
		<div class="tab-pane" ng-class="{ 'active': lista.active }">
			<table ng-table="demo.tableParams" class="table table-condensed table-bordered table-striped" show-filter="true">
			   <tr ng-repeat="element in $data">
			   		<td title="'Titolo'" filter="{ title: 'text'}" style="width: 38%" sortable="'title'">
			        	<span ng-bind="element.title"></span></td>
			        <td data-sort='YYYYMMDD' title="'Date'" filter="{ date: 'text'}" style="width: 10%" sortable="'date'">
			        	<span ng-bind="element.date"></span></td>
			       	<td title="'Author'" filter="{ author: 'text'}" style="width: 30%"  sortable="'author'">
			        	<span ng-bind="element.author"></span></td>
			        <td title="'Date Upload'" filter="{ date_upload: 'text'}" style="width: 10%"  sortable="'date_upload'">
			        	<span ng-bind="element.date_upload"></span></td>
			       	<td title="'Documento'" filter="{ type: 'text'}" style="width: 10%" sortable="'type'">
			        	<span ng-bind="element.type"></span></td>
			       	<td style="width: 2%" >
			       		<button ng-click="new_tab(element)" class="btn btn-default btn-sm"><i class="fa fa-info-circle"></i></button>
					</td>
					<!-- <td>
			       		<button ng-click="show_metadata(element)" class="btn btn-default btn-sm"><i class="fas fa-project-diagram"></i></button>
					</td> -->
				</tr>
			</table>
			
		</div>
		
		<!-- Repeat for the other panes -->
		<div ng-repeat="tab in tabs" class="tab-pane" ng-class="tab.active">
			<br>
			<div>
				<div>
				    <h3>Title</h3>
				</div>
				<div>
				    <span ng-bind="tab.title"></span>
				</div>
				<br/>
				<div>
				    <h3>Date</h3>
				</div>
				<div>
					<span ng-bind="tab.date"></span>
				</div>
				<br/>
				<div>
				    <h3>Authors</h3>
				</div>
				<div ng-repeat="author in tab.author">
					<span ng-bind="author"></span>
				</div>
				<br/>
				<div>
				    <h3>Date Upload</h3>
				</div>
				<div>
					<span ng-bind="tab.date_upload"></span>
				</div>
				<br/>
				<div>
				    <h3>Abstract</h3>
				</div>
				<div>
					<span ng-bind="tab.abstract"></span>
				</div>
				<br/>
			</div>
			<div ng-if="tab.type == 'Paper'">
				<div>
				    <h3>Pages</h3>
				</div>
				<div>
				    <span ng-bind="tab.pages"></span>
				</div>
				<br/>
				<div>
				    <h3>Journal</h3>
				</div>
				<div>
					<span ng-bind="tab.journal"></span>
				</div>
				<br/>
				<div>
				    <h3>ISBN</h3>
				</div>
				<div>
					<span ng-bind="tab.isbn"></span>
				</div>
				<br/>
				<div>
				    <h3>DOI</h3>
				</div>
				<div>
					<span ng-bind="tab.doi"></span>
				</div>
				<br/>
				<div>
				    <h3>Requester</h3>
				</div>
				<div>
					<span ng-bind="tab.requester"></span>
				</div>
				<br/>
				<div>
				    <h3>PMCID</h3>
				</div>
				<div>
					<span ng-bind="tab.pmcid"></span>
				</div>
				<br/>
				<div>
				    <h3>Author Address</h3>
				</div>
				<div>
					<span ng-bind="tab.author_address"></span>
				</div>
				<br/>
			</div>
			<div ng-if="tab.type !== 'Paper'">
				<div>
				    <h3>Relation Number</h3>
				</div>
				<div>
				    <span ng-bind="tab.numrel"></span>
				</div>
				<br/><div>
				    <h3>CRO Name</h3>
				</div>
				<div>
				    <span ng-bind="tab.cro"></span>
				</div>
				<br/>
				<div>
				    <h3>Material</h3>
				</div>
				<div>
				    <span ng-bind="tab.material"></span>
				</div>
				<br/>
				<div>
				    <h3>Document Number</h3>
				</div>
				<div>
				    <span ng-bind="tab.documentno"></span>
				</div>
				<br/>
				<div>
				    <h3>Project/Product</h3>
				</div>
				<div>
				    <span ng-bind="tab.project"></span>
				</div>
				<br/>
				<div>
				    <h3>GLP/GCP</h3>
				</div>
				<div>
				    <span ng-bind="tab.glp"></span>
				</div>
				<br/>
				<div>
				    <h3>Saggio</h3>
				</div>
				<div>
				    <span ng-bind="tab.saggio"></span>
				</div>
				<br/>
				<div>
				    <h3>Administration Type</h3>
				</div>
				<div>
				    <span ng-bind="tab.administration"></span>
				</div>
				<br/>
				<div>
				    <h3>Location</h3>
				</div>
				<div>
				    <span ng-bind="tab.location"></span>
				</div>
				<br/>
				<div>
				    <h3>Material Description</h3>
				</div>
				<div>
				    <span ng-bind="tab.desc_material"></span>
				</div>
				<br/>
				<div>
				    <h3>Archiving Date</h3>
				</div>
				<div>
				    <span ng-bind="tab.data_arch"></span>
				</div>
				<br/>
				<div>
				    <h3>Lotto Number</h3>
				</div>
				<div>
				    <span ng-bind="tab.num_lotto"></span>
				</div>
				<br/>
				<div>
				    <h3>Product</h3>
				</div>
				<div>
				    <span ng-bind="tab.prodotto"></span>
				</div>
				<br/>
				<div>
				    <h3>Container</h3>
				</div>
				<div>
				    <span ng-bind="tab.container"></span>
				</div>
				<br/>
				<div>
				    <h3>Formula</h3>
				</div>
				<div>
				    <span ng-bind="tab.formula"></span>
				</div>
				<br/>
				<div>
				    <h3>Study Type</h3>
				</div>
				<div>
				    <span ng-bind="tab.tipo_studio"></span>
				</div>
				<br/>
			</div>
			<div>
				<div>
				    <h3>Notes</h3>
				</div>
				<div>
					<span ng-bind="tab.notes"></span>
				</div>
				<br/>
				<div>
				    <h3>Keywords</h3>
				</div>
				<div>
					<span ng-bind="tab.keywords"></span>
				</div>
				<br/>
				<div>
				    <h3>Attachments</h3>
				</div>
				<div ng-repeat="url in tab.urls">
					<span ng-click="show_pdf(url)" style="font-weight:bold" ng-bind="url"></span>
				</div>
			</div>
			<br/>
			<div class="clearfix"></div>
			<br/>
			
		
			<div class="container" align="right">
				<div class="col-md-2 col-sm-3 col-xs-4">
					<div class="col-md-10">
						<button type="button" class="btn btn-block btn-cancel btn-lg" ng-click="close($index)">Close</button>
					</div>
				</div>
			</div>
	 	</div>
	 	
	</div> 
	
</div>



<script src="js/listmanager/classesList.js"></script>

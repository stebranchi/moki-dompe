<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
width: 4%;
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
					<input class = "input" type="search" name="searchid" placeholder="Search Doc ID" ng-model="searchid">
					<button type="submit" class="btn btn-default search" aria-label="Left Align">
						<i class="fa fa-search" aria-hidden="true"> </i>
					</button>
					<br/>
					<label>Show only:</label>
					<input type="radio" name="type" value="Paper" ng-model="type" required>
			    	<label for="typeChoice1">Paper</label>
			    	<input type="radio" name="type" value="Preclinical_Trial" ng-model="type" required>
			    	<label for="typeChoice2">Preclinical Trial</label>
			    	<input type="radio" name="type" value="Clinical_Trial" ng-model="type" required>
			    	<label for="typeChoice3">Clinical Trial</label>
					<!-- <input autoComplete="on" list="attività" class = "input" type="search" name="searchactivity" placeholder="Search For Activity" ng-model="searchactivity"> -->
					
					<div class="clearfix"></div>
				</form>
		</div>
		</div>
		</div>
		<br/>
		<br/>
		<label class="label-search">Advanced Search</label>
		<div class="row" >
		<div class="box">
		<div class="w3l_search search" ng-model="collapsed" ng-click="collapsed=!collapsed">
			<form class="form" ng-show="collapsed" name="Search" ng-submit="searchBySection()" novalidate style="width:80%; margin-right:20px">
				<input type="radio" name="type" value="Paper" ng-model="type" required>
		    	<label for="typeChoice1">Paper</label>
		    	<input type="radio" name="type" value="Preclinical_Trial" ng-model="type" required>
		    	<label for="typeChoice2">Trial Preclinico</label>
		    	<input type="radio" name="type" value="Clinical_Trial" ng-model="type" required>
		    	<label for="typeChoice3">Trial Clinico</label>
		    	<c:set var="type" value="${param.type}" />
		    	<br/>
		    	<input type="text" name="title" ng-model="filename" placeholder="Enter the title of the document" required/>
		    	<br/>
			    <input type="text" name="author" ng-model="author" placeholder="Enter authors of the document"/>
			    <br/>
			    <label for="date">Date:</label>
				<input type="date" id="date" name="date">
				<br/>
				<c:when test="${type == Paper}">
				<input type="text" name="pages" ng-model="pages" placeholder="Enter number of pages of the article"/>
				<br/>
				<input type="text" name="isbn" ng-model="isbn" placeholder="Enter ISBN/ISSN of the article"/>
				<br/>
				<input type="text" name="doi" ng-model="doi" placeholder="Enter DOI of the article"/>
				<br/>
				<input type="text" name="requester" ng-model="requester" placeholder="Enter Requester of the article"/>
				<br/>
				<input type="text" name="pmcid" ng-model="pmcid" placeholder="Enter PMCID of the article"/>
				<br/>
				</c:when>
				<c:otherwise>
				<input type="text" name="cro" ng-model="cro" placeholder="Enter Institution/CRO name"/>
				<br/>
				<input type="text" name="keywords" ng-model="keywords" placeholder="Enter keywords"/>
				<br/>
				<input type="text" name="material" ng-model="material" placeholder="Enter material"/>
				<br/>
				<input type="text" name="documentno" ng-model="documentno" placeholder="Enter document's number"/>
				<br/>
				<input type="text" name="project" ng-model="project" placeholder="Enter Project/Product"/>
				<br/>
				<input type="text" name="glp" ng-model="glp" placeholder="Enter GLP/GCP code"/>
				<br/>
				<input type="text" name="essay" ng-model="essay" placeholder="Enter an essay of the document"/>
				<br/>
				<input type="text" name="administration" ng-model="administration" placeholder="Enter Administration type"/>
				<br/>
				<input type="text" name="location" ng-model="location" placeholder="Enter alphanumeric code for locating the document"/>
				<br/>
				</c:otherwise>
				<input type="text" name="notes" ng-model="notes" placeholder="Enter Notes"/>
				<br/>
				<input type="text" name="author_address" ng-model="author_address" placeholder="Enter optional institutions references"/>
				<br/>
				<input type="text" name="pmcid" ng-model="pmcid" placeholder="Enter PMCID of the article"/>
				<br/>
				<input type="text" name="language" ng-model="language" placeholder="Enter the language of the document's content"/>
				<button type="submit" class="btn btn-default search" aria-label="Left Align">
					<i class="fa fa-search" aria-hidden="true"> </i>
				</button>
			</form>
		</div>  
		</div>  
	</div>

		  <div scrolling-tabs-wrapper watch-tabs="tabs">
            <!-- Standard Bootstrap ul.nav-tabs -->
            <ul class="nav nav-tabs" role="tablist">
            	<li ng-class="{ 'active': lista.active, 'disabled': lista.disabled }"><a role="tab" data-toggle="tab" ng-click="go(-1)">Lista</a></li>
            	<li ng-repeat="tab in tabs" ng-class="tab.active"><a role="tab" data-toggle="tab" ng-click="go($index)"><span ng-bind="tab.id"></span>&nbsp; <button ng-click="close($index)" class="close close-sm"><span class="glyphicon glyphicon-remove-circle black"></span></button></a></li>
            </ul>
          </div>
    
          <!-- Tab panes -->
          
	 <!-- Tab of the table -->
	 <div class="tab-content clearfix">	 
		<div class="tab-pane" ng-class="{ 'active': lista.active }">
			<table ng-table="demo.tableParams" class="table table-condensed table-bordered table-striped" show-filter="true">
			   <tr ng-repeat="element in $data">
			   		<td title="'Titolo'" filter="{ title: 'text'}" style="width: 48%" sortable="'title'">
			        	<span ng-bind="element.title"></span></td>
			       	<td title="'Autori'" filter="{ authors: 'text'}" style="width: 40%"  sortable="'authors'">
			        	<span ng-bind="element.authors"></span></td>
			        <!--<td title="'Codice'" filter="{ code: 'text'}" style="width: 10%" sortable="'code'">
			        	<span ng-bind="element.code"></span></td>
			        <td ng-click="new_tab(element)" title="'Owning Site'" filter="{ sopApplicabilityAreasRaw: 'text'}" sortable="'titolo'">
			        	<span ng-bind="element.sopOwningSite"></span></td>
			       	<td> -->
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
			<div ng-if="details == 'endnote'">
				<div>
				    <h3>Titolo</h3>
				</div>
				<div>
				    <span ng-bind="tab.title"></span>
				</div>
				<br/>
				<div>
				    <h3>Codice</h3>
				</div>
				<div>
				<span ng-bind="tab.isbn"></span>
				</div>
				<br/>
				<div>
				    <h3>Abstract</h3>
				</div>
				<div>
					<span ng-bind="tab.abstract"></span>
				</div>
				<br/>
				<div>
				    <h3>Autori</h3>
				</div>
				<div>
					<span ng-bind="tab.authors"></span>
				</div>
				<br/>
				<div>
				    <h3>Allegati</h3>
				</div>
				<div ng-repeat="url in tab.urls">
					<span ng-click="show_pdf(url)" style="font-weight:bold" ng-bind="url"></span>
				</div>
			</div>
			<div ng-if="details == 'glp'">
				<div>
				    <h3>Materiale</h3>
				</div>
				<div>
				    <span ng-bind="tab.materiale"></span>
				</div>
				<br/>
				<div>
				    <h3>Contenuto</h3>
				</div>
				<div>
					<span ng-bind="tab.conten"></span>
				</div>
				<br/>
				<div>
				    <h3>Numlot</h3>
				</div>
				<div>
					<span ng-bind="tab.numlot"></span>
				</div>
				<br/>
				<div>
				    <h3>Testata</h3>
				</div>
				<div>
					<span ng-bind="tab.testata"></span>
				</div>
				<br/>
				<div>
				    <h3>Numero studio</h3>
				</div>
				<div>
					<span ng-bind="tab.numstud"></span>
				</div>
				<br/>
				<div>
				    <h3>Descrizione Materiale</h3>
				</div>
				<div>
					<span ng-bind="tab.desc2"></span>
				</div>
			</div>
			<button type="button" class="btn btn-info" ng-click="changeValue('endnote')">Dettagli Endnote</button>
			<button type="button" class="btn btn-info" ng-click="changeValue('glp')">Dettagli GLP</button>
			<!-- <div>
				<span ng-bind="tab.testoapparea"></span></td>
			<div/>
			<br/>
			<div>
				<span style="font-weight:bold" ng-bind="tab.titoloownsite"></span></td>
			</div>
			<div>
				<span ng-bind="tab.testoownsite"></span></td>
			<div/> -->
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

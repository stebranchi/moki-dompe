<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
<link rel="stylesheet" href="css/scrolling-tabs.css">	

<div class="login">	
	<div ng-controller="GetUploadController as demo" class="container">
		<h2>Upload</h2>
		<div class="login-form-grids animated wow slideInUp" data-wow-delay=".5s">
			<form class="form" name="upload" ng-submit="uploadFile()">
			<label>Document Type:</label>
			<input type="radio" name="type_upload" value="Paper" ng-model="type_upload" checked>
	    	<label for="typeChoice1">Paper</label>
	    	<input type="radio" name="type_upload" value="Preclinical_Trial" ng-model="type_upload">
	    	<label for="typeChoice2">Preclinical Trial</label>
	    	<input type="radio" name="type_upload" value="Clinical_Trial" ng-model="type_upload">
	    	<label for="typeChoice3">Clinical Trial</label>
	    	<br/>
	    	<div ng-show = "type_upload === 'Paper'">
	    	<label>XML File:</label>
		    <input type="file" custom-on-change="notempty" accept="file/csv" name="file_xml" fileread="xml" label="File xml"/>
	    	</div>
	    	<div ng-show = "empty == true">
	    	<label>Title:</label>
	    	<input type="text" name="title" ng-model="title" placeholder="Enter the title of the document" required/>
	    	<br/>
	    	<label>Authors:</label>
		    <input type="text" name="author" ng-model="author" placeholder="Enter authors of the document"/>
		    <br/>
		    <label for="date">Date:</label>
			<input type="date" id="date" name="date">
			<br/>
			<br/>
		    <label for="abstract">Abstract:</label>
			<input type="text"id="abstract" name="abstract" placeholder="Enter abstract of the document"/>
			<br/>
			<div ng-show="type_upload === 'Paper'">
				<label>Pages:</label>
				<input type="text" name="pages" ng-model="pages" placeholder="Enter number of pages of the article"/>
				<br/>
				<label>Journal:</label>
				<input type="text" name="journal" ng-model="journal" placeholder="Enter the journal of the article"/>
				<br/>
				<label>ISBN:</label>
				<input type="text" name="isbn" ng-model="isbn" placeholder="Enter ISBN/ISSN of the article"/>
				<br/>
				<label>DOI:</label>
				<input type="text" name="doi" ng-model="doi" placeholder="Enter DOI of the article"/>
				<br/>
				<label>Requester:</label>
				<input type="text" name="requester" ng-model="requester" placeholder="Enter Requester of the article"/>
				<br/>
				<label>PMCID:</label>
				<input type="text" name="pmcid" ng-model="pmcid" placeholder="Enter PMCID of the article"/>
				<br/>
			</div>
			<div ng-show="type_upload !== 'Paper'">
				<label>Relation number:</label>
				<input type="text" name="numrel" ng-model="numrel" placeholder="Enter Relation Number"/>
				<br/>
				<label>CRO:</label>
				<input type="text" name="cro" ng-model="cro" placeholder="Enter Institution/CRO name"/>
				<br/>
				<label>Material:</label>
				<input type="text" name="material" ng-model="material" placeholder="Enter material"/>
				<br/>
				<label>Document Number:</label>
				<input type="text" name="documentno" ng-model="documentno" placeholder="Enter document's number"/>
				<br/>
				<label>Project/Product:</label>
				<input type="text" name="project" ng-model="project" placeholder="Enter Project/Product"/>
				<br/>
				<label>GLP/GCP:</label>
				<input type="text" name="glp" ng-model="glp" placeholder="Enter GLP/GCP code"/>
				<br/>
				<label>Saggio:</label>
				<input type="text" name="essay" ng-model="saggio" placeholder="Enter a saggio of the document"/>
				<br/>
				<label>Administration Type:</label>
				<input type="text" name="administration" ng-model="administration" placeholder="Enter Administration type"/>
				<br/>
				<label>Location:</label>
				<input type="text" name="location" ng-model="location" placeholder="Enter alphanumeric code for locating the document"/>
				<br/>
				<label>Material Description:</label>
				<input type="text" name="desc_material" ng-model="desc_material" placeholder="Enter Material Description for the document"/>
				<br/>
				<label>Archiviation Date:</label>
				<input type="text" name="data_arch" ng-model="data_arch" placeholder="Enter archiviation date of the document"/>
				<br/>
				<label>Lotto Number:</label>
				<input type="text" name="num_lotto" ng-model="num_lotto" placeholder="Enter lotto number of the document"/>
				<br/>
				<label>Product:</label>
				<input type="text" name="prodotto" ng-model="prodotto" placeholder="Enter product of the document"/>
				<br/>
				<label>Container:</label>
				<input type="text" name="container" ng-model="container" placeholder="Enter container of the document"/>
				<br/>
				<label>Formula:</label>
				<input type="text" name="formula" ng-model="formula" placeholder="Enter formula of the document"/>
				<br/>
				<label>Study Type:</label>
				<input type="text" name="tipo_studio" ng-model="tipo_studio" placeholder="Enter study type of the document"/>
				<br/>
			</div>
			<label>Notes:</label>
			<input type="text" name="notes" ng-model="notes" placeholder="Enter Notes"/>
			<br/>
			<label>Author Address:</label>
			<input type="text" name="author_address" ng-model="author_address" placeholder="Enter optional institutions references"/>
			<br/>
			<label>Keywords:</label>
			<input type="text" name="keywords" ng-model="keywords" placeholder="Enter keywords of the article"/>
			<br/>
			<label>Language:</label>
			<input type="text" name="language" ng-model="language" placeholder="Enter the language of the document's content"/>
			<br/>
			</div>
		    <label>Attachment:</label>
		    <input type="file" name="file_pdf" fileread="pdf"/>
		    <input type="submit" value="Upload">
		    </form>
		</div>
	</div>
</div>

<script src="js/upload/uploadcard.js"></script>
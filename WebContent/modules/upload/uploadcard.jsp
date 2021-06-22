<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
<link rel="stylesheet" href="css/scrolling-tabs.css">	

<div class="login">	
	<div ng-controller="GetUploadController as demo" class="container">
		<h2>Upload</h2>
		<div class="login-form-grids animated wow slideInUp" data-wow-delay=".5s">
			<form class="form" name="upload" ng-submit="uploadFile()">
			<label>Document Type:</label>
			<input type="radio" name="type" value="Paper" ng-model="type" checked>
	    	<label for="typeChoice1">Paper</label>
	    	<input type="radio" name="type" value="Preclinical_Trial" ng-model="type">
	    	<label for="typeChoice2">Trial Preclinico</label>
	    	<input type="radio" name="type" value="Clinical_Trial" ng-model="type">
	    	<label for="typeChoice3">Trial Clinico</label>
	    	<br/>
	    	<label>XML File:</label>
		    <input type="file" name="file_xml" fileread="xml" label="File xml"/>
	    	<br/>
	    	<label>Title:</label>
	    	<input type="text" name="title" ng-model="title" placeholder="Enter the title of the document" required/>
	    	<br/>
	    	<label>Authors:</label>
		    <input type="text" name="author" ng-model="author" placeholder="Enter authors of the document"/>
		    <br/>
		    <label>Date</label>
		    <label for="date">Date:</label>
			<input type="date" id="date" name="date">
			<br/>
			<div ng-if="type === 'Paper'">
				<label>Pages:</label>
				<input type="text" name="pages" ng-model="pages" placeholder="Enter number of pages of the article"/>
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
			<div ng-if="type !== 'Paper'">
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
				<label>Essay:</label>
				<input type="text" name="essay" ng-model="essay" placeholder="Enter an essay of the document"/>
				<br/>
				<label>Administration Type:</label>
				<input type="text" name="administration" ng-model="administration" placeholder="Enter Administration type"/>
				<br/>
				<label>Location:</label>
				<input type="text" name="location" ng-model="location" placeholder="Enter alphanumeric code for locating the document"/>
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
		    <label>Attachment:</label>
		    <input type="file" name="file_pdf" fileread="pdf"/>
		    </form>
		</div>
	</div>
</div>

<script src="js/upload/uploadcard.js"></script>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<style>
#search{
width: 60%;
display:flex;
flex-direction: row;
margin-bottom: 20px;
margin-left: 0;
}
.container{
margin-top: 20px;
width: auto;
}

.w3l_search input[type="search"] {
width: 30%;
margin-right:10px
}

.row{
display: flex;
flex-direction: row;
justify-content: space-around;
}

.filters{
width: 36%;
display: flex;
flex-direction: row;
margin-top: 10px;
}

label {
   margin:0px 10px 0px 10px;
}
</style>
<meta charset="utf-8">
<div ng-controller="GetSopController as sc" class="container">
	<div class="row">
		<div class="w3l_search" id="search">
				<form name="Search" ng-submit="searchByName()" novalidate style="width:100%; margin-right:20px">
				    <input class = "input" type="search" name="searchname" placeholder="Free Text Search" ng-model="searchname">				
					<input class = "input" type="search" name="searchid" placeholder="Search Doc ID" ng-model="searchid">
					<input autoComplete="on" list="ruoli" class = "input" type="search" name="searchrole" placeholder="Search Role" ng-model="searchrole">
					<!--  <input autoComplete="on" list="attività" class = "input" type="search" name="searcactivity" placeholder="Search Activity" ng-model="searchname"> -->
					<button type="submit" class="btn btn-default search" aria-label="Left Align">
						<i class="fa fa-search" aria-hidden="true"> </i>
					</button>
					<div class="clearfix"></div>
				</form>
		</div>
		<div class="filters">
				<input class="singlefilter" type="radio" name="filter" value="getIsolated" ng-model="filterNodes"> <label for="getIsolated">Senza archi</label>
		  		<input class="singlefilter" type="radio" name="filter" value="getPointing" ng-model="filterNodes"> <label for="getPointing">Solo archi uscenti</label>
		  		<input class="singlefilter" type="radio" name="filter" value="getPointed" ng-model="filterNodes"> <label for="getPointed">Solo archi entranti</label>
			</div>
	</div>	
<div align='center' id="d3_selectable_force_directed_graph" style="width: 90%; height: 600px; margin: auto; margin-bottom: 12px">
    <svg />
</div>
	<!-- <svg class="graph" width="1200" height="900"></svg> -->

<!-- <script src="https://d3js.org/d3.v4.min.js"></script>
<script src="js/provad3/graphPage.js"></script> -->
<script src="https://d3js.org/d3.v4.js"></script>
<script src="js/graphEditor/utilityGraph.js"></script>
<script src="js/graphEditor/graphPage.js"></script>
</div>
<datalist id="ruoli">
    <option>Responsabile di Laboratorio</option>
    <option>Laboratorio Microbiologico</option>
    <option>Operatori di Laboratorio</option>
    <option>Laboratorio di Sintesi</option>
    <option>Personale di Laboratorio</option>
    <option>Laboratorio Analisi Chimiche</option>
    <option>Analisti del Laboratorio</option>
    <option>Responsabile di Produzione</option>
    <option>Direttore di Produzione</option>
    <option>Supervisori di Produzione</option>
    <option>Operatori Produzione</option>
    <option>Responsabile PPU</option>
    <option>Ingegneria e Manutenzione</option>
    <option>Technology R&D</option>
    <option>Produzione Biotech</option>
    <option>Operatori Produzione Biotech</option>
    <option>Microbiologico del Controllo</option>
    <option>Manutenzione</option>
    <option>Società Esterna</option>
    <option>Controllo Qualità</option>
    <option>Responsabile della Sezione</option>
    <option>Ambiente e Sicurezza</option>
    <option>Sezione di Chimica</option>
    <option>Vigilanza</option>
    <option>Responsabile Elettro-Strumentale</option>
    <option>Quality Assurance Product</option>
    <option>Prevenzione e Protezione</option>
    <option>Site Quality Manager</option>
    <option>Responsabile dei Lavori</option>
    <option>Dompé Compliance Tool</option>
    <option>Responsabile di Funzione</option>
    <option>Responsabile di Reparto</option>
    <option>Direzione Supply Chain</option>
    <option>Responsabile del Magazzino</option>
    <option>Squadra Emergenza</option>
    <option>Reparto Ingegneria</option>
    <option>Direzione Risorse Umane</option>
    <option>Regulatory Affairs Department</option>
    <option>Direzione Medical Affairs</option>
    <option>Servizio Prevenzione</option>
    <option>Responsabile Controllo Qualità</option>
</datalist>	
<datalist id="attività">
    <option>First option</option>
    <option>Second Option</option>
</datalist>	
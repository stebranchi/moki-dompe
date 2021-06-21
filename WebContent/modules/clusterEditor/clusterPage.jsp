<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<style>
.node text {
  pointer-events: none;
  font: 10px sans-serif;
}
</style>
<meta charset="utf-8">
<div ng-controller="GetClusterController as sc" class="container">
<br/>
<br/>
<div align='center' id="d3_selectable_force_directed_graph" style="width: 90%; height: 600px; margin: auto; margin-bottom: 12px">
    <svg />
</div>
	<!-- <svg class="graph" width="1200" height="900"></svg> -->

<!-- <script src="https://d3js.org/d3.v4.min.js"></script>
<script src="js/provad3/graphPage.js"></script> -->
<script src="https://d3js.org/d3.v4.js"></script>
<script src="js/clusterEditor/utilityGraph.js"></script>
<script src="js/clusterEditor/clusterPage.js"></script>
</div>

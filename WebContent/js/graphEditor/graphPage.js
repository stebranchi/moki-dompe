var app = angular.module("Moki") 

app.controller("GetSopController",   
        function($scope, $http, $window, NgTableParams, mokiService) {
	
	$scope.DocumentById = [];
	$scope.DocumentByName = [];
	$scope.sop_all_json = {nodes : [], links : []};
	$scope.sop_id_json = {nodes : [], links : []};
	$scope.document = null;
	var svg = d3.select('#d3_selectable_force_directed_graph');
	var texts = ['Blue = results', 'Orange = linked Docs', 'Use the scroll wheel to zoom'];
	
	req = {
			method: 'POST',
			url: 'RequestManager',
			headers: {
			  'Content-Type': "application/json"
			},
			data: {
			"serviceName": "UserDataManager",
			"locationService": "usermanager",
			"data" : {
				"mode": "getUserData"
			}
		}
	}
	
	$http(req)
		.then(function(response){
			if(response.data.data.email == null)
				{
				$window.location.href='index.jsp?page=login&module=usermanager';
				}
		});
	
	//Function for query the single SOP when clicked in the graph
//	It calls the function "Search" in the module LuceneTester 
//	And the json taken from the response is given to the function createV4SelectableForceDirectedGraph
	$scope.searchById = function(id){
			reqId = {
					method: 'POST',
					url: 'RequestManager',
					headers: {
					  'Content-Type': "application/json"
					},
					data: {
					"serviceName": "LuceneTester",
					"locationService": "sopmanager",
					"data" : {
						"mode": "searchById",
						"id": id,
					}
				}
			}
			
			$http(reqId)
			.then(function(response){
				$scope.createV4SelectableForceDirectedGraph(svg, response.data.json);
			})
			.catch(console.error);
	}
	
//	General wrap function for the search of SOP
//	It checks which field is used when clicked the search taking the first available
//	Searchname: it calls the "Search" function of the LuceneTester module for the free text search 
//	SearchRole: Same as searchname but with a name taken from a list of roles 
//	SearchId: it calls the function "searchById" to retrieve the single SOP
//	At the end it checks if: Isolated, Pointing, Pointed are checkedl it loads the json
//	And it is given to the function createV4SelectableForceDirectedGraph
	$scope.searchByName = function(){
		if(typeof $scope.searchname !== "undefined"  && $scope.searchname.length > 0) {
			reqName = {
					method: 'POST',
					url: 'RequestManager',
					headers: {
					  'Content-Type': "application/json"
					},
					data: {
					"serviceName": "LuceneTester",
					"locationService": "sopmanager",
					"data" : {
						"mode": "search",
						"name": $scope.searchname,
					}
				}
			}
		
			$http(reqName)
			.then(function(response){
				$scope.createV4SelectableForceDirectedGraph(svg, response.data.json, texts);
			})
			.catch(console.error);
		}
		else if(typeof $scope.searchrole !== "undefined"  && $scope.searchrole.length > 0) {
			reqRole = {
					method: 'POST',
					url: 'RequestManager',
					headers: {
					  'Content-Type': "application/json"
					},
					data: {
					"serviceName": "LuceneTester",
					"locationService": "sopmanager",
					"data" : {
						"mode": "searchRole",
						"name": $scope.searchrole,
					}
				}
			}
		
			$http(reqRole)
			.then(function(response){
				$scope.createV4SelectableForceDirectedGraph(svg, response.data.json, texts);
			})
			.catch(console.error);
		}
		else if(typeof $scope.searchid !== "undefined" && $scope.searchid.length > 0) {
			$scope.searchById($scope.searchid);
		}		
		else if ($scope.filterNodes != null){
			switch ($scope.filterNodes) {
			  case "getIsolated":
				  d3.json('modules/graphEditor/data/alone_graph.json', function(error, graph) {
				        if (!error) {
				            //console.log('graph', graph);
				        	$scope.createV4SelectableForceDirectedGraph(svg, graph, texts);
				        } else {
				            console.error(error);
				        }
				    })
				    break;
			  case "getPointed":
				  d3.json('modules/graphEditor/data/pointed_graph.json', function(error, graph) {
				        if (!error) {
				            //console.log('graph', graph);
				        	$scope.createV4SelectableForceDirectedGraph(svg, graph, texts);
				        } else {
				            console.error(error);
				        }
				    })
			    break;
			  case "getPointing":
				  d3.json('modules/graphEditor/data/pointing_graph.json', function(error, graph) {
				        if (!error) {
				            //console.log('graph', graph);
				        	$scope.createV4SelectableForceDirectedGraph(svg, graph, texts);
				        } else {
				            console.error(error);
				        }
				    })
			    break;
			  default:
			    break;
			}
		}
		else
		{
			graphAll();
		}
	}
	graphAll();
	function graphAll() {
		d3.json('modules/graphEditor/data/documents_graph.json', function(error, graph) {
        if (!error) {
            //console.log('graph', graph);
        	var texts = ['Blue = SOP', 'Orange = WI', 'green = GOV', 'Use the scroll wheel to zoom']
        	$scope.createV4SelectableForceDirectedGraph(svg, graph, texts);
        } else {
            console.error(error);
        }
    })
	}

//  This function takes as input the svg container of the graph and the json of SOPS
//	And it creates the graph of all nodes that are in the json
	$scope.createV4SelectableForceDirectedGraph = function(svg, graph, texts) {
	    // if both d3v3 and d3v4 are loaded, we'll assume
	    // that d3v4 is called d3v4, otherwise we'll assume
	    // that d3v4 is the default (d3)
	    if (typeof d3v4 == 'undefined')
	        d3v4 = d3;
	
	    var width = +svg.attr("width"),
	        height = +svg.attr("height");
	
	    let parentWidth = d3v4.select('svg').node().parentNode.clientWidth;
	    let parentHeight = d3v4.select('svg').node().parentNode.clientHeight;
	
	    var svg = d3v4.select('svg')
	    .attr('width', parentWidth)
	    .attr('height', parentHeight)
	
	    // remove any previous graphs
	    svg.selectAll('.g-main').remove();
	
	    var gMain = svg.append('g')
	    .classed('g-main', true);
	
	    var rect = gMain.append('rect')
	    .attr('width', parentWidth)
	    .attr('height', parentHeight)
	    .style('fill', 'white')
	
	    var gDraw = gMain.append('g');
	
	    var zoom = d3v4.zoom()
	    .on('zoom', zoomed)
	
	    gMain.call(zoom);
	
	
	    function zoomed() {
	        gDraw.attr('transform', d3v4.event.transform);
	    }
	    
	    svg.append("defs").append("marker")
	        .attr("id", "arrow")
	        .attr("viewBox", "0 -5 10 10")
	        .attr("refX", 20)
	        .attr("refY", 0)
	        .attr("markerWidth", 8)
	        .attr("markerHeight", 4)
	        .attr("orient", "auto")
	      .append("svg:path")
	        .attr("d", "M0,-5L10,0L0,5");
	
	    var color = d3v4.scaleOrdinal(d3v4.schemeCategory10);
	
	    if (! ("links" in graph)) {
	        console.log("Graph is missing links");
	        return;
	    }
	
	    var nodes = {};
	    var i;
	    for (i = 0; i < graph.nodes.length; i++) {
	        nodes[graph.nodes[i].id] = graph.nodes[i];
	        graph.nodes[i].weight = 1.01;
	    }
	
	    // the brush needs to go before the nodes so that it doesn't
	    // get called when the mouse is over a node
	    var gBrushHolder = gDraw.append('g');
	    var gBrush = null;
	
	    var link = gDraw.append("g")
	        .attr("class", "link")
	        .selectAll("line")
	        .data(graph.links)
	        .enter().append("line")
	        .attr("stroke-width", function(d) { return Math.sqrt(d.value); })
	        .attr("marker-end", "url(#arrow)");	    	
	
	    var node = gDraw.append("g")
	        .attr("class", "node")
	        .selectAll("circle")
	        .data(graph.nodes)
	        .enter().append("circle")
	        .attr("r", 5)
	        .attr("fill", function(d) { 
	            if ('color' in d)
	                return d.color;
	            else
	                return color(d.group); 
	        })
	        .call(d3v4.drag()
	        .on("start", dragstarted)
	        .on("drag", dragged)
	        .on("end", dragended))
	        .on("click", function(d) {$scope.searchById(d.id)});
	
	      
	    // add titles for mouseover blurbs
	    node.append("title")
	        .text(function(d) { 
	            if ('name' in d)
	                return d.name;
	            else
	                return d.id; 
	        });
	
	    var simulation = d3v4.forceSimulation()
	        .force("link", d3v4.forceLink()
	                .id(function(d) { return d.id; })
	                .distance(function(d) { 
	                    return 30;
	                    //var dist = 20 / d.value;
	                    //console.log('dist:', dist);
	
	                    return dist; 
	                })
	              )
	        .force("charge", d3v4.forceManyBody())
	        .force("center", d3v4.forceCenter(parentWidth / 2, parentHeight / 2))
	        .force("x", d3v4.forceX(parentWidth/2))
	        .force("y", d3v4.forceY(parentHeight/2));
	
	    simulation
	        .nodes(graph.nodes)
	        .on("tick", ticked);
	
	    simulation.force("link")
	        .links(graph.links);
	
	    function ticked() {
	        // update node and line positions at every step of 
	        // the force simulation
	        link.attr("x1", function(d) { return d.source.x; })
	            .attr("y1", function(d) { return d.source.y; })
	            .attr("x2", function(d) { return d.target.x; })
	            .attr("y2", function(d) { return d.target.y; });
	
	        node.attr("cx", function(d) { return d.x; })
	            .attr("cy", function(d) { return d.y; });
	    }
	
	    var brushMode = false;
	    var brushing = false;
	
	    var brush = d3v4.brush()
	        .on("start", brushstarted)
	        .on("brush", brushed)
	        .on("end", brushended);
	
	    function brushstarted() {
	        // keep track of whether we're actively brushing so that we
	        // don't remove the brush on keyup in the middle of a selection
	        brushing = true;
	
	        node.each(function(d) { 
	            d.previouslySelected = shiftKey && d.selected; 
	        });
	    }
	
	    rect.on('click', () => {
	        node.each(function(d) {
	            d.selected = false;
	            d.previouslySelected = false;
	        });
	        node.classed("selected", false);
	    });
	
	    function brushed() {
	        if (!d3v4.event.sourceEvent) return;
	        if (!d3v4.event.selection) return;
	
	        var extent = d3v4.event.selection;
	
	        node.classed("selected", function(d) {
	            return d.selected = d.previouslySelected ^
	            (extent[0][0] <= d.x && d.x < extent[1][0]
	             && extent[0][1] <= d.y && d.y < extent[1][1]);
	        });
	    }
	
	    function brushended() {
	        if (!d3v4.event.sourceEvent) return;
	        if (!d3v4.event.selection) return;
	        if (!gBrush) return;
	
	        gBrush.call(brush.move, null);
	
	        if (!brushMode) {
	            // the shift key has been release before we ended our brushing
	            gBrush.remove();
	            gBrush = null;
	        }
	
	        brushing = false;
	    }
	
	    d3v4.select('body').on('keydown', keydown);
	    d3v4.select('body').on('keyup', keyup);
	
	    var shiftKey;
	
	    function keydown() {
	        shiftKey = d3v4.event.shiftKey;
	
	        if (shiftKey) {
	            // if we already have a brush, don't do anything
	            if (gBrush)
	                return;
	
	            brushMode = true;
	
	            if (!gBrush) {
	                gBrush = gBrushHolder.append('g');
	                gBrush.call(brush);
	            }
	        }
	    }
	
	    function keyup() {
	        shiftKey = false;
	        brushMode = false;
	
	        if (!gBrush)
	            return;
	
	        if (!brushing) {
	            // only remove the brush if we're not actively brushing
	            // otherwise it'll be removed when the brushing ends
	            gBrush.remove();
	            gBrush = null;
	        }
	    }
	
	    function dragstarted(d) {
	      if (!d3v4.event.active) simulation.alphaTarget(0.9).restart();
	
	        if (!d.selected && !shiftKey) {
	            // if this node isn't selected, then we have to unselect every other node
	            node.classed("selected", function(p) { return p.selected =  p.previouslySelected = false; });
	        }
	
	        d3v4.select(this).classed("selected", function(p) { d.previouslySelected = d.selected; return d.selected = true; });
	
	        node.filter(function(d) { return d.selected; })
	        .each(function(d) { //d.fixed |= 2; 
	          d.fx = d.x;
	          d.fy = d.y;
	        })
	
	    }
	
	    function dragged(d) {
	        node.filter(function(d) { return d.selected; })
	        .each(function(d) { 
	            d.fx += d3v4.event.dx;
	            d.fy += d3v4.event.dy;
	        })
	    }
	
	    function dragended(d) {
	      if (!d3v4.event.active) simulation.alphaTarget(0);
	      d.fx = null;
	      d.fy = null;
	        node.filter(function(d) { return d.selected; })
	        .each(function(d) { //d.fixed &= ~6; 
	            d.fx = null;
	            d.fy = null;
	        })
	    }    
	
	    svg.selectAll("text").remove()
	    
	    svg.selectAll('text')
	        .data(texts)
	        .enter()
	        .append('text')
	        .attr('x', parentWidth-10)
	        .attr('y', function(d,i) { return parentHeight - (4-i) * 18; })
	        .text(function(d) { return d; });
	    
	    return graph;
	}

});


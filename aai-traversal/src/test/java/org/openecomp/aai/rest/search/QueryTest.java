/*-
 * ============LICENSE_START=======================================================
 * org.openecomp.aai
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property. All rights reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */

package org.openecomp.aai.rest.search;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

import org.openecomp.aai.exceptions.AAIException;
import org.openecomp.aai.serialization.db.EdgeRules;
import org.openecomp.aai.serialization.db.exceptions.NoEdgeRuleFoundException;

public abstract class QueryTest {
	
	protected Graph graph;
	private GremlinServerSingleton gremlinServerSingleton;
	private GremlinGroovyShellSingleton shell;
	
	protected final List<Vertex> expectedResult = new ArrayList<>();
	protected final EdgeRules rules = EdgeRules.getInstance();
	
	
	public QueryTest() throws AAIException, NoEdgeRuleFoundException {
		setUp();
	}
	public void setUp() throws AAIException, NoEdgeRuleFoundException {
		System.setProperty("AJSC_HOME", ".");
		System.setProperty("BUNDLECONFIG_DIR", "bundleconfig-local");
		graph = TinkerGraph.open();
		createGraph();
		gremlinServerSingleton = GremlinServerSingleton.getInstance();
		shell = GremlinGroovyShellSingleton.getInstance();
	}
	
	public void run() {
		
		String query = "g." + gremlinServerSingleton.getStoredQuery(getQueryName());
		
		Map<String, Object> params = new HashMap<>();
		
		GraphTraversal<Vertex, Vertex> g = graph.traversal().V();
		addStartNode(g);
		params.put("g", g);
		addParam(params);
		GraphTraversal<Vertex, Vertex> result = (GraphTraversal<Vertex, Vertex>)shell.executeTraversal(query, params);
		
		List<Vertex> vertices = result.toList();
		assertTrue("all vertices found", vertices.containsAll(expectedResult) && expectedResult.containsAll(vertices));

	}
	
	protected abstract void createGraph() throws AAIException, NoEdgeRuleFoundException;
		
	protected abstract String getQueryName();
	
	protected abstract void addStartNode(GraphTraversal<Vertex, Vertex> g);
	
	protected abstract void addParam(Map<String, Object> params);
}

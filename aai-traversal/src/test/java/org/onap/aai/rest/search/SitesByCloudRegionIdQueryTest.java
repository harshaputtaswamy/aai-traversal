/**
 * ============LICENSE_START=======================================================
 * org.onap.aai
 * ================================================================================
 * Copyright © 2017-2018 AT&T Intellectual Property. All rights reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */
package org.onap.aai.rest.search;

import java.util.Map;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.junit.Test;
import org.onap.aai.exceptions.AAIException;
import org.onap.aai.serialization.db.exceptions.NoEdgeRuleFoundException;

public class SitesByCloudRegionIdQueryTest extends QueryTest {

    public SitesByCloudRegionIdQueryTest() throws AAIException, NoEdgeRuleFoundException {
        super();
    }

    @Test
    public void run() {
        super.run();
    }

    @Override
    protected void createGraph() throws AAIException, NoEdgeRuleFoundException {
        Vertex complex = graph.addVertex(T.label, "complex", T.id, "0", "aai-node-type", "complex");
        Vertex network =
            graph.addVertex(T.label, "l3-network", T.id, "1", "aai-node-type", "l3-network");
        Vertex region = graph.addVertex(T.label, "cloud-region", T.id, "2", "aai-node-type",
            "cloud-region", "cloud-region-id", "regionuuid");
        Vertex complex2 =
            graph.addVertex(T.label, "complex", T.id, "3", "aai-node-type", "complex");
        Vertex network2 =
            graph.addVertex(T.label, "l3-network", T.id, "4", "aai-node-type", "l3-network");
        Vertex region2 = graph.addVertex(T.label, "cloud-region", T.id, "5", "aai-node-type",
            "cloud-region", "cloud-region-id", "regionuuid2");

        GraphTraversalSource g = graph.traversal();
        rules.addEdge(g, complex, network);
        rules.addEdge(g, region, network);
        rules.addEdge(g, complex2, network2);
        rules.addEdge(g, region2, network2);

        expectedResult.add(complex);

    }

    @Override
    protected String getQueryName() {
        return "sites-byCloudRegionId";
    }

    @Override
    protected void addStartNode(GraphTraversal<Vertex, Vertex> g) {
        g.has("cloud-region-id", "regionuuid");

    }

    @Override
    protected void addParam(Map<String, Object> params) {
        return;
    }

}

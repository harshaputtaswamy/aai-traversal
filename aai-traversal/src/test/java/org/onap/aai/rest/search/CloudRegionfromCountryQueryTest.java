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

public class CloudRegionfromCountryQueryTest extends QueryTest {
    public CloudRegionfromCountryQueryTest() throws AAIException, NoEdgeRuleFoundException {
        super();
    }

    @Test
    public void run() {
        super.run();
    }

    @Override
    protected void createGraph() throws AAIException, NoEdgeRuleFoundException {

        Vertex complex = graph.addVertex(T.label, "complex", T.id, "0", "aai-node-type", "complex",
            "physical-location-id", "physical-location-id-1");
        Vertex cloudregion =
            graph.addVertex(T.label, "cloud-region", T.id, "1", "aai-node-type", "cloud-region",
                "cloud-region-id", "cloud-region-id-1", "cloud-region-owner", "cloud-owner-name-1");

        Vertex complex1 = graph.addVertex(T.label, "complex", T.id, "2", "aai-node-type", "complex",
            "physical-location-id", "physical-location-id-2");
        Vertex cloudregion1 =
            graph.addVertex(T.label, "cloud-region", T.id, "3", "aai-node-type", "cloud-region",
                "cloud-region-id", "cloud-region-id-2", "cloud-region-owner", "cloud-owner-name-2");

        GraphTraversalSource g = graph.traversal();
        rules.addEdge(g, complex, cloudregion);

        rules.addEdge(g, complex1, cloudregion1);

        expectedResult.add(cloudregion);

    }

    @Override
    protected String getQueryName() {
        return "cloudRegion-fromCountry";
    }

    @Override
    protected void addStartNode(GraphTraversal<Vertex, Vertex> g) {
        g.has("physical-location-id", "physical-location-id-1");

    }

    @Override
    protected void addParam(Map<String, Object> params) {
        return;
    }
}

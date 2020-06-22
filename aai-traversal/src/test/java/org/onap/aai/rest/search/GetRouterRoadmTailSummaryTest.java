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
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */
package org.onap.aai.rest.search;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.step.util.Tree;
import org.apache.tinkerpop.gremlin.structure.T;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.junit.Test;
import org.onap.aai.exceptions.AAIException;
import org.onap.aai.serialization.db.exceptions.NoEdgeRuleFoundException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;

public class GetRouterRoadmTailSummaryTest extends TreeQueryTest{

    public GetRouterRoadmTailSummaryTest() throws AAIException, NoEdgeRuleFoundException {
        super();
    }

    @Override
    protected void createGraph() throws AAIException, NoEdgeRuleFoundException {
        Vertex logicalLink1 = graph.addVertex(T.label, "logical-link", T.id, "4", "aai-node-type", "logical-link", "link-name", "logical-link-1");
        Vertex logicalLink2 = graph.addVertex(T.label, "logical-link", T.id, "5", "aai-node-type", "logical-link", "link-name", "logical-link-2");
        Vertex pInterface1 = graph.addVertex(T.label, "p-interface", T.id, "6", "aai-node-type", "p-interface", "interface-name", "p-interface-1");
        Vertex pInterface2 = graph.addVertex(T.label, "p-interface", T.id, "7", "aai-node-type", "p-interface", "interface-name", "p-interface-2");
        Vertex pInterface3 = graph.addVertex(T.label, "p-interface", T.id, "8", "aai-node-type", "p-interface", "interface-name", "p-interface-3");
        Vertex pInterface4 = graph.addVertex(T.label, "p-interface", T.id, "12", "aai-node-type", "p-interface", "interface-name", "p-interface-4");
        Vertex pnf1 = graph.addVertex(T.label, "pnf", T.id, "9", "aai-node-type", "pnf", "pnf-name", "pnf1name");
        Vertex pnf2 = graph.addVertex(T.label, "pnf", T.id, "10", "aai-node-type", "pnf", "pnf-name", "pnf2name");

        Vertex lInterface1 = graph.addVertex(T.label, "l-interface", T.id, "11", "aai-node-type", "l-interface", "interface-name", "l-interface-1");

        rules.addEdge(gts, logicalLink2, logicalLink1);
        rules.addEdge(gts, logicalLink2, pInterface1);
        rules.addEdge(gts, logicalLink2, pInterface2);
        rules.addEdge(gts, logicalLink2, pInterface3);

        rules.addTreeEdge(gts, pInterface1, pnf1);
        rules.addTreeEdge(gts, pInterface2, pnf2);
        rules.addTreeEdge(gts, pInterface3, pnf2);
        rules.addTreeEdge(gts, pInterface4, pnf1); //false

        rules.addEdge(gts, logicalLink2, lInterface1);//false

        expectedResult.add(pnf1);
        expectedResult.add(pInterface1);
        //expectedResult.add(logicalLink1);
        expectedResult.add(pInterface2);
        expectedResult.add(pInterface3);
        expectedResult.add(pnf2);
        expectedResult.add(logicalLink2);
        
    }

    @Test
    public void run() {
        super.run();
        
        Tree tree = treeList.get(0); //pnf1
        
        Vertex l1 = graph.traversal().V().has("aai-node-type","logical-link").has("link-name","logical-link-1").next();
        
        Vertex l2 = graph.traversal().V().has("aai-node-type","logical-link").has("link-name","logical-link-2").next();
        Vertex pInt1 = graph.traversal().V().has("aai-node-type","p-interface").has("interface-name","p-interface-1").next();
        Vertex pInt2 = graph.traversal().V().has("aai-node-type","p-interface").has("interface-name","p-interface-2").next();
        Vertex pInt3 = graph.traversal().V().has("aai-node-type","p-interface").has("interface-name","p-interface-3").next();
        Vertex pInt4 = graph.traversal().V().has("aai-node-type","p-interface").has("interface-name","p-interface-4").next();
        Vertex pnf1 = graph.traversal().V().has("aai-node-type","pnf").has("pnf-name","pnf1name").next();
        Vertex pnf2 = graph.traversal().V().has("aai-node-type","pnf").has("pnf-name","pnf2name").next();
        
        /*
         * Expected:
         * {v[9]                                -> pnf1
                {v[6]                               -> pInt1
                    {v[5]                               -> l2
                        {
                            v[6]                            -> pInt1
                                {v[9]={}},                      -> pnf1
                            v[7]=                           -> pInt2
                                {v[10]={}},                     -> pnf2
                            v[8]=                           -> pInt3
                                {v[10]={}}                      -> pnf2
                        }
                    }
                }
            }
         */
        
        assertTrue(tree.containsKey(pnf1));
        assertFalse(((Tree) tree.get(pnf1)).containsKey(pInt4)); //pInt4 is not connected to any logical-link
        assertTrue(((Tree) tree.get(pnf1)).containsKey(pInt1));
        assertTrue(tree.getObjectsAtDepth(3).contains(l2));
        assertTrue(tree.getObjectsAtDepth(4).contains(pInt1));
        assertTrue(tree.getObjectsAtDepth(5).contains(pnf1));
        assertTrue(tree.getObjectsAtDepth(4).contains(pInt2));
        assertTrue(tree.getObjectsAtDepth(4).contains(pInt3));
        assertTrue(tree.getObjectsAtDepth(5).contains(pnf2));
    }
    
    @Override
    protected String getQueryName() {
        return "getRouterRoadmTailSummary";
    }

    @Override
    protected void addStartNode(GraphTraversal<Vertex, Vertex> g) {
        g.has("aai-node-type", "pnf").has("pnf-name", "pnf1name");
    }

    @Override
    protected void addParam(Map<String, Object> params) {
        return;
    }

}
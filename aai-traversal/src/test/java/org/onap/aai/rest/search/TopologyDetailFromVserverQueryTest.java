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

public class TopologyDetailFromVserverQueryTest extends QueryTest {
    public TopologyDetailFromVserverQueryTest() throws AAIException, NoEdgeRuleFoundException {
        super();
    }

    @Test
    public void run() {
        super.run();
    }

    @Override
    protected void createGraph() throws AAIException, NoEdgeRuleFoundException {
        // set up test graph
        Vertex genericvnf = graph.addVertex(T.label, "generic-vnf", T.id, "0", "aai-node-type",
            "generic-vnf", "vnf-id", "vnfid0", "nf-type", "sample-nf-type");
        Vertex cloudregion = graph.addVertex(T.label, "cloud-region", T.id, "1", "aai-node-type",
            "cloud-region", "cloud-region-id", "regionid0", "cloud-owner", "cloudOwnername0");
        Vertex tenant = graph.addVertex(T.label, "tenant", T.id, "2", "aai-node-type", "tenant",
            "tenant-id", "tenantid0", "tenant-name", "tenantName0");
        Vertex vserver = graph.addVertex(T.label, "vserver", T.id, "3", "aai-node-type", "vserver",
            "vserver-id", "vserverid0");
        Vertex linterface =
            graph.addVertex(T.label, "l-interface", T.id, "4", "aai-node-type", "l-interface",
                "l-interface-id", "l-interface-id0", "l-interface-name", "l-interface-name0");
        Vertex l3inter1ipv4addresslist = graph.addVertex(T.label, "interface-ipv4-address-list",
            T.id, "5", "aai-node-type", "l3-interface-ipv4-address-list",
            "l3-interface-ipv4-address", "l3-interface-ipv4-address-0");
        Vertex subnet4 = graph.addVertex(T.label, "subnet", T.id, "6", "aai-node-type", "subnet",
            "subnet-id", "subnet4-id-0");
        Vertex l3network4 = graph.addVertex(T.label, "l3-network", T.id, "7", "aai-node-type",
            "l3-network", "network-id", "network4-id-0", "network-name", "network4-name0");
        Vertex l3inter1ipv6addresslist = graph.addVertex(T.label, "l3-interface-ipv6-address-list",
            T.id, "8", "aai-node-type", "l3-interface-ipv6-address-list",
            "l3-interface-ipv6-address", "l3-interface-ipv6-address-0");
        Vertex subnet6 = graph.addVertex(T.label, "subnet", T.id, "9", "aai-node-type", "subnet",
            "subnet-id", "subnet6-id-0");
        Vertex l3network6 = graph.addVertex(T.label, "l3-network", T.id, "10", "aai-node-type",
            "l3-network", "network-id", "network6-id-0", "network-name", "network6-name0");
        Vertex platform = graph.addVertex(T.label, "platform", T.id, "11", "aai-node-type",
            "platform", "platform-name", "platform0");
        Vertex lineofbusiness = graph.addVertex(T.label, "line-of-business", T.id, "12",
            "aai-node-type", "line-of-business", "line-of-business-name", "business0");
        Vertex servinst = graph.addVertex(T.label, "service-instance", T.id, "13", "aai-node-type",
            "service-instance", "service-instance-id", "servInstId0", "service-type", "servType0");
        Vertex owningentity = graph.addVertex(T.label, "owning-entity", T.id, "14", "aai-node-type",
            "owning-entity", "owning-entity-id", "entityId0", "owning-entity-name", "entityName0");
        Vertex project = graph.addVertex(T.label, "project", T.id, "15", "aai-node-type", "project",
            "project-name", "project0");
        Vertex vnfc = graph.addVertex(T.label, "vnfc", T.id, "16", "aai-node-type", "vnfc",
            "vnfc-name", "vnfc0", "nfc-naming-code", "namingCode0", "nfc-function", "function0");
        Vertex pserver = graph.addVertex(T.label, "pserver", T.id, "17", "aai-node-type", "pserver",
            "hostname", "pservername1");
        Vertex complex = graph.addVertex(T.label, "pserver", T.id, "18", "aai-node-type", "complex",
            "physical-location-id", "locationId", "physical-location-type", "locationType",
            "physical-location-id", "locationId", "city", "cityName", "state", "stateName",
            "postal-code", "zip", "country", "countryName");
        Vertex availabilityZone =
            graph.addVertex(T.label, "availability-zone", T.id, "19", "aai-node-type",
                "availability-zone", "availability-zone-name", "azName0", "hypervisor-type", "ht0");
        Vertex virtualDataCenter = graph.addVertex(T.label, "virtual-data-center", T.id, "20",
            "aai-node-type", "virtual-data-center", "vdc-id", "vdcId0", "vdc-name", "vdcName0");
        Vertex volumeGroup =
            graph.addVertex(T.label, "volume-group", T.id, "21", "aai-node-type", "volume-group",
                "volume-group-id", "vgId0", "volume-group-name", "vgName0", "vnf-type", "vnfType0");
        Vertex image = graph.addVertex(T.label, "image", T.id, "22", "aai-node-type", "image",
            "image-id", "imageId0", "image-name", "imageName0", "image-os-distro", "imageOsDistro0",
            "image-os-version", "imageOsVersion0", "image-selflink", "imageSl0");
        Vertex flavor = graph.addVertex(T.label, "flavor", T.id, "23", "aai-node-type", "flavor",
            "flavor-id", "flavorId0", "flavor-name", "flavorName0", "flavor-selflink", "flavorSl0");
        Vertex vfModule = graph.addVertex(T.label, "vf-module", T.id, "24", "aai-node-type",
            "vf-module", "vf-module-id", "vfmId0", "is-base-vf-module", "true");
        Vertex genericvnf2 = graph.addVertex(T.label, "generic-vnf", T.id, "25", "aai-node-type",
            "generic-vnf", "vnf-id", "vnfid1", "nf-type", "sample-nf-type");
        Vertex vfModule2 = graph.addVertex(T.label, "vf-module", T.id, "26", "aai-node-type",
            "vf-module", "vf-module-id", "vfmId1", "is-base-vf-module", "true");

        GraphTraversalSource g = graph.traversal();

        rules.addTreeEdge(g, tenant, cloudregion);
        rules.addTreeEdge(g, vserver, tenant);
        rules.addEdge(g, pserver, vserver);
        rules.addEdge(g, complex, pserver);
        rules.addEdge(g, availabilityZone, pserver);
        rules.addEdge(g, genericvnf, vserver);
        rules.addTreeEdge(g, genericvnf, vfModule); // related to the genericvnf only, not the
                                                    // vserver
        rules.addEdge(g, genericvnf, platform);
        rules.addEdge(g, genericvnf, lineofbusiness);
        rules.addEdge(g, genericvnf, vnfc);
        rules.addEdge(g, genericvnf, servinst);
        rules.addEdge(g, virtualDataCenter, genericvnf);
        rules.addEdge(g, volumeGroup, genericvnf);
        rules.addEdge(g, owningentity, servinst);
        rules.addEdge(g, project, servinst);
        rules.addTreeEdge(g, linterface, vserver);
        rules.addTreeEdge(g, l3inter1ipv4addresslist, linterface);
        rules.addEdge(g, l3inter1ipv4addresslist, subnet4);
        rules.addTreeEdge(g, l3network4, subnet4);
        rules.addTreeEdge(g, l3inter1ipv6addresslist, linterface);
        rules.addEdge(g, l3inter1ipv6addresslist, subnet6);
        rules.addTreeEdge(g, l3network6, subnet6);
        rules.addEdge(g, image, vserver);
        rules.addEdge(g, flavor, vserver);
        rules.addTreeEdge(g, genericvnf2, vfModule2);
        rules.addEdge(g, vserver, vfModule2);

        expectedResult.add(vserver);
        expectedResult.add(linterface);
        expectedResult.add(l3inter1ipv4addresslist);
        expectedResult.add(subnet4);
        expectedResult.add(l3network4);
        expectedResult.add(l3inter1ipv6addresslist);
        expectedResult.add(subnet6);
        expectedResult.add(l3network6);
        expectedResult.add(tenant);
        expectedResult.add(cloudregion);
        expectedResult.add(pserver);
        expectedResult.add(complex);
        expectedResult.add(availabilityZone);
        expectedResult.add(genericvnf);
        expectedResult.add(vfModule); // related to the genericvnf only, not the vserver
        expectedResult.add(platform);
        expectedResult.add(lineofbusiness);
        expectedResult.add(virtualDataCenter);
        expectedResult.add(volumeGroup);
        expectedResult.add(owningentity);
        expectedResult.add(project);
        expectedResult.add(vnfc);
        expectedResult.add(image);
        expectedResult.add(flavor);
        expectedResult.add(vfModule2); // related to the vserver
    }

    @Override
    protected String getQueryName() {
        return "topology-detail-fromVserver";
    }

    @Override
    protected void addStartNode(GraphTraversal<Vertex, Vertex> g) {
        g.has("aai-node-type", "cloud-region").has("cloud-region-id", "regionid0")
            .has("cloud-owner", "cloudOwnername0").in("org.onap.relationships.inventory.BelongsTo")
            .has("aai-node-type", "tenant").has("tenant-id", "tenantid0")
            .in("org.onap.relationships.inventory.BelongsTo").has("aai-node-type", "vserver")
            .has("vserver-id", "vserverid0");
    }

    @Override
    protected void addParam(Map<String, Object> params) {
        return;
    }
}

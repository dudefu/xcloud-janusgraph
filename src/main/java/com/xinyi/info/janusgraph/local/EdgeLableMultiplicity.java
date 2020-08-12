package com.xinyi.info.janusgraph.local;

import org.janusgraph.core.*;
import org.janusgraph.core.schema.JanusGraphManagement;

public class EdgeLableMultiplicity {

	public static void main(String[] args) throws InterruptedException {
		JanusGraph janusGraph = JanusGraphFactory.open("conf/janusgraph-hbase-es.properties");
		JanusGraphManagement mgmt = janusGraph.openManagement();
		EdgeLabel follow = mgmt.makeEdgeLabel("ss11").multiplicity(Multiplicity.MULTI).make();
		PropertyKey birthDate = mgmt.makePropertyKey("birthDate11").dataType(Long.class).cardinality(Cardinality.SINGLE).make();
		mgmt.addProperties(follow,birthDate);
		System.out.println(mgmt.printSchema());
		mgmt.commit();
		
		
		
	}
}

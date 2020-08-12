package com.xinyi.info.janusgraph.remote.connect;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Anlw
 *`
 * @date May 31, 2019
 */
public class RemoteGraphConnection {

	private static final Logger LOGGER = LoggerFactory.getLogger(RemoteGraphConnection.class);

	private static volatile GraphTraversalSource g = null;

	private static final Class<RemoteGraphConnection> CLASS_LOCK = RemoteGraphConnection.class;

	private RemoteGraphConnection() {
	}

	// using the remote graph for queries
	public static GraphTraversalSource getGraphTraversalSource() throws Exception {

		LOGGER.info("open graph...");
		synchronized (CLASS_LOCK) {
			if (g == null) {
				Configuration conf = new PropertiesConfiguration("conf/remote-graph.properties");
				g = AnonymousTraversalSource.traversal().withRemote(conf);
				return g;
			}
		}
		return g;
	}

	public static void closeGraphTraversalSource() throws Exception {
		LOGGER.info("close graph...");
		try {
			if (g != null) {
				// this closes the remote, no need to close the empty graph
				g.close();
			}
		} finally {
			g = null;
		}
	}
}

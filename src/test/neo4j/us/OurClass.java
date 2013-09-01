package test.neo4j.us;

import java.io.File;
import java.io.IOException;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.FileUtils;

public class OurClass {

	private GraphDatabaseService db;
	private String path;

	private OurClass(GraphDatabaseService mGraphDB) {
		db = mGraphDB;
		TransactionFactory.setInstance(db);
		registerShutdownHook(db);
	}

	public OurClass(String mPath) {
		this(new GraphDatabaseFactory().newEmbeddedDatabase(mPath));
		path = mPath;
	}

	private static void registerShutdownHook(final GraphDatabaseService graphDb) {
		// Registers a shutdown hook for the Neo4j instance so that it
		// shuts down nicely when the VM exits (even if you "Ctrl-C" the
		// running application).
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}

	private void clearDb() {
		try {
			FileUtils.deleteRecursively(new File(path));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void run() throws Exception {
		Node paulAtreides = null;
		Node jessicaAtreides = null;
		
		try (MyTransaction tx = TransactionFactory.beginTx()) {
			paulAtreides = db.createNode();
			paulAtreides.setProperty("name", "Paul Atreides");
			jessicaAtreides = db.createNode();
			jessicaAtreides.setProperty("name", "Jessica Atreides");

			paulAtreides.createRelationshipTo(jessicaAtreides, DynamicRelationshipType.withName("SON-OF"));
			
			tx.success();
		}

		try (MyTransaction tx = TransactionFactory.beginTx()){ 
			for (Node node : db.getAllNodes()) {
				if(node.hasProperty("name")) {
					System.out.println(node.getProperty("name"));
				}
			} 
			tx.success();
		}
		
		db.shutdown();
		
//		clearDb();
	}
}

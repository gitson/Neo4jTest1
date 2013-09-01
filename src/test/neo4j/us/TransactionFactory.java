package test.neo4j.us;

import org.neo4j.graphdb.GraphDatabaseService;

public class TransactionFactory {
	private static volatile GraphDatabaseService db;
	
	private TransactionFactory() {
		
	}
	
	public static synchronized void setInstance(GraphDatabaseService mDb) {		
		db = mDb;
	}
	
	public static synchronized GraphDatabaseService getInstance() {
		return db;
	}
	
	public static MyTransaction beginTx() {
		return new MyTransaction(db.beginTx());
	}
}

package test.neo4j.us;

import org.neo4j.graphdb.Lock;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.PropertyContainer;

public class MyTransaction implements IMyTransaction {
	Transaction tx;
	public MyTransaction(Transaction mTx) {
		tx = mTx;
	}
	
	@Override
	public Lock acquireReadLock(PropertyContainer arg0) {
		return tx.acquireReadLock(arg0);
	}

	@Override
	public Lock acquireWriteLock(PropertyContainer arg0) {
		return tx.acquireWriteLock(arg0);
	}

	@Override
	public void failure() {
		tx.failure();
	}

	@Override
	public void finish() {
		tx.finish();
	}

	@Override
	public void success() {
		tx.success();
	}

	@Override
	public void close() throws Exception {
		tx.finish();
	}
}

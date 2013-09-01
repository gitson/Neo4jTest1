package test.neo4j.us;

import org.neo4j.graphdb.Transaction;

public interface IMyTransaction extends Transaction, AutoCloseable {
}

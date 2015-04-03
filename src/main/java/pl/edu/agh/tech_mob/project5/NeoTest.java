package pl.edu.agh.tech_mob.project5;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class NeoTest {

	private static final String DB_PATH = "E:\\Programy\\JAVA\\tech_mob_neo4j_db";

	public static void main(String[] args) {
		GraphDatabaseService graphDb;
		Node firstNode;
		Node secondNode;
		Relationship relationship;

		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);

		try (Transaction tx = graphDb.beginTx()) {
			firstNode = graphDb.createNode();
			firstNode.setProperty("message", "Hello, ");
			secondNode = graphDb.createNode();
			secondNode.setProperty("message", "World!");
			relationship = firstNode.createRelationshipTo(secondNode,
					DynamicRelationshipType.withName("LOOL"));
			relationship.setProperty("message", "brave Neo4j ");
			tx.success();
		}

		try (Transaction tx = graphDb.beginTx()) {
			for (Node node : graphDb.getAllNodes()) {
				System.out.println(node);
			}
		}

		clearDb(graphDb);

		graphDb.shutdown();
	}

	private static void clearDb(GraphDatabaseService graphDb) {
		try (Transaction tx = graphDb.beginTx()) {
			for (Node node : graphDb.getAllNodes()) {
				for (Relationship rel : node.getRelationships()) {
					rel.delete();
				}
				// lol
				node.delete();
			}
			tx.success();
		}
	}
}

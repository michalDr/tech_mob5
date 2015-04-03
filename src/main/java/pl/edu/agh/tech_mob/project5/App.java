package pl.edu.agh.tech_mob.project5;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import pl.edu.agh.tech_mob.project5.files.PointsFile;
import pl.edu.agh.tech_mob.project5.files.RelationshipsFile;

public class App {
	private static final String DB_PATH = "E:\\Programy\\JAVA\\tech_mob_neo4j_db";

	private static GraphDatabaseService graphDb;

	public static void main(String[] args) {
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);

		PointsFile pf = new PointsFile();
		pf.readFile("points_file.txt");
		RelationshipsFile rf = new RelationshipsFile(graphDb, pf);
		try {
			boolean neof = true;
			rf.openFile("relationships_file.txt");
			long relationshipNumber = 0;
			while (neof) {
				neof = rf.createRelationShipsFromOneLine("Relationship-"
						+ String.valueOf(relationshipNumber));
				relationshipNumber++;
			}
			rf.closeFile();
		} catch (FileNotFoundException e) {
			System.err.println("Could not find file!");
		} catch (IOException e) {
			System.err.println("I/O error!");
		}
		// int maxRelationships = 0;
		// List<Node> maxNodes = new ArrayList<>();
		graphDb.shutdown();
	}
}

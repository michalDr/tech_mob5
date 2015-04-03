package pl.edu.agh.tech_mob.project5.files;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import pl.edu.agh.tech_mob.project5.files.contents.Point;

public class RelationshipsFile {

	private BufferedReader br;
	private PointsFile pointsFile;
	private GraphDatabaseService graphDb;

	public RelationshipsFile(GraphDatabaseService graphDb, PointsFile pf) {
		this.graphDb = graphDb;
		this.pointsFile = pf;
		System.out.println("RelationshipFile object created.");
	}

	public void openFile(String fileName) throws FileNotFoundException {
		System.out.println("Opening file " + fileName + ".");
		br = new BufferedReader(new FileReader(fileName));
	}

	public boolean createRelationShipsFromOneLine(String relationshipName)
			throws IOException {
		System.out.println("Creating relationships named: " + relationshipName
				+ ".");
		String line = null;
		List<Long> columns = new ArrayList<>();
		List<Long> rows = new ArrayList<>();
		List<Point> points = new ArrayList<>();
		Point point;
		line = br.readLine();
		Node node;
		if (line != null) {
			createListsFromString(columns, rows, line);
			System.out.println(columns);
			System.out.println(rows);
			for (Long col : columns) {
				for (Long r : rows) {
					point = pointsFile.find(r, col);
					if (point != null) {
						points.add(point);
					}
				}
			}

			try (Transaction tx = graphDb.beginTx()) {
				System.out.println("Collecting points.");
				for (Point p : points) {
					if (p.isAddedToDb()) {
						p.setNode(graphDb.getNodeById(p.getNodeId()));
					} else {
						node = graphDb.createNode();
						node.setProperty("value", p.getValue());
						p.setNodeNodeIdAndAddedToDb(node);
					}
				}
				System.out.println("Creating relationships.");
				for (Point p : points) {
					for (Point pp : points) {
						if (pp != p) {
							p.getNode().createRelationshipTo(
									pp.getNode(),
									DynamicRelationshipType
											.withName(relationshipName));
						}
					}
				}
				tx.success();
			}

		} else {
			return false;
		}
		return true;

	}

	public void closeFile() throws IOException {
		System.out.println("Closing file.");
		br.close();
	}

	public void setPointsFile(PointsFile pf) {
		System.out.println("Setting points file.");
		this.pointsFile = pf;
	}

	private void createListsFromString(List<Long> columns, List<Long> rows,
			String strigData) {
		System.out.println("Creating list of numbers from string line.");
		String stringRows;
		String stringColumns;
		int divisionIndex;
		divisionIndex = strigData.indexOf("|");
		stringRows = strigData.substring(0, divisionIndex - 1);
		stringColumns = strigData.substring(divisionIndex + 2);
		getNumbersFromString(stringColumns, columns);
		getNumbersFromString(stringRows, rows);

	}

	private void getNumbersFromString(String stringNumbers,
			List<Long> numbersList) {
		System.out.println("Getting numbers from string.");
		boolean eol = false;
		int spaceIndex;
		Long number;
		while (!eol) {
			if (stringNumbers.contains(" ")) {
				spaceIndex = stringNumbers.indexOf(" ");
			} else {
				spaceIndex = stringNumbers.length() - 1;
				eol = true;
			}
			if (!eol) {
				number = Long.valueOf(stringNumbers.substring(0, spaceIndex));
			} else {
				number = Long.valueOf(stringNumbers);
			}
			numbersList.add(number);
			if (!eol) {
				stringNumbers = stringNumbers.substring(spaceIndex + 1);
			}
		}
	}

	public void setGraphDb(GraphDatabaseService graphDb) {
		System.out.println("Setting graphDb");
		this.graphDb = graphDb;
	}
}

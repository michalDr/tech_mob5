package pl.edu.agh.tech_mob.project5.files.contents;

import org.neo4j.graphdb.Node;

public class Point {

	private long row;
	private long column;
	private float value;
	private Long nodeId = null;
	private boolean addedToDb = false;

	private Node node;

	public boolean isAddedToDb() {
		return addedToDb;
	}

	public void setAddedToDb(boolean addedToDb) {
		this.addedToDb = addedToDb;
	}

	public Point(Long row, Long column, float value) {
		this.column = column;
		this.row = row;
		this.value = value;
		this.addedToDb = false;
		this.setNodeId(null);

	}

	public Long getRow() {
		return row;
	}

	public void setRow(Long row) {
		this.row = row;
	}

	public Long getColumn() {
		return column;
	}

	public void setColumn(Long column) {
		this.column = column;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Point [row=" + row + ", column=" + column + ", value=" + value
				+ ", addedToDb=" + addedToDb + "]";
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public void setNodeNodeIdAndAddedToDb(Node node) {
		this.node = node;
		this.nodeId = node.getId();
		this.addedToDb = true;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

}

package pl.edu.agh.tech_mob.project5.files.contents;

import java.util.List;

public class Relationship {

	private List<Long> rows;
	private List<Long> columns;

	public List<Long> getRows() {
		return rows;
	}

	public void setRows(List<Long> rows) {
		this.rows = rows;
	}

	public List<Long> getColumns() {
		return columns;
	}

	public void setColumns(List<Long> columns) {
		this.columns = columns;
	}

	public Relationship(List<Long> rows, List<Long> columns) {
		super();
		this.rows = rows;
		this.columns = columns;
	}

	public void addRow(Long row) {
		this.rows.add(row);
	}

	public void addColumn(Long column) {
		this.columns.add(column);
	}

}

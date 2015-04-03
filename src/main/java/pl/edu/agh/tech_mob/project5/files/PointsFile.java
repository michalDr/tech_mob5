package pl.edu.agh.tech_mob.project5.files;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.tech_mob.project5.files.contents.Point;

public class PointsFile {

	private List<Point> points = new ArrayList<>();

	public void readFile(String fileName) {
		System.out.println("PointsFile - readling from file.");
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line = br.readLine();
			line = br.readLine();
			long x = 0, y = 0;
			int spaceIndex;
			String subLine;
			String value;
			Point point;

			while (line != null) {

				spaceIndex = line.indexOf(" ");
				subLine = line.substring(spaceIndex + 1);

				boolean eol = false;
				x = 0;
				while (!eol) {
					if (subLine.contains(" ")) {
						spaceIndex = subLine.indexOf(" ");
					} else {
						spaceIndex = subLine.length();
						eol = true;
					}
					value = subLine.substring(0, spaceIndex);
					point = new Point(y, x, Float.valueOf(value));
					points.add(point);
					if (!eol) {
						subLine = subLine.substring(spaceIndex + 1);
					}
					x++;
				}
				line = br.readLine();
				y++;
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not found!");
		} catch (IOException e) {
			System.err.println("I/O error!");
		}

		printPoints();
	}

	private void printPoints() {
		System.out.println("PointsFile - printing points");
		for (Point p : points) {
			System.out.println(p);
		}
	}

	public Point find(long row, long column) {
		System.out.println("PointsFile - searching point.");
		for (Point p : points) {
			if (p.getColumn() == column && p.getRow() == row) {
				return p;
			}
		}
		return null;
	}
}

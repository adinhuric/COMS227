package hw3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import api.GridCell;
import api.Line;
import api.Location;
import api.StringUtil;

/**
 * Utility class with methods to help initializing a Lines game from a string
 * descriptor, and for creating a collection of games from a file containing
 * descriptors.
 */

public class Util {

	/**
	 * Given a 2d array of GridCell, constructs an array of Line objects based on
	 * the information in the grid. Specifically, for each pair of endpoints with
	 * matching ids, a corresponding Line object is constructed with that id and
	 * with the given endpoints. The order of the endpoints (endpoint 0 vs endpoint
	 * 1) is unspecified. If there are more than two endpoints with the same id, or
	 * if there is only one endpoint with the given id, this method returns null. No
	 * other error-checking is performed (e.g. there may be middle cells with no
	 * matching endpoint, or the game may be unsolvable for other reasons).
	 * <p>
	 * Note that in general the id for a Line will <em>not</em> be the same as its
	 * index in the returned array.
	 * 
	 * @param grid a 2d array of GridCell
	 * @return array of Line objects based on the grid information
	 * 
	 *         Creates the end points and the matching ID from a given 2D array of
	 *         grid cell.
	 * @param grid a 2d array of GridCell
	 * @return array of lines that were made off of the grid
	 */
	public static ArrayList<Line> createLinesFromGrid(GridCell[][] grid) {

		ArrayList<Line> lines = new ArrayList<>();
		ArrayList<GridCell> endpoints = new ArrayList<>();
		ArrayList<Location> locations = new ArrayList<>();

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {

				if (grid[i][j].isEndpoint()) {
					endpoints.add(grid[i][j]);
					locations.add(new Location(i, j));
				}
			}

		}

		for (int i = 0; i < endpoints.size(); i++) {

			for (int j = 0; j < endpoints.size(); j++) {

				if (endpoints.get(i).getId() == endpoints.get(j).getId() && locations.get(i) != locations.get(j)) {

					lines.add(new Line(endpoints.get(i).getId(), locations.get(i), locations.get(j)));
					break;

				}

			}
		}

		return lines;
	}

	/**
	 * Reads the given file and constructs a list of LinesGame objects, one for each
	 * descriptor in the file. Descriptors in the file are separated by one or more
	 * blank lines, where a "blank line" consists of some amount of whitespace and a
	 * newline character. The file may have extra whitespace at the beginning, and
	 * it must always end with one or more blank lines. Invalid descriptors are
	 * ignored, so the method may return an empty list. (A descriptor is "invalid"
	 * if either createGridFromStringArray returns null, or createLinesFromGrid
	 * returns null.)
	 * 
	 * @param filename name of the file to read
	 * @return list of LinesGame objects created from the valid descriptors in the
	 *         file
	 * @throws FileNotFoundException if a file with the given name can't be opened
	 * 
	 * 
	 * 
	 *                               Reads a text file and creates a list of lines
	 *                               game objects.
	 * @param filename text file that is used
	 * @return list of LinesGame objects that are made
	 * @throws FileNotFoundException if a file with the given name can't be opened
	 */
	public static ArrayList<LinesGame> readFile(String filename) throws FileNotFoundException {
		File file = new File(filename);
		Scanner scnr = new Scanner(file);
		String string = "";
		ArrayList<LinesGame> gameList = new ArrayList<>();
		ArrayList<String> stringArray = new ArrayList<>();

		while (scnr.hasNextLine()) {
			string = scnr.nextLine();

			if (string.equals("")) {

				String[] list = new String[stringArray.size()];
				for (int i = 0; i < stringArray.size(); i++) {
					list[i] = stringArray.get(i);
					stringArray.remove(i);
				}
				gameList.add(new LinesGame(list));

			} else {
				stringArray.add(string);
			}

		}

		return gameList;

	}

	/**
	 * Determines whether a line between two diagonally adjacent locations would
	 * cross any existing line in the given list. The check is based on the
	 * following test:
	 * <ul>
	 * <li>Let (rOld, cOld) denote the current cell location and let (rNew, cNew)
	 * denote the new cell location.
	 * <li>Let rDiff = rNew - rOld and cDiff = cNew - cOld.
	 * <li>If either rDiff or cDiff does not have absolute value 1, then the two
	 * positions are not diagonally adjacent and the method returns false
	 * <li>If the two positions are diagonally adjacent, then p0 = (rOld, cOld +
	 * cDiff) and p1 = (rOld + rDiff, cOld) always form the opposite diagonal (i.e.,
	 * the line that could potentially be crossed).
	 * <li>The method returns true if p0 and p1 occur consecutively, in either
	 * order, in any existing line in the given array.
	 * </ul>
	 * 
	 * @param lines      list of Line objects
	 * @param currentLoc any Location
	 * @param newLoc     any Location
	 * @return true if the two locations are diagonally adjacent and some existing
	 *         line crosses the opposite diagonal
	 * 
	 *         Checks to see if there is a line blocking the new location cell you
	 *         are trying to reach.
	 * @param lines      list of line objects
	 * @param currentLoc current location
	 * @param newLoc     location you drag to
	 * @return true if there is a line blocking you from reaching the new location
	 */
	public static boolean checkForPotentialCrossing(ArrayList<Line> lines, Location currentLoc, Location newLoc) {
		Location p1 = new Location(currentLoc.row(), newLoc.col());
		Location p2 = new Location(newLoc.row(), currentLoc.col());

		for (Line l : lines) {

			ArrayList<Location> temp = l.getCells();

			for (int i = 0; i < temp.size() - 1; i++) {
				if ((temp.get(i).equals(p1) && temp.get(i + 1).equals(p2))
						|| (temp.get(i).equals(p2) && temp.get(i + 1).equals(p1))) {

					return true;

				}
			}
		}
		return false;
	}

	/**
	 * Determines whether any line in the given array already contains the segment
	 * between the given locations; that is, whether the two given locations occur
	 * consecutively, in either order, in any of the given lines.
	 * 
	 * @param lines      any array of lines
	 * @param currentLoc any position object
	 * @param newLoc     any position object
	 * @return true if the two locations occur consecutively in some line
	 * 
	 *         Checks if a line has passed through from the current location to the
	 *         new location.
	 * @param array   of line objects
	 * @param current location
	 * @param nnew    location you are trying to make a line to
	 * @return true if there is a line segment
	 */
	public static boolean checkForLineSegment(ArrayList<Line> lines, Location currentLoc, Location newLoc) {
		for (Line l : lines) {
			if (l.getCells().contains(currentLoc) && l.getCells().contains(newLoc)) {
				return true;
			}
		}
		return false;

	}

}

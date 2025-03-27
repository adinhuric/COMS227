package hw3;

import java.util.ArrayList;

import api.GridCell;
import api.Line;
import api.Location;
import api.StringUtil;

/**
 * Game state for a Lines game.
 */
public class LinesGame {

	// 2D array of the grid which is initialized in the constructor.
	private GridCell[][] grid;
	// Array list of all the lines which include end points and their ID.
	private ArrayList<Line> line;
	// Current line that is being used.
	private Line currentLine;
	// Amount of moves that the user has made.
	private int moveCount;

	/**
	 * Constructs a LinesGame from the given grid and Line list. This constructor
	 * does not do any error-checking to ensure that the grid and the Line array are
	 * consistent. Initially the current line is null.
	 * 
	 * @param givenGrid  a 2d array of GridCell
	 * @param givenLines list of Line objects
	 * 
	 * 
	 * 
	 *                   Creates the game from the grid and end points with their id
	 *                   which is given by the user.
	 * @param givenGrid  a 2d array of GridCell
	 * @param givenLines list of Line objects
	 */

	public LinesGame(GridCell[][] givenGrid, ArrayList<Line> givenLines) {
		grid = givenGrid;
		line = givenLines;
		currentLine = null;
	}

	/**
	 * Constructs a LinesGame from the given descriptor. Initially the current line
	 * is null.
	 * 
	 * @param descriptor array of strings representing initial state
	 * 
	 * 
	 *                   Creates a line game from a array of strings that contain
	 *                   the end points from capital letters and their ID. Then it
	 *                   creates those lines and also creates the grid from the
	 *                   string array.
	 * @param descriptor array of strings representing initial state
	 */
	public LinesGame(String[] descriptor) {

		currentLine = null;
		ArrayList<Line> lines = new ArrayList<>();
		ArrayList<GridCell> endpoints = new ArrayList<>();
		ArrayList<Location> locations = new ArrayList<>();
		GridCell[][] grids = StringUtil.createGridFromStringArray(descriptor);

		for (int i = 0; i < descriptor.length; i++) {
			for (int j = 0; j < descriptor[0].length(); j++) {
				{
					char x = descriptor[i].charAt(j);

					for (char c : StringUtil.COLOR_CODES)
						if (c == x) {
							endpoints.add(grids[i][j]);
							locations.add(new Location(i, j));
							break;
						}

				}

			}

		}
		for (int i = 0; i < endpoints.size(); i++) {

			for (int j = 0; j < endpoints.size(); j++) {

				if (endpoints.get(i).getId() == endpoints.get(j).getId() && locations.get(i) != locations.get(j)) {
					if (!lines.contains(new Line(endpoints.get(i).getId(), locations.get(i), locations.get(j)))
							|| !lines
									.contains(new Line(endpoints.get(i).getId(), locations.get(j), locations.get(i)))) {

						lines.add(new Line(endpoints.get(i).getId(), locations.get(i), locations.get(j)));
						break;
					}

				}

			}
		}

		grid = grids;
		line = lines;
	}

	/**
	 * Returns the number of columns for this game.
	 * 
	 * @return width for this game
	 * 
	 * 
	 *         Returns the amount of columns of the grid of the game.
	 * @return columns for this game
	 */
	public int getWidth() {
		return grid[0].length;
	}

	/**
	 * Returns the number of rows for this game.
	 * 
	 * @return height for this game
	 * 
	 * 
	 *         Returns how many rows the grid of the game has.
	 * @return amount of rows of the grid for the game
	 */
	public int getHeight() {
		return grid.length;
	}

	/**
	 * Returns the current cell for this game, possibly null. The current cell is
	 * just the last location, if any, in the current line, if there is one. Returns
	 * null if the current line is null or if the current line has an empty list of
	 * locations.
	 * 
	 * @return current cell for this game, or null
	 * 
	 * 
	 *         Returns the location that the user is currently on. It does this by
	 *         getting the last location in the list of cells of the current line.
	 *         Returns null if the current line is null.
	 * @return current cell for this game, or null
	 * 
	 */
	public Location getCurrentLocation() {
		if (currentLine == null || currentLine.getCells() == null) {
			return null;
		} else
			return currentLine.getLast();
	}

	/**
	 * Returns the id for the current line, or -1 if the current line is null.
	 * 
	 * @return id for the current line
	 * 
	 * 
	 *         Returns the ID of the current line. Returns -1 if the current line is
	 *         null.
	 * @return id for the current line
	 */
	public int getCurrentId() {
		return currentLine.getId();
	}

	/**
	 * Return this game's current line (which may be null).
	 * 
	 * @return current line for this game
	 * 
	 * 
	 *         Returns the current line of the game. Returns null if there is no
	 *         current line.
	 * @return current line of game
	 */
	public Line getCurrentLine() {
		return currentLine;
	}

	/**
	 * Returns a reference to this game's grid. Clients should not modify the array.
	 * 
	 * @return the game grid
	 * 
	 * 
	 *         Returns the grid of the game.
	 * @return grid of the line game
	 */
	public GridCell[][] getGrid() {
		return grid;
	}

	/**
	 * Returns the grid cell at the given position.
	 * 
	 * @param row given row
	 * @param col given column
	 * @return grid cell at (row, col)
	 * 
	 * 
	 *         Returns the cell of the grid with a given row and col.
	 * @param row
	 * @param column
	 * @return grid cell at (row, col)
	 */
	public GridCell getCell(int row, int col) {
		return grid[row][col];
	}

	/**
	 * Returns all Lines for this game. Clients should not modify the returned list
	 * or the Line objects.
	 * 
	 * @return list of lines for this game
	 * 
	 *         Returns all of the lines of the game which include end points and ID.
	 * @return list of lines for this game
	 */
	public ArrayList<Line> getAllLines() {
		return line;
	}

	/**
	 * Returns the total number of moves. A "move" means that a new Location was
	 * successfully added to the current line in addCell.
	 * 
	 * @return total number of moves so far in this game
	 * 
	 *         Returns the amount of moves that have been made in the game.
	 * @return total moves made in the game
	 */
	public int getMoveCount() {
		return moveCount;
	}

	/**
	 * Returns true if all lines are connected and all cells are at their maximum
	 * count.
	 * 
	 * @return true if all lines are complete and all cells are at max
	 * 
	 *         Checks to see if the game has been finished by seeing if the lines
	 *         are all connected and if all cells in the grid are at their max count
	 *         and if they are then it returns true.
	 * @return true if lines are all connected and cells are all at max count
	 */
	public boolean isComplete() {
		for (Line l : line) {
			if (!l.isConnected()) {
				return false;
			}
		}
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (!grid[i][j].maxedOut()) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Attempts to set the current line based on the given row and column. When
	 * using a GUI, this method is typically invoked when the mouse is pressed. If
	 * the current line is already non-null, this method does nothing. There are two
	 * possibilities:
	 * <ul>
	 * <li>Any endpoint can be selected. Selecting an endpoint clears the line
	 * associated with that endpoint's id, and all cells that were previously
	 * included in the line are decremented. The line then becomes the current line,
	 * and the endpoint is incremented and placed on the line's list of locations as
	 * its only element.
	 * <li>A non-endpoint cell can be selected if it is not a crossing and if it is
	 * the last cell in some line. That line then becomes the current line.
	 * </ul>
	 * If neither of the above conditions is met, or if the current line is
	 * non-null, this method does nothing.
	 * 
	 * @param row given row
	 * @param col given column
	 * 
	 * 
	 *            Start line is when you click your mouse. Start line only works if
	 *            the cell you click on is a end point of one the lines or if you
	 *            are starting from a non end point but it is the last cell of one
	 *            of the lines.
	 * @param row given row
	 * @param col given column
	 */
	public void startLine(int row, int col) {
		Location x = new Location(row, col);

		if (currentLine == null) {
			if (grid[row][col].isEndpoint()) {
				for (Line l : line) {
					if (l.getEndpoint(0).equals(x) || l.getEndpoint(1).equals(x)) {

						for (Location m : l.getCells()) {
							grid[m.row()][m.col()].decrement();

						}
						currentLine = l;
						currentLine.clear();
						grid[row][col].increment();
						currentLine.add(x);
						break;
					}

				}
			} else if (!grid[row][col].isCrossing()) {
				for (Line l : line) {

					if (grid[row][col].getId() == l.getId()) {
						if (l.getLast().equals(x)) {
							currentLine = l;
							break;
						}

					} else if (grid[row][col].isOpen()) {

						for (Location loc : l.getCells()) {

							if (loc.equals(x)) {
								currentLine = l;
								break;
							}
						}

					}
				}
			}
		}
	}

	/**
	 * Sets the current line to null. When using a GUI, this method is typically
	 * invoked when the mouse is released.
	 * 
	 * Sets the current line to null when the mouse is release.
	 */
	public void endLine() {
		currentLine = null;
	}

	/**
	 * Attempts to add a new cell to the current line. When using a GUI, this method
	 * is typically invoked when the mouse is dragged. In order to add a cell, the
	 * following conditions must be satisfied. Here the "current cell" is the last
	 * cell in the current line, and "new cell" is the cell at the given row and
	 * column: :
	 * <ol>
	 * <li>The current line is non-null
	 * <li>The current line is not connected
	 * <li>The given row and column are adjacent to the location of the current cell
	 * (horizontally, vertically, or diagonally) and not the same as the current
	 * cell
	 * <li>The count for the new cell is less than its max count
	 * <li>If the new cell is a MIDDLE or ENDPOINT, then its id matches the id for
	 * the current line
	 * <li>Adding the new cell will not cause the line to re-trace any existing line
	 * (according to the result of Util.checkForLineSegment)
	 * <li>Adding the new cell to the line would not cross any existing line
	 * (according to the result of Util.checkForPotentialCrossing)
	 * </ol>
	 * If the above conditions are met, a new Location at (row, col) is added to the
	 * current line and the cell count is incremented. Otherwise, the method does
	 * nothing. If a new location is added to the current line, the move counter is
	 * increased by 1.
	 * 
	 * @param row given row for the new cell
	 * @param col given column for the new cell
	 * 
	 * 
	 * 
	 *            Add cell is used when you drag your mouse. Add cell is used only
	 *            when the current line isn't null, current line isn't connected, if
	 *            the given location is adjacent to the location of the current
	 *            cell, the count of the new locations cell is less than the max,
	 *            the new locations cell is a middle or end point and the id matches
	 *            the current line, there is no potential crossings between lines,
	 *            and there wont be any re tracing between lines
	 * @param row for the new cell
	 * @param col for the new cell
	 */
	public void addCell(int row, int col) {
		Location loc = new Location(row, col);

		if (currentLine != null && !currentLine.isConnected()
				&& ((Math.abs(loc.row() - currentLine.getLast().row()) <= 1)
						&& (Math.abs(loc.col() - currentLine.getLast().col()) <= 1))
				&& !currentLine.getLast().equals(loc) && !grid[row][col].maxedOut()
				&& (grid[row][col].isMiddle() || grid[row][col].isEndpoint() || grid[row][col].isCrossing()
						|| grid[row][col].isOpen())
				&& grid[row][col].idMatches(currentLine.getId())
				&& !Util.checkForLineSegment(line, currentLine.getLast(), loc)
				&& !Util.checkForPotentialCrossing(line, currentLine.getLast(), loc)) {

			currentLine.add(loc);
			grid[row][col].increment();
			moveCount++;
		}
	}

	/**
	 * Returns a string representation of this game.
	 * 
	 * Creates a string of the game
	 */
	public String toString() {
		String result = "";
		result += "-----\n";
		result += StringUtil.originalGridToString(getGrid());
		result += "-----\n";
		result += StringUtil.currentGridToString(getGrid(), getAllLines());
		result += "-----\n";
		result += StringUtil.allLinesToString(getAllLines());
		Line ln = getCurrentLine();
		if (ln != null) {
			result += "Current line: " + ln.getId() + "\n";
		} else {
			result += "Current line: null\n";
		}
		return result;
	}

}

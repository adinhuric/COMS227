package hw2;
import api.ThrowType;
import static api.ThrowType.*;
/**
 * This class models a standard game of darts, keeping track of the scores,
 * whose turn it is, and how many darts the current player has remaining.
 * The number of starting points and the number of darts used in 
 * a player's turn are configurable.
 */
public class DartGame
{  
	private int player;
	private int darts;
	private int dartCount;
	private int player0Score;
	private int player1Score;
	private int beforeTurnScore;

	private boolean player0DoubleIn;
	private boolean player1DoubleIn;
	
	
/**
 * constructs a dart game with which player is starting, the score amount, and amount of starting darts
 * @param startingPlayer
 * 		determines which player will be going first
 * @param startingScore
 * 		determines the starting score of each player
 * @param startingDarts
 * 		determines the amount of starting darts
 */
	public DartGame(int startingPlayer, int startingScore, int startingDarts) {
		player = startingPlayer;
		darts = startingDarts;
		dartCount = startingDarts;
		player0Score = startingScore;
		player1Score = startingScore;
		beforeTurnScore = startingScore;
		player0DoubleIn = false;
		player1DoubleIn = false;
	}
	
/**
 * constructs a dart game with which player is starting, score is set to 301 and dart amount is set to 3
 * @param startingPlayer
 * 		determines who is the starting player
 */
	public DartGame(int startingPlayer) {
		player = startingPlayer;
		player1Score = 301;
		player0Score = 301;
		darts = 3;
		dartCount = darts;
	}
	

  
  /**
   * Returns the player whose turn it is.  (When the game is over,
   * this method always returns the winning player.)
   * @return
   *   current player (0 or 1)
   */
  public int getCurrentPlayer()
  {
    return player;
  }
  
  /**
   * Returns the score of the indicated player (0 or 1).  If
   * the argument is any value other than 0 or 1, the method returns
   * -1.
   * @param which
   *   indicator for which player (0 or 1)
   * @return
   *   score for the indicated player, or -1 if the argument is invalid
   */
  public int getScore(int which)
  {
	if (which == 0) {
		return player0Score;
	}
	else if (which == 1) {
		return player1Score;
	}
	else {
		return -1;
	}
  }
  
  /**
   * Returns the number of darts left in the current player's turn.
   * @return
   *   the number of darts left in the current player's turn
   */
  public int getDartCount()
  {
    return darts;
  }
  
  /**
   * Returns a string representation of the current game state.
   */
  public String toString()
  {
    String result = "Player 0: " + getScore(0) +
                    "  Player 1: " + getScore(1) +
                    "  Current: Player " + getCurrentPlayer() +
                    "  Darts: " + getDartCount();
    return result;
  }
  
  /**
   * determines if the player has doubled in or not
   * @param type
   * 	the type of throw they threw
   */
  private void doubleIn(ThrowType type) {
	  if((type == DOUBLE || type == INNER_BULLSEYE) && player == 0) {
		  player0DoubleIn = true;
	  }
	  else if ((type == DOUBLE || type == INNER_BULLSEYE) && player == 1){
		  player1DoubleIn = true;
	  }
  }
  
  /**
   * gets the value of the player that has doubled in
   * @return
   * 	returns the current players double in value
   */
  private boolean getDoubleIn() {
	  if (player == 0) {
		  return player0DoubleIn;
	  }
	  else {
		  return player1DoubleIn;
	  }
  }
  
  /**
   * does the action of throwing a dart and then adjusting the current players score
   * also it must subtract the current players dart from their turn and if they bust
   * @param type
   * 	type of throw the player did
   * @param number
   * 	the number that the players dart landed on
   */
  public void throwDart(ThrowType type, int number) {
	  

	  if (dartCount == darts && player == 0) {
		  beforeTurnScore = player0Score;
	  }
	  else if(dartCount == darts && player == 1){
		  beforeTurnScore = player1Score;
	  }
	  
	  if (isOver() == false) {
	  darts = darts - 1;
	  }
	  
	  if(!getDoubleIn()) {
		  doubleIn(type);
	  }
	  
	  if (getDoubleIn() && isOver() == false) {
		  if (player == 0 && player0DoubleIn == true) {
			  number = calcPoints(type,number);
			  adjustScore(number);
		  }
		  else if (player == 1 && player1DoubleIn == true)
		  {
			  number = calcPoints(type,number);
			  adjustScore(number);
		  }
	  }
	  if (darts == 0 && isOver() == false) {
		  switchPlayer();
	  }
	  
	  if (type != ThrowType.INNER_BULLSEYE && type != ThrowType.DOUBLE && player0Score == 0)
	  {
		  player0Score = beforeTurnScore;
		  switchPlayer();
	  }
	  else if (type != ThrowType.INNER_BULLSEYE && type != ThrowType.DOUBLE && player1Score == 0) {
		  player1Score = beforeTurnScore;
		  switchPlayer();
	  }
	  
  }
  
  /**
   * calculates the amount of points to subtract from the players score
   * @param type
   * 	the type of throw that the player did
   * @param number
   * 	the number the dart landed on
   * @return
   * 	returns the number after calculating how many points to subtract
   */
  public static int calcPoints(ThrowType type, int number) {
	  
	  if (type == ThrowType.MISS) {
		  number = 0;
	  }
	  if (type == ThrowType.SINGLE) {
		  number = number * 1;
	  }
	  if (type == ThrowType.DOUBLE) {
		  number = number * 2;
	  }
	  if (type == ThrowType.TRIPLE) {
		  number = number * 3;
	  }
	  if (type == ThrowType.OUTER_BULLSEYE) {
		  number = 25;
	  }
	  if (type == ThrowType.INNER_BULLSEYE) {
		  number = 50;
	  }
	  return number;
  }
  
  /** 
   * Reduces the score for the current player by the given amount. 
   * @param amount 
   *   number of points to subtract 
   */ 
  private void adjustScore(int amount) {
	  
	  
	  if (player == 0) {
		  player0Score -= amount;
	  }
	  else if (player == 1) {
		  player1Score -= amount;
	  }
	  if (player0Score < 0 || player0Score == 1) {
		  player0Score = beforeTurnScore;
		  switchPlayer();
	  }
	  else if (player1Score < 0 || player1Score == 1) {
		  player1Score = beforeTurnScore;
		  switchPlayer();
	  }
	  
  }
  
  /** 
   * Switches players and resets the dart count and  
   * the starting score for the current player's turn. 
   */ 
  private void switchPlayer() {
	  if (player == 0 && player0Score > 0) {
		  player = 1;
		  darts = dartCount;
	  }
	  else if(player == 1 && player1Score > 0) {
		  player = 0;
		  darts = dartCount;
	  }
  }
 

  /**
   * checks to see if the game is over by seeing if either players scores are at 0
   * @return
   * 	returns which player has won the game or if no one has won
   */
  public boolean isOver()
  {
	  if (player0Score == 0) {
		  return true;
	  }
	  else if (player1Score == 0) {
		  return true;
	  }
	  else return false;
  }
  
  /**
   * determines who has won the game
   * @return
   * 	returns which player has won the game and if neither have it returns the value -1
   */
  public int whoWon()
  {
	  if (player0Score == 0) {
		  return 0;	  
  }
	  else if (player1Score == 0) {
		  return 1;
  }
  else 
	  return -1;
}
}
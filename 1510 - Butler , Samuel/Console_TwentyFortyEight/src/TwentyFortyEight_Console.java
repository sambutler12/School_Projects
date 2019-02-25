/*This program is game of 2048. The goal of the game is to add up multiple tiles consisting of powers of 2 (2 , 4 , 8 , 16 , ....)
 * that will get you to a 2048 tile by adding two 1024 tiles together and thus winning the game. Once you have either gotten a 
 * 2048 tile or the board is full and there are no possible moves left you can start a new game and play again. If you do happen 
 * to get a 2048 tile then you have the choice of going into extended play to get a even higher score and see how far you 
 * can get before the board is full. You are able to move left,up,down,and right. There are also 5 other commands that would be 
 * helpful as well which are save(S) , get(G) , undo(Z) , quit(Q), and help(H) that could help you be successful during the game.
 * If you are not able to get a 2048 tile then you can try and get the highest score that you can which will be saved as you go on.
 * 
 * Author: Samuel Butler
 * 		   EECS 1510
 * Date:   April 12, 2018
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
public class TwentyFortyEight_Console
{
	static int[][] board       = new int[4][4];
	static int[][] lastBoard   = new int[4][4];
	static int[][] boardHolder = new int[4][4];
	static int[][] invalid     = new int[4][4];
	static int score           = 0;
	static int movesTotal      = 0;
	static int winCount        = 0;
	static int highScore       = 0;
	static int lastScore       = 0;
	static int undoCount       = 0;
	static int full            = 0;
	static int gamesPlayed     = 0;
	static int gamesWon        = 0;
	static int moves           = 0;
	static boolean validMove   = true;
	static boolean anotherGame = true;
		 								
	public static void main(String[] args) throws FileNotFoundException
	{
		/* The way my main works is putting it into a do..while loop so if the user quits it will generate a whole new board
		 * and let them start playing again and if they do win and do not want to go into extended play then it will set that while
		 * loop (variable anotherGame) to false and terminate. My main consist of keeping track of the high Score, quit, undo, save
		 * and load as well and prompting the user for a direction that they would like to go. Before anything ever really happens I generate the 
		 * high Score, if there hasn't been a high score yet, and the file has not been created then it will ignore that step and move on and
		 * set the high Score to 0. Also main will check to see if the board is and if it is and there are no possible moves it will return true
		 * that the board is full and display game over.
		 */
		do
		{
			int[][] zeroBoard   = new int[4][4];
			gamesPlayed++;
			boolean game = true;
			
			File file = new File("highScore.txt");
			try(Scanner input2 = new Scanner(file);)
			{
				if(file.exists())
				{
					highScore = input2.nextInt();
				}
			}
			catch(FileNotFoundException e)
			{
				// Will continue to the game loop and set the high Score equal to zero since there is no
				// high score currently saved
			}

			int count = 0;
			do
			{
				generateNumbers();
				count++;
			}while(count <=1);
			
			System.out.println("You can type H for the help bar and information about the game.");
			
			do
			{
				displayBoard(board);
				
				System.out.println();
				System.out.println("High Score: "+ highScore);
				System.out.print("Score: "       + score+"    ");
				System.out.println("Moves: "     + movesTotal);
				System.out.print("Enter a Command (U/D/L/R): ");
				
				Scanner input = new Scanner(System.in);
				String entry = input.next();
				char move = entry.charAt(0);
				
				if(!"uU8dD2rR6lL4zZqQgGsShH".contains(""+entry))
				{
					System.out.println();
					System.out.println("Sorry not a valid direction.Try again.");
					continue;
				}
				
				if("hHsSgGzZ".contains(""+entry))
				{
					command(move);
					continue;
				}
				else undoCount = 0;
				
				//this if statement is for when the user wants to undo a move, I made 2 different arrays one to always
				//save the last board and one to save the board before the last board if the user runs into a wall
				//and they want to undo that move
				if(validMove)
				{
					if(movesTotal % 2 == 0)
					{
					for(int r = 0; r < 4; r++)
						for(int c = 0; c < 4; c++)
							lastBoard[r][c] = board[r][c];
					}
					else
					{
						for(int r = 0; r < 4; r++)
							for(int c = 0; c < 4; c++)
								invalid[r][c] = board[r][c];
					}
				}
				
				if(move == 'q' || move == 'Q')
				{
					//quits the current game and starts a new one and resets the board to all 0's
					System.out.println();
					System.out.println("Would you like to start a new game? Type Y/N: ");
					Scanner input3 = new Scanner(System.in);
					char quit = input3.next().charAt(0);
					if(quit == 'n' || quit == 'N')
					{
						System.out.println("Final Score: "+score);
						System.out.println("Total Moves Made: "+movesTotal);
						System.out.println("Total Games Played: "+gamesPlayed);
						System.out.println("Games Won: "+gamesWon);
						displayBoard(board);
						anotherGame = false;
						input3.close();
						break;
					}
					else
					{
						System.out.println();
						System.out.println("NEW GAME!!!");
						board = zeroBoard.clone();
						movesTotal = 0;
						score = 0;
						winCount = 0;
						game = false;
						break;
					}
				}
				//keep the previous score in case the do an undo 
				lastScore = score;
					for(int r = 0; r < 4; r++)
						for(int c = 0; c < 4; c++)
							boardHolder[r][c] = board[r][c];
				if("rR6".contains(""+entry)) validMove = moveRight(entry);
				if("lL4".contains(""+entry)) validMove = moveLeft(entry);
				if("uU8".contains(""+entry)) validMove = moveUp(entry);
				if("dD2".contains(""+entry)) validMove = moveDown(entry);
				
				//Saves the current board after it moves to see if the board is full right after that user's move
				for(int r = 0; r < 4; r++)
					for(int c = 0; c < 4; c++)
						boardHolder[r][c] = board[r][c];
				
				if(validMove)
				{
					movesTotal++;
					moves++;
				}
				else
				{
					System.out.println();
					System.out.println("Move not avaiable, try again");
				}
				
				if(validMove) generateNumbers();
				
				// If the current score that the user has is greater than the high Score then it will
				//continue to update the high score
				if(score > highScore)
				{
					highScore = score;
					File high = new File("highScore.txt");
					try(PrintWriter output = new PrintWriter(high);)
					{
						output.println(highScore);
					}
				}
				if(checkWinner() && winCount < 1)
				{
					//moves to check while they are in extended play, mainly used for if the use undo as their
					//first move in extended play
					moves = 0;
					gamesWon++;
					displayBoard(board);
					System.out.println();
					System.out.print("Congratulations you win!!!! \nWould you like to keep playing to get a higher score? Type Y/N: ");

					Scanner input2 = new Scanner(System.in);
					String choice = input2.next();
					char playing = choice.charAt(0);
					
					winCount++;
					
					if(playing == 'n' || playing == 'N') 
					{
						System.out.println("Would you like to start a new game. Type Y/N: ");
						Scanner input4 = new Scanner(System.in);
						String choice1 = input4.next();
						char playing1 = choice1.charAt(0);
						
						//if the user wants to play again but doesn't want to go into extended play
						if(playing1 == 'n' || playing1 == 'N')
						{
							System.out.println("Final Score: "+score);
							System.out.println("Total Moves Made: "+movesTotal);
							System.out.println("Total Games Played: "+gamesPlayed);
							System.out.println("Games Won: "+gamesWon);
							displayBoard(board);
							game = false;
							anotherGame = false;
							input2.close();
							input4.close();
						}
						else 
						{
							gamesPlayed++;
							board = zeroBoard.clone();
							movesTotal = 0;
							score = 0;
							game = false;
							System.out.println();
							undoCount = 0;
							break;
						}
					}
					else
					{
						gamesPlayed++;
						System.out.println("Enjoy extended play!! See how high you can get!");
					}
				}

				if(boardFull())
				{
					System.out.println();
					System.out.println("Game Over!!!");
					System.out.println("Final Score: "+score);
					System.out.println("Total Moves Made: "+movesTotal);
					System.out.println("Total Games Played: " +gamesPlayed);
					System.out.println("Games Won: " +gamesWon);
					displayBoard(board);
					
					System.out.println();
					System.out.print("Would you like to play again? Type Y/N:");
					Scanner input3 = new Scanner(System.in);
					String playAgain = input3.next();
					char newGame = playAgain.charAt(0);
					
					if(newGame == 'n' || newGame == 'N')
					{
						anotherGame = false;
						input3.close();
						input.close();
					}
					else
					{
						//Sets the board to all zero's and clears all the stats
						board = zeroBoard.clone();
						movesTotal = 0;
						score = 0;
						game = false;
						System.out.println();
					}
				}
			}while(game);
		}while(anotherGame);
	}//end main
	
	public static void command(char move)
	{
		/*these are where all the main commands like undo,save,load and help are executed to make main a little
		 *less cluttered and more readable without all the commands getting in the way.
		 */
		if((move == 'z'|| move == 'Z') && undoCount < 1)
		{
			//This is for if they just entered extended play and they hit undo as their first move
			if(moves > 1 && winCount == 1)
			{
				for(int r = 0; r < 4; r++)
					for(int c = 0; c < 4; c++)
						board[r][c] = lastBoard[r][c];
				movesTotal--;
				gamesPlayed--;
				gamesWon--;
				score = lastScore;
				undoCount++;
			}
			else if(movesTotal < 1)
			{
				System.out.println();
				System.out.println("Can not use undo as your first move.");
			}
			else
			{
				if(validMove)
				{
					for(int r = 0; r < 4; r++)
						for(int c = 0; c < 4; c++)
							board[r][c] = lastBoard[r][c];
					movesTotal--;
					score = lastScore;
					undoCount++;
				}
				else
				{
					for(int r = 0; r < 4; r++)
						for(int c = 0; c < 4; c++)
							board[r][c] = invalid[r][c];
					movesTotal--;
					score = lastScore;
					undoCount++;
				}
			}
		}
		else if(undoCount == 1)
		{
			System.out.println();
			System.out.println("Can not use undo two times in a row");
			undoCount = 0;
		}
		if(move == 's' || move == 'S')
		{
			try
			{
				saveBoard();
			}
			catch(FileNotFoundException ex)
			{
				System.out.println("File was not found");
			}
		}
		
		if(move == 'g' || move == 'G')
		{
			try
			{
				loadBoard();
			}
			
			//if the file that the load game is referring to is not found
			catch(FileNotFoundException ex)
			{
				System.out.println();
				System.out.println("File not found. Must save a game before you can load.");
			}
		}
		if(move == 'h' || move == 'H') Help();
	}//end command

	public static void displayBoard(int[][] board)
	{
		char DV      = '\u2551'; //Vertical double line
		char tDCross = '\u2564'; //Top double line cross
		char bDCross = '\u2567'; //bottom double line cross
		char tLC     = '\u2554'; //top left corner
		char DH      = '\u2550'; //double horizontal line
		char tRC     = '\u2557'; //top right corner
		char bLC     = '\u255A'; //bottom left corner
		char bRC     = '\u255D'; //bottom right corner
		char SH      = '\u2500'; //single horizontal line
		char lSCross = '\u255F'; //left single cross line
		char SV      = '\u2502'; //single vertical line
		char rSCross = '\u2562'; //right single cross line
		char cS      = '\u253C'; //cross section
		
		//This makes the top line of the board
		String dH4     = (DH  + "" + DH  + "" + DH + "" + DH);
		String TopLine = (tLC + "" + dH4 + "" + tDCross + "" + dH4 + "" + tDCross + "" + dH4 + "" + tDCross + "" + dH4 + "" + tRC);
		System.out.println(TopLine);
		String HLine4  = (SH + "" + SH + "" + SH + "" + SH);
		
		 for(int row = 0 ; row < 4 ; row++)
		 {
			 //creates all the horizontal lines within the board between each of the numbers
			 //and each row 
			 
			 if(row > 0)
			 {
				 System.out.println(lSCross +""+ HLine4 +""+ cS +""+ HLine4 +""+ cS +""+ HLine4 +""+ cS +""+ HLine4 +""+ rSCross);
			 }
			 
			 for(int col=0 ; col < 4 ; col++)
			 {
				 
			 /*The for loop makes the vertical lines within the board taking into account if it is a 
			  * 4-digit number, triple digit number, double digit number, single digit number, or if it is the 0 (blank spot).
			  *At every spot there is a digit in column 2 or 3, it will print single lines on
			  *either side of the number, if the number is in columns 1 or 3 then it will print a double line and 
			  *a single line on either side depending if it was in column 1(left side or board) or 3(right side of board)
			  */
				 
				 int value = (board[row][col]);
				 if(value < 10 && value != 0)
				 {
				 	if(col == 0)             System.out.print(DV + "  " + board[row][col] + " "); 
				 	if(col == 1 || col == 2) System.out.print(SV + "  " + board[row][col] + " ");
				 	if(col == 3)	         System.out.print(SV + "  " + board[row][col] + " "+ DV);
				 }
				 
				 if(value >=10 && value <= 99 && value != 0)
				 {
			 		if(col == 0)             System.out.print(DV + " " + board[row][col] + " "); 
			 		if(col == 1 || col == 2) System.out.print(SV + " " + board[row][col] + " ");
			 		if(col == 3)	         System.out.print(SV + " " + board[row][col] + " "+ DV);
				 }
				 
				 if(value >100 && value <= 999 && value != 0)
				 {
			 		if(col == 0)             System.out.print(DV + "" + board[row][col] + " "); 
			 		if(col == 1 || col == 2) System.out.print(SV + "" + board[row][col] + " ");
			 		if(col == 3)	         System.out.print(SV + "" + board[row][col] + " "+ DV);
				 }
				 
				 if(value >1000 && value != 0)
				 {
			 		if(col == 0)             System.out.print(DV + "" + board[row][col] + ""); 
			 		if(col == 1 || col == 2) System.out.print(SV + "" + board[row][col] + "");
			 		if(col == 3)	         System.out.print(SV + "" + board[row][col] + ""+ DV);
				 }
				 
				 if(value == 0)
				 {
				 	if(col == 1 || col == 2)  System.out.print(SV +"    ");
				 	if(col == 0)              System.out.print(DV +"    ");
				 	if(col == 3)              System.out.print(SV +"    "+ DV);
				 }
			 }
			 
			System.out.println();
		 }
		 
		 //this will create the bottom portion of the board
		 String BottomLine = (bLC +""+ dH4 +""+ bDCross +""+ dH4 +""+ bDCross +""+ dH4 +""+ bDCross +""+ dH4 +""+ bRC);
		 System.out.print(BottomLine);
		 
	}//end displayBoard
	
	public static boolean moveRight(String entry)
	{
		/* This method is for moving the tiles to the right. The way the moveRight works is by storing all the non-zero numbers into a Array 
		 * list going row by row , Since the user has decided to go right then we will focus on the ROWS.
		 * 
		 *  					Board Array													    Array List
		 *                  _________________ 
		 *               	| 2 |   | 2 |   |      Would store this in the Array List            
		 *	                |   |   |   |   |          with only non-zero numbers
		 *					|   |   |   |   |	   ---------------------------------->			 [2 , 2]
		 *					|   |   |   |   | 
		 *
		 * If there are 2 numbers in the array list, then we add 2 zero's, if there are only 3 numbers in the array
		 * list then we add 1 zero, we just add the amount of zero's that will get the size up to 4.
		 * 
		 * 							Array List before 0's		--------->			Array List after 0's
		 *						[2]         --------> [0 , 0 , 0 , 2]    [2 , 4]         --------> [0 , 0 , 2 , 4]
		 *						[2 , 4 , 2] --------> [0 , 2 , 4 , 2]	 [2 , 4 , 2 , 4] --------> [2 , 4 , 2 , 4]
		 */
			for(int i = 0; i < 4; i++) 
			{
				//stores the current board into an array list
				ArrayList<Integer> moveTile = new ArrayList<>();
				for(int j = 0; j < 4; j++) 
	            {
	                if (board[i][j] != 0) moveTile.add(board[i][j]);
	            }
				
				// adds the numbers that are next to each other to see if they are equal or not
				for(int j = moveTile.size() - 1 ; j > 0; j--)
				{
					if(moveTile.get(j).equals(moveTile.get(j - 1)))
					{
						moveTile.set(j, 2 * (moveTile.get(j - 1)));
						moveTile.set(j - 1, 0);
						score = score + moveTile.get(j);
					}
				}
				
				for (int j = 0; j < moveTile.size(); j++) 
				{
	                if (moveTile.get(j) == 0) moveTile.remove(j);
	            }
				
				//since it is the right direction
				// we want to add it to the left of the numbers
	        	int size = moveTile.size();
	        	for(int j = 0; j < (board.length - size); j++) 
	        		moveTile.add(0 , 0);
	         
	        	for(int j = 0; j < board.length; j++) 
	        		board[i][j] = moveTile.get(j);
			}
		
			if(Arrays.deepEquals(boardHolder, board)) return false;
			else 								      return true;

		
	}//end moveRight
	
	public static boolean moveLeft(String entry)
	{
		/* This method is for moving the tiles to the left. The way the moveLeft works is by storing all the non-zero numbers into a Array 
		 * just like we did for the moveRight , going row by row. Since the user has decided to go Left then we will focus on the ROWS again.
		 * 
		 *  					Board Array													    Array List
		 *                  _________________ 
		 *               	| 4 |   | 2 |   |      Would store this in the Array List            
		 *	                |   |   |   |   |          with only non-zero numbers
		 *					|   |   |   |   |	   ---------------------------------->			 [4 , 2]
		 *					|   |   |   |   | 
		 *
		 * Same concept applies when adding the zero's except we put the zero's to the RIGHT.
		 * 
		 * 							Array List before 0's		--------->			Array List after 0's
		 *						[2]         --------> [2 , 0 , 0 , 0]    [2 , 4]         --------> [2 , 4 , 0 , 0]
		 *						[2 , 4 , 2] --------> [2 , 4 , 2 , 0]	 [2 , 4 , 2 , 4] --------> [2 , 4 , 2 , 4]
		 */
			for(int i = 0; i < 4; i++) 
			{
				//stores the current board into an array list
				ArrayList<Integer> moveTile = new ArrayList<>();
				for(int j = 0; j < 4; j++) 
	            {
	                if (board[i][j] != 0)  moveTile.add(board[i][j]);
	            }
				
				// adds the numbers that are next to each other to see if they are equal or not
				for(int j = 0; j < moveTile.size() - 1; j++)
				{
					if(moveTile.get(j).equals(moveTile.get(j + 1)))
					{
						moveTile.set(j, 2 * (moveTile.get(j + 1)));
						moveTile.set(j + 1, 0);
						score = score + moveTile.get(j);
					}
				}
				
				for (int j = 0; j < moveTile.size(); j++) 
				{
	                if (moveTile.get(j) == 0) moveTile.remove(j);
	            }
				
				// If the numbers where equal to each other and got added then this will
				// remove the zero in that array list, since it is the left direction
				// we want to add it to the right of the numbers
	        	int size = moveTile.size();
	        	for(int j = 0; j < (board.length - size); j++) 
	        		moveTile.add(0);
	         
	        	for(int j = 0; j < board.length; j++) 
	        		board[i][j] = moveTile.get(j);
			}
		
			if(Arrays.deepEquals(boardHolder, board)) return false;
			else 								    return true;
	}//end moveLeft
	
	public static boolean moveUp(String entry)
	{
		// Same as the moveLeft method but moves the numbers up instead.
			for (int j = 0; j < 4; j++) 
			{
				//stores the current board into an array list
				ArrayList<Integer> moveTile = new ArrayList<>();
				for(int i = 0; i < 4; i++) 
	            {
	                if (board[i][j] != 0) 
	                {
	                    moveTile.add(board[i][j]);
	                }
	            }
				
				// adds the numbers that are next to each other to see if they are equal or not
				for(int i = 0; i < moveTile.size()-1; i++)
				{
					if(moveTile.get(i).equals(moveTile.get(i + 1)))
					{
						moveTile.set(i, 2 * (moveTile.get(i + 1)));
						moveTile.set(i + 1, 0);
						score = score +  moveTile.get(i);
					}
				}
				
				// If the numbers where equal to each other and got added then this will
				// remove the zero in that array list, since it is the up direction
				// we want to add it to the right of the numbers
				for (int i = 0; i < moveTile.size(); i++) 
				{
	                if (moveTile.get(i) == 0) 
	                {
	                    moveTile.remove(i);
	                }
	            }
				
	        	int size = moveTile.size();
	        	for(int i = 0; i < (board.length - size); i++) 
	        		moveTile.add(0);
	         
	        	for(int i = 0; i < board.length; i++) 
	        		board[i][j] = moveTile.get(i);
			}
		
			if(Arrays.deepEquals(boardHolder, board)) return false;
			else 								    return true;
	}//end moveUp
	
	public static boolean moveDown(String entry)
	{
		// Same as the moveRight method but moves the numbers down on the board
		for(int j = 0; j < 4; j++) 
		{
			//stores the current board into an array list
			ArrayList<Integer> moveTile = new ArrayList<>();
			for(int i = 0; i < 4; i++) 
            {
                if (board[i][j] != 0) moveTile.add(board[i][j]);
            }
			
			// adds the numbers that are next to each other to see if they are equal or not
			for(int i = moveTile.size()-1 ; i > 0; i--)
			{
				if(moveTile.get(i).equals(moveTile.get(i-1)))
				{
					moveTile.set(i, 2 * (moveTile.get(i-1)));
					moveTile.set(i-1, 0);
					score = score + moveTile.get(i);
				}
			}
			
			for (int i = 0; i < moveTile.size(); i++) 
			{
                if (moveTile.get(i) == 0) moveTile.remove(i);
            }
			
			// If the numbers where equal to each other and got added then this will
			// remove the zero in that array list, since it is the down direction
			// we want to add it to the left of the numbers
        	int size = moveTile.size();
        	for(int i = 0; i < (4 - size); i++) 
        		moveTile.add(0 , 0);
         
        	for(int i = 0; i < board.length; i++) 
        		board[i][j] = moveTile.get(i);
		}

			if(Arrays.deepEquals(boardHolder, board)) return false;
			else 								    return true;
	}//end moveDown
	
	public static void generateNumbers()
	{
		/*The function of this method is generate 2 numbers at the beginning of the game and also generate one number 
		 * throughout the game as the user moves left, right, up, and down. First it will find where all the empty cells
		 * are and will store the row in an ARRAY LIST and also store the column in a separate ARRAY LIST. Once we have all
		 * the empty cells located we will generate a random number that will choose from both the row and column ARRAY LIST. 
		 * Once we have the row and column then we will generate numbers from 0-9 to make sure that it generates a 2 90% of the
		 * time and a 4 10% of the time. Finally once we have all this information we can store that number into are actual array
		 */
		ArrayList<Integer> emptyr = new ArrayList<>();
		ArrayList<Integer> emptyc = new ArrayList<>();
		
		for(int row = 0 ; row < 4 ; row++)
		{
			for(int col = 0 ; col < 4 ; col++)
				if(board[row][col] == 0)
					emptyr.add(row);
		}
		
		for(int row = 0 ; row < 4 ; row++)
		{
			for(int col = 0 ; col < 4 ; col++)
				if(board[row][col] == 0)
					emptyc.add(col);
		}
		
		Random ran = new Random();
		int location = ran.nextInt(emptyr.size());
		
		int row = emptyr.get(location);
		int col = emptyc.get(location);
		
		Random generate = new Random();
		int value = generate.nextInt(10);
		
		if(value != 6) board[row][col] = 2;
		else           board[row][col] = 4;
	}//end generateNumbers
	
	public static boolean checkWinner()
	{
		/* The check winner is pretty simple, I used a for loop to go through the board and check to see
		 * if there is a 2048 tile somewhere on the board, if there is then it will return true, if there isn't 
		 * then it will return false and keep playing
		 */
		int win = 0;
		for(int r = 0; r < 4; r++)
			for(int c = 0; c < 4; c++)
			{
				if(board[r][c] == 2048) win = 1;	
			}
		
		if(win == 1) return true;
		else         return false;
	}
	
	public static void Help()
	{
		System.out.println();
		System.out.println("The object of the game is to get a 2048 tile by adding like numbers together. For example you would ");
		System.out.println("add two \"2s\" together to get you a 4 and then two \"4s\" would get you an 8 and so on until you ");
		System.out.println("get a 2048 tile. You have the normal up(U) , Down(D) , Left(L) , and Right(R) directions you can move. ");
		System.out.println("If the board is full and there is no 2048 tile then you loose the game.");
		System.out.println();
		System.out.println("You also have the following commands that you can use:");
		System.out.println("S = Save the current game and stats");
		System.out.println("G = Get a previous game and stats");
		System.out.println("Z = undo a pervious move");
		System.out.println("Q = Quit the current");
		System.out.println();
	}
	
	public static void saveBoard() throws FileNotFoundException
	{
		/*This method is for if the the user wants to save a game that they are playing into
		*a file named "saveBoard". Then create a text file to be able to save the board 
		*to and using the PrintWriter method to be able to output the numbers into the text file.
		*The file will store the movesTotal
		*/
		File game = new File("saveBoard.txt");
		try(PrintWriter output = new PrintWriter(game);)
		{
			for(int i = 0 ; i < 4 ; i++)
				for(int j = 0 ; j < 4 ; j++)
					output.println(board[i][j]);
			for(int i = 0 ; i < 4 ; i++)
				for(int j = 0 ; j < 4 ; j++)
					output.println(lastBoard[i][j]);
			
			output.println(movesTotal);
			output.println(score);
			output.println(gamesPlayed);
			output.println(gamesWon);
			output.println(lastScore);
			System.out.println();
			System.out.println("Game saved!!");
		}
	}
	
	public static void loadBoard() throws FileNotFoundException
	{
		/*This is where the complier will be able to load a previous game that the user has saved,
		*since we saved the game into a file it is able to retrieve that file and read it by referring to
		*the file by name and using a for loop to retrieve the data and put the values right into out 2D array
		*/
		File file = new File("saveBoard.txt");
		try(Scanner input = new Scanner(file);)
		{
			for(int i = 0 ; i < 4 ; i++)
				for(int j = 0 ; j < 4 ; j++)
					board[i][j] = input.nextInt();
			for(int i = 0 ; i < 4 ; i++)
				for(int j = 0 ; j < 4 ; j++)
					lastBoard[i][j] = input.nextInt();
			
			movesTotal  = input.nextInt();
			score       = input.nextInt();
			gamesPlayed = input.nextInt();
			gamesWon    = input.nextInt();
			lastScore = input.nextInt();
			System.out.println();
			System.out.println("Game loaded!!");
		}
	}

	public static boolean boardFull()
	{
		/* The way to check if the board is full is by sending default moves (meaning the 4 possible moves R,L,D,U) and checking
		 * if they come back true or false. So if all of them come back false that means that the board is full and there are 
		 * no other possible moves left, if at least one of them come back true then that means there is at least one 
		 * possible move. I saved the board here as well because since I'm sending the moves to my move tile methods, its really
		 * moving the board, so I made sure to set it back to the original board after it checks. If the board is full with no
		 * possible moves left then it will return true and end the game, if there are possible moves then it will return a false
		 * and keep playing.
		 */
		boolean gameOver = false;
		int holder = score;
			for(int r = 0; r < 4; r++)
				for(int c = 0; c < 4; c++)
					boardHolder[r][c] = board[r][c];
			
			if(moveLeft("l") == false && moveRight("r") == false && moveUp("u") == false && moveDown("d") == false)
			{
				gameOver = true;
				for(int r = 0; r < 4; r++)
					for(int c = 0; c < 4; c++)
						board[r][c] = boardHolder[r][c];
			}
			else 
			{
				for(int r = 0; r < 4; r++)
					for(int c = 0; c < 4; c++)
						board[r][c] = boardHolder[r][c];
			}
		// to make sure that it doesn't change the score that the user gets
		score = holder;
		if(gameOver) return true;
		else 		 return false;
		
		
	}// end boardFull();
}

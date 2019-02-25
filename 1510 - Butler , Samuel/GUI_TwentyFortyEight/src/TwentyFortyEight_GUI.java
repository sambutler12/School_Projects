/* The function of the game is identical to the 2048 console version just this program implements a GUI that is more user friendly
 * and easier to work with. You still the same directions except now all you need to do is use the arrow keys in order to 
 * move the tiles up , down , left , and right. You also still have the same basic commands as well, which are Save , Load , Undo ,
 * Help , and Quit, but now they are buttons you can click on to execute these actions and as well referring to them as command keys 
 * with alt+S , alt+L , crtl+Z , alt+H , and alt+X. Also you are able to undo up to 10 moves instead of just one time which makes the 
 * game more realistic to win just in case the user made a mistake farther back down the line. Also when the user ends the game either
 * by getting a board full or exiting, if they have exceeded the current high score then it will play the "Ta-Da" sound and display the
 * new high score and then after 5 seconds it will display the game over for 2 seconds and then finally terminate the program. 
 * Same as last game if the user gets a 2048tile then they have the choice to either go into extended play or quit the game clicking
 *  on the button of their choosing.
 * 
 * Author: Samuel Butler
 * 		   EECS 1510
 * Date:   April 12, 2018
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TwentyFortyEight_GUI extends Application
{
	static int score         = 0;
	static int highScore     = 0;
	static int movesTotal    = 0;
	static int winCount      = 0;
	static int undoCount     = 0;
	static int moves         = 0;
	static boolean newHigh   = false;
	static boolean validMove = true;
	static Label scoreBox        = new Label("Score: "+score);
	static Label highScoreBox    = new Label("High Score: "+highScore);
	static Label movesTotalBox   = new Label("Total Moves: "+movesTotal);
	static Label textDisplay     = new Label("");
	static GridPane boardPane    = new GridPane();
	static GridPane top          = new GridPane();
	static BorderPane wholePane  = new BorderPane();
	static StackPane mainBoard   = new StackPane();
	static VBox command          = new VBox(80);
	static int[][] board         = new int[4][4];
	static int[][] boardHolder   = new int[4][4];
	static int[] scoreHolder     = new int[10];
	static int[][][] lastBoard   = new int[10][4][4];
	static Label[][] lbl         = new Label[4][4];
	static Rectangle[][] miniRec = new Rectangle[4][4];
	static boolean extendedP     = false;
	
	public void start(Stage primaryStage)
	{
		/*The function of the start method is where are all the magic happens to how the GUI is displayed and how all
		 * the actions that make everything work happen. Everything is inside a BorderPane where I set my center to be
		 * the board of course(pane mainBoard) and the left is where my buttons are set for the commands(pane command) and then
		 * I set the top where my title, movesTotal, high Score, and current score are displayed(top). The things that made
		 * up making my board was an array of Rectangles and an Array of Labels all on a stackPane and then I stored the numbers
		 * into my labels casting them from an integer into a string(lines 149-175). The next thing that I did was create the buttons
		 * for save,load,exit,and help and assign action events to them so if they were clicked on then it fired an action and was handled 
		 * by the action I set(lines 122-129 & 178-181). Then the last thing for setting up the board was I made the top with the title, 
		 * movesTotal, high score, and current score. Those were all labels set into colored rectangles with an integer attached to them that
		 * would update after each move(lines 97-122)
		 */
		try(ObjectInputStream loadHighGame = new ObjectInputStream(new FileInputStream("highScore.dat"));)
		{
			highScore = (int)loadHighGame.readObject();
		} catch (IOException | ClassNotFoundException e1)
		{
			//if there is no saved highScore yet it will catch the fill not found exeption
		} 
		
		wholePane.setPadding(new Insets(30,30,30,30));
		highScoreBox = new Label("High Score: "+highScore);
		Label title = new Label("      2048           ");
		title.setFont(Font.font("Arial", FontWeight.BOLD,90));
		//top.setHgap(2);
		top.add(title, 60, 1);
		Rectangle r2 = new Rectangle(250,30); r2.setFill(Color.rgb(187, 173, 160));
		Rectangle r3 = new Rectangle(250,30); r3.setFill(Color.rgb(187, 173, 160));
		Rectangle r4 = new Rectangle(250,30); r4.setFill(Color.rgb(187, 173, 160));
		r2.setArcWidth(10); r2.setArcHeight(10);
		r3.setArcWidth(10); r3.setArcHeight(10);
		r4.setArcWidth(10); r4.setArcHeight(10);
		
		scoreBox.setFont(Font.font("Arial", FontWeight.BOLD,25));
		highScoreBox.setFont(Font.font("Arial", FontWeight.BOLD,25));
		movesTotalBox.setFont(Font.font("Arial", FontWeight.BOLD,25));
		scoreBox.setTextFill(Color.BLACK);
		highScoreBox.setTextFill(Color.BLACK);
		
		top.add(r2, 200, 0);
		top.add(r3, 200, 1);
		top.add(r4, 200, 2);
		top.add(scoreBox, 200, 0);
		top.add(highScoreBox, 200, 2);
		top.add(movesTotalBox, 200, 1);
		top.add(textDisplay, 60, 2);
		wholePane.setTop(top);
		
		Button saveBt = new Button("Save Game"); saveBt.setPrefSize(150, 35); saveBt.setFocusTraversable(false);
		Button loadBt = new Button("Load Game"); loadBt.setPrefSize(150, 35); loadBt.setFocusTraversable(false);
		Button exitBt = new Button("Exit Game"); exitBt.setPrefSize(150, 35); exitBt.setFocusTraversable(false);
		Button helpBt = new Button("Help");      helpBt.setPrefSize(150, 35); helpBt.setFocusTraversable(false);
		
		command.getChildren().addAll(saveBt,loadBt,exitBt,helpBt);
		VBox.setMargin(saveBt, new Insets(60,10,10,0));
		wholePane.setLeft(command);
		
		int count = 0;
		do
		{
			generateNumber();
			count++;
		}while(count <=1);
		
		Rectangle r1 = new Rectangle(618,618);
		Color color = Color.rgb(187,173,157);
		r1.setFill(color);
		
		r1.setArcWidth(15);
		r1.setArcHeight(15);
		mainBoard.getChildren().add(r1);
		
		boardPane.setAlignment(Pos.CENTER);
		boardPane.setHgap(18);
		boardPane.setVgap(18);
		
		for(int i = 0; i < 4 ; i++)
			for(int j = 0; j < 4 ; j++)
			{
				miniRec[i][j] = new Rectangle(132,132);
				miniRec[i][j].setFill(Color.rgb(205,192,176));
				miniRec[i][j].setStroke(Color.rgb(205,192,176));
				miniRec[i][j].setArcWidth(15);
				miniRec[i][j].setArcHeight(15);
				boardPane.add(miniRec[i][j],i,j);
			}
		
		for(int i = 0; i<4;i++)
			for(int j = 0 ; j<4;j++)
			{
				if(board[i][j] != 0)
				{
					if(board[i][j] == 2) miniRec[i][j].setFill(Color.rgb(238, 228, 218));
					if(board[i][j] == 4) miniRec[i][j].setFill(Color.rgb(236, 224, 200));
					lbl[i][j] = new Label("   "+Integer.toString(board[i][j]));
					boardPane.add(lbl[i][j],i,j);
					lbl[i][j].setFont(Font.font("Arail", FontWeight.BOLD,60));}
				else
				{
					lbl[i][j] = new Label("");
					boardPane.add(lbl[i][j], i, j);
				}
			}
		
		exitBt.setOnAction(e-> endGame());
		helpBt.setOnAction(e-> help());
		saveBt.setOnAction(e-> save());
		loadBt.setOnAction(e-> load());
		
		/*The moving of the tiles, undo, winner, boardFull, and the command keys are all executed in between these .setOnKeyPressed braces.
		 * Starting with the command keys. 
		 * 
		 * UNDO: I first had to check if the control key was down and also see if the Z key was down as well, if it was
		 * then it would undo a move and increment the undoCount making sure it wasn't greater than 10, if they chose to undo more than once then it 
		 * would increment my undo count each time. Also my score is saved using a 1D array that is 10 long and stores them one by one as the
		 * user progresses throughout the game, if it undoCount exceeds 10 then it will move them all down one by one. 
		 * 
		 * Save, Load, Help, and Exit all have the same basic functions, which are responding to the alt key and
		 * then the letter specified for them (alt+S, alt+L, alt+H, and alt+X). when it saves it goes to my save method to be executed and similar with 
		 * load, exit, and help as well they are both sent to methods to have their actions executed.
		 * 
		 * The moving of the tiles was identical to what it was in 2048 console but this time we had to make sure that we put the integer into a 
		 * label and cast it as a string so it was able to be displayed onto the pane and move properly responding to the directions that the user has
		 * chosen to move. Involving the move it returns a boolean to see if it was a valid move or not, if it was then it will generate a new number, 
		 * increment both my undoCount and my undoHold as well. if the move was not valid then it will go to the InvalidMove method and let the user know.
		 * 
		 * The other to main functions that are in my setOnKeyPressed are boardFull and Winner. if the board is full it will return a boolean and go
		 * to the end game method and terminate the program. The winner also returns a boolean, if it returns true that means that the user has gotten a 2048 
		 * tile and they now have the choice if they want to go into extended play or end the game.
		 */
		mainBoard.setOnKeyPressed(e->
		{
			if(e.isControlDown())
			{
				if(e.getCode() == KeyCode.Z)
				{
					undo();
					if(movesTotal != 0 && undoCount > 0) movesTotal--;
					if(undoCount > 0) undoCount--;
				}
			}
			
			if(e.isControlDown() != true)
			{
				//this is how it stores the first 10 boards and score when undoCount < 10
				if(undoCount < 10)
				{
					for(int r = 0; r < 4; r++)
						for(int c = 0; c < 4; c++)
						{
							lastBoard[undoCount][r][c] = board[r][c];
						}
					scoreHolder[undoCount] = score;
				}
				else
				{	
				//this will get executed when undoCount is greater than 10 which means we need to push them all down and get rid
				//of the oldest board and oldest score and replace index 9 with the current board and current score
					for(int d = 0; d<10;d++)
						for(int r = 0; r < 4; r++)
							for(int c = 0; c < 4; c++)
							{
								if(d==9)
								{
									lastBoard[d][r][c] = board[r][c];
									scoreHolder[d] = score;
								}
								else     
								{
									lastBoard[d][r][c] = lastBoard[d+1][r][c];
									scoreHolder[d] = scoreHolder[d+1];
								}
							}
				}
				//will decrement the win count if they undo when they have just won
				if(extendedP) winCount--;
			}
			
			if(e.isAltDown())
			{
				if(e.getCode() == KeyCode.S) save();

				if(e.getCode() == KeyCode.L) load();

				if(e.getCode() == KeyCode.X) endGame();
				
				if(e.getCode() == KeyCode.H) help(); 
			}
			
			//used to check if the board is full when we check and send it to a method
			for(int r = 0; r < 4; r++)
				for(int c = 0; c < 4; c++)
					boardHolder[r][c] = board[r][c];
			
			switch(e.getCode()) 
			{
			case RIGHT: validMove = moveRight(); if(validMove) 
												 {
													if(undoCount < 10) undoCount++;
													movesTotal++;
													generateNumber();
												 }
												else invalidMove();
												break;
												 
			case UP:    validMove = moveUp();    if(validMove) 
											     {
													if(undoCount < 10) undoCount++;
													movesTotal++;
													generateNumber();
											     }
												else invalidMove();
												break;
			
			case LEFT:  validMove = moveLeft();  if(validMove) 
												 {
													if(undoCount < 10) undoCount++;
													movesTotal++;
													generateNumber();
												 }
												else invalidMove();
												break;
												 
			case DOWN:  validMove = moveDown();  if(validMove) 
												 {
													if(undoCount < 10) undoCount++;
													movesTotal++;
													generateNumber();
												 }
												else invalidMove();
												break;
			default: break;
			}
			
			for(int r = 0; r < 4; r++)
				for(int c = 0; c < 4; c++)
					boardHolder[r][c] = board[r][c];
			
			//will keep updating the high score if they keep getting higher than the previous one
			if(score > highScore)
			{
				newHigh = true;
				highScore = score;
				try(ObjectOutputStream highGame = new ObjectOutputStream(new FileOutputStream("highScore.dat"));)
				{
					highGame.writeObject(highScore);
				} 
				catch (IOException e1)
				{
					//Will continue if there is an IOException when trying to output the high score into the file
				}
			}
			
			tileColor();
	
			if(checkWinner() && winCount < 1) winner();
			if(boardFull()) endGame();
		});//end pane1.setOnKeyPressed
		
		mainBoard.getChildren().add(boardPane);
		wholePane.setCenter(mainBoard);
		wholePane.setBackground(new Background(new BackgroundFill(Color.rgb(251,248,241), null, null)));
		Scene scene = new Scene(wholePane,970,970);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setTitle("2048 GUI Game");
		primaryStage.setResizable(false);
		mainBoard.requestFocus();
	}//end start method
	
	public static boolean moveDown()
	{
		//same concept as the console version of 2048 for moving down
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
	}//end moveDown method
	
	public static boolean moveUp()
	{
		//same concept as the console version of 2048 for moving Up
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
		else 								      return true;
	}//end moveUp method
	
	public static boolean moveLeft()
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
		command.setDisable(true);
		command.setDisable(false);
		if(Arrays.deepEquals(boardHolder, board)) return false;
		else 								      return true;
	}//end moveLeft method
	
	public static boolean moveRight()
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
		else 								      return true;
	}//end moveRight method
	
	public static void generateNumber()
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
	}//end generateNumber method
	
	public static void tileColor()
	{
		/*This method is for setting the tiles with the right color and spacing when the board generates a new number after
		 * each valid move. It is in one big for loop checking to see if the number at the position is a 2, 4, 8, 16, 32,....
		 * and it will then set the square to the proper color, text color, and spacing for the specific number size. Also
		 * this is where it changes the high score, current score, and the movesTotal if they have exceeded the previous amount
		 * that they were at before.
		 */
		for(int i = 0; i<4;i++)
			for(int j = 0 ; j<4;j++)
			{
				if(board[i][j] != 0)
				{
					if(board[i][j] > 1 && board[i][j] < 10)
					{
						if(board[i][j] == 2) miniRec[i][j].setFill(Color.rgb(238, 228, 218));
						if(board[i][j] == 4) miniRec[i][j].setFill(Color.rgb(236, 224, 200));
						if(board[i][j] == 8) miniRec[i][j].setFill(Color.rgb(242, 177, 121));
						
						lbl[i][j].setText("   "+Integer.toString(board[i][j]));
						lbl[i][j].setFont(Font.font("Arail", FontWeight.BOLD,60));
						lbl[i][j].setTextFill(Color.BLACK);
						if(board[i][j] == 8) lbl[i][j].setTextFill(Color.WHITE);
					}
					if(board[i][j] > 9 && board[i][j] < 100)
					{
						if(board[i][j] == 16) miniRec[i][j].setFill(Color.rgb(246, 149, 97));
						if(board[i][j] == 32) miniRec[i][j].setFill(Color.rgb(249, 122, 92));
						if(board[i][j] == 64) miniRec[i][j].setFill(Color.rgb(247, 94, 58));
						
						lbl[i][j].setText("  "+Integer.toString(board[i][j]));
						lbl[i][j].setFont(Font.font("Arail", FontWeight.BOLD,60));
						lbl[i][j].setTextFill(Color.WHITE);
					}
					if(board[i][j] > 99 && board[i][j] <1000)
					{
						if(board[i][j] == 128) miniRec[i][j].setFill(Color.rgb(239, 204, 112));
						if(board[i][j] == 256) miniRec[i][j].setFill(Color.rgb(235, 205, 99));
						if(board[i][j] == 512) miniRec[i][j].setFill(Color.rgb(238, 199, 80));
						
						lbl[i][j].setText(" "+Integer.toString(board[i][j]));
						lbl[i][j].setFont(Font.font("Arail", FontWeight.BOLD,55));
						lbl[i][j].setTextFill(Color.WHITE);
					}
					if(board[i][j] >999)
					{
						if(board[i][j] == 1024) miniRec[i][j].setFill(Color.rgb(233, 200, 60));
						if(board[i][j] == 2048) miniRec[i][j].setFill(Color.rgb(239, 196, 0));
						if(board[i][j] == 4096) miniRec[i][j].setFill(Color.BLACK);
						if(board[i][j] == 8192) miniRec[i][j].setFill(Color.BLACK);
						
						lbl[i][j].setText(""+Integer.toString(board[i][j]));
						lbl[i][j].setFont(Font.font("Arail", FontWeight.BOLD,55));
						lbl[i][j].setTextFill(Color.WHITE);
					}
				}
				else
				{
					lbl[i][j].setText("");
					miniRec[i][j].setFill(Color.rgb(205,192,176));
					miniRec[i][j].setStroke(Color.rgb(205,192,176));
				}
			}
		scoreBox.setText("Score: "+score);
		highScoreBox.setText("High Score: "+highScore);
		movesTotalBox.setText("Total Moves: "+movesTotal);
	}//tileColor method
	
	public static boolean checkWinner()
	{
		int win = 0;
		for(int r = 0; r < 4; r++)
			for(int c = 0; c < 4; c++)
			{
				if(board[r][c] == 2048) win = 1;	
			}
		
		if(win == 1) return true;
		else         return false;
	}//end checkWinner method
	
	public static void undo()
	{
		/*This is where the user can undo and they are able to go back up to 10 times, the way it works is it first
		 * checks to see if the undoCount is greater than zero because if it is 0 then we don't want to execute it 
		 * because that means that they have already executed the max possible then it works the same way as undoing
		 * in the console version but instead it can undo multiple times by storing the board into a 3D array and 
		 * it will also set the high score back as well by when we stored the 10 most recent scores into a 1D array
		 * and the all that specific score based off of the undoCount.
		 */
		if(undoCount > 0)
		{
			for(int r = 0; r < 4; r++)
				for(int c = 0; c < 4; c++)
					board[r][c] = lastBoard[undoCount-1][r][c];
			score = scoreHolder[undoCount-1];
		}
	}//end undo method
	
	public static void help()
	{
		/*My help method is pretty straight forward and it will explain the rules of the game and I display them using 
		 * a text and going line by line. Also added a motion picture that helps illustrate the game more on how the game
		 * moves and what happens when to like numbers combine to form a bigger number getting closer to 2048 with 
		 * each move that they do.
		 */
		BorderPane info = new BorderPane();
		Text ms1  = new Text("The goal of the game is to get a 2048 tile by combining like numbers");
		Text ms2  = new Text("together working up to bigger numbers. You can only use the up,");
		Text ms3  = new Text("down, left, and right arrow keys to move the tiles in the direction you");
		Text ms4  = new Text("choose. If the board becomes full with no other possible moves and");
		Text ms5  = new Text("there is not a 2048 tile on the board then you lose the game.The");
		Text ms6  = new Text("following commands that you can use are Save, Load, Exit, Help, and");
		Text ms7  = new Text("Undo. You can refer to these also by Crtl+S, Crtl+L, Crtl+X, Crtl+H,");
		Text ms8  = new Text("and Crtl+Z(You can undo up to 10 times).");
		VBox mess = new VBox(15);
		ms1.setFont(Font.font("Arial", 30)); ms5.setFont(Font.font("Arial", 30));
		ms2.setFont(Font.font("Arial", 30)); ms6.setFont(Font.font("Arial", 30));
		ms3.setFont(Font.font("Arial", 30)); ms7.setFont(Font.font("Arial", 30));
		ms4.setFont(Font.font("Arial", 30)); ms8.setFont(Font.font("Arial", 30));
		mess.getChildren().addAll(ms1,ms2,ms3,ms4,ms5,ms6,ms7,ms8);
		
		Image image = new Image("Corner-Test.gif");
		ImageView imageView = new ImageView(image);
		info.setCenter(imageView);
		info.setTop(mess);
		Stage stage = new Stage();
		Scene sc = new Scene(info,940,900);
		stage.setScene(sc);
		stage.setTitle("Help Menu");
		stage.show();
	}//end help method
	
	public static void save()
	{
		/*The output stream will be executed when the user decides to save the current board that they are playing on right
		 * now, it ill save the board, lastBoard, boardHolder, movesTotal, undoCount, score and the undoHold. This will use 
		 * objects as well so when I'm dealing with arrays all I have to do is refer to the array by name and it stores the 
		 * whole thing as binary into a file that i can later load from.
		 */
		try(ObjectOutputStream saveGame = new ObjectOutputStream(new FileOutputStream("saveGame.dat"));)
		{
			saveGame.writeObject(board);
			saveGame.writeObject(lastBoard);
			saveGame.writeObject(boardHolder);
			saveGame.writeObject(movesTotal);
			saveGame.writeObject(undoCount);
			saveGame.writeObject(score);
			
			textDisplay.setText("                            Game Saved Successfully");
			textDisplay.setFont(Font.font("Arail", FontWeight.BOLD ,20));
			textDisplay.setTextFill(Color.BLUE);
			FadeTransition ft = new FadeTransition(Duration.millis(2000),textDisplay);
			ft.setFromValue(1.0);
			ft.setToValue(0);
			ft.play();
		}
		catch(IOException e)
		{
			//A catch just for having an IOException when trying to save the contents of the board
		}
	}
	
	public static void load()
	{
		/*This is my load that will load a previously saved game using the objectInputStream. since it is 
		 * binary I/O I can just refer to my arrays by name and set them equal to read.object and cast it as
		 * the array that I need it to be (1D, 2D, 3D, etc). Then the catch statement will execute when there 
		 * has been no saved game yet and the user try's to load, it will catch that and display to the user
		 * that there has been no previously saved game yet.
		 */
		try(ObjectInputStream loadGame = new ObjectInputStream(new FileInputStream("saveGame.dat"));)
		{
			board       = (int[][]) (loadGame.readObject());
			lastBoard   = (int[][][]) (loadGame.readObject());
			boardHolder = (int[][]) (loadGame.readObject());
			movesTotal  = (int)loadGame.readObject();
			undoCount   = (int)loadGame.readObject();
			score       = (int)loadGame.readObject();
		}
		catch(IOException | ClassNotFoundException e)
		{
			textDisplay.setText("                            No Previously saved game");
			textDisplay.setFont(Font.font("Arail", FontWeight.BOLD,20));
			FadeTransition ft = new FadeTransition(Duration.millis(2000),textDisplay);
			ft.setFromValue(1.0);
			ft.setToValue(0);
			ft.play();
		}
		tileColor();
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
			
			if(moveLeft() == false && moveRight() == false && moveUp() == false && moveDown() == false)
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
	
	public static void endGame()
	{
		/* This is the method that is executed when the user has either chose to exit the game or the board is full and 
		 * there are no longer no more valid moves. What this does is if the user has just gotten a new high score and they 
		 * either chose to end the game or the board has become full it will play the "Ta-Da" sound from windows and display that they
		 * have gotten a new high Score and set that to display for 5 seconds and the transition to say game over
		 * 
		 * Now if the user just exits the game and hasn't exceeded the high score then it will display game over for 
		 * 2 seconds and skips the high score if statement.
		 */
		Text gameNew  = new Text("NEW HIGH SCORE!!");
		Label display = new Label(Integer.toString(highScore));
		gameNew.setFont(Font.font("Arail", FontWeight.BOLD,100));
		display.setFont(Font.font("Arail", FontWeight.BOLD,100));
		
		wholePane.setDisable(true);
		FadeTransition f1 = new FadeTransition(Duration.millis(1500),wholePane);
		f1.setFromValue(1);
		f1.setToValue(0);
		f1.play();

		int exit = 0;
		if(newHigh)
		{
			wholePane.setDisable(false);
			exit = 11500;
			
			Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(1500), ae -> 
			{
				wholePane.getChildren().clear();
				wholePane.setBackground(new Background(new BackgroundFill(Color.rgb(238, 228, 218), null, null)));
				wholePane.setTop(gameNew);
				wholePane.setCenter(display);
				FadeTransition f = new FadeTransition(Duration.millis(3000),wholePane);
				Media sound = new Media("http://www.winhistory.de/more/winstart/mp3/win31.mp3");
				MediaPlayer playSound = new MediaPlayer(sound);
				playSound.play();
				f.setFromValue(0);
				f.setToValue(1);
				f.play();
			}));
		
			Timeline timeline4 = new Timeline(new KeyFrame(Duration.millis(5500), ae -> 
			{
				wholePane.setBackground(new Background(new BackgroundFill(Color.rgb(238, 228, 218), null, null)));
				FadeTransition f3 = new FadeTransition(Duration.millis(3000),gameNew);
				FadeTransition f4 = new FadeTransition(Duration.millis(3000),display);
				f3.setFromValue(1);
				f3.setToValue(0);
				f3.play();
				f4.setFromValue(1);
				f4.setToValue(0);
				f4.play();
			}));
			Timeline timeline5 = new Timeline(new KeyFrame(Duration.millis(8500), ae -> 
			{
				display.setText(" GAME OVER!!");
				display.setFont(Font.font("Arail", FontWeight.BOLD,100));
				FadeTransition f3 = new FadeTransition(Duration.millis(3000),display);
				f3.setFromValue(0);
				f3.setToValue(1);
				f3.play();
	
			}));
			timeline2.play();
			timeline4.play();
			timeline5.play();
		}
		else 
		{
			wholePane.setDisable(false);
			exit = 5500;
			
			//this fades in the game over and then exits after 2 seconds
			Timeline timeline5 = new Timeline(new KeyFrame(Duration.millis(1500), ae -> 
			{
				wholePane.getChildren().clear();
				wholePane.setBackground(new Background(new BackgroundFill(Color.rgb(238, 228, 218), null, null)));
				gameNew.setText(" GAME OVER!!");
				gameNew.setFont(Font.font("Arail", FontWeight.BOLD,100));
				wholePane.setCenter(gameNew);
				FadeTransition f3 = new FadeTransition(Duration.millis(3000),wholePane);
				f3.setFromValue(0);
				f3.setToValue(1);
				f3.play();
	
			}));
			timeline5.play();
		}
		Timeline timeline3 = new Timeline(new KeyFrame(Duration.millis(exit), ae -> System.exit(0)));
		timeline3.play();
		
		
	}// end endGame method
	
	public static void winner()
	{
		/*This is the winner method where if the user gets a 2048 it will come here and pop a new window saying that they have
		 * won. It will then prompt them to choose to either exit the game or go into extended play. If they choose to go into extended 
		 * play then that current window will close and increment the win count to make sure that if they get another 2048 it wont display
		 * winner again and it will allow them to keep playing the current game. On the other hand if the user chooses to exit it then it 
		 * simply just uses system,exit(0) to exit the program and terminate.
		 */
		winCount++;
		extendedP = true;
		
		Text congrats  = new Text("                             YOU WIN!!");
		Text congrats2 = new Text(" You can go into Extended Play or Exit Game?");
		congrats.setFont(Font.font("Arail", FontWeight.BOLD,30));  congrats.setFill(Color.WHITE);
		congrats2.setFont(Font.font("Arail", FontWeight.BOLD,30)); congrats2.setFill(Color.WHITE);
		
		Stage youWin  = new Stage();
		GridPane win  = new GridPane();
		Button playBt = new Button("Extened Play");
		Button endBt  = new Button("Exit Game");
		
		playBt.setPrefSize(150, 20);
		endBt.setPrefSize(150, 20);
		HBox bt = new HBox(20);
		bt.getChildren().addAll(playBt,endBt);
		
		win.setAlignment(Pos.CENTER);
		win.setPadding(new Insets(0,20,0,20));
		bt.setPadding(new Insets(0,150,0,150));
		
		win.add(bt, 0, 2);
		win.add(congrats,0,0);
		win.add(congrats2,0,1);
		win.setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
		
		//causes the "You Win" label to seem like its blinking by going back and forth between blank 
		//and the test "You Win".
		EventHandler<ActionEvent> blink = e -> 
		{
			if(congrats.getText().length() != 0) congrats.setText("");
			else congrats.setText("                             YOU WIN!!");
		};
		
		Timeline ani = new Timeline(new KeyFrame(Duration.millis(350),blink));
		ani.setCycleCount(Timeline.INDEFINITE);
		ani.play();
		
		playBt.setOnAction(e-> youWin.close());
		endBt.setOnAction(e-> System.exit(0));
		
		Scene w = new Scene(win,700,300);
		youWin.setScene(w);
		youWin.show();
	}//end winner method
	
	public static void invalidMove()
	{
		/*All this method is for is letting the user know that they have made an invalid move, meaning the have ran into
		 * a wall and cannot go in that direction any further, it will display "Invalid Move, Try again" and slowly fade away
		 * using the animation of FadeTransition.
		 */
		textDisplay.setText("                            Invalid move. Try again.");
		textDisplay.setFont(Font.font("Arail", FontWeight.BOLD ,20));
		textDisplay.setTextFill(Color.RED);
		FadeTransition ft = new FadeTransition(Duration.millis(2000),textDisplay);
		ft.setFromValue(1.0);
		ft.setToValue(0);
		ft.play();
	}
	
}//end class
import java.util.Scanner;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 
 * The Terminal creates the field/dashboard.
 * It is also able to open a console for the input of the game instructions.
 * Terminal gets and checks the user inputs.
 * 
 * One important thing is to give messages and feedback to the user.
 *              - game instruction
 *              - hints for inputs
 *              - help
 * 
 * @author      Johannes Richter
 *              Simon Cirdei
 *              Christoph Schramm
 * @version     v1.3 (02.06.2022 16:45)
 */ 
public class Terminal extends Game
{
    private Scanner scanner;        // create a new scanner 
    private short a;                // variable for area of bullseye
    private int ahalbe;             // variable to calculate the target
    private int starttarget;        // variable to set the beginning of the target
    private CanvasBG screenbg;      // set a new field/background
    private boolean gameRunning;    // is game on?
    private int ea, eb;             // help-variables for user inputs
    
    
    /**
     * The method game checks all the inputs of the players and runs selected loops.
     * Instructions and errors/feedback will be given
     */
    public void newGame () {       
        scanner = new Scanner (System.in);  // receives the input of the users
        welcomeText();
        boolean noInput = true;             // noInput is true or input is false
        super.currentPlayer = 1;            // current player is one
        
        // while the input is false, checking of input
        while (noInput = true) {    
            String input = scanner.nextLine();  // checking of the input
            // if s, the game will starts
            if (input.equals("s")) {
                // starts the game
                if(gameRunning == false) {
                    startGame();
                    gameRunning = true;
                } else {
                    System.out.println("The game is already running. Please press n for the next turn.");
                }
            }
            // if n, checking of running. If true, the players change
            else if (input.equals("n")) {
                if(gameRunning == false) {
                    System.out.println("The game is not yet running. Please press s to start it first.");
                } else {
                    screenbg.clearField();
                    swapPlayers();
                    turn();
                }
                
            }
            // if e, the game ends and the score will be printed
            else if (input.equals("e")) {
                noInput = false;
                System.out.println("Game over.");
                gameRunning = false;
                returnPoints();
                System.exit(0);         // closes the screen -> the console stays opened
            }
            // if h, the instruction are printed
            else if (input.equals("h")) {
                howTo();
            }
            /**
             * this is a fast solution for debbugging!
             * 
            else if (input.equals("joe")) {
                screenbg = new CanvasBG ((short)30, 265);
                gameRunning = true;
                super.currentPlayer = 1;
                super.scorePlayer1 = 0;
                super.scorePlayer2 = 0;
                screenbg.printScore(super.scorePlayer1, super.scorePlayer2);
                screenbg.visible(true);
                screenbg.printArcher();
                turn();
            }
            */
        
            // if nothing of the previous conditions happens, an error mesage will be print. 
            else {
                error();
            }   
        }
    }
    
    /**
     * The method checks explicity the user input for the target-size
     */
    public void inputForDartboard() {
        //ask for a
        System.out.println("Please enter a value for a [between 1 & "
        + (super.canvasHeight-super.wallStart)/9+"]. Hint: "
        +((super.canvasHeight-super.wallStart)/30)+" is a good value.");
        //check input for a
        boolean aOK = false;
        while(!aOK) {
            Scanner inputDimensions = new Scanner(System.in);
            ea = inputDimensions.nextShort();            //process a
            ahalbe = ea / 2;                             //required var for check b
            if(ea > (super.canvasHeight-super.wallStart) / 9) {
                errorValue();
            } else if(ea < 1) {
                errorValue();
            } else {
                aOK = true;
            }
        }
        //ask for b
        System.out.println("Please enter a value for b [between "+(super.wallStart+(4*ea)+ahalbe)
        +" & "+(super.canvasHeight-(4*ea)-ahalbe)+"]. Hint: "
        +((super.canvasHeight-super.wallStart)/2+super.wallStart)+" is a good value.");
        //check input for b
        boolean bOK = false;
        while(!bOK) {
            Scanner inputDimensions = new Scanner(System.in);
            eb = inputDimensions.nextInt();             //process b
            starttarget = eb - 4 * ea - ahalbe;         //required var for check b
            if(eb < super.wallStart+(4*ea)+ahalbe) {
                errorValue();
            } else if(eb > (super.canvasHeight-(4*ea)-ahalbe)) {
                errorValue();
            } else {
                bOK = true;
            }
        }
        // output for player  :) 
        System.out.println("Inputs saved. Let's go!");
    }
    
    /**
     * the method start the game:
     *          - inputs will be validated
     *          - score is set to zero
     *          - the current player is ONE
     *          - a new surface (canvas) will be opened
     *          - printing of score and archers
     */
    public void startGame() {
        inputForDartboard();
        super.currentPlayer = 1;
        super.scorePlayer1 = 0;
        super.scorePlayer2 = 0;
        screenbg = new CanvasBG ((short)ea, starttarget);
        screenbg.visible(true);
        screenbg.printScore(super.scorePlayer1, super.scorePlayer2);
        screenbg.printArcher();
        turn();
    }
    
    /**
     * the method switch the players
     */

    public void swapPlayers() {
        // archer will be switched
        screenbg.swapArcher(super.currentPlayer);
        
        //players-switch
        if (super.currentPlayer == 1) {
            super.currentPlayer = 2;
        }
        else if(super.currentPlayer == 2) {
            super.currentPlayer = 1;
        }
    }

    /**
     * Gives information which player turn it is. 
     * Displays the points of the round in the console.
     */   
    public void turn() {
        System.out.println("It's player "+ super.currentPlayer +"'s turn.");
                
        int score = screenbg.shootArrow();
        
        // send won points to super
        if(super.currentPlayer == 1) {
            super.scorePlayer1 = super.scorePlayer1 + score;
        } else if(super.currentPlayer == 2) {
            super.scorePlayer2 = super.scorePlayer2 + score;
        }
        
        // Message when arrow hits the bullseye
        if(score == 50) {
            System.out.println("BULLSEYE! Player "+ super.currentPlayer +" got "+ score + " points! :)");
        } 
        // Message when arrow hits nothing
        else if(score == 0) {
            System.out.println("Player "+ super.currentPlayer +" missed. :(");
        } 
        // Message when arrow hits green or black
        else {
            System.out.println("Player "+ super.currentPlayer +" got "+ score + " points.");
        }
        
        screenbg.printScore(super.scorePlayer1, super.scorePlayer2);
    }
      
    /**
     *  Method is able to give the points/score of the two players in the console at the end of the game
     */
    public void returnPoints()
    {
        //If score player 1 is bigger than two, Player 1 wins
        if(super.scorePlayer1 > super.scorePlayer2) {
            System.out.println("Player 1 wins with " + super.scorePlayer1 + " points. "
            +"Player 2 has " + super.scorePlayer2 + " points.");
        } 
        //If score player 2 is bigger than two, Player 2 wins
        else if(super.scorePlayer2 > super.scorePlayer1) {
            System.out.println("Player 2 wins with " + super.scorePlayer2 + " points. "
            +"Player 1 has " + super.scorePlayer1 + " points.");
        } 
        //If score is zero
        else if((super.scorePlayer1 == 0) && (super.scorePlayer1 == 0)) {
            System.out.println("Did you even play? No player has any points.");
        } 
        //If score player 1 is the same as player 2 = tie
        else if(super.scorePlayer1 == super.scorePlayer2) {
            System.out.println("It's a tie! Both players have " + super.scorePlayer1 + " points.");
        }
        // you can close the console with Ctrl+W
        System.out.println("Press Ctrl+W to close this window.");
    } 
    
    /**
     * Feedback-Message due to an error related to the surface (CanvasBG) 
     */
    public void errorValue() {
        System.out.println ("ERROR. Please enter a valid value. The limits can be seen above. You may try again.");
    }
    
    /**
     * First message to welcome the players
     */
    public void welcomeText() {
        System.out.println("Welcome to 2Darts" + "\n\n" + "Before playing, here is the overview:" +"\n");
        System.out.println("- The red area (bullseye) gives 50 points" + "\n" + "- Green (bull) gives 25 points"
        + "\n" + "- Black gives 10 points" + "\n" + "- Everything else 0 points");
        howTo();
    }
    
    /**
     * Feedback-Message for instruction or help
     * (How to play the game)
     */
    public void howTo() {
        System.out.println("\n" +"------------------------------------------------");
        System.out.println("s = start game");
        System.out.println("n = next player");
        System.out.println("e = end game");
        System.out.println("h = help");
        System.out.println("------------------------------------------------");
    }
    
    /**
     * Feedback-Message in case of an error
     */
    public void error() {
        System.out.println("ERROR. This input cannot be processed. Please check your entry.");
        System.out.println("If you need help, enter h.");
    }
}


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
 * @author  Johannes Richter
 *          Simon Cirdei
 *          Christoph Schramm
 *          
 * @version v1.5 (03.06.2022 19:20)
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
    public void newGame() {       
        scanner = new Scanner (System.in);  // receives the input of the users
        welcomeText();
        boolean noInput = true;             // noInput is true or input is false
        
        // wait for input
        while (noInput = true) {    
            String input = scanner.nextLine();  // read the input
            if (input.equals("s")) {    // if s, the game will start
                //only start the game if game is not yet running
                if(gameRunning == false) {
                    ea = super.qsA;
                    starttarget = super.qsB - 4 * super.qsA - super.qsA / 2;
                    // output for player  :) 
                    System.out.println("SUCCESS: Quickstart was selected. Let's go!");
                    System.out.println("-------------------------------------------------------------");
                    startGame(false);
                    gameRunning = true;
                } else {
                    System.out.print("The game is already running. Do you want to reset it? [y/n] ");
                    String securityQuestion = scanner.nextLine();  // read the input
                    if (securityQuestion.equals("y")) {
                        startGame(true);
                    } else if(securityQuestion.equals("n")) {
                        System.out.println("Okay, no reset. Press 'n' to keep on playing: ");
                    } else {
                        System.out.println("You didn't enter 'y' nor 'n '. Press 'n' to keep on playing: ");
                    }
                }
            }  // if d, the game will start in dev-mode (ALL INPUTS)
            else if (input.equals("d")) {
                //only start the game if game is not yet running
                if(gameRunning == false) {
                    inputForDeveloperMode();
                    inputForDartboard();
                    startGame(false);
                    gameRunning = true;
                } else {
                    System.out.println("Unfortunately, a game in DEV MODE cannot be restarted.");
                    System.out.print("You can either keep on playing (n) or reset the scores (s). ");
                }
            } // if n, next turn
            else if (input.equals("n")) {
                if(gameRunning == false) {  //check if game is already running
                    System.out.println("ERROR. The game is not yet running. Please start it first.");
                } else {                    //next turn is allowed
                    screenbg.clearField();  //remove old arrows
                    swapPlayers();          //swap the archers
                    turn();
                }
            }
            // if e, the game ends and the score will be printed
            else if (input.equals("e")) {
                noInput = false;
                System.out.println("-------------------------------------------------------------");
                System.out.println("Game over. You played "+super.rounds+" rounds.");
                returnPoints();
                gameRunning = false;
                System.exit(0);         // quits the application
            }
            // if h, the instruction are printed
            else if (input.equals("h")) {
                howTo();
            }
            // if any other button is pressed, an error message will be printed. 
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
        System.out.print("Enter value for a [1 - "
        + (super.canvasHeight-super.wallStart)/9+". Recommendation: "
        +((super.canvasHeight-super.wallStart)/30)+"]: ");
        //check input for a
        boolean aOK = false;
        while(!aOK) {
            Scanner inputDimensions = new Scanner(System.in);
                while (true) //check if input is a number. source: https://stackoverflow.com/questions/2912817/how-to-use-scanner-to-accept-only-valid-int-as-input
                    try {
                        ea = Integer.parseInt(inputDimensions.nextLine());
                        break;
                    } catch (NumberFormatException nfe) {
                        System.out.print("ERROR. Not a number (NaN). Try again: ");
                    }
            //System.out.println("Stored: "+ea+"\n");         // For debugging: Show the stored value
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
        System.out.print("Enter value for b ["+(super.wallStart+(4*ea)+ahalbe)
        +" - "+(super.canvasHeight-(4*ea)-ahalbe)+". Recommendation: "
        +((super.canvasHeight-super.wallStart)/2+super.wallStart)+"]: ");
        //check input for b
        boolean bOK = false;
        while(!bOK) {
            Scanner inputDimensions = new Scanner(System.in);
                while (true) //check if input is a number. source: https://stackoverflow.com/questions/2912817/how-to-use-scanner-to-accept-only-valid-int-as-input
                    try {
                        eb = Integer.parseInt(inputDimensions.nextLine());
                        break;
                    } catch (NumberFormatException nfe) {
                        System.out.print("ERROR. Not a number (NaN). Try again: ");
                    }
            //System.out.println("Stored: "+eb+"\n");         // For debugging: Show the stored value
            starttarget = eb - 4 * ea - ahalbe;             // required var for check b
            if(eb < super.wallStart+(4*ea)+ahalbe) {
                errorValue();
            } else if(eb > (super.canvasHeight-(4*ea)-ahalbe)) {
                errorValue();
            } else {
                bOK = true;
            }
        }
        // output for player  :) 
        System.out.println("SUCCESS: Inputs saved and (a & b) checked. Let's go!");
        System.out.println("-------------------------------------------------------------");
    }
    
    /**
     * The method checks explicity the user input for the target-size
     */
    public void inputForDeveloperMode() {
        //display warning
        System.out.println("WARNING. You started the game in DEV MODE.");
        System.out.println("Wrong inputs can cause weird behavior and are not checked.");
        System.out.println("-------------------------------------------------------------");
        //ask for all the variables and check if its a number(int or double)
        System.out.print("Enter width of the canvas [Default: 1000]: ");
        Scanner inputDimensions = new Scanner(System.in);
            while (true) //check if input is a number. source: https://stackoverflow.com/questions/2912817/how-to-use-scanner-to-accept-only-valid-int-as-input
                try {
                    super.canvasWidth = Integer.parseInt(inputDimensions.nextLine());
                    break;
                } catch (NumberFormatException nfe) {
                    System.out.print("ERROR. Not an integer number (NaN). Try again: ");
                }
        System.out.print("Enter height of the canvas [Default: 700]: ");
            while (true) //check if input is a number. source: https://stackoverflow.com/questions/2912817/how-to-use-scanner-to-accept-only-valid-int-as-input
                try {
                    super.canvasHeight = Integer.parseInt(inputDimensions.nextLine());
                    break;
                } catch (NumberFormatException nfe) {
                    System.out.print("ERROR. Not an integer number (NaN). Try again: ");
                }
        System.out.print("Enter where the wall starts (px from top) [Default: 100]: ");
            while (true) //check if input is a number. source: https://stackoverflow.com/questions/2912817/how-to-use-scanner-to-accept-only-valid-int-as-input
                try {
                    super.wallStart = Integer.parseInt(inputDimensions.nextLine());
                    break;
                } catch (NumberFormatException nfe) {
                    System.out.print("ERROR. Not an integer number (NaN). Try again: ");
                }
        System.out.print("Enter thickness of the wall [Default: 20]: ");
            while (true) //check if input is a number. source: https://stackoverflow.com/questions/2912817/how-to-use-scanner-to-accept-only-valid-int-as-input
                try {
                    super.wallThickness = Integer.parseInt(inputDimensions.nextLine());
                    break;
                } catch (NumberFormatException nfe) {
                    System.out.print("ERROR. Not an integer number (NaN). Try again: ");
                }
        System.out.print("Enter width of the arrow [Default: 25]: ");
            while (true) //check if input is a number. source: https://stackoverflow.com/questions/2912817/how-to-use-scanner-to-accept-only-valid-int-as-input
                try {
                    super.arrowWidth = Integer.parseInt(inputDimensions.nextLine());
                    break;
                } catch (NumberFormatException nfe) {
                    System.out.print("ERROR. Not an integer number (NaN). Try again: ");
                }
        System.out.print("Enter height of the arrow [Default: 3]: ");
            while (true) //check if input is a number. source: https://stackoverflow.com/questions/2912817/how-to-use-scanner-to-accept-only-valid-int-as-input
                try {
                    super.arrowHeight = Integer.parseInt(inputDimensions.nextLine());
                    break;
                } catch (NumberFormatException nfe) {
                    System.out.print("ERROR. Not an integer number (NaN). Try again: ");
                }
        System.out.print("Enter gravity in px/s^2 [Default: 9.81]: ");
            while (true) //check if input is a number. source: https://stackoverflow.com/questions/2912817/how-to-use-scanner-to-accept-only-valid-int-as-input
                try {
                    super.gravity = Double.parseDouble(inputDimensions.nextLine());
                    break;
                } catch (NumberFormatException nfe) {
                    System.out.print("ERROR. Not a double number (NaN). Try again: ");
                }
        System.out.print("Enter minimum shooting angle [Default: 10]: ");
            while (true) //check if input is a number. source: https://stackoverflow.com/questions/2912817/how-to-use-scanner-to-accept-only-valid-int-as-input
                try {
                    super.angleMin = Integer.parseInt(inputDimensions.nextLine());
                    break;
                } catch (NumberFormatException nfe) {
                    System.out.print("ERROR. Not an integer number (NaN). Try again: ");
                }
        System.out.print("Enter maximum shooting angle [Default: 60]: ");
            while (true) //check if input is a number. source: https://stackoverflow.com/questions/2912817/how-to-use-scanner-to-accept-only-valid-int-as-input
                try {
                    super.angleMax = Integer.parseInt(inputDimensions.nextLine());
                    break;
                } catch (NumberFormatException nfe) {
                    System.out.print("ERROR. Not an integer number (NaN). Try again: ");
                }
        System.out.print("Enter minimum shooting speed [Default: 10]: ");
            while (true) //check if input is a number. source: https://stackoverflow.com/questions/2912817/how-to-use-scanner-to-accept-only-valid-int-as-input
                try {
                    super.speedMin = Integer.parseInt(inputDimensions.nextLine());
                    break;
                } catch (NumberFormatException nfe) {
                    System.out.print("ERROR. Not an integer number (NaN). Try again: ");
                }
        System.out.print("Enter maximum shooting speed [Default: 25]: ");
            while (true) //check if input is a number. source: https://stackoverflow.com/questions/2912817/how-to-use-scanner-to-accept-only-valid-int-as-input
                try {
                    super.speedMax = Integer.parseInt(inputDimensions.nextLine());
                    break;
                } catch (NumberFormatException nfe) {
                    System.out.print("ERROR. Not an integer number (NaN). Try again: ");
                }
    }
    
    /**
     * the method starts the game:
     *          - inputs will be collected
     *          - score is initialised to zero
     *          - rounds is initialised to 1
     *          - the first player player becomes the currentplayer
     *          - a new surface (canvas) will be opened
     *          - printing of score and archers
     */
    public void startGame(boolean restart) {
        super.currentPlayer = 1;
        super.scorePlayer1 = 0;
        super.scorePlayer2 = 0;
        super.rounds = 0;
        if(restart == false) {
            screenbg = new CanvasBG ((short)ea, starttarget);
        } else {
            screenbg.clearField();
        }
        screenbg.visible(true);
        screenbg.printScore(super.scorePlayer1, super.scorePlayer2);
        screenbg.printArcher();
        turn();
    }
    
    /**
     * the method switch the players
     */

    public void swapPlayers() {
        // archer switch on canvas
        screenbg.swapArcher(super.currentPlayer);
        
        //players switch in super
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
        super.rounds++;
        
        System.out.print("It's player "+ super.currentPlayer +"'s turn. ");
                
        int score = screenbg.shootArrow();
        
        // send won points to super
        if(super.currentPlayer == 1) {
            super.scorePlayer1 = super.scorePlayer1 + score;
        } else if(super.currentPlayer == 2) {
            super.scorePlayer2 = super.scorePlayer2 + score;
        }
        
        // Message when arrow hits the bullseye
        if(score == 50) {
            System.out.print("BULLSEYE! Player "+ super.currentPlayer +" got "+ score + " points!    ");
        } 
        // Message when arrow hits nothing
        else if(score == 0) {
            System.out.print("Player "+ super.currentPlayer +" missed.                      ");
        } 
        // Message when arrow hits green or black
        else {
            System.out.print("Player "+ super.currentPlayer +" got "+ score + " points.              ");
        }
        
        screenbg.printScore(super.scorePlayer1, super.scorePlayer2);
    }
      
    /**
     *  Method is able to give the points/score of the two players in the console at the end of the game
     */
    public void returnPoints()
    {
        //If score player 1 is bigger than 2, Player 1 wins
        if((super.scorePlayer1 > super.scorePlayer2) && (gameRunning == true)) {
            System.out.println("Player 1 wins with " + super.scorePlayer1 + " points. "
            +"Player 2 has " + super.scorePlayer2 + " points.");
        } 
        //If score player 2 is bigger than 1, Player 2 wins
        else if((super.scorePlayer2 > super.scorePlayer1) && (gameRunning == true)) {
            System.out.println("Player 2 wins with " + super.scorePlayer2 + " points. "
            +"Player 1 has " + super.scorePlayer1 + " points.");
        }
        //If score is zero
        else if((super.scorePlayer1 == 0) && (super.scorePlayer1 == 0) && (gameRunning == true)) {
            System.out.println("That was no successful match. No player has any points.");
        } 
        //If score player 1 is the same as player 2 = tie
        else if((super.scorePlayer1 == super.scorePlayer2) && (gameRunning == true)) {
            System.out.println("It's a tie! Both players have " + super.scorePlayer1 + " points.");
        }
        System.out.println("-------------------------------------------------------------");
        // you can close the console with Ctrl+W
        System.out.print("Press Ctrl+W to close this window.");
    } 
    
    /**
     * Feedback-Message due to an error related to the surface (CanvasBG) 
     */
    public void errorValue() {
        System.out.print("ERROR. Not a valid value. The limits can be seen above. Try again: ");
    }
    
    /**
     * First message to welcome the players
     */
    public void welcomeText() {
        System.out.println("Welcome to 2Darts " + super.version + "\n\n" + "Before playing, here is the overview:" +"\n");
        System.out.println("- The red area (bullseye) gives 50 points" + "\n" + "- Green (bull) gives 25 points"
        + "\n" + "- Black gives 10 points" + "\n" + "- Everything else 0 points"+"\n");
        howTo();
        System.out.print("Make your choice: ");
    }
    
    /**
     * Feedback-Message for instruction or help
     * (How to play the game)
     */
    public void howTo() {
        System.out.println("-------------------------------------------------------------");
        System.out.println("s = Start game                n = Next Player    e = End game");
        System.out.println("d = Start game in DEV MODE                       h = Help");
        System.out.println("-------------------------------------------------------------");
    }
    
    /**
     * Feedback-Message in case of an error
     */
    public void error() {
        System.out.println("ERROR. This input cannot be processed. Please try again.");
        System.out.print("If you need help, enter h: ");
    }
}

import java.awt.Color;

/**
 * 
 * @author  Johannes Richter
 *          Simon Cirdei
 *          Christoph Schramm
 *          
 * @version v1.5.3 (09.11.2023 17:18)
 */
public class Game
{
    private Terminal terminal;                                 //Global terminal
    protected final String version            = "v1.5.3";        //This version number
    protected static int canvasHeight         = 700;           //Height of the canvas in px.                Default: 700
    protected static int canvasWidth          = 1000;          //Width of the canvas in px.                 Default: 1000
    protected static int wallStart            = 100;           //Start of the wall in px.                   Default: 100
    protected static int wallThickness        = 20;            //Thickness of the wall in px.               Default: 20
    protected static final Color colorPlayer1 = Color.blue;    //Color for player 1 (archer and arrow)
    protected static final Color colorPlayer2 = Color.red;     //Color for player 2 (archer and arrow)
    protected static final Color bgColor      = Color.white;   //Background color of the canvas
    protected static int arrowHeight          = 3;             //Height of the arrow in px.                 Default: 3
    protected static int arrowWidth           = 25;            //Width of the arrow in px.                  Default: 25
    protected static double gravity           = 9.81;          //Influence of the gravity in px/s^2.        Default: 9.81
    protected static final int frequency      = 20;            //update frequency for movements in ms.      Default: 20
    protected static final double timestep    = 0.05;          //Seconds of difference for trajectory calc. Default: 0.05
    protected static double angleMin          = 10;            //Minimum angle of the arrow in degrees.     Default: 10
    protected static double angleMax          = 60;            //Maximum angle of the arrow in degrees.     Default: 60
    protected static double speedMin          = 10;            //Minimum speed of the arrow in px/s.        Default: 10
    protected static double speedMax          = 25;            //Maximum speed of the arrow in px/s.        Default: 25
    protected static final int player         = 2;             //Number of players                          Default: 2
    protected static final short qsA          = 30;            //value A for quickstart
    protected static final int qsB            = 300;           //value B for quickstart
    
    protected int scoreXpos = canvasWidth - 150;    //X-position of the first score
    protected static int scoreYpos = 80;            //Y-position of the first score
    public int archerXpos = canvasWidth - 100;      //X-position of the first archer
    public int archerYpos = canvasHeight - 68;      //Y-position of the first archer
    protected int arrowXpos = canvasWidth - 125;    //Starting x-position of the arrow
    protected int arrowYpos = canvasHeight - 65;    //Starting y-position of the arrow
    
    public int scorePlayer1;        //global var for player1's score
    public int scorePlayer2;        //global var for player2's score
    public int currentPlayer;       //global var for the number of the current player
    public int rounds;              //global var for the number of rounds played
        
    public static void main (String[] args) {
        Game game = new Game();
        game.newGame();
    }
    
    public void newGame() {
        terminal = new Terminal();
        terminal.newGame();
    }
}
import java.awt.Color;

/**
 * 
 * @author JR, CS, SVC
 * @version v1.2 (02.06.2022 15:35)
 */
public class Game
{
    private Terminal terminal;                                 //Global terminal
    protected static final int canvasHeight = 700;             //Height of the canvas. Default: 700
    protected static final int canvasWidth = 1000;             //Width of the canvas. Default: 1000
    protected static final int wallStart = 100;                //Start of the wall. Default: 100
    protected static final int wallThickness = 20;             //Thickness of the wall. Default: 20
    protected static final Color colorPlayer1 = Color.blue;    //Color for player 1 (archer and arrow)
    protected static final Color colorPlayer2 = Color.red;     //Color for player 2 (archer and arrow)
    protected static final Color bgColor = Color.white;        //Background color of the canvas
    protected static final int arrowWidth = 25;                //Width of the arrow. Default: 25
    protected static final int arrowHeight = 3;                //Height of the arrow. Default: 3
    protected static final double gravity = 9.81;              //Influence of the gravity in m/s^2. Default: 9.81
    protected static final int frequency = 20;                 //update frequency for movements in ms. Default: 20
    protected static final double timestep = 0.05;             //Seconds of difference for trajectory calc. Default: 0.05
    protected static final double angleMin = 10;               //Minimum angle of the arrow. Default: 10
    protected static final double angleMax = 60;               //Maximum angle of the arrow. Default: 60
    protected static final double speedMin = 18;               //Minimum speed of the arrow. Default: 18
    protected static final double speedMax = 30;               //Maximum speed of the arrow. Default: 30
    
    protected static final int scoreXpos = canvasWidth - 250;  //X-position of the first score
    protected static final int scoreYpos = 80;                 //Y-position of the first score
    protected static final int archerXpos = canvasWidth - 100; //X-position of the first archer
    protected static final int archerYpos = canvasHeight - 68; //Y-position of the first archer
    protected static final int arrowXpos = canvasWidth-125;    //Starting x-position of the arrow
    protected static final int arrowYpos = canvasHeight-65;    //Starting y-position of the arrow
    
    public int scorePlayer1;        //global var for player1's score
    public int scorePlayer2;        //global var for player2's score                               
    public int currentPlayer;       //global var for the number of the current player
        
    public static void main (String[] args) {
        Game game = new Game();
        game.newGame();
    }
    
    public void newGame() {
        terminal = new Terminal();
        terminal.newGame();
    }
}
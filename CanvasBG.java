import java.awt.*;
import java.awt.geom.*;
import java.util.*;

/**
 * The class CanvasBG is one of the most important classes for the game 2Darts.
 * The class is able to draw: 
 *          - the target of the dartboard 
 *          - the two archers
 *          - the score of both players
 *          
 * CanvasBG also moves the arrow trough the dashboard. Loops calculates the positions and points.
 * The change of the players is also implemented here. 
 * 
 * @author  Johannes Richter
 *          Simon Cirdei
 *          Christoph Schramm
 *          
 * @version v1.5 (03.06.2022 19:20)
 */
public class CanvasBG extends Game
{
    private Canvas screenbg;        //define a new Canvas
    private int targettop;          //top of the target (dartboard)
    private int evalscore;          //score from the evaluation
    private int curplay;            //private variant of currentplayer, the Terminal tells which archer switches
    private int score1, score2;     //two scores for two players
    private Color arrowcolor;       //color of the arrow
    private short a;                //data-input of the height of bullseye
    public Archer Archer1, Archer2; //new archers
    public Points Points1, Points2; //new scoretables
    private Arrow thisArrow;        //new arrow
    
    /**
     * Constructor for instances of the CanvasBG (dashboard)
     *
     * @param inputa        input from user for target size
     * @param startarget    input from user for beginning of target
     */
    public CanvasBG(short inputa,int starttarget)
    {
        a = inputa;                     
        targettop = starttarget;        
        curplay = 1;                    //init local player number one
        score1 = super.scorePlayer1;
        score2 = super.scorePlayer2;
        screenbg = new Canvas("2Darts " + super.version, super.canvasWidth, super.canvasHeight, super.bgColor);
        printWall();                    
        printTarget();
    }
      
    /**
     * The method is able to shoot an arrow on the dashboard with correct color
     */
    public int shootArrow()
    {
        screenbg.setVisible(true);
        //set color of the arrow for player one
        if(curplay == 1) {
            arrowcolor = super.colorPlayer1;
        }
        //set color of the arrow for player two
        else if(curplay == 2) {
            arrowcolor = super.colorPlayer2;
        }
        
        // Create and show the arrow
        Arrow thisArrow = new Arrow(arrowcolor, screenbg);
        thisArrow.draw();
        
        screenbg.wait(300);;
        
        // Let the arrow fly
        fly(thisArrow);
        
        // Result evaluation
        eval(thisArrow);
        
        //get the score for other classes/methods
        return evalscore;
    }
    
    /**
     * The method is able to let the arrow fly.
     * A loop checks the position at all times. 
     * Within certain limits the loop finishes.
     */
    public void fly(Arrow thisArrow)
    {
        boolean done =  false;
        while(!done) {
            screenbg.wait(20);           // kurze Pause
            thisArrow.move();
            // Stop if the arrow hits the ground
            if(thisArrow.giveYposition() >= (super.canvasHeight - super.arrowHeight)) {
                done = true;
            }  // Stop if the arrow hits the target
            else if(thisArrow.giveXposition() <= super.wallThickness  && thisArrow.giveYposition() >= super.wallStart) {
                done = true; 
            }  // Stop if the arrow missed the target
            else if(thisArrow.giveXposition() <= 0) {
                done = true;
            }   // Stop if the arrow is in the clouds
            else if(thisArrow.giveYposition() <= 0) {
                done = true;
            }
        }
    }
    
    /**
     * The method returns the final position of the arrow.
     * If the position is in the defined area of the bullseye, 
     * the position blinks and the points will be set.
     * 
     * Same function for the different areas.
     */
    public void eval(Arrow thisArrow)
    {
        // first statement is false
        boolean evaluated =  false;
        int blink = 0;              //init of first blink
        
        // checking of the boolean
        while(!evaluated) {
            // will set the score to 50, if arrow hits the area of bullseye
            if((thisArrow.giveYposition() >= targettop + 4 * a) && (thisArrow.giveYposition() <= targettop + 5 * a) && (thisArrow.giveXposition() == super.wallThickness)) {
                evalscore = 50;
                evaluated = true;
                
                // area which has been hit blinks 3 times
                while(blink < 3) {
                        screenbg.setForegroundColor(Color.yellow);
                        Rectangle bullseye = new Rectangle(0, targettop+4*a, super.wallThickness, a);
                        screenbg.fill(bullseye);
                    screenbg.wait(200);         // delay for 200ms
                        printRed();
                    screenbg.wait(200);         // delay for 200ms
                    blink++;
                }
            }
            // will set the score to 25, if arrow hits the green area
            else if((thisArrow.giveYposition() >= targettop + 3 * a) && (thisArrow.giveYposition() <= targettop + 6 * a) && (thisArrow.giveXposition() == super.wallThickness)) {
                evalscore = 25;
                evaluated = true;
                while(blink < 3) {
                        screenbg.setForegroundColor(Color.yellow);
                        Rectangle greenbox = new Rectangle(0, targettop+3*a, super.wallThickness, 3*a);
                        screenbg.fill(greenbox);
                        printRed();
                    screenbg.wait(200);     // delay for 200ms
                        printGreen();
                        printRed();
                    screenbg.wait(200);     // delay for 200ms
                        blink++;
                }
            }
            // will set the score to 10, if arrow hits the black area 
            else if((thisArrow.giveYposition() >= targettop) && (thisArrow.giveYposition() <= targettop + 9 * a) && (thisArrow.giveXposition() == super.wallThickness)) {
                evalscore = 10;
                evaluated = true;
                while(blink < 3) {
                        screenbg.setForegroundColor(Color.yellow);
                        Rectangle blackbox = new Rectangle(0, targettop, super.wallThickness, 9*a);
                        screenbg.fill(blackbox);
                        printGreen();
                        printRed();
                        screenbg.wait(200);     // delay for 200ms
                        printTarget();        
                        screenbg.wait(200);     // delay for 200ms
                        blink++;
                }
            }
            // zero points, if previous requirements not true
            else {
                evalscore = 0;
                evaluated = true;
            }
        } 
    }
    
    /**
     * The method draw the background of the dartboard with brown.
     */
    public void printWall()
    {
        screenbg.setVisible(true);
        Color brown = new Color(191, 128, 64); // Color brown
        screenbg.setForegroundColor(brown);
        //usage of super variables from superclass
        Rectangle brownbox = new Rectangle(0, super.wallStart, super.wallThickness, super.canvasHeight - super.wallStart);
        screenbg.fill(brownbox);
    }
    
    /**
     * Draw the target
     */
    public void printTarget()
    {
        printBlack();       // draw black area
        printGreen();       // draw green area
        printRed();         // draw red area
    }
    
    /**
     * Draw black
     */
    public void printBlack()
    {
        screenbg.setForegroundColor(Color.black);        
        Rectangle blackbox = new Rectangle(0, targettop, super.wallThickness, 9*a);
        screenbg.fill(blackbox);
    }
    
    /**
     * Draw green
     */
    public void printGreen()
    {
        screenbg.setForegroundColor(Color.green);
        Rectangle greenbox = new Rectangle(0, targettop+3*a, super.wallThickness, 3*a);
        screenbg.fill(greenbox);
    }
    
    /**
     * Draw red
     */
    public void printRed()
    {
        screenbg.setForegroundColor(Color.red);
        Rectangle bullseye = new Rectangle(0, targettop+4*a, super.wallThickness, a);
        screenbg.fill(bullseye);
    }
      
    /**
     * Draw the two Archers
     */
    public void printArcher()
    {
        screenbg.setVisible(true);
        Archer Archer1 = new Archer(1, super.colorPlayer1, screenbg);   // init the first archer
        Archer1.draw();
        Archer Archer2 = new Archer(2, super.colorPlayer2, screenbg);   // init the second archer
        Archer2.draw();
    }
    
    /**
     * The method is able to switch the players. 
     * First the position of the archers will be moved.
     * Methods from Archer are used.
     */
    public void swapArcher(int currentplno)
    {
        screenbg.setVisible(true);
        boolean swapdone =  false;
        // If the current player is no 1, the archer one will move to right, and the second move left
        if(curplay == 1) {     
            Archer1 = new Archer (1, super.colorPlayer1, screenbg);
            Archer2 = new Archer (2, super.colorPlayer2, screenbg);
            while(!swapdone) {
                screenbg.wait(20);           // short waiting time
                Archer1.stepRight();
                Archer2.stepLeft();
                // Stop if the arrow hits the ground
                if(Archer2.giveXposition() <= (super.archerXpos)) {
                    swapdone = true;
                    curplay = 2;
                }
            }
        // If the current player is no 2, the archer one will move to left, and the second move right
        } else if(curplay == 2) {
            Archer1 = new Archer (2, super.colorPlayer1, screenbg);
            Archer2 = new Archer (1, super.colorPlayer2, screenbg);
            while(!swapdone) {
                screenbg.wait(20);           // short waiting time
                Archer2.stepRight();
                Archer1.stepLeft();
                // Stop if the arrow hits the ground
                if(Archer1.giveXposition() <= (super.archerXpos)) {
                    swapdone = true;
                    curplay = 1;
                }
            }
        }
    }
    
    /**
     * Method is able to clear the arrow from the field
     */
    public void clearField()
    {
        screenbg.setForegroundColor(super.bgColor);
        // if the arrow hit the target, it will be re painted
        Rectangle hitarrows = new Rectangle(super.wallThickness, 0, super.arrowWidth, super.canvasHeight);
        screenbg.fill(hitarrows);
        // if the arrow did not hit the target, it will be re painted
        Rectangle lostarrows = new Rectangle(super.wallThickness, super.canvasHeight - super.arrowHeight,
        super.archerXpos - super.wallThickness, super.arrowHeight);
        screenbg.fill(lostarrows);
    }
    
    /**
     * Draw the two Scores
     */
    public void printScore(int inputscore1, int inputscore2)
    {
        score1 = inputscore1;           //init of score player one
        score2 = inputscore2;           //init of score player one
        screenbg.setVisible(true);
        screenbg.setForegroundColor(super.bgColor);
        screenbg.fillRectangle(super.scoreXpos, super.scoreYpos - 20, super.canvasWidth - super.scoreXpos, 50);
        // set position of the scores and draw them
        Points Points1 = new Points(1, score1, screenbg);   
        Points1.draw();
        Points Points2 = new Points(2, score2, screenbg);
        Points2.draw();
    }
    
    /**
     * Show the canvas
     */
    public void visible(boolean state)
    {
        screenbg.setVisible(state);
    }
}

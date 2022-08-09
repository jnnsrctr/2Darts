import java.awt.*;
import java.awt.geom.*;

/**
 * Startet Canvas
 * {@code Canvas}
 * 
 * @author CS, SVC, JR 
 * @version v1.1 (02.06.2022 15:10)
 */
public class CanvasBG extends Game
{
    private Canvas screenbg;
    private int targettop;
    private int evalscore;   //score from the evaluation
    private int curplay;   // private variant of currentplayer, the Terminal tells which archer switches
    private int score1, score2;
    private Color arrowcolor;
    private short a;
    public Archer Archer1, Archer2;     //new archers
    public Points Score1, Score2;       //new scores
    private Arrow thisArrow;
    
    public CanvasBG(short inputa,int starttarget)
    {
        targettop = starttarget;
        a = inputa;
        curplay = 1;     //init local player no
        score1 = super.scorePlayer1;
        score2 = super.scorePlayer2;
        screenbg = new Canvas("2Darts", super.canvasWidth, super.canvasHeight, super.bgColor);
        printBG();
    }
      
     /**
     * Shoot an arrow
     */
    public int shootArrow()
    {
        screenbg.setVisible(true);
        if(curplay == 1) {
            arrowcolor = super.colorPlayer1;
        }
        else if(curplay == 2) {
            arrowcolor = super.colorPlayer2;
        }        // Create and show the arrow
        Arrow thisArrow = new Arrow(arrowcolor, screenbg);
        thisArrow.draw();
        
        // Move the arrow
        boolean done =  false;
        boolean wallhit;
        while(!done) {
            screenbg.wait(20);           // kurze Pause
            thisArrow.move();
            // Stop if the arrow hits the ground
            if(thisArrow.giveYposition() >= (super.canvasHeight - super.arrowHeight)) {
                done = true;
                wallhit = false;
            }  // Stop if the arrow hits the target
            else if(thisArrow.giveXposition() <= super.wallThickness  && thisArrow.giveYposition() >= super.wallStart) {
                done = true; 
                wallhit = true;
            }  // Stop if the arrow missed the target
            else if(thisArrow.giveXposition() <= 0) {
                done = true; 
                wallhit = false;
            }   // Stop if the arrow is in the clouds
            else if(thisArrow.giveYposition() <= 0) {
                done = true; 
                wallhit = false;
            }
        }
        
        //Result evaluation.
        
        boolean evaluated =  false;
        int blink = 0;
        while(!evaluated) {
            if(thisArrow.giveYposition() >= targettop + 4 * a && thisArrow.giveYposition() <= targettop + 5 * a) {
                evalscore = 50;
                evaluated = true;
                while(blink < 3) {
                    screenbg.setForegroundColor(Color.yellow);
                    Rectangle bullseye = new Rectangle(0, targettop+4*a, super.wallThickness, a); //x, y, width, height
                    screenbg.fill(bullseye);                                       // draw the bullseye
                    screenbg.wait(200);
                    screenbg.setForegroundColor(Color.red);
                    screenbg.fill(bullseye);                                       // draw the bullseye
                    screenbg.wait(200);
                    blink++;
                }
            }
            else if(thisArrow.giveYposition() >= targettop + 3 * a && thisArrow.giveYposition() <= targettop + 6 * a) {
                evalscore = 25;
                evaluated = true;
                while(blink < 3) {
                    screenbg.setForegroundColor(Color.yellow);
                    Rectangle greenbox = new Rectangle(0, targettop+3*a, super.wallThickness, 3*a);
                    screenbg.fill(greenbox);
                    screenbg.setForegroundColor(Color.red);                                         // draw the green areas
                    Rectangle bullseye = new Rectangle(0, targettop+4*a, super.wallThickness, a); //x, y, width, height
                    screenbg.fill(bullseye);                                       // draw the bullseye
                    
                    screenbg.wait(200);
                    
                    screenbg.setForegroundColor(Color.green);
                    screenbg.fill(greenbox);
                    screenbg.setForegroundColor(Color.red);
                    screenbg.fill(bullseye);                         
                    
                    screenbg.wait(200);
                    blink++;
                }
            }
            else if(thisArrow.giveYposition() >= targettop && thisArrow.giveYposition() <= targettop + 9 * a) {
                evalscore = 10;
                evaluated = true;
                while(blink < 3) {
                    screenbg.setForegroundColor(Color.yellow);
                    Rectangle blackbox = new Rectangle(0, targettop, super.wallThickness, 9*a); //x, y, width, height
                    screenbg.fill(blackbox);                                     //  draw the black areas
                    screenbg.setForegroundColor(Color.green); 
                    Rectangle greenbox = new Rectangle(0, targettop+3*a, super.wallThickness, 3*a);
                    screenbg.fill(greenbox);
                    screenbg.setForegroundColor(Color.red);                                         // draw the green areas
                    Rectangle bullseye = new Rectangle(0, targettop+4*a, super.wallThickness, a); //x, y, width, height
                    screenbg.fill(bullseye);                                       // draw the bullseye
                    screenbg.wait(200);
                    screenbg.setForegroundColor(Color.black);
                    screenbg.fill(blackbox);
                    screenbg.setForegroundColor(Color.green);
                    screenbg.fill(greenbox);
                    screenbg.setForegroundColor(Color.red);
                    screenbg.fill(bullseye);          
                    screenbg.wait(200);
                    blink++;
                }
            }
            else {
                evalscore = 0;
                evaluated = true;
            }
        }       
        return evalscore;
    }
    
     /**
     * Draw the background
     */
    public void printBG()
    {
        screenbg.setVisible(true);
        Color brown = new Color(191, 128, 64); // Color brown
        screenbg.setForegroundColor(brown);
        Rectangle brownbox = new Rectangle(0, super.wallStart, super.wallThickness, super.canvasHeight - super.wallStart);    //x, y, width, height
        screenbg.fill(brownbox);                                                //draw the brown wall
        screenbg.setForegroundColor(Color.black);        
        Rectangle blackbox = new Rectangle(0, targettop, super.wallThickness, 9*a);
        screenbg.fill(blackbox);                                     //  draw the black areas
        screenbg.setForegroundColor(Color.green);
        Rectangle greenbox = new Rectangle(0, targettop+3*a, super.wallThickness, 3*a);
        screenbg.fill(greenbox);                                         // draw the green areas
        screenbg.setForegroundColor(Color.red);
        Rectangle bullseye = new Rectangle(0, targettop+4*a, super.wallThickness, a);
        screenbg.fill(bullseye);                                       // draw the bullseye
    }
      
     /**
     * Draw the two Archers
     */
    public void printArcher()
    {
        screenbg.setVisible(true);
        Archer Archer1 = new Archer(1, super.colorPlayer1, screenbg);
        Archer1.draw();
        Archer Archer2 = new Archer(2, super.colorPlayer2, screenbg);
        Archer2.draw();
    }
    
    public void swapArcher(int currentplno)
    {
        screenbg.setVisible(true);
        boolean swapdone =  false;
        if(curplay == 1) {     
            Archer1 = new Archer (1, super.colorPlayer1, screenbg);
            Archer2 = new Archer (2, super.colorPlayer2, screenbg);
            while(!swapdone) {
                screenbg.wait(20);           // kurze Pause
                Archer1.stepRight();
                Archer2.stepLeft();
                // Stop if the arrow hits the ground
                if(Archer2.giveXposition() <= (super.archerXpos)) {
                    swapdone = true;
                    curplay = 2;
                }
            }
        } else if(curplay == 2) {
            Archer1 = new Archer (2, super.colorPlayer1, screenbg);
            Archer2 = new Archer (1, super.colorPlayer2, screenbg);
            while(!swapdone) {
                screenbg.wait(20);           // kurze Pause
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
     * Clear all arrows
     */
    public void clearField()
    {
        screenbg.setForegroundColor(super.bgColor);
        Rectangle hitarrows = new Rectangle(super.wallThickness, 0, super.arrowWidth, super.canvasHeight);
        screenbg.fill(hitarrows);
        Rectangle lostarrows = new Rectangle(super.wallThickness, super.canvasHeight - super.arrowHeight, super.archerXpos - super.wallThickness, super.arrowHeight);
        screenbg.fill(lostarrows);
    }
    
     /**
     * Draw the two Scores
     */
    public void printScore(int inputscore1, int inputscore2)
    {
        score1 = inputscore1;
        score2 = inputscore2;
        screenbg.setVisible(true);
        screenbg.setForegroundColor(super.bgColor);
        screenbg.fillRectangle(super.scoreXpos, super.scoreYpos - 20, 200, 50);
        Points Score1 = new Points(1, score1, screenbg);
        Score1.draw();
        Points Score2 = new Points(2, score2, screenbg);
        Score2.draw();
    }
    
     /**
     * Show the canvas
     */
    public void visible(boolean state)
    {
        screenbg.setVisible(state);
    }
}

import java.awt.*;
import java.awt.geom.*;
import java.lang.*;

/**
 * Class Archer creates and draws the archers on the Canvas.
 * This class is able to move the archers from right to left / left to right.
 *
 * 
 * @author  Johannes Richter
 *          Simon Cirdei
 *          Christoph Schramm
 *          
 * @version v1.4 (03.06.2022 15:35)
 */
public class Archer extends Game
{   
    private Color color;                 // color of the archer
    private Canvas screenbg;             // canvas where to plot the archer
    private int xPosition, yPosition;    //_position at the start
    private int playerno;                // number of the player
    
    
    /**
     * Constructor for instances of Archer
     *
     * @param inputplayer    current player
     * @param archercolor   color of the archer
     * @param usedcanvas  canvas where to plot the arrow
     */
    public Archer(int inputplayer, Color archercolor, Canvas usedcanvas)
    {
        playerno = inputplayer;                            
        xPosition = super.archerXpos + (playerno-1)*33;     //x-position Archer: access from super-class
        yPosition = super.archerYpos;                       //y-position Archer: access from super-class             
        color = archercolor;
        screenbg = usedcanvas;    
    }

    /**
     * The archer will be painted at the pre-set-position on the Canvas
     */
    public void draw()
    {
        screenbg.setForegroundColor(color);
        screenbg.setVisible(true);
        // the head
        screenbg.fillCircle(xPosition+8,yPosition,14);
        // the body top to bottom
        screenbg.drawLine(xPosition+15,yPosition+13,xPosition+15,yPosition+53);
        // the arms
        screenbg.drawLine(xPosition, yPosition+8,xPosition+15, yPosition+23);   //left arm
        screenbg.drawLine(xPosition+15,yPosition+23,xPosition+30,yPosition+8);  //right arm
        // the legs
        screenbg.drawLine(xPosition,yPosition+68,xPosition+15,yPosition+53);    //left leg
        screenbg.drawLine(xPosition+15,yPosition+53,xPosition+30,yPosition+68); //right leg
    }
    
    /**
     * The archer will be erased
     */
    public void erase()
    {
        screenbg.eraseCircle(xPosition+8,yPosition,14);
        screenbg.setForegroundColor(super.bgColor); //no erase-function: redraw with canvas-backgorund-color
        // the body top to bottom
        screenbg.drawLine(xPosition+15,yPosition+13,xPosition+15,yPosition+53);
        // the arms
        screenbg.drawLine(xPosition, yPosition+8,xPosition+15, yPosition+23);   //left arm
        screenbg.drawLine(xPosition+15,yPosition+23,xPosition+30,yPosition+8);  //right arm
        // the legs
        screenbg.drawLine(xPosition,yPosition+68,xPosition+15,yPosition+53);    //left leg
        screenbg.drawLine(xPosition+15,yPosition+53,xPosition+30,yPosition+68); //right leg
    }
    
    /**
     * The archer moves to the right
     */
    public void stepRight()
    {
        // Erase at current position
        erase();
            
        // Calculate new position and decide which direction to walk
        xPosition += 1;
        
        // Re-draw at the newly calculated position
        draw();
    }   
    
    /**
     * The archer moves to the right
     */
    public void stepLeft()
    {
        // Erase at current position
        erase();
            
        // Calculate new position and decide which direction to walk
        xPosition -= 1;     

        // Re-draw at the newly calculated position
        draw();
    }     
    
    /**
     * Supply other classes with the current X position of the Archer
     */
    public int giveXposition()
    {
        return xPosition;
    }
}

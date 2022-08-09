import java.awt.*;
import java.awt.geom.*;
import java.lang.*;
import java.util.Random;

/**
 * Class Arrow calculates the trajectory of the "arrow". 
 * This class is able to draw and erase the arrow.
 *
 * 
 * @author  Johannes Richter
 *          Simon Cirdei
 *          Christoph Schramm
 *          
 * @version v1.5 (03.06.2022 19:20)
 */
public class Arrow extends Game
{
    private Color localcolor, colpl1, colpl2;       // local variant of color of the arrow
    private Canvas screenbg;                        // canvas where to plot the arrow
    private int xPosition, yPosition;               // position at the start
    private int localcurplay;                       // count which players turn it is
    Random r1 = new Random();                       // random number between 0 and 1
    Random r2 = new Random();                       // random number between 0 and 1
    private double speed, angle;                    // speed and angle of the arrow
    private double time = 0;                        // time for the trajectory calc  

    /**
     * Constructor for instances of Arrow
     *
     * @param inputcolor  color of the arrow
     * @param usedcanvas  canvas where to plot the arrow
     */
    public Arrow(Color inputcolor, Canvas usedcanvas)
    {
        xPosition = super.arrowXpos;     //x-Position of the Arrow: access from super-class   
        yPosition = super.arrowYpos;     //y-Position of the Arrow: access from super-class  
        screenbg = usedcanvas;
        localcolor = inputcolor;
        speed = super.speedMin + (super.speedMax-super.speedMin) * r1.nextDouble();
        angle = super.angleMin + (super.angleMax-super.angleMin) * r2.nextDouble();
    }

    /**
     * Draw the arrow on the current position on the canvas
     **/
    public void draw()
    {
        screenbg.setForegroundColor(localcolor);
        screenbg.fillRectangle(xPosition, yPosition, super.arrowWidth, super.arrowHeight);
    }

    /**
     * Erase the arrow at his current position
     **/
    public void erase()
    {
        screenbg.eraseRectangle(xPosition, yPosition, super.arrowWidth, super.arrowHeight);
    }    

    /**
     * Erase the arrow from his current position,
     * calculate trajectory and re-draw at new position
     **/
    public void move()
    {
        // Erase at current position
        erase();
                
        // Calculate new position
        xPosition -= speed * Math.cos(Math.toRadians(angle)) * time;
        yPosition -= - 0.5 * super.gravity * Math.pow(time,2) + speed * Math.sin(Math.toRadians(angle)) * time;
        // Source: Wikipedia (https://en.wikipedia.org/wiki/Projectile_motion)
        
        // add to the clock
        time += super.timestep;
        
        
        if((yPosition >= (super.canvasHeight - super.arrowHeight)) && (xPosition <= super.wallThickness)) {
            yPosition = (int)(super.canvasHeight - super.arrowHeight);
            xPosition = (int)(super.wallThickness);
            speed=0;
        } // Check if arrow lands on the floor
        else if(yPosition >= (super.canvasHeight - super.arrowHeight)) {
            yPosition = (int)(super.canvasHeight - super.arrowHeight);
            speed=0;
        }  // Check if wall is hit
        else if(xPosition <= super.wallThickness && yPosition >= super.wallStart) {
            xPosition = (int)(super.wallThickness);
            // reverse calculate yPos (one timestep before)
            yPosition += - 0.5 * super.gravity * Math.pow((time-super.timestep),2) + speed * Math.sin(Math.toRadians(angle)) * (time-super.timestep);
        }  // Check if over the wall
        else if(xPosition <= 0) {
            xPosition = (int)(-super.arrowWidth);
        }  // Check if into the sky
        else if(yPosition <= 0) {
            yPosition = (int)(-super.arrowHeight);
        }
        
        // Re-draw at the newly calculated position
        draw();
    }    

    /**
     * Supply other classes with the current X position of the arrow
     */
    public int giveXposition()
    {
        return xPosition;
    }

    /**
     * Supply other classes with the current Y position of the arrow
     */
    public int giveYposition()
    {
        return yPosition;
    }
}

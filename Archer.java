import java.awt.*;
import java.awt.geom.*;
import java.lang.*;

/**
 * Beschreiben Sie hier die Klasse Archer.
 * 
 * @author (Ihr Name) 
 * @version v1.1 (02.06.2022 15:10)
 */
public class Archer extends Game
{   
    private Color color;                 // color of the arrow
    private Canvas screenbg;             // canvas where to plot the arrow
    private int xPosition, yPosition;    //position at the start
    private int playerno;                // number of the player
    
    public Archer(int inputplayer, Color archercolor, Canvas usedcanvas)
    {
        playerno = inputplayer;
        xPosition = super.archerXpos + (playerno-1)*33;
        yPosition = super.archerYpos;
        color = archercolor;
        screenbg = usedcanvas;    
    }

    /**
     * Es wird ein Archer an der von CanvasBG vorgegebenen Stelle und Farbe
     * gezeichnet
     * 
     */
    
    public void draw()
    {
        screenbg.setForegroundColor(color);
        screenbg.setVisible(true);
        // the head
        //screenbg.fillCircle(910,640,5,5);
        screenbg.fillCircle(xPosition+8,yPosition,14);
        // the body top to bottom
        screenbg.drawLine(xPosition+15,yPosition+13,xPosition+15,yPosition+53);
        // the arms
        screenbg.drawLine(xPosition, yPosition+8,xPosition+15, yPosition+23); //left arm
        screenbg.drawLine(xPosition+15,yPosition+23,xPosition+30,yPosition+8); //right arm
        // the legs
        screenbg.drawLine(xPosition,yPosition+68,xPosition+15,yPosition+53); //left leg
        screenbg.drawLine(xPosition+15,yPosition+53,xPosition+30,yPosition+68); //right leg
    }
    
    public void eraseArcher()
    {
        screenbg.eraseCircle(xPosition+8,yPosition,14);
        screenbg.setForegroundColor(super.bgColor); //da keine Methode, um die Linien zu entfernen existiert, weiß machen
        // the head
        //screenbg.fillCircle(910,640,5,5);
        screenbg.fillCircle(xPosition+8,yPosition,14);
        // the body top to bottom
        screenbg.drawLine(xPosition+15,yPosition+13,xPosition+15,yPosition+53);
        // the arms
        screenbg.drawLine(xPosition, yPosition+8,xPosition+15, yPosition+23); //left arm
        screenbg.drawLine(xPosition+15,yPosition+23,xPosition+30,yPosition+8); //right arm
        // the legs
        screenbg.drawLine(xPosition,yPosition+68,xPosition+15,yPosition+53); //left leg
        screenbg.drawLine(xPosition+15,yPosition+53,xPosition+30,yPosition+68); //right leg
    }
    
    public void stepRight()
    {
        // Erase at current position
        eraseArcher();
            
        // Calculate new position and decide which direction to walk
        xPosition += 1;
        
        // Re-draw at the newly calculated position
        draw();
    }   
    
    public void stepLeft()
    {
        // Erase at current position
        eraseArcher();
            
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
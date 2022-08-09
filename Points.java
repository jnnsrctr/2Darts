import java.awt.*;

/**
 * The class points sets the color of the player-scores 
 * and prints the scores on the dashboard.
 * 
 * @author      Johannes Richter
 *              Simon Cirdei
 *              Christoph Schramm
 *          
 * @version     v1.5 (03.06.2022 19:20)
 */
public class Points extends Game
{
    private Color textcolor;        // color of the text
    private Canvas screenbg;        // canvas where to plot the points
    private int player;             // player
    private int yPosition;          // yposition of the points
    private int score;              // score 

    /**
     * Constructor for instances of Points
     *
     * @param playerno    current player
     * @param playerscore score of player    
     * @param usedcanvas  canvas where to plot the points/score 
     */
    public Points(int playerno, int playerscore, Canvas usedcanvas)
    {
        player = playerno;
        score = playerscore;
        screenbg = usedcanvas;
        yPosition = super.scoreYpos + (player-1) * 20;  //y-Position of the score: access from super-class  
        
        //set the color of this score  
        if(player == 1) {
            textcolor = super.colorPlayer1;
        } else if(player == 2) {
            textcolor = super.colorPlayer2;
        }
    }
    
    /**
     * Prints the score on the dashboard
     */
    
    public void draw()
    {
        screenbg.setVisible(true);
        screenbg.setForegroundColor(textcolor);
        screenbg.drawString("Player " + player + ": " + score + " Points", super.scoreXpos, yPosition);
    }
}

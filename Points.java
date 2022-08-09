import java.awt.*;

/**
 * Beschreiben Sie hier die Klasse Points.
 * 
 * @author (Ihr Name) 
 * @version v1.2 (02.06.2022 15:35)
 */
public class Points extends Game
{
    private Color textcolor;      // color of the text
    private Canvas screenbg;  // canvas where to plot the arrow
    private int player;
    private int yPosition;
    private int score;

    /**
     * Konstruktor für Objekte der Klasse Points
     */
    public Points(int playerno, int playerscore, Canvas usedcanvas)
    {
        player = playerno;
        score = playerscore;
        screenbg = usedcanvas;
        yPosition = super.scoreYpos + (player-1) * 20;
        
        if(player == 1) {
            textcolor = super.colorPlayer1;
        } else if(player == 2) {
            textcolor = super.colorPlayer2;
        }
    }
    
    /**
     * Mal den Score auf die Fläche
     */
    
    public void draw()
    {
        screenbg.setVisible(true);
        screenbg.setForegroundColor(textcolor);
        screenbg.drawString("Score Player " + player + ": " + score, super.scoreXpos, yPosition);
    }
}

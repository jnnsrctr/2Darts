import java.util.Scanner;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 
 * Die Klasse game beinhaltet die wichtige Funktion, das Spielfeld zu erstellen.
 * 
 * @author JR, CS, SVC
 * @version v1.2 (02.06.2022 15:35)
 */ 
public class Terminal extends Game
{
    private Scanner scanner;        //Einführen des scanners
    private short a;                //Short für den Bereich bullseye
    private int ahalbe;             //Variable zum Rechnen
    private int starttarget;        //Anfang Zielscheibenbereich
    private CanvasBG screenbg;
    private boolean checker;
    private int ea, eb;
    
    public void newGame () {       
        scanner = new Scanner (System.in); // Soll die Eingabe scannen. Dafür wird in Zeile 1 der Java Scanner importiert
        welcomeText();
        boolean noInput = true;       //setzt noInput auf true
        super.currentPlayer = 1;
        while (noInput = true) {    
            String input = scanner.nextLine();  //Scanner der Eingabe für Tastenkürzel liest
            if (input.equals("s")) {
                startGame();
            }
            else if (input.equals("n")) {
                screenbg.clearField();
                swapPlayers();
                turn();
            }
            else if (input.equals("e")) {
                noInput = false;
                System.out.println("Game over.");
                returnPoints();
                System.exit(0);
            }
            else if (input.equals("h")) {
                howTo();
            }
            else if (input.equals("joe")) {
                screenbg = new CanvasBG ((short)30, 265);
                super.currentPlayer = 1;
                super.scorePlayer1 = 0;
                super.scorePlayer2 = 0;
                screenbg.printScore(super.scorePlayer1, super.scorePlayer2);
                screenbg.visible(true);
                screenbg.printArcher();
                turn();
            }
            else {
                error();
            }   
        }
    }
    
    public void inputForDartboard() {
        //ask for a
        System.out.println("Please enter a value for a [between 1 & "
        + (super.canvasHeight-super.wallStart)/9+"]. Hint: "
        +((super.canvasHeight-super.wallStart)/30)+" is a good value.");
        boolean aOK = false;
        while(!aOK) {
            Scanner inputDimensions = new Scanner(System.in);
            ea = inputDimensions.nextShort();            //process a
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
        System.out.println("Please enter a value for b [between "+(super.wallStart+(4*ea)+ahalbe)
        +" & "+(super.canvasHeight-(4*ea)-ahalbe)+"]. Hint: "
        +((super.canvasHeight-super.wallStart)/2+super.wallStart)+" is a good value.");
        boolean bOK = false;
        while(!bOK) {
            Scanner inputDimensions = new Scanner(System.in);
            eb = inputDimensions.nextInt();     //process b
            starttarget = eb - 4 * ea - ahalbe;     //required var for check b
            if(eb < super.wallStart+(4*ea)+ahalbe) {
                errorValue();
            } else if(eb > (super.canvasHeight-(4*ea)-ahalbe)) {
                errorValue();
            } else {
                bOK = true;
            }
        }
        
        System.out.println("Inputs saved. Let's go!");
    } 
    
    /**
     * Methode zur Ausgabe eines Error bezüglich der Oberfläche
     */
    public void errorValue() {
        System.out.println ("ERROR. Please enter a valid value. The limits can be seen above. You may try again.");
    }
    
    /**
     * Methode zur Ausgabe eines Willkommen-Textes
     */
    public void welcomeText() {
        System.out.println("Welcome to 2Darts" + "\n\n" + "Before playing, here is the overview:" +"\n");
        System.out.println("- The red area (bullseye) gives 50 points" + "\n" + "- Green (bull) gives 25 points" + "\n" + "- Black gives 10 points" + "\n" + "- Everything else 0 points");
        howTo();
    }
    
    /**
     * Die Methode help gibt die kleine Spielanleitung wieder.
     * Sie wird dort aufgerufen, wo sie benötit wird. 
     */
    public void howTo() {
        System.out.println("\n" +"------------------------------------------------");
        System.out.println("s = start game");
        System.out.println("n = next player");
        System.out.println("e = end game");
        System.out.println("h = help");
        System.out.println("------------------------------------------------");
    }
    
    /**
     * Error gibt einen Fehler wieder, wenn keine der geforderten Tasten gedrückt wird
     */
    public void error() {
        System.out.println("\n" +"------------------------------------------------" +"\n");
        System.out.println("Mistake! Please check your entry!");
        System.out.println("\n" + "If you need some help, press" + " h" + "");
    }

    public void startGame() {
        inputForDartboard();
        super.currentPlayer = 1;
        super.scorePlayer1 = 0;
        super.scorePlayer2 = 0;
        screenbg = new CanvasBG ((short)ea, starttarget);
        screenbg.visible(true);
        screenbg.printScore(super.scorePlayer1, super.scorePlayer2);
        screenbg.printArcher();
        turn();
    }

    public void swapPlayers() {
        screenbg.swapArcher(super.currentPlayer);
                
        if (super.currentPlayer == 1) {
            super.currentPlayer = 2;
        }
        else if(super.currentPlayer == 2) {
            super.currentPlayer = 1;
        }
    }

    public void turn() {
        System.out.println("It's player "+ super.currentPlayer +"'s turn.");
                
        int score = screenbg.shootArrow();
        
        //send won points to super
        if(super.currentPlayer == 1) {
            super.scorePlayer1 = super.scorePlayer1 + score;
        } else if(super.currentPlayer == 2) {
            super.scorePlayer2 = super.scorePlayer2 + score;
        }
        
        if(score == 50) {
            System.out.println("BULLSEYE! Player "+ super.currentPlayer +" got "+ score + " Points! :)");
        } else if(score == 0) {
            System.out.println("Player "+ super.currentPlayer +" missed. :(");
        } else {
            System.out.println("Player "+ super.currentPlayer +" got "+ score + " Points.");
        }
        
        screenbg.printScore(super.scorePlayer1, super.scorePlayer2);
    }
      
     /**
     *  Diese Methode gibt den aktuellen Punktestand auf der Konsole aus.
     */
    public void returnPoints()
    {
        if(super.scorePlayer1 > super.scorePlayer2) {
            System.out.println("Player 1 wins with " + super.scorePlayer1 + " points. Player 2 has " + super.scorePlayer2 + " points.");
        } else if(super.scorePlayer2 > super.scorePlayer1) {
            System.out.println("Player 2 wins with " + super.scorePlayer2 + " points. Player 1 has " + super.scorePlayer1 + " points.");
        } else if((super.scorePlayer1 == 0) && (super.scorePlayer1 == 0)) {
            System.out.println("Did you even play? Both players have " + super.scorePlayer1 + " points.");
        } else if(super.scorePlayer1 == super.scorePlayer2) {
            System.out.println("It's a tie! Both players have " + super.scorePlayer1 + " points.");
        }
        System.out.println("Press Ctrl+W to close this window.");
    }
}

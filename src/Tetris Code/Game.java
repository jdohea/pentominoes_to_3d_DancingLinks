import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.*;
import java.awt.*;
 
/**
 * Game
 * TODO: REMOVE all 'field =' as the field is a reference variable that is being passed as a parameter.
 * thats why we'll have to make a copy of the field for the bot as we only need to parse the reference for the field.
 */
public class Game extends JPanel {

    public static HighScores gameScores;
    public static int[][] field = new int[5][15];
    public static int [][] nextPieceField = new int[5][5];
    public static Piece currentPiece;
    public static Piece nextPiece;
    private static int liveScore =0;
    public static JLabel liveScoreLabel = new JLabel();
    public static UI ui1;
    public static UI nextPieceUI;
    public static JPanel mainPanel;
    public static JFrame f;
    public static JPanel temporaryPanel;
    public static JButton restartButton;
    public static JButton quitButton;

    /**
     * The Constructor starts the game
     */
    public Game(){
        gameScores = new HighScores();
        /**
         * TODO: if we change the pieces around in the piece class the next piece UI would only need to be 3 by 5
         */
        nextPieceUI = new UI(nextPieceField.length, nextPieceField[0].length,25);
        nextPiece = new Piece();
        
        ui1 = new UI(field.length, field[0].length, 25);
        ui1.setState(field);
        
        mainPanel = new JPanel();
        mainPanel.add(ui1);
        mainPanel.add(nextPieceUI);
        mainPanel.add(gameScores.getScores());
        mainPanel.add(liveScoreLabel);


        f = new JFrame("Tetris");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(350, 500);
        f.setVisible(true);
        f.addKeyListener(new KeyListener(){
            public void keyTyped(KeyEvent e) {   
            }
            public void keyReleased(KeyEvent e) {
            }
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                field = currentPiece.rotateClockwise(field);
                break;
                case KeyEvent.VK_DOWN:
                field =currentPiece.rotateAntiClockwise(field);
                break;
                case KeyEvent.VK_LEFT:
                field = currentPiece.moveLeft(field);
                break;
                case KeyEvent.VK_RIGHT:
                field = currentPiece.moveRight(field);
                break;
                case KeyEvent.VK_SPACE:
                field = currentPiece.dropPiece(field);
                break;
                }
            }
        });
        f.add(mainPanel);
        playGame();

    }

    /**
     * This method is the core of the game.
     * @return the score of the game played
     */
    public void playGame() {
        
        liveScore = 0;
        liveScoreLabel.setText("Your Current Score is: " + liveScore);
        field = setField(field);
        currentPiece = nextPiece;
        nextPiece = new Piece();
        nextPieceField = setField(nextPieceField);
        nextPiece.placePiece(nextPieceField);
        nextPiece.fixToField(nextPieceField);
        nextPieceUI.setState(nextPieceField);

        while(currentPiece.allowMove(field)){
            /**
             * TODO start bot here while current piece has not been placed yet.
             */
            field = currentPiece.placePiece(field);
            ui1.setState(field);
            Bot decideMove = new Bot(currentPiece, nextPiece, field);
            while(currentPiece.canMoveDownOne(field)){
                /**
                 * TODO: Put a timer here instead of a sleep method.
                 */
                try{Thread.sleep(5000);
                }catch (Exception e) { }//sleep for a bit

                field = currentPiece.downOne(field);
                ui1.setState(field);
            }
            
            field = currentPiece.fixToField(field);
            liveScore += getScore(field);
            liveScoreLabel.setText("Your Current Score is: " + liveScore);
            /**
             * TODO: we need to repaint the livescore component here 
             */
            field = deleteRows(field);
            currentPiece = nextPiece;
            
            nextPiece  = new Piece();
            nextPieceField = setField(nextPieceField);
            nextPiece.placePiece(nextPieceField);
            nextPiece.fixToField(nextPieceField);
            nextPieceUI.setState(nextPieceField);

        }
        currentPiece.placePiece(field);
        currentPiece.fixToField(field);
        ui1.setState(field);
        gameScores.setHighScore(liveScore);
        
        try{Thread.sleep(1000);
        }catch (Exception e) { }//sleep for a bit

        replay();
        playGame();
        
    }
    /**
     * Sets an empty field. -2 represents and empty field. The field is a static instance varibale of the class Game.
     */
    public static int[][] setField(int[][]field) {
        for(int i= 0; i<field.length; i++){
            for(int j = 0; j < field[0].length; j++){
                field[i][j] = -2;
            }
        }
        return field;
        
    }
    /**
     * Checks how many full rows are in this field and adds a point to the score gained for every full row
     * @param field
     * @return The score gained from the field parsed
     */
    public static int getScore(int[][] field) {
        int scoreGained = 0;
        for(int i = 0; i< field[0].length; i++){
            boolean fullRow = true;
            for(int j = 0 ; j< field.length; j++){
                if(field[j][i]<0){
                    fullRow = false;
                }
            }
            if(fullRow){
                scoreGained++;
            }
        }
        return scoreGained;
    }
    /**
     * Deletes all the full rows and moves the rows above down one for every row removed.
     * @param field
     * @return field with full rows deleted
     */
    public static int[][] deleteRows(int[][]field) {
            
        for(int i = 0; i< field[0].length; i++){
            boolean canDelete = true;
            for(int j = 0 ; j< field.length; j++){
                if(field[j][i]<0){
                    canDelete = false;
                }
            }
            if(canDelete&&i>0){
                 int x = i-1;
                while(x>0){
                    for(int j = 0; j<field.length; j++){
                        field[j][x+1] = field[j][x];
                    }
                    x--;
                }
                for(int j = 0; j<field.length; j++){
                    field[j][0] = -2; 
                }
            }
            
        }
        return field;
    }
    /**
     * This appears after the game has run through and offers a replay.
     * There is two options to 'Play Again' and to 'Quit', 
     * If play again is chosen, this button calls the playGame() method.
     * If 'Quit' is chose the button does nothing and the game simply runs through the rest of its code and the window closes.
     */
    public static void replay(){

        temporaryPanel = new JPanel();
        Color background = new Color(255,255,153);
        temporaryPanel.setBackground(background);

        restartButton = new JButton("RESTART"); 
        restartButton.setPreferredSize(new Dimension(200, 100));
        temporaryPanel.add(restartButton);

        quitButton = new JButton("QUIT");
        quitButton.setPreferredSize(new Dimension(200, 100));
        temporaryPanel.add(quitButton);

        //create two separates listener (one for each button)
        ActionListener listenerRestart = new ClickListener(); 
        ActionListener listenerQuit = new ClickListener(); 
        restartButton.addActionListener(listenerRestart); 
        quitButton.addActionListener(listenerQuit); 
        
        mainPanel.setVisible(false);
        temporaryPanel.setVisible(true);
        f.add(temporaryPanel);

    }
        
    public static void main(String[] args) {
       final Game game = new Game();   
    }    
}

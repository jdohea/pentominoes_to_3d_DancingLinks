/**
 * PossibleMove...maybe should be an inner class of the bot.
 */
public class PossibleMove {

    int[][]field;
    Piece currentPiece;
    Piece nextPiece;
    int nRotations;
    int mMoveRight;
    double fitness;
    /**
     * Constructor if we parse a next piece
     * 
     * TODO: make a constructor for if there is no next piece and 
     * 
     * @param rotation
     * @param movesRight
     * @param field
     * @param currentPiece
     * @param nextPiece
     */
    public PossibleMove(int rotation, int movesRight, int[][] field, Piece currentPiece, Piece nextPiece){
        this.currentPiece = currentPiece.clone();
        nRotations = rotation;
        mMoveRight = movesRight;
        this.field = Bot.copyField(field);
        makeMove();
        setFitness();

    }
    
    public double getFitness() {
        return fitness;
    }
    public void makeMove() {
        for(int i=0; i<nRotations; i++){
            currentPiece.rotateClockwise(field);
        }
        for(int i=0; i<mMoveRight; i++){
            currentPiece.moveRight(field);
        }
        currentPiece.dropPiece(field);
        currentPiece.fixToField(field);
    }

    public void setFitness() {
        int scoreGained = Game.getScore(field);
        Game.deleteRows(field);
        int heightOfField = heightOfField(field);
        int numberOfGaps = gapsInField(field, heightOfField);
        fitness = (scoreGained*1000) - (heightOfField*10000) - (numberOfGaps*10);
    }
    public static int gapsInField(int[][]field, int heightOfField) {
        int gaps=0;
        for(int i= field[0].length-1; i<heightOfField; i++){
            for(int j = 0; j<field.length; j++){
                if(field[j][i]<0){
                    gaps++;
                }
            }
        }
        return gaps;
        
    }
    public static int heightOfField(int[][]field) {
        for(int i=0; i<field[0].length; i++){
            for(int j = 0; j<field.length; j++){
                if(field[j][i]<0){
                    return field[0].length - i;
                }
            }
        }
        return 0;
    }

}
package FinalCodePhase3;
import java.util.*;
import java.util.List;

/**
 * Dancing Links search algorithm
 */

public class DancingLinks{

    static long start;

    static boolean canPickOne = true;
    static boolean canPickTwo = true;
    static boolean canPickThree = true;

    static int pieceOneBound = 0;//T,A
    static int pieceTwoBound = 0;//P,B
    static int pieceThreeBound = 0;//L,C

    static int pieceOneValue = 0;//T
    static int pieceTwoValue = 0;//P
    static int pieceThreeValue = 0;//L

    public static int currentBestVal=0;

    static int bestNoOnes=0;
    static int bestNoTwos=0;
    static int bestNoThrees=0;

    static int currentNumberOfPieceOne=0;
    static int currentNumberOfPieceTwo=0;
    static int currentNumberOfPieceThree=0;

    static final boolean verbose = true;

    class DancingNode{
        DancingNode L;
        DancingNode R;
        DancingNode U;
        DancingNode D;
        ColumnNode C;
        int pieceID;

        // hooks node n1 `below` current node
        DancingNode attachBelow(DancingNode n1){
            assert (this.C == n1.C);
            n1.D = this.D;
            n1.D.U = n1;
            n1.U = this;
            this.D = n1;
            return n1;
        }

        // hoke a node n1 to the right of `this` node
        DancingNode attachRight(DancingNode n1){
            n1.R = this.R;
            n1.R.L = n1;
            n1.L = this;
            this.R = n1;
            return n1;
        }

        void unlinkLR(){
            this.L.R = this.R;
            this.R.L = this.L;
        }

        void relinkLR(){
            this.L.R = this.R.L = this;
        }

        void unlinkUD(){
            this.U.D = this.D;
            this.D.U = this.U;
        }

        void relinkUD(){
            this.U.D = this.D.U = this;
        }

        protected DancingNode clone() {
            DancingNode x = new DancingNode(this.C,this.pieceID);
            x.R = this.R;
            x.L = this.L;
            return x;
        }

        public DancingNode(){
            L = R = U = D = this;
        }

        public DancingNode(ColumnNode c, int pieceID){
            this();
            this.pieceID = pieceID;
            C = c;
        }

    }

    class ColumnNode extends DancingNode{
        int size; // number of ones in current column
        int name;

        public ColumnNode(int n){
            super();
            size = 0;
            name = n;
            C = this;
        }

        void cover(){
            unlinkLR();
            for(DancingNode i = this.D; i != this; i = i.D){
                for(DancingNode j = i.R; j != i; j = j.R){
                    j.unlinkUD();
                    j.C.size--;
                }
            }
            header.size--; // not part of original
        }

        void uncover(){
            for(DancingNode i = this.U; i != this; i = i.U){
                for(DancingNode j = i.L; j != i; j = j.L){
                    j.C.size++;
                    j.relinkUD();
                }
            }
            relinkLR();
            header.size++; // not part of original
        }
    }

    private ColumnNode header;
    private SolutionHandler handler;
    private List<DancingNode> answer;

    private void search(){
        if (header.R == header) { // all the columns removed
            System.out.print("blah");
        } else {

            ColumnNode c = selectColumnNodeHeuristic();
            if (columnContainsValidPiece(c)) {
                c.cover();
                for (DancingNode r = c.D; r != c; r = r.D) {
                    while (!canChoose(r)) {
                        r = r.D;
                    }
                    answer.add(r);
                    if (((currentNumberOfPieceOne * pieceOneValue +
                            currentNumberOfPieceTwo * pieceTwoValue +
                            currentNumberOfPieceThree * pieceThreeValue)
                            > currentBestVal)&&(
                            ((currentNumberOfPieceThree+currentNumberOfPieceTwo+currentNumberOfPieceOne)*5)<=Parcels.VOLUME)
                            ) {
                        copySolution();
                    }
                    for (DancingNode j = r.R; j != r; j = j.R) {
                        j.C.cover();

                    }if(System.currentTimeMillis()-start<5000) {
                        search();
                    }
                    r = answer.remove(answer.size() - 1);
                    removeFromTracker(r);
                    c = r.C;
                    for (DancingNode j = r.L; j != r; j = j.L) {
                        j.C.uncover();
                    }
                }
                c.uncover();
            }
        }
    }

    private boolean columnContainsValidPiece(ColumnNode c) {
        for(DancingNode r = c.D; r != c; r = r.D){
            if(
                    ((r.pieceID==1)&&canPickOne)||
                            ((r.pieceID==2)&&canPickTwo)||
                            ((r.pieceID==3&&canPickThree))

            ){
                return true;
            }
        }
        return false;
    }

    private void copySolution() {
        currentBestVal=
                currentNumberOfPieceOne*pieceOneValue+
                        currentNumberOfPieceTwo*pieceTwoValue+
                        currentNumberOfPieceThree*pieceThreeValue;
        bestNoOnes=currentNumberOfPieceOne;
        bestNoTwos=currentNumberOfPieceTwo;
        bestNoThrees=currentNumberOfPieceThree;
        handler.handleSolution(answer);
    }

    private Boolean canChoose(DancingNode r) {
        if(r.pieceID==1){
            if(currentNumberOfPieceOne<pieceOneBound){
                currentNumberOfPieceOne++;
                if(currentNumberOfPieceOne==pieceOneBound){
                    canPickOne=false;
                }
                return true;
            }
        }else if(r.pieceID==2){
            if(currentNumberOfPieceTwo<pieceTwoBound){
                currentNumberOfPieceTwo++;
                if(currentNumberOfPieceTwo==pieceTwoBound){
                    canPickTwo=false;
                }
                return true;
            }
        }else{
            if(r.pieceID==3){
                if(currentNumberOfPieceThree<pieceThreeBound){
                    currentNumberOfPieceThree++;
                    if(currentNumberOfPieceThree==pieceThreeBound){
                        canPickThree=false;
                    }
                    return true;
                }
            }
        }
        return false;
    }
    private void removeFromTracker(DancingNode r){
        if(r.pieceID==1){
            canPickOne=true;
            currentNumberOfPieceOne--;
        }else if(r.pieceID==2){
            canPickTwo=true;
            currentNumberOfPieceTwo--;
        }else{
            canPickThree=true;
            currentNumberOfPieceThree--;
        }
    }

    private ColumnNode selectColumnNodeHeuristic(){
        int min = Integer.MAX_VALUE;
        ColumnNode ret = null;
        for(ColumnNode c = (ColumnNode) header.R; c != header; c = (ColumnNode) c.R){
            if (c.size < min){
                min = c.size;
                ret = c;
            }
        }
        return ret;
    }
    // grid is a grid of 0s and 1s to solve the exact cover for
    // returns the root column header node
    private ColumnNode makeDLXBoard(int[][] grid){
        final int COLS = grid[0].length;
        final int ROWS = grid.length;

        ColumnNode headerNode = new ColumnNode(-1);
        ArrayList<ColumnNode> columnNodes = new ArrayList<ColumnNode>();

        for(int i = 0; i < COLS; i++){
            ColumnNode n = new ColumnNode(i);
            columnNodes.add(n);
            headerNode = (ColumnNode) headerNode.attachRight(n);
        }
        headerNode = headerNode.R.C;

        for(int i = 0; i < ROWS; i++){
            DancingNode prev = null;
            for(int j = 0; j < COLS; j++){
                if (grid[i][j] == 1){
                    ColumnNode col = columnNodes.get(j);
                    DancingNode newNode = new DancingNode(col, Parcels.ids[i]);
                    if (prev == null)
                        prev = newNode;
                    col.U.attachBelow(newNode);
                    prev = prev.attachRight(newNode);
                    col.size++;
                }
            }
        }

        headerNode.size = COLS;

        return headerNode;
    }


    // Grid consists solely of 1s and 0s. Undefined behaviour otherwise
    public DancingLinks(int[][] grid){

        this(grid, new DefaultHandler());
        if(pieceOneBound==0){
            canPickOne=false;
        }
        if(pieceTwoBound==0){
            canPickTwo=false;
        }
        if(pieceThreeBound==0){
            canPickThree=false;
        }
    }

    public DancingLinks(int[][] grid, SolutionHandler h){
        header = makeDLXBoard(grid);
        handler = h;
    }

    public void runSolver(){
        answer = new LinkedList<DancingNode>();
        if(canPickOne||canPickTwo||canPickThree) {

            search();
        }
    }

}

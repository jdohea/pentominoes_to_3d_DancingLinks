package FinalCodePhase3;
import java.util.ArrayList;
/**
 *
 * Piece
 * Code that generates out DataBase for all rotations of each pentomino in the container.
 *
 * There is a capability below to alternate between divide and conquer or a full container search.
 *
 */
class Parcels {
    public static boolean useLPT = false;
    public static double pieceOneValuePerUnit;
    public static double pieceTwoValuePerUnit;
    public static double pieceThreeValuePerUnit;
    public static int pieces [][][];
    public static int LPTpieces[][][] = {
            {// T piece and it's four rotations
                    {1,1,1,1},
                    {1,2,1,1},
                    {1,3,1,1},
                    {2,2,1,1},
                    {3,2,1,1}
            },
            {
                    {1,3,1,1},
                    {2,3,1,1},
                    {2,2,1,1},
                    {2,1,1,1},
                    {3,3,1,1}
            },
            {
                    {3,3,1,1},
                    {1,2,1,1},
                    {2,2,1,1},
                    {3,2,1,1},
                    {3,1,1,1}
            },
            {
                    {1,1,1,1},
                    {2,1,1,1},
                    {3,1,1,1},
                    {2,2,1,1},
                    {2,3,1,1}
            },


            {//P piece and it's four rotations
                    {1,1,1,2},
                    {1,2,1,2},
                    {1,3,1,2},
                    {2,3,1,2},
                    {2,2,1,2}
            },
            {
                    {1,2,1,2},
                    {1,1,1,2},
                    {2,2,1,2},
                    {2,1,1,2},
                    {3,1,1,2}
            },
            {
                    {1,2,1,2},
                    {2,2,1,2},
                    {1,1,1,2},
                    {2,1,1,2},
                    {2,3,1,2}
            },
            {
                    {1,2,1,2},
                    {2,2,1,2},
                    {3,2,1,2},
                    {2,1,1,2},
                    {3,1,1,2}
            },
            {
                    {1,3,1,2},
                    {1,2,1,2},
                    {1,1,1,2},
                    {2,2,1,2},
                    {2,1,1,2}
            },
            {
                    {1,2,1,2},
                    {1,2,1,2},
                    {2,2,1,2},
                    {2,1,1,2},
                    {3,2,1,2}
            },
            {
                    {1,3,1,2},
                    {1,2,1,2},
                    {2,3,1,2},
                    {2,2,1,2},
                    {2,1,1,2}
            },
            {
                    {2,2,1,2},
                    {3,2,1,2},
                    {1,1,1,2},
                    {2,1,1,2},
                    {3,1,1,2}
            },
            {//L piece and it's 4 rotations
                    {1,4,1,3},
                    {1,3,1,3},
                    {1,2,1,3},
                    {1,1,1,3},
                    {2,1,1,3}
            },
            {
                    {1,1,1,3},
                    {2,1,1,3},
                    {3,1,1,3},
                    {4,1,1,3},
                    {4,2,1,3}
            },
            {
                    {1,4,1,3},
                    {2,4,1,3},
                    {2,3,1,3},
                    {2,2,1,3},
                    {2,1,1,3},
            },
            {
                    {1,1,1,3},
                    {1,2,1,3},
                    {2,2,1,3},
                    {3,2,1,3},
                    {4,2,1,3}
            }
    };

    public static int ABCpieces[][][] = {
            {// A piece and it's three rotations

                    {1,1,1,1},
                    {1,2,1,1},
                    {2,2,1,1},
                    {2,1,1,1},
                    {1,1,2,1},
                    {1,2,2,1},
                    {2,2,2,1},
                    {2,1,2,1},
                    {1,1,3,1},
                    {1,2,3,1},
                    {2,2,3,1},
                    {2,1,3,1},
                    {1,1,4,1},
                    {1,2,4,1},
                    {2,2,4,1},
                    {2,1,4,1},
            },
            {
                    {1,2,1,1},
                    {1,1,1,1},
                    {2,1,1,1},
                    {2,2,1,1},
                    {3,1,1,1},
                    {3,2,1,1},
                    {4,1,1,1},
                    {4,2,1,1},
                    {1,2,2,1},
                    {1,1,2,1},
                    {2,1,2,1},
                    {2,2,2,1},
                    {3,1,2,1},
                    {3,2,2,1},
                    {4,1,2,1},
                    {4,2,2,1},
            },
            {
                    {1,4,1,1},
                    {1,3,1,1},
                    {1,2,1,1},
                    {1,1,1,1},
                    {2,4,1,1},
                    {2,3,1,1},
                    {2,2,1,1},
                    {2,1,1,1},
                    {1,4,2,1},
                    {1,3,2,1},
                    {1,2,2,1},
                    {1,1,2,1},
                    {2,4,2,1},
                    {2,3,2,1},
                    {2,2,2,1},
                    {2,1,2,1},
            },


            {//B piece and it's six rotations
                    {1,3,1,2},
                    {1,2,1,2},
                    {1,1,1,2},
                    {2,3,1,2},
                    {2,2,1,2},
                    {2,1,1,2},
                    {1,3,2,2},
                    {1,2,2,2},
                    {1,1,2,2},
                    {2,3,2,2},
                    {2,2,2,2},
                    {2,1,2,2},
                    {1,3,3,2},
                    {1,2,3,2},
                    {1,1,3,2},
                    {2,3,3,2},
                    {2,2,3,2},
                    {2,1,3,2},
                    {1,3,4,2},
                    {1,2,4,2},
                    {1,1,4,2},
                    {2,3,4,2},
                    {2,2,4,2},
                    {2,1,4,2},
            },
            {
                    {1,4,1,2},
                    {1,3,1,2},
                    {1,2,1,2},
                    {1,1,1,2},
                    {2,4,1,2},
                    {2,3,1,2},
                    {2,2,1,2},
                    {2,1,1,2},
                    {1,4,2,2},
                    {1,3,2,2},
                    {1,2,2,2},
                    {1,1,2,2},
                    {2,4,2,2},
                    {2,3,2,2},
                    {2,2,2,2},
                    {2,1,2,2},
                    {1,4,3,2},
                    {1,3,3,2},
                    {1,2,3,2},
                    {1,1,3,2},
                    {2,4,3,2},
                    {2,3,3,2},
                    {2,2,3,2},
                    {2,1,3,2},
            },
            {
                    {1,4,1,2},
                    {1,3,1,2},
                    {1,2,1,2},
                    {1,1,1,2},
                    {2,4,1,2},
                    {2,3,1,2},
                    {2,2,1,2},
                    {2,1,1,2},
                    {3,4,1,2},
                    {3,3,1,2},
                    {3,2,1,2},
                    {3,1,1,2},
                    {1,4,2,2},
                    {1,3,2,2},
                    {1,2,2,2},
                    {1,1,2,2},
                    {2,4,2,2},
                    {2,3,2,2},
                    {2,2,2,2},
                    {2,1,2,2},
                    {3,4,2,2},
                    {3,3,2,2},
                    {3,2,2,2},
                    {3,1,2,2},
            },
            {
                    {1,2,1,2},
                    {1,1,1,2},
                    {2,2,1,2},
                    {2,1,1,2},
                    {3,2,1,2},
                    {3,1,1,2},
                    {1,2,2,2},
                    {1,1,2,2},
                    {2,2,2,2},
                    {2,1,2,2},
                    {3,2,2,2},
                    {3,1,2,2},
                    {1,2,3,2},
                    {1,1,3,2},
                    {2,2,3,2},
                    {2,1,3,2},
                    {3,2,3,2},
                    {3,1,3,2},
                    {1,2,4,2},
                    {1,1,4,2},
                    {2,2,4,2},
                    {2,1,4,2},
                    {3,2,4,2},
                    {3,1,4,2},
            },
            {
                    {1,2,1,2},
                    {1,1,1,2},
                    {2,2,1,2},
                    {2,1,1,2},
                    {3,2,1,2},
                    {3,1,1,2},
                    {4,2,1,2},
                    {4,1,1,2},
                    {1,2,2,2},
                    {1,1,2,2},
                    {2,2,2,2},
                    {2,1,2,2},
                    {3,2,2,2},
                    {3,1,2,2},
                    {4,2,2,2},
                    {4,1,2,2},
                    {1,2,3,2},
                    {1,1,3,2},
                    {2,2,3,2},
                    {2,1,3,2},
                    {3,2,3,2},
                    {3,1,3,2},
                    {4,2,3,2},
                    {4,1,3,2},
            },
            {
                    {1,3,1,2},
                    {1,2,1,2},
                    {1,1,1,2},
                    {2,3,1,2},
                    {2,2,1,2},
                    {2,1,1,2},
                    {3,3,1,2},
                    {3,2,1,2},
                    {3,1,1,2},
                    {4,3,1,2},
                    {4,2,1,2},
                    {4,1,1,2},
                    {1,3,2,2},
                    {1,2,2,2},
                    {1,1,2,2},
                    {2,3,2,2},
                    {2,2,2,2},
                    {2,1,2,2},
                    {3,3,2,2},
                    {3,2,2,2},
                    {3,1,2,2},
                    {4,3,2,2},
                    {4,2,2,2},
                    {4,1,2,2},
            },
            {//C piece and it's one rotation
                    {1,3,1,3},
                    {1,2,1,3},
                    {1,1,1,3},
                    {2,3,1,3},
                    {2,2,1,3},
                    {2,1,1,3},
                    {3,3,1,3},
                    {3,2,1,3},
                    {3,1,1,3},
                    {1,3,2,3},
                    {1,2,2,3},
                    {1,1,2,3},
                    {2,3,2,3},
                    {2,2,2,3},
                    {2,1,2,3},
                    {3,3,2,3},
                    {3,2,2,3},
                    {3,1,2,3},
                    {1,3,3,3},
                    {1,2,3,3},
                    {1,1,3,3},
                    {2,3,3,3},
                    {2,2,3,3},
                    {2,1,3,3},
                    {3,3,3,3},
                    {3,2,3,3},
                    {3,1,3,3},
            }
    };
    //ALWAYS put the largest side on the Z dimension
    public static  int CONTAINER_X_DIM = 33;
    public static final int CONTAINER_Y_DIM = 8;
    public static final int CONTAINER_Z_DIM = 5;

    public static final int LTP_ROWSinSOLUTION = 39120;
    public static int ROWSinSOLUTION = LTP_ROWSinSOLUTION;
    public static int LAYER;
    public static int VOLUME;

    public ArrayList<ArrayList<Cube>> parcels = new ArrayList<ArrayList<Cube>>();
    public int A [][];//[NUMBER OF TOTAL PLACEMENTS OF (ROWS IN THE MATRIX),][NUMBER OF CUBES IN THE CONTAINER (COLUMNS)+ 3 PIECES]
    public static int ids [] = new int [ROWSinSOLUTION];
    static int divideAndConquerLayer=0;

    public Parcels(){
        if(useLPT){
            pieces = LPTpieces;
            pieceOneValuePerUnit = (double)DancingLinks.pieceOneValue/5.0;
            pieceTwoValuePerUnit = (double)DancingLinks.pieceTwoValue/5.0;
            pieceThreeValuePerUnit =(double)DancingLinks.pieceThreeValue/5.0;

        }else{
            pieces = ABCpieces;
            pieceOneValuePerUnit = (double)DancingLinks.pieceOneValue/2.0;
            pieceTwoValuePerUnit = (double)DancingLinks.pieceTwoValue/3.0;
            pieceThreeValuePerUnit =(double)DancingLinks.pieceThreeValue/3.375;
        }
        /**
         * Alternate between divide and conquer or full container search using the two methods below
         */
        //divideAndConquer();
        fullContainerSearch();
        Run.launchProgram();
    }

    public void divideAndConquer(){
        CONTAINER_X_DIM=11;
        A = new int [ROWSinSOLUTION][CONTAINER_X_DIM*CONTAINER_Y_DIM*CONTAINER_Z_DIM];
        LAYER = CONTAINER_X_DIM*CONTAINER_Y_DIM;
        VOLUME = LAYER*CONTAINER_Z_DIM;
        createParcels();
        createAmatrix();
        removeZeros();
        int pieceOneUsed=0;
        int pieceTwoUsed=0;
        int pieceThreeUsed=0;
        int bestValTrack=0;
        for(int i = 0; i<3; i++) {
            DancingLinks.currentNumberOfPieceOne =0;
            DancingLinks.currentNumberOfPieceTwo =0;
            DancingLinks.currentNumberOfPieceThree =0;
            DancingLinks.currentBestVal=0;
            DancingLinks DLX = new DancingLinks(A);
            DancingLinks.start=System.currentTimeMillis();
            DLX.runSolver();
            DancingLinks.pieceOneBound -= DancingLinks.bestNoOnes;
            DancingLinks.pieceTwoBound -= DancingLinks.bestNoTwos;
            DancingLinks.pieceThreeBound -= DancingLinks.bestNoThrees;
            pieceOneUsed += DancingLinks.bestNoOnes;
            pieceTwoUsed +=DancingLinks.bestNoTwos;
            pieceThreeUsed += DancingLinks.bestNoThrees;
            bestValTrack+=DancingLinks.currentBestVal;
            DancingLinks.bestNoOnes=0;
            DancingLinks.bestNoTwos=0;
            DancingLinks.bestNoThrees=0;
            divideAndConquerLayer+=11;

        }
        DancingLinks.currentBestVal=bestValTrack;
        DancingLinks.bestNoOnes =pieceOneUsed;
        DancingLinks.bestNoTwos =pieceTwoUsed;
        DancingLinks.bestNoThrees =pieceThreeUsed;

    }

    public void fullContainerSearch(){
        CONTAINER_X_DIM=33;
         A = new int [ROWSinSOLUTION][CONTAINER_X_DIM*CONTAINER_Y_DIM*CONTAINER_Z_DIM];
        LAYER = CONTAINER_X_DIM*CONTAINER_Y_DIM;
        VOLUME = LAYER*CONTAINER_Z_DIM;
        createParcels();
        createAmatrix();
        //removeZeros();
        DancingLinks.currentNumberOfPieceOne =0;
        DancingLinks.currentNumberOfPieceTwo =0;
        DancingLinks.currentNumberOfPieceThree =0;
        DancingLinks.currentBestVal=0;
        DancingLinks DLX = new DancingLinks(A);
        DancingLinks.start=System.currentTimeMillis();
        DLX.runSolver();

    }

    public void createParcels() {
        for(int i = 0; i<pieces.length; i++){
            ArrayList<Cube> q = new ArrayList<Cube>();
            for(int j = 0; j< pieces[i].length; j++){
                q.add(new Cube(pieces[i][j]));
            }
            parcels.add(q);
        }
    }

    public void createAmatrix(){
        int [] order = mostValueablePiece();
        int indexOfA = 0;//because we need header column to be all 0's
        for (int blah =0; blah<order.length; blah++) {
            for (int i = 0; i < parcels.size(); i++) {
                for (int z = 0; z < CONTAINER_X_DIM; z++) {
                    for (int y = 0; y < CONTAINER_X_DIM; y++) {
                        for (int x = 0; x < CONTAINER_X_DIM; x++) {
                            if(order[blah]==parcels.get(i).get(1).pieceID) {
                                if (inContainer(i, x, y, z, CONTAINER_X_DIM, CONTAINER_Y_DIM, CONTAINER_Z_DIM)) {
                                    placeInA(indexOfA, i, parcels.get(i).get(1).pieceID, x, y, z, 1);
                                    indexOfA++;
                                }
                                if (inContainer(i, x, y, z, CONTAINER_Z_DIM, CONTAINER_X_DIM, CONTAINER_Y_DIM)) {
                                    placeInA(indexOfA, i, parcels.get(i).get(1).pieceID, x, y, z, 2);
                                    indexOfA++;
                                }
                                if (inContainer(i, x, y, z, CONTAINER_Y_DIM, CONTAINER_Z_DIM, CONTAINER_X_DIM)) {
                                    placeInA(indexOfA, i, parcels.get(i).get(1).pieceID, x, y, z, 3);
                                    indexOfA++;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean inContainer (int parcelNumber, int x, int y, int z, int Xdim, int Ydim, int Zdim){
        ArrayList<Cube> q = parcels.get(parcelNumber);
        for(int i = 0; i<q.size(); i++){
            if((q.get(i).dim1+x>Xdim)||(q.get(i).dim2+y>Ydim)||(q.get(i).dim3+z>Zdim)){
                return false;
            }
        }
        return true;
    }
    public void placeInA (int index, int parcelNumber, int pieceID, int x, int y, int z, int scenario){
        //Scenarios:
        //below formula cube dimension that was compared with CONTAINER_Z_DIM is multiplied by the layer
        //cube dimension that was compared with CONTAINER_Y_DIM is multiplied by the CONTAINER_X_DIM
        //then the dimension that was compared with the CONTAINER_X_DIM gets added alone
        ids[index]=pieceID;
        ArrayList<Cube> q = parcels.get(parcelNumber);
        if(scenario==1){
            for(int i =0; i<q.size(); i++){
                A[index][((q.get(i).dim3+z-1)*LAYER) + ((q.get(i).dim2+y-1)*CONTAINER_X_DIM) + (q.get(i).dim1+x-1)]=1;
            }
        }else if(scenario==2){
            for(int i =0; i<q.size(); i++){
                A[index][((q.get(i).dim1+x-1)*LAYER) + ((q.get(i).dim3+z-1)*CONTAINER_X_DIM) + (q.get(i).dim2+y-1)]=1;
            }
        }else{
            for(int i =0; i<q.size(); i++){
                A[index][((q.get(i).dim2+y-1)*LAYER) + ((q.get(i).dim1+x-1)*CONTAINER_X_DIM) + (q.get(i).dim3+z-1)]=1;
            }
        }
    }
    public void removeZeros(){
        int zeroRowIndex = 0;
        for(int i = 0; i< A.length; i++){
            boolean containsOne = false;
            for(int j = 0; j<A[0].length; j++){
                if(A[i][j]==1){
                    containsOne=true;
                }
            }
            if(!containsOne){
                zeroRowIndex=i-1;
                break;
            }
        }
        int B[][] = new int[zeroRowIndex+1][A[0].length];
        for(int i = 0; i<B.length; i++){
            for(int j = 0; j<B[0].length; j++){
                B[i][j]=A[i][j];
            }
        }
        A = B;
    }
    public int [] mostValueablePiece(){
        int ans [] = new int[3];
        if(pieceOneValuePerUnit<pieceTwoValuePerUnit){
            if(pieceOneValuePerUnit<pieceThreeValuePerUnit){
                ans[2]=1;
                if(pieceTwoValuePerUnit<pieceThreeValuePerUnit){
                    ans[1]=2;
                    ans[0]=3;
                }else{
                    ans[1]=3;
                    ans[0]=2;
                }
            }else{
                ans[1]=1;
                ans[0]=2;
                ans[2]=3;
            }
        }else if(pieceOneValuePerUnit<pieceThreeValuePerUnit){
            ans[0]=3;
            ans[1]=1;
            ans[2]=2;
        }else{
            ans[0]=1;
            if(pieceTwoValuePerUnit>pieceThreeValuePerUnit){
                ans[1]=2;
                ans[2]=3;
            }else{
                ans[1]=3;
                ans[2]=2;
            }
        }

        return ans;
    }

    public void printA (){
        for(int i =0; i<A.length; i++){
            for(int j = 0; j<A[0].length; j++){
                System.out.print(A[i][j]);
            }
            System.out.println("");
        }
    }
    public static void main(String[] args) {
        Run.arguments = args;
        Parcels go = new Parcels();

    }

}
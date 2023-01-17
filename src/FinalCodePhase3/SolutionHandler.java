package FinalCodePhase3;
import FinalCodePhase3.DancingLinks.*;
import java.util.*;

public interface SolutionHandler{
    void handleSolution(List<DancingNode> solution);
}

class DefaultHandler implements SolutionHandler{
     public void handleSolution(List<DancingNode> answer) {
        for (DancingNode n : answer) {
            getCoordinates(n.C.name, n.pieceID);
            DancingNode tmp = n.R;
            while (tmp != n) {
                getCoordinates(tmp.C.name, tmp.pieceID);
                tmp = tmp.R;
            }
        }
    }
    public static void getCoordinates(int BoxNumber, int pieceID){
        int x;
        int y;
        int z;
        int num = BoxNumber + 1;
        int p = num/ Parcels.LAYER;
        int remainder1 = num%Parcels.LAYER;
        if(remainder1==0){
            z = p - 1;
            y = Parcels.CONTAINER_Y_DIM - 1;
            x = Parcels.CONTAINER_X_DIM - 1;
        }else{
            z = p;
            int r = remainder1/Parcels.CONTAINER_X_DIM;
            int remainder2 = remainder1%Parcels.CONTAINER_X_DIM;
            if(remainder2==0){
                y = r-1;
                x = Parcels.CONTAINER_X_DIM - 1;
            } else{
                y = r;
                x = remainder2 - 1;
            }
        }
        Run.field[z][y][x+Parcels.divideAndConquerLayer] = pieceID;
    }
}
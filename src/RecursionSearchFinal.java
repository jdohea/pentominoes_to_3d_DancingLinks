import java.util.Scanner;

import javax.naming.directory.SearchResult;

import java.util.Arrays;

public class RecursionSearchFinal{

    public static int horizontalGridSize = 0;
    public static int verticalGridSize = 0;
    
    // static UI class to display the board
    // public static UI ui = new UI(horizontalGridSize, verticalGridSize, 50);

    // sets the field size
    public static int[][] field()
    {
        // initialize an empty board
        int[][] field = new int[horizontalGridSize][verticalGridSize];

        for(int i = 0; i < field.length; i++)
        {
            for(int j = 0; j < field[i].length; j++)
            {
                // -1 in the state matrix corresponds to empty square
                // any positive number identifies the ID of the pentomino
            	field[i][j] = -1;
            }
        }
        return field;
        
    }
    // takes char[] input and transforms it into sorted (!) int[] pentIDs 
    private static int[] inputIDs (char[] input) {

        //all existing pentominoes
        char[] pentoes = {'X','I','Z','T','U','V','W','Y','L','P','N','F'};
        String pentoStr =  String.valueOf(pentoes);
       
       //initialize an array with the indexes of the input
        int[] inputIDs = new int[input.length];
        
        for (int i=0; i<input.length; i++) {
            inputIDs[i] = pentoStr.indexOf(input[i]);
        }

        // sorting array
        Arrays.sort(inputIDs);

    	int [] tmp = new int[inputIDs.length];
    		for (int i=1; i<=inputIDs.length; i++) {
    			tmp[inputIDs.length-i] = inputIDs[i-1];
    		}
    		for (int i=0; i<inputIDs.length; i++) {
    			inputIDs[i]=tmp[i];
    		}
    		return inputIDs;
    }

    public static boolean fieldChecker(int[][]field){
        // check whether complete field is filled
			for (int i = 0; i < field.length; i++) {
				for (int j = 0; j < field[i].length; j++) {
					if (field[i][j] == -1){
                        return false; //the field is not completely filled 
					}
				}
            }
        return true;//the field is completely filled
    }

    // adds a pentomino to the position on the field (overriding current board at that position)
    public static void addPiece(int[][] field, int[][] piece, int pieceID, int x, int y)
    {
        for(int i = 0; i < piece.length; i++) // loop over x position of pentomino
        {
            for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
            {
                if (piece[i][j] == 1)
                {
                    // add the ID of the pentomino to the board if the pentomino occupies this square
                    field[x + i][y + j] = pieceID;
                }
            }
        }
    }

    // removes a pentomino to the position on the field (overriding current board at that position)
    public static void removePiece(int[][] field, int[][] piece, int pieceID, int x, int y)
    {
        for(int i = 0; i < piece.length; i++) // loop over x position of pentomino
        {
            for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
            {
                if (piece[i][j] == 1)
                {
                    // add the ID of the pentomino to the board if the pentomino occupies this square
                    field[x + i][y + j] = -1;
                }
            }
        }
    }

    //checks whether a pentomino to fit will overlap with the ones already places
    public static boolean checkOverlap(int[][] field, int[][] piece, int pieceID, int x, int y)
    {
        for(int i = 0; i < piece.length; i++){ // loop over x position of pentomino 
            for (int j = 0; j < piece[i].length; j++){ // loop over y position of pentomino
            	if(piece[i][j]==1){
					if (field[x + i][y + j]>-1){
                        return false; //will overlaps
					}
				}
            }
		}
		return true; //no risk to overlap
    }

    public static boolean searcher (int[] inputs, int indexTracker, int[][] field){
        
        boolean solution = false; //we must set the solution to be false and only set it to be true in one case, when we have placed all the pentominos and there is no need to continue searching
                                //there is a condition in every loop to only keep searching if no solution can be found
        
        //BASE CASE
        if(indexTracker==-1){//if n is -1 there is no more pentominos to place on the board so we should check to see if this is a valid solution
            if(fieldChecker(field)){//checking if we have found a solution
                UI ui = new UI(horizontalGridSize, verticalGridSize, 50);
    			ui.setState(field);             //display the field when we find the solution
                System.out.println("Solution found");
                solution = true;
            }else{solution = false;}//because we return false here this will cause a backtrack and attempt a different solution

        }
        // n is greater than -1 so we still have pentominos to place!
        if(indexTracker>-1){
            int pentID = (inputs[indexTracker]);
            int mutation=0;
            while((!solution)&&(mutation<PentominoDatabase.data[pentID].length)){//we will only continue searching if we have no solution and we still have mutations of a pentomino to check for 
                // we check for each mutation of each pentiomino until we have a solution
                int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];

                //if the piece doesn't fit in the grid this mutation of the pentomino will not work
                if ((horizontalGridSize < pieceToPlace.length)||(verticalGridSize < pieceToPlace[0].length)){
                    
                    solution = false; 
                
                //if the piece will only just fit in one way we need to fix one of the dimension coordinates of the field to 0
                }else if(verticalGridSize == pieceToPlace[0].length){
                    int y=0;
                    int x = 0;
                    while((x<horizontalGridSize-pieceToPlace.length+1)&&(!solution)){
                            int nextIndexTracker=indexTracker;
                            boolean checked = checkOverlap(field, pieceToPlace, pentID, x, y);//we only want to place a piece if there is no overlap PRUNING
                            if(checked){ // if no risk to overlap
                                addPiece(field, pieceToPlace, pentID, x, y);
                                nextIndexTracker--;
                                solution = searcher(inputs,nextIndexTracker,field);
                                if(!solution){
                                    removePiece(field,pieceToPlace, pentID,x,y);//we must remove the piece we added before proceeding with the next iteration PRUNING, there is no point on clearing the field and starting again
                                    x++;
                                }
                            }
                            if(!checked){x++;}
                    }
                //if the piece will only just fit the other way we need to fix the other dimension coordinate of the field to 0
                }else if(horizontalGridSize == pieceToPlace.length){
                    int x=0;
                    int y = 0; 
                    while((y<verticalGridSize-pieceToPlace[0].length+1)&&(!solution)){
                        int nextIndexTracker = indexTracker;
                        boolean checked = checkOverlap(field, pieceToPlace, pentID, x, y);//we only want to place a piece if there is no overlap PRUNING
                        if(checked){
                            addPiece(field, pieceToPlace, pentID, x, y);
                            nextIndexTracker--;
                            solution = searcher(inputs,nextIndexTracker,field);
                            if(!solution){
                                removePiece(field,pieceToPlace, pentID,x,y);//we must remove the piece we added before proceeding with the next iteration PRUNING, there is no point on clearing the field and starting again
                                y++;
                            }
                        }
                        if(!checked){y++;}
                    }
                }else{
                    int x=0; 
                    while((x<horizontalGridSize-pieceToPlace.length+1)&&(!solution)){
                        int y=0;
                        while(y<verticalGridSize-pieceToPlace[0].length+1){
                            int nextIndexTracker = indexTracker;
                            boolean checked = checkOverlap(field, pieceToPlace, pentID, x, y);//we only want to place a piece if there is no overlap PRUNING
                            if(checked){
                                addPiece(field, pieceToPlace, pentID, x, y);
                                nextIndexTracker--;
                                solution = searcher(inputs,nextIndexTracker,field);
                                if(!solution){
                                    removePiece(field,pieceToPlace, pentID,x,y);//we must remove the piece we added before proceeding with the next iteration PRUNING, there is no point on clearing the field and starting again
                                                                                //we will test with Adele's method to see if its faster to store a copy instead of adding and removing from the same field every time
                                   y++;
                                }
                            }
                            if(!checked){y++;}
                        }
                        x++;
                    }
                }
                mutation++;
            }
        }
        return solution;
    }

    public static void main(String[] args)
    {   
   		Scanner in = new Scanner(System.in);
    	//input the size of the field
    	System.out.println("Type in the horizontal and vertical grid size respectively");
    	horizontalGridSize = in.nextInt();
    	verticalGridSize = in.nextInt();
    	in.nextLine();
    	//check if the field is divisible by 5, so the pentominos fit perfectly in there
    	if ((horizontalGridSize * verticalGridSize) % 5 != 0) {
    		System.out.println("Incorrect input: the area of the field is not divisible by 5.");
    	} else {
    		//input the usable pentominos
    		System.out.println("Now type in the pentonimos, where there are no empty spaces in between the letters.");
    		System.out.println("The usable pentominos are: P, X, T, Z, F, U, V, W, Y, I, N and L");
    		String pentonimos = in.nextLine();
    		//put the inputs in an array
    		char[] inputs = pentonimos.toCharArray();
    		//check if there are enough pentominos to fill the entire field and if all are used.
    		if (inputs.length * 5 != horizontalGridSize * verticalGridSize) {
    			System.out.println("The area of the pentominos is not equal to the area of the field.");
    		} else {
    			//check if the inputs are all valid, e.g. no lower case letters, empty spaces, wrong capital letters or other symbols.
    			for (int i = 0; i<inputs.length; i++) {
    				if (inputs[i] == 'P' ||inputs[i] == 'X' ||inputs[i] == 'T' ||inputs[i] == 'Z' ||inputs[i] == 'F' ||inputs[i] == 'U' ||inputs[i] == 'V' ||inputs[i] == 'W' ||inputs[i] == 'Y' ||inputs[i] == 'I' ||inputs[i] == 'N' ||inputs[i] == 'L') {

    				} else {
    					System.out.println("This input does not match the usable pentominos: " + inputs[i]);
    					System.exit(1);
    				}
    			}
    			//start running the branching algorithm
    			 int [] intinputs = inputIDs(inputs); //should return an optimally ordered integer array to find a solution
    			 //this integer will be used to determine which pentomino we will place on the board
    			 int indexTracker = inputs.length-1;
    			 						//this is why we need to order the intinputs[] array as the first pentomino we place will be the one at the end of the array(the one with the largest index)
                           		     	//then we reduce this each recursion, changing which pentomino we place until q<0 and we have placed all the pentomin
                 int[][] field = field();
                 long now = System.currentTimeMillis();//start the timer only after we have all the inputs and we are ready to call the searcher method
                 boolean searchResult = searcher(intinputs,indexTracker,field);
                 if(!searchResult){
                    System.out.println("There is no possible solution for this combination of Pentominos");
                 }
                 long end = System.currentTimeMillis();
                 System.out.println("This search took " +(end-now)+"ms");
    		}
    	}
    	in.close();
    }
}




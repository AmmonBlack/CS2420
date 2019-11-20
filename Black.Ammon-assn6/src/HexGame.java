import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class HexGame {
    IntUpTree redState;
    IntUpTree blueState;
    char[] spaces;
    int boardSideLength;
    int boardSize;

    HexGame(int boardSideLength) {
        this.boardSideLength = boardSideLength;
        boardSize = boardSideLength*boardSideLength;

        //the last two positions hold the wall as a whole
        redState = new IntUpTree(boardSideLength * boardSideLength + 4);
        blueState = new IntUpTree(boardSideLength * boardSideLength + 4);

        spaces = new char[boardSideLength * boardSideLength];
        Arrays.fill(spaces, '.');
    }

    HexGame(){
        this(11);
    }


    public void playGame(String moveFile){
        ArrayList<Integer> moves = readMoves(moveFile);

        boolean blueTurn = true;
        for (int move : moves) {
            if (spaces[move] == '.'){
                if (blueTurn) {
                    spaces[move] = 'b'; //mark spot as blue
                    ArrayList<Integer> neighbors = this.getNeighbors(move);
                    for (int n : neighbors) {
                        if(spaces[n] == 'b') //only union if they are blue
                            blueState.union(move,n);
                        if(n == boardSize-2 || n == boardSize-4) // or if it is an edge
                            blueState.union(move,n);
                    }
                }
                else {
                    spaces[move] = 'r'; //mark spot as red
                    ArrayList<Integer> neighbors = this.getNeighbors(move);
                    for (int n : neighbors) {
                        if(spaces[n] == 'r') //only union if they are red
                            redState.union(move,n);
                        if(n == boardSize-1 || n == boardSize-3) // or if it is an edge
                            redState.union(move,n);
                    }
                }
                this.printBoard();
                if (this.haveWinner()) {
                    this.haveWinner();
                    break;
                }
                blueTurn = !blueTurn;
            }
        }
    }

    private boolean haveWinner(){
        if (redState.find(boardSize-1)>0
                && redState.find(boardSize-1) == redState.find(boardSize-3)) {
            System.out.println("Red has won!");
            return true;
        }
        else if (blueState.find(boardSize-2)>0
                && blueState.find(boardSize-2) == blueState.find(boardSize-4)) {
            System.out.println("Blue has won!");
            return true;
        }
        return false;
    }


    private ArrayList<Integer> getNeighbors(int spot){
        int[] differences = {-boardSideLength, -boardSideLength+1, -1, 1, boardSideLength-1, boardSideLength};

        ArrayList<Integer> out = new ArrayList<>();
        for( int diff: differences){
            if(diff+spot > 0 && diff +spot < boardSize && Math.abs(spot%11-(diff+spot%11))<=1){
                out.add(diff+spot);

                if (this.checkWall(spot) != -1)
                    out.add(this.checkWall(spot));

            }
        }
        return out;
    }


    //This will return which wall is being touched,
    // -1 is top, -2 is left, -3 is bottom, -4 is right, or -1 if no wall is touched
    private int checkWall(int space){
        if( space < boardSideLength ) { return boardSize - 1; }
        else if ( space % boardSideLength == 0 ) { return boardSize - 2; }
        else if ( space > boardSize - boardSideLength ) { return boardSize - 3; }
        else if ( space % boardSideLength == boardSideLength - 1) { return boardSize - 4; }
        else return -1;
    }

    ArrayList<Integer> readMoves(String filename){
        try {
            Scanner fileReader = new Scanner(new File(filename));
            ArrayList<Integer> moves = new ArrayList();
            while (fileReader.hasNextInt()){
                moves.add(fileReader.nextInt()-1); // We need to subtract by one to move into same refrence as index
            }
            return moves;

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void printBoard(){
        for(int i = 0; i < spaces.length; i++){
            if (i % boardSideLength == 0)
                System.out.print("\n");
            System.out.print(spaces[i]+ " ");
        }
        System.out.println("\n");
    }

    public static void main(String[] args) {
        HexGame test = new HexGame(11);
        test.printBoard();
        test.playGame("moves2.txt");
    }
}

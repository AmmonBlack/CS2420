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

    /**
     * Will initialize all data.
     * spaces will be filled with '.'
     * @param boardSideLength this is the length of one side of a square hexboard
     */
    HexGame(int boardSideLength) {
        this.boardSideLength = boardSideLength;
        boardSize = boardSideLength*boardSideLength;

        //the last four positions hold the wall as a whole
        redState = new IntUpTree(boardSize + 4);
        blueState = new IntUpTree(boardSize + 4);

        spaces = new char[boardSize];
        Arrays.fill(spaces, '.');
    }

    HexGame(){
        this(11);
    }

    /**
     * utilizes the other functions to run a game of Hex
     * @param moveFile the file containing the moves
     */
    public void playGame(String moveFile){
        ArrayList<Integer> moves = readMoves(moveFile);

        boolean blueTurn = true;
        for (int move : moves) {
            if (spaces[move] == '.'){
                if (blueTurn) {
                    spaces[move] = 'b'; //mark spot as blue
                    ArrayList<Integer> neighbors = this.getNeighbors(move);
                    for (int n : neighbors) {
                        if(n == boardSize+1 || n == boardSize+3) //union if it is an edge
                            blueState.union(move,n);
                        else if(n < boardSize && spaces[n] == 'b') //only union if they are blue
                            blueState.union(move,n);

                    }
                }
                else {
                    spaces[move] = 'r'; //mark spot as red
                    ArrayList<Integer> neighbors = this.getNeighbors(move);
                    for (int n : neighbors) {
                        if(n == boardSize || n == boardSize+2) // or if it is an edge
                            redState.union(move,n);
                        else if(n < boardSize && spaces[n] == 'r') //only union if they are red
                            redState.union(move,n);
                    }
                }
                this.printBoard();
                if (this.haveWinner()) {
                    break;
                }
                blueTurn = !blueTurn;
            }
            System.out.println("Red UpTree state: \n" + redState.toString());
            System.out.println("Blue UpTree state: \n" + blueState.toString());
        }
    }

    /**
     * Determines if the wall's for red or blue respectively have been connected
     * @return
     */
    private boolean haveWinner(){
        if (redState.find(boardSize)>0
                && redState.find(boardSize) == redState.find(boardSize+2)) {
            System.out.println("Red has won!");
            return true;
        }
        else if (blueState.find(boardSize+1)>0
                && blueState.find(boardSize+1) == blueState.find(boardSize+3)) {
            System.out.println("Blue has won!");
            return true;
        }
        return false;
    }


    /**
     * Figures out who neighbors are
     *
     * @param spot spot to identify neighbors from
     * @return Returns an arraylist of neighbors
     */
    private ArrayList<Integer> getNeighbors(int spot){
        int[] differences = {-boardSideLength, -boardSideLength+1, -1, 1, boardSideLength-1, boardSideLength};

        ArrayList<Integer> out = new ArrayList<>();
        for( int diff: differences){
            if(diff+spot > 0 && diff +spot < boardSize && Math.abs(spot%boardSideLength-((diff+spot)%boardSideLength))<=1){
                out.add(diff+spot);

                if (this.checkWall(spot) != -1)
                    out.add(this.checkWall(spot));

            }
        }
        return out;
    }


    /**
     * This will return which wall is being touched,
     * 0 is top, +1 is left, +2 is bottom, +3 is right, or -1 if no wall is touched
     *
     * @param space checks the space for whether it boarders any wall
     * @return the index for a wall it boarders
     */

    private int checkWall(int space){
        if( space < boardSideLength ) { return boardSize; }
        else if ( space % boardSideLength == 0 ) { return boardSize + 1; }
        else if ( space > boardSize - boardSideLength ) { return boardSize + 2; }
        else if ( space % boardSideLength == boardSideLength - 1) { return boardSize + 3; }
        else return -1;
    }

    /**
     * Will scan a new file in
     *
     * @param filename name of the file
     * @return arrayList of moves
     */
    private ArrayList<Integer> readMoves(String filename){
        try {
            Scanner fileReader = new Scanner(new File(filename));
            ArrayList<Integer> moves = new ArrayList();
            while (fileReader.hasNextInt()){
                moves.add(fileReader.nextInt()-1); // We need to subtract by one to move into same refrence as index
            }
            fileReader.close();
            return moves;

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Prints out a board with proper demensions
     */
    public void printBoard(){
        for(int i = 0; i < spaces.length; i++){
            if (i % boardSideLength == 0)
                System.out.print("\n");
            System.out.print(spaces[i]+ " ");
        }
        System.out.println("\n");
    }

    /**
     * runs playGame for two scenarios
     * @param args
     */
    public static void main(String[] args) {
        HexGame game = new HexGame(11);
        game.printBoard();

        //play first game
        game.playGame("moves.txt");

        //play second game
        game.playGame("moves2.txt");
    }
}

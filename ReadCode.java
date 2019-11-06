import java.util.Scanner;
import java.io.File;


public class ReadCode {
    public static void main(String[] args) {
        try {
            LeftistHeap<Term> h = new LeftistHeap<>();
            String filename = "small.txt";
            Scanner reader = new Scanner( new File( filename ) );
            while ((reader.hasNext())) {
                String word = reader.next();
                long freq = reader.nextInt();
             }

            System.out.println( "\n\n\n " + h.deleteMax() );
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
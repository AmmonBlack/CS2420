import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;


public class AutoCorrect {
    public static void main(String[] args) {
        try {
            String filename = "SortedWords.txt";
            Scanner reader = new Scanner( new File( filename ) );
            ArrayList<Term> wordInfo = new ArrayList<>();

            while ((reader.hasNext())) {
                String word = reader.next();
                long freq = reader.nextInt();
                wordInfo.add(new Term(word, freq));
             }

            reader.close();

            Scanner user = new Scanner(System.in);
            System.out.println("Enter the beginning of a word and the number of results you would like to see:");

            String word = user.next();
            int count = user.nextInt();

            user.close();

            ArrayList<Term> autoFillOptions = binarySearch(word, wordInfo);
            SkewHeap<Term> heap = new SkewHeap<>();

            for (int i = 0; i < autoFillOptions.size(); i++)
                heap.insert(autoFillOptions.get(i));

            System.out.println("The " + count + " most probable words starting with " + word +" are:");
            for (int i = 0; i < count; i++)
                System.out.println(heap.pop().word);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    static ArrayList<Term> binarySearch(String search, ArrayList<Term> list){
        return binarySearch(search.toLowerCase(), list, 0, list.size() - 1);

    }

    static ArrayList<Term> binarySearch(String search, ArrayList<Term> list, int low, int high)
    {
        int mid = (low + high)/2;
        if (mid >= high || mid <= low) {
            System.out.println("Failed to find option");
            return null;
        }
        if (list.get(mid).word.startsWith(search)){
            ArrayList<Term> words = new ArrayList<>();
            words.add(list.get(mid));

            boolean upDone = false;
            boolean downDone = false;
            int upCount = mid + 1;
            int downCount = mid - 1;
            while(!upDone || !downDone)
            {
                if(!list.get(upCount).word.startsWith(search))
                    upDone = true;
                else
                    words.add(list.get(upCount)); upCount++;

                if(!list.get(downCount).word.startsWith(search))
                    downDone = true;
                else
                    words.add(list.get(downCount)); downCount--;
            }
            return words;
        }

        if (list.get(mid).word.compareTo(search) < 0)
            return binarySearch(search, list, mid+1, high);
        else
            return binarySearch(search, list, low, mid-1);
    }



}
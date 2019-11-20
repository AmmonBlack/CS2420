//We can test by initializing a IntUpTree and unioning several and printing the results

import java.util.Arrays;

public class IntUpTree {

    int[] path;

    //Construct tree

    IntUpTree(int size){
        path = new int[size];
        Arrays.fill(path, -1);
    }

    int find(int key){
        if (key > path.length || key < 0){return -path.length+1;} //Check to see if it's in the tree
        if (path[key] < 0)
            return key;

        // else statement below compresses as it calls find recursively
        else {
            int root = find(path[key]);
            path[key] = root;
            return root;
        }
    }

    boolean union(int a, int b){
        int rootA = find(a);
        int rootB = find(b);

        //this will check if union was called on valid integers
        if (rootA == -path.length+1 || rootB == -path.length+1)
            return false;

        //this will check if they have the same root and do nothing if they do
        if (rootA == rootB)
            return false;

        if( rootA < rootB){
            path[rootA] += path[rootB];
            path[rootB] = rootA;
            return true;
        }
        else {
            path[rootB] += path[rootA];
            path[rootA] = rootB;
            return true;
        }
    }

    public String toString() {
        String out = "";
        for (int i = 0; i<path.length; i++) {
            if (i % 20 == 0) out = out+ "\n";
            out = out + i + ": " + path[i] + "  ";
        }
        return out;
    }


}

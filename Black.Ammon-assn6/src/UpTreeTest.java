public class UpTreeTest {

    public static void main(String[] args) {
        IntUpTree test = new IntUpTree(5);
        System.out.println("Before Unions" + test.toString());

        test.union(0,1);
        System.out.println("\nAfter union(0,1)" + test.toString());
        if (test.find(0)== test.find(1))
            System.out.println("success");
        else
            System.out.println("failure");

        test.union(0, 2);
        System.out.println("\nAfter union(0,2)" + test.toString());
        if (test.find(0)== test.find(2))
            System.out.println("success");
        else
            System.out.println("failure");

        //make smaller tree
        test.union(3,4);
        System.out.println("\nAfter union(3,4)" + test.toString());
        if (test.find(3)== test.find(4))
            System.out.println("success");
        else
            System.out.println("failure");

        test.union(4,0);
        System.out.println("\nAfter union(4,0)" + test.toString());
        if (test.find(4)== test.find(0))
            System.out.println("success");
        else
            System.out.println("failure");

        test.union(4,0);
        System.out.println("\nAfter union(4,0)" + test.toString());
        if (test.find(4)== test.find(0))
            System.out.println("compression success");
        else
            System.out.println("compression failure");
    }
}

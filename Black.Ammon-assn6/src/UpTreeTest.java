public class UpTreeTest {

    public static void main(String[] args) {
        IntUpTree test = new IntUpTree(5);
        System.out.println(test.toString());

        test.union(0,1);
        System.out.println(test.toString());

        test.union(0, 2);
        System.out.println(test.toString());

        //make smaller tree
        test.union(3,4);
        System.out.println(test.toString());

        test.union(4,0);
        System.out.println(test.toString());
    }
}

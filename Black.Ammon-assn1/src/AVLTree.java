// AvlTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x (unimplemented)
// boolean contains( x )  --> Return true if x is present
// boolean remove( x )    --> Return true if x was present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements an AVL tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class AVLTree<AnyType extends Comparable<? super AnyType>>
{
    /**
     * Construct the tree.
     */
    public AVLTree( )
    {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert( AnyType x )
    {
        root = insert( x, root );
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public void remove( AnyType x )
    {
        root = remove( x, root );
    }

    public AnyType getRoot(){
        return root.element;
    }


    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param current the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<AnyType> remove( AnyType x, AvlNode<AnyType> current )
    {
        if( current == null )
            return current;   // Item not found; do nothing

        int compareResult = x.compareTo( current.element );

        if( compareResult < 0 )
            current.left = remove( x, current.left );
        else if( compareResult > 0 )
            current.right = remove( x, current.right );
        else if( current.left != null && current.right != null ) // Two children
        {
            current.element = findMin( current.right ).element;
            current.right = remove( current.element, current.right );
        }
        else
            current = ( current.left != null ) ? current.left : current.right;
        return balance( current );
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public AnyType findMin( )
    {
        if( isEmpty( ) )
            throw new RuntimeException( );
        return findMin( root ).element;
    }


    public  void  deleteMin( ){

        root =  deleteMin(root);
     }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public AnyType findMax( )
    {
        if( isEmpty( ) )
            throw new RuntimeException( );
        return findMax( root ).element;
    }

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return true if x is found.
     */
    public boolean contains( AnyType x )
    {
        return contains( x, root );
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( )
    {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree( String label)
    {
        System.out.println(label);
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root,"" );
    }

    private static final int ALLOWED_IMBALANCE = 1;

    // Assume current is either balanced or within one of being balanced
    private AvlNode<AnyType> balance( AvlNode<AnyType> current )
    {
        if( current == null )
            return current;

        if( height( current.left ) - height( current.right ) > ALLOWED_IMBALANCE )
            if( height( current.left.left ) >= height( current.left.right ) )
                current = rightRotation( current );
            else
                current = doubleRightRotation( current );
        else
        if( height( current.right ) - height( current.left ) > ALLOWED_IMBALANCE )
            if( height( current.right.right ) >= height( current.right.left ) )
                current = leftRotation( current );
            else
                current = doubleLeftRotation( current );

        current.height = Math.max( height( current.left ), height( current.right ) ) + 1;
        return current;
    }

    public void checkBalance( )
    {
        checkBalance( root );
    }

    private int checkBalance( AvlNode<AnyType> current )
    {
        if( current == null )
            return -1;

        if( current != null )
        {
            int hl = checkBalance( current.left );
            int hr = checkBalance( current.right );
            if( Math.abs( height( current.left ) - height( current.right ) ) > 1 ||
                    height( current.left ) != hl || height( current.right ) != hr )
                System.out.println( "\n\n***********************OOPS!!" );
        }

        return height( current );
    }


    /**
     * Internal method to insert into a subtree.  Duplicates are allowed
     * @param in the item to insert.
     * @param current the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<AnyType> insert( AnyType in, AvlNode<AnyType> current )
    {
        if( current == null )
            return new AvlNode<>( in, null, null );

        int compareResult = in.compareTo( current.element );

        if(compareResult ==0)

        if( compareResult < 0 )
            current.left = insert( in, current.left );
        else
            current.right = insert( in, current.right );

        return balance( current );
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param current the node that roots the tree.
     * @return node containing the smallest item.
     */
    private AvlNode<AnyType> findMin( AvlNode<AnyType> current )
    {
        if( current == null )
            return current;

        while( current.left != null )
            current = current.left;
        return current;
    }

    private AvlNode<AnyType> deleteMin( AvlNode<AnyType> current )
    {
        if( current == null)
            return current;

        if(current.left == null){
            current = findMax(current.right);
        }
        else current.left = deleteMin(current.left);

        return balance(current);
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param current the node that roots the tree.
     * @return node containing the largest item.
     */
    private AvlNode<AnyType> findMax( AvlNode<AnyType> current )
    {
        if( current == null )
            return current;

        while( current.right != null )
            current = current.right;
        return current;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param current the node that roots the tree.
     * @return true if x is found in subtree.
     */
    private boolean contains( AnyType x, AvlNode<AnyType> current )
    {
        while( current != null )
        {
            int compareResult = x.compareTo( current.element );

            if( compareResult < 0 )
                current = current.left;
            else if( compareResult > 0 )
                current = current.right;
            else
                return true;    // Match
        }

        return false;   // No match
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param current the node that roots the tree.
     */
    private void printTree( AvlNode<AnyType> current, String indent )
    {
        if( current != null )
        {
            printTree( current.right, indent+"   " );
            System.out.println( indent+ current.element + "("+ current.height  +")" );
            printTree( current.left, indent+"   " );
        }
    }

    /**
     * Return the height of node t, or -1, if null.
     */
    private int height( AvlNode<AnyType> current )
    {   if (current==null) return -1;
        return current.height;
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> rightRotation(AvlNode<AnyType> current )
    {
        AvlNode<AnyType> theLeft = current.left;
        current.left = theLeft.right;
        theLeft.right = current;
        current.height = Math.max( height( current.left ), height( current.right ) ) + 1;
        theLeft.height = Math.max( height( theLeft.left ), current.height ) + 1;
        return theLeft;
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> leftRotation(AvlNode<AnyType> current )
    {
        AvlNode<AnyType> theRight = current.right;
        current.right = theRight.left;
        theRight.left = current;
        current.height = Math.max( height( current.left ), height( current.right ) ) + 1;
        theRight.height = Math.max( height( theRight.right ), current.height ) + 1;
        return theRight;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> doubleRightRotation( AvlNode<AnyType> current )
    {
        current.left = leftRotation( current.left );
        return rightRotation( current );

    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> doubleLeftRotation(AvlNode<AnyType> current )
    {
        current.right = rightRotation( current.right );
        return leftRotation( current );
    }

    private static class AvlNode<AnyType>
    {
        // Constructors
        AvlNode( AnyType theElement )
        {
            this( theElement, null, null );
        }

        AvlNode( AnyType theElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt )
        {
            element  = theElement;
            left     = lt;
            right    = rt;
            height   = 0;
        }

        AnyType           element;      // The data in the node
        AvlNode<AnyType>  left;         // Left child
        AvlNode<AnyType>  right;        // Right child
        int               height;       // Height
    }

    /** The tree root. */
    public AvlNode<AnyType> root;


    // Test program
    public static void main( String [ ] args ) {
        AVLTree<Integer> t = new AVLTree<>();
        AVLTree<Dwarf> t2 = new AVLTree<>();

        String[] nameList = {"Snowflake", "Sneezy", "Doc", "Grumpy", "Bashful", "Dopey", "Happy", "Doc", "Grumpy", "Bashful", "Doc", "Grumpy", "Bashful"};
        for (int i=0; i < nameList.length; i++)
            t2.insert(new Dwarf(nameList[i]));

        t2.printTree( "The Tree" );

        t2.remove(new Dwarf("Bashful"));

        t2.printTree( "The Tree after delete Bashful" );
        for (int i=0; i < 8; i++) {
            t2.deleteMin();
            t2.printTree( "\n\n The Tree after deleteMin" );
        }
    }

}

//SkewHeap class
//
//*****Public Operations*****
//
//
//
//
//********Errors*************
//Throws UnderflowException


/**
 * implements SkewHeap
 * @autho Ammon Black
 */

public class SkewHeap<E extends Comparable<? super E>>
{
	//Data
	SkewNode<E> root;

	/**
	 * Constructor
	 */
	public SkewHeap(){ this(null); }

	public SkewHeap(E element){
		root = SkewNode(element);
	}

	public void insert(E element){
		root = merge( new SkewNode<E>(element), root );

	}

	/**
	 * returns item with the highest priority
	 */
	public E pop()
	{
		E pop = root.element;
		this.deleteMin();
		return pop;
	}

	public void deleteMin()
	{
		root = merge( root.left, root.right );
	}

	private SkewNode<E> merge( SkewNode<E> up, SkewNode<E> down )
	{
		if ( up == null || up.element == null)
			return down;
		if ( down == null || down.element == null)
			return up;
		if ( up.element.compareTo( down.element ) > 0 ){
			swap(down);
			return merge( up, down.right );
		}
		else{
			swap(up);
			return merge( down, up );
		}
	}

	private void swap( SkewNode<E> node )
       	{
		SkewNode<E> temp = node.left;
		node.left = node.right;
		node.right = temp;
	}

	private static class SkewNode<E>{
		//Data
		E           element;
		SkewNode<E> left;
		SkewNode<E> right;
	
		/**
		 * Constructor
		 */
		SkewNode( E element ) { this(element, null, null); }

		SkewNode( E element, SkewNode<E> lt, SkewNode<E> rt) {
			this.element = element;
			this.left = lt;
			this.right = rt;
		}
	}


}

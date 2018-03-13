package core;

/**
 * A single variable encoder
 * 
 * @author Daniel Joplin Mar 12, 2018
 */
public interface Encoder<E> {
	
	public void insert(E e);

	public default int insertAndEncoder(E e) {
		insert(e);
		return encode(e);
	}

	public int encode(E e);
	
	public E decode(int x);
	
	public void updateViews();

}

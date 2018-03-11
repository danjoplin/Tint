package core;

public interface SupervisedEncoder<E, C> extends Encoder<E> {
	
	public int putAndEncode(E e, C c);
	
	public void update(E e, C c);

}

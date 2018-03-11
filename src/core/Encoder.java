package core;

public interface Encoder<E> {
	
	public int encode(E e);
	
	public E decode(int x);
	
	public void updateViews();
	
	public void registerView(EncoderView view);

}

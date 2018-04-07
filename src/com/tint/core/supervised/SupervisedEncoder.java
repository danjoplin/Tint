package com.tint.core.supervised;

import com.tint.core.Encoder;

public interface SupervisedEncoder<E, C> extends Encoder<E> {
	
	public int putAndEncode(E e, C c);
	
	public void update(E e, C c);

}

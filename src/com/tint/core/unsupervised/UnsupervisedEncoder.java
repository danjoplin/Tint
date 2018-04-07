package com.tint.core.unsupervised;

import com.tint.core.Encoder;

public interface UnsupervisedEncoder<E> extends Encoder<E> {
	
	public int putAndEncode(E e);
	
	public void update(E e);
	
}

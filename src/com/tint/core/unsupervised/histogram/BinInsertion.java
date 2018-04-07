package com.tint.core.unsupervised.histogram;

public class BinInsertion {
	
	public final int index;
	public final boolean isExact;
	
	public BinInsertion(int index) {
		this(index, true);
	}
	
	public BinInsertion(int index, boolean isExact) {
		this.index = index;
		this.isExact = isExact;
	}
	
	@Override
	public String toString() {
		return index+":"+isExact;
	}
}

package com.tint.core.util.collections.pairheap;

public class PairingHeapEntry<T> {
	
	private T value;
	
	transient PairingHeapEntry<T> parent, child, sibling;
	
	public PairingHeapEntry(T element) {
		this.value = element;
	}
	
	public T get() {
		return this.value;
	}
	
	public PairingHeapEntry<T> parent() {
		return this.parent;
	}
	
	public PairingHeapEntry<T> child() {
		return this.child;
	}
	
	public PairingHeapEntry<T> leftSibling() {
		if (parent == null) {
			return null;
		}
		
		if (parent.child != null && parent.child.sibling != this) {
			return parent.child.sibling;
		}
		
		return null;
	}
	
	public PairingHeapEntry<T> rightSibling() {
		return this.sibling;
	}
	
	public boolean setValue(T value) {
//		if (this.value == null)
//			return false;
		this.value = value;
		return true;
	}

}

package com.tint.core.util.collections.pairheap;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class PairingHeap<T> implements Queue<T> {
	
	private final Comparator<T> comp;
	
	private HashMap<T, PairingHeapEntry<T>> valuesToEntries = new HashMap<>();
	
	private transient PairingHeapEntry<T> root;
	private int size = 0;
	
	public PairingHeap(Comparator<T> comparator) {
		this.comp = comparator;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	
	@Override
	public boolean contains(Object o) {
		return valuesToEntries.containsKey(o);
	}

	@Override
	public Iterator<T> iterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		if (isEmpty())
			return false;
		
		PairingHeapEntry<T> entry = this.valuesToEntries.get(o);
		if (entry == null)
			return false;
		
		this.removeEntry(entry);
		return true;
	}
	
	private void removeEntry(PairingHeapEntry<T> e) {
		
		PairingHeapEntry<T> first = e.child;
		e.child = null;
		
		if (first == null)	return;
		
		PairingHeapEntry<T> c = first.sibling==null?null:first.sibling.sibling;
		first = link(first, first.sibling);
		PairingHeapEntry<T> prev = first;
		
		while (c != null) {
			PairingHeapEntry<T> next = c.sibling==null?null:c.sibling.sibling;
			c = link(c, c.sibling);
			prev.sibling = c;
			prev = c;
			c = next;
		}
		
		c = first.sibling;
		
		while (c!=null) {
			PairingHeapEntry<T> next = c.sibling;
			first = link(first, c);
			c = next;
		}
		
		first.parent = e.parent;
		if (e.parent == null) {
			this.root = first;
		} else {
			c = e.parent.child;
			if (c==e) {
				e.parent.child = first;
				first.sibling = e.sibling;
			} else {
				while (c.sibling != e)
					c = c.sibling;
				c.sibling = first;
				first.sibling = e.sibling;
			}
			e.parent = null;
			e.sibling = null;
		}
		this.valuesToEntries.remove(e.get());
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return c.stream().allMatch(valuesToEntries::containsKey);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return c.stream().anyMatch(this::add);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return c.stream().anyMatch(this::remove);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return valuesToEntries.keySet().stream().filter(x -> !c.contains(x)).anyMatch(this::remove);
	}

	@Override
	public void clear() {
		clear(root);
	}
	
	private void clear(PairingHeapEntry<T> r) {
		if (r == null)
			return;
		
		root.parent = null;
		PairingHeapEntry<T> c = root.child;
		
		while (c != null) {
			clear(c);
			PairingHeapEntry<T> next = c.sibling;
			c.sibling = null;
			c = next;
		}
		
		r.child = null;
	}

	@Override
	public boolean add(T e) {
		if (e == null || valuesToEntries.containsKey(e))
			return false;
		
		PairingHeapEntry<T> entry = new PairingHeapEntry<T>(e);
		this.valuesToEntries.put(e, entry);
		
		this.root = link(root, entry);
		this.size++;
		return false;
	}

	@Override
	public boolean offer(T e) {
		return add(e);
	}

	@Override
	public T peek() {
		return root.get();
	}

	@Override
	public T poll() {
		if (root == null)
			return null;
		T out = peek();
		removeEntry(root);
		return out;
	}

	@Override
	public Object[] toArray() {
		return this.valuesToEntries.values().toArray();
	}

	@Override
	public <E> E[] toArray(E[] a) {
		return this.valuesToEntries.values().toArray(a);
	}

	@Override
	public T remove() {
		if (isEmpty())
			throw new NoSuchElementException("top is null");
		return poll();
	}

	@Override
	public T element() {
		if (isEmpty())
			throw new NoSuchElementException("top is null");
		return peek();
	}
	
	public boolean union(PairingHeap<T> other) {
		if (other == null || other.isEmpty())
			return false;
		
		this.root = link(this.root, other.root);
		this.size += other.size;
		return true;
	}
	
	private PairingHeapEntry<T> link(PairingHeapEntry<T> a, PairingHeapEntry<T> b) {
		if (a == null)
			return b;
		
		if (b == null)
			return a;
		
		if (compare(a, b) < 0)
			return link(b, a);
		
		b.parent = a;
		PairingHeapEntry<T> tmp = a.child;
		a.child = b;
		b.sibling = tmp;
		return a;
	}
	
	private int compare(PairingHeapEntry<T> a, PairingHeapEntry<T> b) {
		return comp.compare(a.get(), b.get());
	}
	
}

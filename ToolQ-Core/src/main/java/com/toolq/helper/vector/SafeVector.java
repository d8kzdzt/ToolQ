package com.toolq.helper.vector;

import java.util.Collection;
import java.util.Vector;

/**
 * @author luoluo
 * @date 2020/10/7 4:55
 */
public final class SafeVector<E> extends Vector<E> {
	public SafeVector(int initialCapacity, int capacityIncrement) {
		super(initialCapacity, capacityIncrement);
	}

	public SafeVector(int initialCapacity) {
		super(initialCapacity, 0);
	}

	public SafeVector() {
		super(10);
	}

	public SafeVector(Collection<? extends E> c) {
		super(c);
	}

	@Override
	public synchronized void addElement(E obj) {
		if(size() > Integer.MAX_VALUE - 100)
			super.clear();
		super.addElement(obj);
	}

	@Override
	public synchronized boolean add(E e) {
		if(size() > Integer.MAX_VALUE - 100)
			super.clear();
		return super.add(e);
	}

	@Override
	public void add(int index, E element) {
		if(size() > Integer.MAX_VALUE - 100)
			super.clear();
		super.add(index, element);
	}

	@Override
	public boolean remove(Object o) {
		synchronized(this) {
			super.remove(o);
			if(super.contains(o))
				this.remove(o);
			return true;
		}
	}

	@Override
	public synchronized E remove(int index) {
		synchronized(this) {
			return super.remove(index);
		}
	}

	@Override
	protected synchronized void removeRange(int fromIndex, int toIndex) {
		synchronized(this) {
			super.removeRange(fromIndex, toIndex);
		}
	}

	@Override
	public synchronized boolean removeElement(Object obj) {
		synchronized(this) {
			super.removeElement(obj);
			if(super.contains(obj))
				this.removeElement(obj);
			return true;
		}
	}
}

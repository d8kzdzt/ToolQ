package com.qq.pb;

import java.util.*;

public final class PBRepeatMessageField<T extends MessageMicro<T>> extends PBField<List<T>> {
	private final Class<T> helper;
	private List<T> value = Collections.emptyList();

	public PBRepeatMessageField(Class<T> cls) {
		this.helper = cls;
	}

	public void add(T t) {
		if(t == null) return;
		get().add(t);
	}

	public void addAll(Collection<T> collection) {
		get().addAll(collection);
	}

	public void addAll(T[] array) {
		get().addAll(Arrays.asList(array));
	}

	public void clear(Object obj) {
		this.value = Collections.emptyList();
	}

	public int computeSize(int i) {
		return computeSizeDirectly(i, this.value);
	}

	protected int computeSizeDirectly(int i, List<T> list) {
		int j = 0;
		Iterator<T> it = list.iterator();
		while (true) {
			if (!it.hasNext()) {
				return j;
			}
			MessageMicro messageMicro = it.next();
			j = messageMicro.computeSizeDirectly(i, messageMicro) + j;
		}
	}

	protected void copyFrom(PBField<List<T>> pBField) {
		PBRepeatMessageField<T> pBRepeatMessageField = (PBRepeatMessageField<T>) pBField;
		if (pBRepeatMessageField.isEmpty()) {
			this.value = Collections.emptyList();
			return;
		}
		List<T> list = get();
		Class<?> cls = pBRepeatMessageField.get(0).getClass();
		int size = pBRepeatMessageField.value.size() - list.size();
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				try {
					list.add((T) cls.newInstance());
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		} else if (size < 0) {
			list.subList(-size, list.size()).clear();
		}
		for (int i2 = 0; i2 < list.size(); i2++) {
			((MessageMicro) list.get(i2)).copyFrom((MessageMicro<T>) pBRepeatMessageField.value.get(i2));
		}
	}

	public T get(int i) {
		return (T) this.value.get(i);
	}

	public List<T> get() {
		if (this.value == Collections.EMPTY_LIST) {
			this.value = new ArrayList<T>();
		}
		return this.value;
	}

	public boolean has() {
		return !isEmpty();
	}

	public boolean isEmpty() {
		return this.value.isEmpty();
	}

	public void readFrom(CodedInputStreamMicro codedInputStreamMicro) {
		try {
			MessageMicro messageMicro = this.helper.newInstance();
			codedInputStreamMicro.readMessage(messageMicro);
			add((T)messageMicro);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	protected List<T> readFromDirectly(CodedInputStreamMicro codedInputStreamMicro) {
		throw new RuntimeException("PBRepeatField not support readFromDirectly method.");
	}

	public void remove(int i) {
		get().remove(i);
	}

	public void set(int i, T t) {
		this.value.set(i, t);
	}

	public void set(List<T> list) {
		this.value = list;
	}

	public int size() {
		return this.value.size();
	}

	public void writeTo(CodedOutputStreamMicro codedOutputStreamMicro, int i) {
		writeToDirectly(codedOutputStreamMicro, i, this.value);
	}

	protected void writeToDirectly(CodedOutputStreamMicro codedOutputStreamMicro, int i, List<T> list) {
		for (T t : list) {
			t.writeToDirectly(codedOutputStreamMicro, i, t);
		}
	}
}

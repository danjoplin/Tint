package core.supervised;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import core.util.FrequencyArray;
import core.views.EncoderWithViews;

public class TargetEncoder<E> implements EncoderWithViews<E, FrequencyArray> {

	private static final int INITIAL_DICTIONARY_SIZE = 4;

	private final HashSet<FrequencyArray> views = new HashSet<>();
	private final ArrayList<E> keys = new ArrayList<>(INITIAL_DICTIONARY_SIZE);
	private final HashMap<E, Integer> dictionary = new HashMap<>(INITIAL_DICTIONARY_SIZE, 0.75f);
	
	public TargetEncoder() {}

	@Override
	public void insert(E e) {
		if (dictionary.putIfAbsent(e, dictionary.size()) == null)
			keys.add(e);
	}

	@Override
	public int encode(E e) {
		return dictionary.get(e);
	}

	@Override
	public E decode(int x) {
		return keys.get(x);
	}

	@Override
	public void updateViews() {
		final int newSize = this.dictionary.size();
		views.parallelStream().forEach(x -> x.resize(newSize));
	}

}

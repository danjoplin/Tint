package com.tint.core.util;

import java.util.Arrays;

import com.tint.core.util.combine.Mergeable;
import com.tint.core.views.EncoderView;

public class FrequencyArray implements EncoderView, Mergeable<FrequencyArray> {
	
	private int[] counts;
	
	public FrequencyArray(int size) {
		this.counts = new int[size];
	}
	
	public void add(FrequencyArray fa) {
		for (int i=0;i<counts.length;i++) {
			this.counts[i] += fa.counts[i];
		}
	}
	
	public int sum() {
		return Arrays.stream(this.counts).sum();
	}
	
	public void set(int i, int value) {
		this.counts[i] = value;
	}
	
	public void increment(int i) {
		this.counts[i]++;
	}
	
	public int get(int i) {
		return this.counts[i];
	}
	
	public void resize(int newSize) {
		int[] old = this.counts;
		this.counts = new int[newSize];
		System.arraycopy(old, 0, this.counts, 0, this.counts.length);
	}

	@Override
	public void merge(FrequencyArray other) {
		this.add(other);
	}
	
	@Override
	public int hashCode() {
		int result = 1;
		for (int element : counts)
			result = 31 * result + element;
		return super.hashCode();
	}
	
	@Override
	public String toString() {
		return Arrays.toString(counts);
	}
	
}

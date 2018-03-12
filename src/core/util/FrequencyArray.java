package core.util;

import java.util.Arrays;

public class FrequencyArray {
	
	private int[] counts;
	
	public FrequencyArray(int size) {
		this.counts = new int[size];
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
	public String toString() {
		return Arrays.toString(counts);
	}
	
}

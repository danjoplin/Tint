package core.unsupervised.histogram;

import core.util.FrequencyArray;
import core.util.combine.Mergeable;

public class HistogramBin implements Mergeable<HistogramBin>, Comparable<HistogramBin> {

	private final FrequencyArray frequencies;

	private double min, max;

	public HistogramBin(double initialValue, FrequencyArray freqs) {
		this.min = initialValue;
		this.max = initialValue;

		this.frequencies = freqs;
	}

	@Override
	public void merge(HistogramBin other) {
		// Always merge left, other MUST be greater than this
		if (this.compareTo(other) > 0)
			throw new IllegalArgumentException("Other HistogramBin is less than this, invalid.");
			
		// Update the frequencies
		this.frequencies.add(other.frequencies);
		
		// Update min and max
		this.max = other.max;
	}
	
	@Override
	public int compareTo(HistogramBin o) {
		return Double.compare(this.max, o.min);
	}

	@Override
	public int hashCode() {
        long bits = Double.doubleToRawLongBits(min);
        int result = 31 + (int)(bits ^ (bits >>> 32));
        bits = Double.doubleToRawLongBits(max);
        return 31 * result + (int)(bits ^ (bits >>> 32));
	}

	public static void main(String[] args) {
		HistogramBin a = new HistogramBin(1, null);

		for (int i=0;i<=20;i++) {
			HistogramBin d = new HistogramBin(i, null);
			System.out.println(i+":"+d.hashCode());
		}

	}

}

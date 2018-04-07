package com.tint.core.unsupervised.histogram;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Histogram {

	private static int DEFAULT_NUMBER_OF_BINS = 256;
	private final int maximumBinCount;

	private final List<HistogramBin> bins = new ArrayList<>();
	private final /*SortedQueue*/ Queue<BinDifference> differences;

	public Histogram() {
		this(DEFAULT_NUMBER_OF_BINS);
	}

	public Histogram(int numberOfBins) {
		this.maximumBinCount = numberOfBins;
	}

	/**
	 * Adds a value to the histogram
	 * @param value The value to insert into the {@link HistogramBin}s
	 * @return true if structure has changed, false if not
	 */
	public void add(double value) {
		addWithoutReduction(value);
		enforceMaxSize();
	}
	
	public void add(double[] values) {
		for (double x : values) {
			addWithoutReduction(x);
		}
		enforceMaxSize();
	}
 	
	public void add(List<Number> values) {
		add(values.stream().mapToDouble(Number::doubleValue).toArray());
	}
	
	public boolean addWithoutReduction(double value) {
		BinInsertion binLocation = findInsertionPoint(value);
		HistogramBin theBin;
		
		if (binLocation.isExact) {
			theBin = this.bins.get(binLocation.index);
			theBin.increment();
			return false;
		} else {
			theBin = new HistogramBin(value);
			this.bins.add(binLocation.index, theBin);
			return true;
		}
	}
	
	public void enforceMaxSize() {
		while (this.size() > this.maximumBinCount) {
			reduce();
		}
	}
	
	private void reduce() {
		// FIXME [dan] - Implement
	}

	private BinInsertion findInsertionPoint(double key) {

        int low = 0;
        int high = bins.size() - 1;
        double diff = 0;

        while (low <= high) {
            int mid = (low + high) >>> 1;
			HistogramBin midBin = bins.get(mid);
			diff = midBin.realDistanceFromValue(key);
			
            if (diff > 0)
                low = mid + 1;
            else if (diff < 0)
                high = mid - 1;
            else {
                return new BinInsertion(mid, true /* isExact */);
            }
        }

        return new BinInsertion(low, false);
	}

	public int size() {
		return this.bins.size();
	}
	
	@Override
	public String toString() {
		return this.bins.toString();
	}

}

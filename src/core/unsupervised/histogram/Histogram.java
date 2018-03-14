package core.unsupervised.histogram;

import java.util.ArrayList;
import java.util.List;

public class Histogram {

	private static int DEFAULT_NUMBER_OF_BINS = 256;
	private final int maximumBinCount;

	private final List<HistogramBin> bins = new ArrayList<>();

	public Histogram() {
		this(DEFAULT_NUMBER_OF_BINS);
	}

	public Histogram(int numberOfBins) {
		this.maximumBinCount = numberOfBins;
	}

	public HistogramBin add(double value) {
		HistogramBin theBin;

		if (this.bins.size() == this.maximumBinCount) {
			// There is no more capacity for new bins, we must merge bins and possibly
			// create another
			theBin = getNearestBin(value);
			
			theBin.add(value);

		} else {
			// Initial loading process
			theBin = getExactBin(value);

			if (theBin == null) {
				// Create a new bin
				theBin = addBin(value);
			} else {
				// Add to existing bin
				theBin.increment();
			}

		}

		return theBin;
	}

	private HistogramBin addBin(double value) {
		HistogramBin bin = new HistogramBin(value);

		int binIndex = getInsertionIndex(value);
		this.bins.add(binIndex, bin);

		return bin;
	}

	private int getInsertionIndex(double value) {
		if (this.bins.size() == 0)
			return 0;

		int low = 0;
		int high = bins.size() - 1;

		while (low <= high) {
			int mid = (low + high) >>> 1;
			int cmp = bins.get(mid).compareToValue(value);

			if (cmp < 0)
				low = mid + 1; // Neither val is NaN, thisVal is smaller
			else if (cmp > 0)
				high = mid - 1; // Neither val is NaN, thisVal is larger
			else {
				return mid; // Key found
			}
		}
		
		return low; // key not found.
	}

	private int getNearestBinIndex(double key) {
		int low = 0;
		int high = bins.size() - 1;
		double diff = 0;

		while (low + 1 < high) {
			int mid = (low + high) >>> 1;
			HistogramBin midBin = bins.get(mid);
			diff = midBin.realDistanceFromValue(key);

			if (diff > 0)
				low = mid; // Neither val is NaN, thisVal is smaller
			else if (diff < 0)
				high = mid; // Neither val is NaN, thisVal is larger
			else {
				// match?
				return mid; // Key found
			}
		}

		if (low != high) {
			return bins.get(low).distanceFromBin(key) <= bins.get(high).distanceFromBin(key) ? low : high;
		}
		return low;
	}

	private HistogramBin getExactBin(double value) {
		int low = 0;
		int high = bins.size() - 1;

		while (low <= high) {
			int mid = (low + high) >>> 1;
			HistogramBin midVal = bins.get(mid);
			int cmp = midVal.compareToValue(value);

			if (cmp < 0)
				low = mid + 1; // Neither val is NaN, thisVal is smaller
			else if (cmp > 0)
				high = mid - 1; // Neither val is NaN, thisVal is larger
			else {
				return midVal;
			}
		}
		return null; // key not found.
	}

	private HistogramBin getNearestBin(double value) {
		return bins.get(getNearestBinIndex(value));
	}

	public int size() {
		return this.bins.size();
	}
	
	@Override
	public String toString() {
		return this.bins.toString();
	}

	public static void main(String[] args) {

		Histogram h = new Histogram(8);

		HistogramBin four = h.add(4);
		HistogramBin two = h.add(2);
		HistogramBin eight = h.add(8);

		int insertion, nearest;
		
		double[] a = new double[] { 1.5, 8, 2.9, 3.1, 2, 4.1, 5.9, 6.1, 4, 7.2, 8.1 };

		for (double x : a) {
			insertion = h.getInsertionIndex(x);
			nearest = h.getNearestBinIndex(x);
			HistogramBin exact = h.getExactBin(x);
			System.out.println(x + ":" + insertion+":"+nearest+"\t\t"+exact);
			
			HistogramBin nearestBin = h.getNearestBin(x);
			nearestBin.add(x);
			System.out.println(x + ":" + insertion+":"+nearest+"\t\t"+nearestBin);
		}
		
		System.out.println(h);

	}

}

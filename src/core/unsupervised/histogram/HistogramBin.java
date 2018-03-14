package core.unsupervised.histogram;

import core.util.combine.Mergeable;

public class HistogramBin implements Mergeable<HistogramBin>, Comparable<HistogramBin> {

	private BinDifference leftDiff, rightDiff;
	private int count = 1;
	double min, max;

	public HistogramBin(double initialValue) {
		this.min = initialValue;
		this.max = initialValue;
	}
	
	void add(double value) {
		if (value < min)
			this.min = value;
		if (value > max)
			this.max = value;
		
		this.count++;
	}
	
	public int increment() {
		return ++this.count;
	}
	
	public double getWidth() {
		return max-min;
	}
	
	public int size() {
		return this.count;
	}
	
	public HistogramBin leftBin() {
		return this.leftDiff.getLeft();
	}
	
	public HistogramBin rightBin() {
		return this.rightDiff.getRight();
	}

	@Override
	public void merge(HistogramBin other) {
		// Always merge left, other MUST be greater than this
		if (this.compareTo(other) > 0)
			throw new IllegalArgumentException("Other HistogramBin is less than this, invalid.");
			
		// Update the frequencies
		this.count += other.count;
		
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
	
	@Override
	public String toString() {
		return min+"-"+max+"("+count+")";
	}
	
    /****************************
     * Distance and comparisons *
     ****************************/
    
	
    /**
     * @return The distance between this {@link AbstractHistogramBin} and {@code value}. -ve if value is less
     * than the range, 0 if between, +ve if value is greater than the range
     */
    public double realDistanceFromValue(double value) {
        if (value < min) {
            return value - min;
        } else if (value > max) {
            return value - max;
        } else {
            return 0;
        }
    }
    
    /**
     * @return The absolute distance between this {@link AbstractHistogramBin} and {@code value}
     */
    public double distanceFromBin(double value) {
        // value - max < 0 if value < max
        // min - value < 0 if value > min
        // 0 if inside
        return Math.abs(realDistanceFromValue(value));
    }
    
    /**
     * @return Is {@code value} less than, greater than, or equal to this bin's value range
     */
    public int compareToValue(double value) {
        // Could implement an epsilon value here to handle floating point errors
        if (value < min)
            return 1;
        
        if (value > max)
            return -1;
            
        return 0;
    }

}

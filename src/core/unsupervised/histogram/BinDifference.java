package core.unsupervised.histogram;

public class BinDifference {
	
	private HistogramBin left, right;
	
	public BinDifference(HistogramBin left, HistogramBin right) {
		this.left = left;
		this.right = right;
	}
	
	public HistogramBin getLeft() {
		return left;
	}
	
	public HistogramBin getRight() {
		return right;
	}
	
	public void setLeft(HistogramBin left) {
		this.left = left;
	}
	
	public void setRight(HistogramBin right) {
		this.right = right;
	}
	
	public double range() {
		if (left == null || right == null)
			return Double.MAX_VALUE;
		return right.min - left.max;
	}
	
	public double gapSize(){
		return left.max - right.min;
	}
	
	public int size() {
		if (left == null)
			return right.size();
		if (right == null)
			return left.size();
		return left.size()+right.size();
	}
	
	public boolean isHeadOrTail() {
		return left==null || right==null;
	}

}

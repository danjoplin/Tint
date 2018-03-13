package core.util;

public class ProportionArray {
	
	private double[] proportions;
	private boolean isNormalised = false;
	
	private void normalise() {
		if (isNormalised)
			return;
		
		double sum = 0.;
		for (double x : proportions)
			sum += x;
		
		for (int i=0;i<proportions.length;i++)
			proportions[i] /= sum;
		
		this.isNormalised = true;
	}
	
	public void merge(ProportionArray pa) {
		add(pa);
		normalise();
	}
	
	public void add(ProportionArray pa) {
		for (int i=0;i<size();i++) {
			this.proportions[i] += pa.proportions[i];
		}
		this.isNormalised = false;
	}
	
	public int size() {
		return this.proportions.length;
	}
	
}

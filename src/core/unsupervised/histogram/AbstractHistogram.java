package core.unsupervised.histogram;

import core.Encoder;

/**
 * @author Daniel Joplin
 * Mar 12, 2018
 */
public abstract class AbstractHistogram implements Encoder<Number> {

	@Override
	public void insert(Number e) {
		// TODO Auto-generated method stub
	}

	@Override
	public int encode(Number e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Number decode(int x) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateViews() {
		// TODO Auto-generated method stub
	}

}

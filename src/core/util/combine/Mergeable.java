package core.util.combine;

/**
 * @author Daniel B. Joplin
 * Mar 12, 2018
 */
public interface Mergeable<SELF> {

	/**
	 * Merges the other object with this one
	 * @param other The object to merge with this one
	 */
	public void merge(SELF other);

}

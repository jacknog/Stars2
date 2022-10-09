package application;

/**
 * A functional interface to select between one of two given star instances
 * @author Adam Divelbiss
 *
 */
public interface StarSelector {
	/**
	 * Returns the star that meets the selection criteria
	 * @param a - a first star to consider for selection
	 * @param b - a second star to consider for selection
	 * @return - returns the selected star
	 */
	Star select(Star a, Star b);

}


package application;

import java.util.ArrayList;

/**
 * A funcitonal interface that reduces an array of Stars to a single star instance
 * @author Adam Divelbiss
 *
 */
public interface StarReducer {
	
	/**
	 * Reduces the given array of stars based on the selector provided
	 * @param array - an array of stars to be reduced
	 * @param selector - a selector that implements the criteria for selection
	 * @return - a star instance as the result of the reduction.
	 */
	Star reduce(ArrayList<Star> array, StarSelector selector);

}

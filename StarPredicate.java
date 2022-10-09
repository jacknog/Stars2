package application;

/**
 * A functional interface defining a method to indicate if the star meets some criteria
 * @author Adam Divelbiss
 *
 */
public interface StarPredicate {

	/**
	 * Indicates if the star meets the programmed criteria
	 * @param star - a star to be evaluated
	 * @return - true if criteria are met otherwise false
	 */
	boolean isOK(Star star);
	
}
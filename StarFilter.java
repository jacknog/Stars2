package application;

import java.util.ArrayList;

/**
 * A functional interface defining a method to filter an array of stars
 * @author Adam Divelbiss
 *
 */
public interface StarFilter {

	/**
	 * Filters a given array of stars using the predicate
	 * @param array - An array of stars to be filtered
	 * @param predicate - A predicate defining the filter conditions
	 * @return - the filtered array of Stars
	 */
	ArrayList<Star> filter(ArrayList<Star> array, StarPredicate predicate);
	
}


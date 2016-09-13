package com.fuzzystream.fif_core;

import java.util.LinkedList;

/**This is a proxy class that checks the data when the client uses the AbstractList objects.
 * 
 * @author Troiano Lorenzo
 * @version 1.0
 */

public class CheckedLinkedList<T> extends LinkedList<T>  {

	
	public T remove(int index) {
		assert (index >= 0 && index < size()) : "Index Out of Bound";
		return super.remove();
	}
	

	public T set(int index, T element) {
		assert (element != null) : "You are trying to set a null value.";
		assert (index >= 0 && index < size()) : "Index Out of Bound";

		return super.set(index, element);
	}
	

	

}
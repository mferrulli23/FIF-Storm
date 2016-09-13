package com.fuzzystream.fif_core;


import java.util.Arrays;

public class FilterUtility {

	public static double min(double... number) {
		assert number != null : "Filters: are not accepted null array.";
		Arrays.sort(number);
		return number[0];
	}

	public static double max(double... number) {
		assert number != null : "Filters: are not accepted null array.";
		Arrays.sort(number);
		return number[number.length - 1];

	}

}

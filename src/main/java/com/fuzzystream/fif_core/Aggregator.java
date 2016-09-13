package com.fuzzystream.fif_core;

import com.fuzzystream.exceptions.IllegalNumerOfValuesException;

public interface Aggregator {
	public double aggregate(double... values) throws IllegalNumerOfValuesException ;

}

package com.fuzzystream.fif_core;

import com.fuzzystream.fif_core.exceptions.IllegalNumerOfValuesException;

public interface Aggregator {
	public double aggregate(double... values) throws IllegalNumerOfValuesException ;

}

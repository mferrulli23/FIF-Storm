package com.fuzzystream;

import java.util.LinkedList;

import com.fuzzystream.fif_core.Attribute;
import com.fuzzystream.fif_core.DescriptionBasedFilter;
import com.fuzzystream.fif_core.Filter;
import com.fuzzystream.fif_core.FuzzySet;
import com.fuzzystream.fif_core.Metadata;
import com.fuzzystream.fif_core.PossibilisticInterpretation;
import com.fuzzystream.fif_core.SequenceFilter;

public class ProfileA extends AbstractProfile {
	
	static ProfileA profileA = null;
	
	public static ProfileA getInstanceProfileA() throws Exception{
		if(profileA == null){
			profileA = new ProfileA();
			profileSelected = profileA;
			return profileA;
		}
		else
			return profileA;
	}
	
	private ProfileA() throws Exception {
		id = 1;
		//costruzione fuzzy set
		profileGenreFs = new FuzzySet();
		profileYearFs = new FuzzySet();
		interestingMovies = new LinkedList<Movie>();
		profileGenreFs.setValue("Action", 0.8);
		profileGenreFs.setValue("Thriller", 0.7);
		profileGenreFs.setValue("Mystery", 0.5);
		profileYearFs.setValue("1986", 0.4);
		profileYearFs.setValue("1997", 0.8);
		
		//costruzione metadato e filtro semplice
		Attribute genre = new Attribute("Genre");
		Metadata profileGenreMd = new Metadata(genre, profileGenreFs, PossibilisticInterpretation.getinstance());
		DescriptionBasedFilter filterGenre = new DescriptionBasedFilter(profileGenreMd);
		
		//costruzione metadato e filtro semplice
		Attribute year = new Attribute("Year");
		Metadata profileYearMd = new Metadata(year, profileYearFs, PossibilisticInterpretation.getinstance());
		DescriptionBasedFilter filterYear = new DescriptionBasedFilter(profileYearMd);
		
		//costruzione filtro composto in sequenza
		Filter filter = new SequenceFilter(filterGenre, filterYear);
		generalFilter = filter;
	}

}

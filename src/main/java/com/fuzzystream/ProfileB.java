package com.fuzzystream;

import java.util.LinkedList;

import com.fuzzystream.fif_core.Attribute;
import com.fuzzystream.fif_core.DescriptionBasedFilter;
import com.fuzzystream.fif_core.Filter;
import com.fuzzystream.fif_core.FuzzySet;
import com.fuzzystream.fif_core.Metadata;
import com.fuzzystream.fif_core.PossibilisticInterpretation;
import com.fuzzystream.fif_core.SequenceFilter;

public class ProfileB extends AbstractProfile {

	static ProfileB profileB = null;
	
	public static ProfileB getInstanceProfileB() throws Exception{
		if(profileB == null){
			profileB = new ProfileB();
			profileSelected = profileB;
			return profileB;
		}
		else
			return profileB;
	}
	
	private ProfileB() throws Exception {
		id = 2;
		//costruzione fuzzy set
		profileGenreFs = new FuzzySet();
		profileYearFs = new FuzzySet();
		interestingMovies = new LinkedList<Movie>();
		profileGenreFs.setValue("Animation", 0.8);
		profileGenreFs.setValue("Comedy", 0.7);
		profileGenreFs.setValue("Adventure", 0.5);
		//profileYearFs.setValue("1983", 0.4);
		//profileYearFs.setValue("1998", 0.8);
		
		//costruzione metadato e filtro semplice
		Attribute genre = new Attribute("Genre");
		Metadata profileGenreMd = new Metadata(genre, profileGenreFs, PossibilisticInterpretation.getinstance());
		DescriptionBasedFilter filterGenre = new DescriptionBasedFilter(profileGenreMd);
		
		//costruzione metadato e filtro semplice
		//Attribute year = new Attribute("Year");
		//Metadata profileYearMd = new Metadata(year, profileYearFs, PossibilisticInterpretation.getinstance());
		//DescriptionBasedFilter filterYear = new DescriptionBasedFilter(profileYearMd);
		
		//costruzione filtro composto in sequenza
		//Filter filter = new SequenceFilter(filterGenre, filterYear);
		generalFilter = filterGenre;
	}
}

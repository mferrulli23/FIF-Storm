package com.fuzzystream;

import java.util.LinkedList;

import com.fuzzystream.fif_core.Attribute;
import com.fuzzystream.fif_core.DescriptionBasedFilter;
import com.fuzzystream.fif_core.FuzzySet;
import com.fuzzystream.fif_core.Metadata;
import com.fuzzystream.fif_core.PossibilisticInterpretation;

public class ProfileC extends AbstractProfile {

	static ProfileC profileC = null;
	
	public static ProfileC getInstanceProfileC() throws Exception{
		if(profileC == null){
			profileC = new ProfileC();
			profileSelected = profileC;
			return profileC;
		}
		else
			return profileC;
	}
	
	private ProfileC() throws Exception {
		id = 2;
		
		//costruzione fuzzy set
		profileGenreFs = new FuzzySet();
		profileYearFs = new FuzzySet();
		interestingMovies = new LinkedList<Movie>();
		
		//profileGenreFs.setValue("Animation", 0.8);
		//profileGenreFs.setValue("Comedy", 0.7);
		//profileGenreFs.setValue("Adventure", 0.5);
		profileYearFs.setValue("1983", 0.9);
		profileYearFs.setValue("1995", 0.2);
		
		//costruzione metadato e filtro semplice
		//Attribute genre = new Attribute("Genre");
		//Metadata profileGenreMd = new Metadata(genre, profileGenreFs, PossibilisticInterpretation.getinstance());
		//DescriptionBasedFilter filterGenre = new DescriptionBasedFilter(profileGenreMd);
		
		//costruzione metadato e filtro semplice
		Attribute year = new Attribute("Year");
		Metadata profileYearMd = new Metadata(year, profileYearFs, PossibilisticInterpretation.getinstance());
		DescriptionBasedFilter filterYear = new DescriptionBasedFilter(profileYearMd);
		
		//costruzione filtro composto in sequenza
		//Filter filter = new SequenceFilter(filterGenre, filterYear);
		generalFilter = filterYear;
	}
}

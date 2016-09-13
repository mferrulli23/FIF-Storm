package com.fuzzystream.fif_core;

import com.fuzzystream.exceptions.*;

import com.fuzzystream.*;

import java.net.URI;

public class Test {

	public static void main(String[] args) throws Exception {
		
		FuzzySet fsUtenteAtt = new FuzzySet();
		fsUtenteAtt.setValue("Edoardo Leo", 0.8);
		fsUtenteAtt.setValue("Marco Giallini", 0.7);
		fsUtenteAtt.setValue("Raoul Bova", 0.4);
		fsUtenteAtt.setValue("Alessandro Gassmann", 0.3);
		
		FuzzySet fsResourceAtt = new FuzzySet();
		fsResourceAtt.setValue("Edoardo Leo", 0.5);
		fsResourceAtt.setValue("Marco Giallini", 0.7);
		fsResourceAtt.setValue("Emilio Solfrizzi", 0.9);
		fsResourceAtt.setValue("Alessandro Gassmann", 0.1);
		fsResourceAtt.setValue("Valerio Mastrandrea", 0.6);
		
		Attribute attore1 = new Attribute("Attore");
		Attribute attore2 = new Attribute("Attore");
		
		Metadata mdUtenteAtt = new Metadata(attore1, fsUtenteAtt, PossibilisticInterpretation.getinstance());
		Metadata mdResourceAtt = new Metadata(attore2, fsResourceAtt, PossibilisticInterpretation.getinstance());
		
		
		Descriptor desc1 = new Descriptor();
		try{
			desc1.setMetadata(mdResourceAtt);
		} catch (MetadataWithSameAttributeException e){
			e.printStackTrace();
		}
		
		Resource r = new Resource(new URI("ff"));
		ResourceRegister rr = ResourceRegister.getinstance();
		rr.associateDescriptor(r, desc1);
		
		DescriptionBasedFilter f1 = new DescriptionBasedFilter(mdUtenteAtt);
		try{
			System.out.println(f1.doFilter(r));
		} catch (Exception e){
			e.printStackTrace();
		}
		
		System.out.println("------------");
		
		Attribute genere = new Attribute("Genere");
		
		FuzzySet fsUtenteGen = new FuzzySet();
		fsUtenteGen.setValue("Horror", 0.2);
		fsUtenteGen.setValue("Drama", 0.7);
		fsUtenteGen.setValue("Action", 0.8);
		fsUtenteGen.setValue("Romance", 0.2);
		
		FuzzySet fsResourceGen = new FuzzySet();
		fsResourceGen.setValue("Horror", 0.1);
		fsResourceGen.setValue("Action", 0.9);
		fsResourceGen.setValue("Thriller", 0.8);
		fsResourceGen.setValue("Drama", 0.7);
		
		Metadata mdUtenteGen = new Metadata(genere, fsUtenteGen, PossibilisticInterpretation.getinstance());
		Metadata mdResourceGen = new Metadata(genere, fsResourceGen, PossibilisticInterpretation.getinstance());
		
		Descriptor desc2 = new Descriptor();
		desc2.setMetadata(mdResourceAtt, mdResourceGen);
		Resource r2 = new Resource(new URI("ResSeq"));
		rr.associateDescriptor(r2, desc2);
		
		
		DescriptionBasedFilter f2 = new DescriptionBasedFilter(mdUtenteGen);		
		SequenceFilter sequenceFilter = new SequenceFilter(f1,f2);
		
		System.out.println(sequenceFilter.doFilter(r2));
		
		

	}

}

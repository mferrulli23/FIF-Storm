package com.fuzzystream.fif_storm;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.Map;
import com.fuzzystream.AbstractProfile;
import com.fuzzystream.Movie;
import com.fuzzystream.fif_core.Attribute;
import com.fuzzystream.fif_core.Descriptor;
import com.fuzzystream.fif_core.Filter;
import com.fuzzystream.fif_core.FuzzySet;
import com.fuzzystream.fif_core.Metadata;
import com.fuzzystream.fif_core.PossibilisticInterpretation;
import com.fuzzystream.fif_core.Resource;
import com.fuzzystream.fif_core.ResourceRegister;
import com.fuzzystream.fif_core.exceptions.DescriptorWithNoValidMetadataException;
import com.fuzzystream.fif_core.exceptions.InterpretationNotEqualException;
import com.fuzzystream.fif_core.exceptions.MetadataWithSameAttributeException;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class FilteringBolt implements IRichBolt {
	
	private static int numfiltr = 0;
	
	private OutputCollector collector;
	
	private FuzzySet resourceGenreFs; 
	private FuzzySet resourceYearFs;
	
	//private FuzzySet profileFs;
	AbstractProfile profile;

	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		profile = null;
		try {
			profile = AbstractProfile.getInstance();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	public void execute(Tuple input) {
		
		
		this.resourceGenreFs = new FuzzySet();
		this.resourceYearFs = new FuzzySet();
		String allGen = input.getStringByField("allGen");
		String year = input.getStringByField("year");
		
		String[] genres = allGen.split("\\|");
		
		//costruzione metadato GENERE
		Attribute resourceGenre = new Attribute("Genre");
		
		for(int i = 0; i < genres.length; i++)
			resourceGenreFs.setValue(genres[i], 1.0);
			
		
		Metadata resourceGenreMd = new Metadata(resourceGenre, resourceGenreFs, PossibilisticInterpretation.getinstance());
		
		//costruzione metadato ANNO
		Attribute resourceYear = new Attribute("Year");
			resourceYearFs.setValue(year, 1);
		
		
		Metadata resourceYearMd = new Metadata(resourceYear, resourceYearFs, PossibilisticInterpretation.getinstance());
		
		
		//costruzione descrittore
		Descriptor descriptor = new Descriptor();
		try {
			descriptor.setMetadata(resourceGenreMd);
			descriptor.setMetadata(resourceYearMd);
		} catch (MetadataWithSameAttributeException e) {
			System.err.println("Exception: metadata with same attribute.");;
		}
		
		//costruzione risorsa
		Resource resource = null;
		try {
			resource = new Resource(new URI("CurrentMovie"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		//associazione descrittore - risorsa
		ResourceRegister rr = ResourceRegister.getinstance();
		rr.associateDescriptor(resource, descriptor);
		
		
		AbstractProfile currentProfile = AbstractProfile.getInstance();
		Filter filter = currentProfile.getFilter();
		
		try {
			double result = filter.doFilter(resource);
			System.out.println("MATCHING DEGREE: " + result);
			numfiltr++;
			System.out.println("FILTRAGGIO NUMERO: " + numfiltr);
			
			if (result != 0.0){
				int id = input.getIntegerByField("ID");
				String title = input.getStringByField("title");
				LinkedList<String> gen = new LinkedList<String>();
				for(String s : genres)
					gen.add(s);
				
				//creazione oggetto Movie se il matching degree è > 0
				Movie currentMovie = new Movie();
				currentMovie.setId(id);
				currentMovie.setTitle(title);
				currentMovie.setGenres(gen);
				currentMovie.setGrade(result);
				profile.addInterestingMovie(currentMovie);  
				
			}
				
		} catch (InterpretationNotEqualException e){
			System.err.println("Exception: different interpretation type between filter and resource");
		} catch (DescriptorWithNoValidMetadataException e){
			System.err.print("Exception: descriptor has not valid metadata.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(MoviesSpout.finished)
			Topology.endTime = System.currentTimeMillis();
		
		collector.ack(input);

	}

	public void cleanup() {
		// TODO Auto-generated method stub

	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("Movie"));

	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}

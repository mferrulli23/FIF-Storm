package com.fuzzystream.fif_storm;

import java.net.URI;
import java.net.URISyntaxException;
//import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.fuzzystream.Movie;
import com.fuzzystream.Profile;
import com.fuzzystream.exceptions.MetadataWithSameAttributeException;
import com.fuzzystream.fif_core.Attribute;
import com.fuzzystream.fif_core.Descriptor;
import com.fuzzystream.fif_core.FuzzySet;
import com.fuzzystream.fif_core.Metadata;
import com.fuzzystream.fif_core.PossibilisticInterpretation;
import com.fuzzystream.fif_core.Resource;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
//import backtype.storm.tuple.Values;

import com.fuzzystream.fif_core.*;


public class FilteringGenreBolt implements IRichBolt{
	
	private FuzzySet resourceFs;
	private FuzzySet profileFs;
	Profile profile;
	

	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		
		profile = null;
		try {
			profile = Profile.getInstance();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.profileFs = profile.getGenrePreferences();
		
				
	}

	public void execute(Tuple input) {
		this.resourceFs = new FuzzySet();
		String genresString = input.getStringByField("genresString");
		
		String[] genres = genresString.split(" ");
		
		for(int i = 0; i < genres.length; i++)
			try {
				resourceFs.setValue(genres[i], 1.0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		Attribute profileAttribute = new Attribute("Genre");
		Attribute resourceAttribute = new Attribute("Genre");
		Metadata profileMetadata = new Metadata(profileAttribute, profileFs, PossibilisticInterpretation.getinstance());
		Metadata resourceMetadata = new Metadata(resourceAttribute, resourceFs, PossibilisticInterpretation.getinstance());
		Descriptor descriptor = new Descriptor();
		try {
			descriptor.setMetadata(resourceMetadata);
		} catch (MetadataWithSameAttributeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Resource r = null;
		try {
			r = new Resource(new URI("Risorsa1"));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResourceRegister rr = ResourceRegister.getinstance();
		rr.associateDescriptor(r, descriptor);
		
		DescriptionBasedFilter dbf = new DescriptionBasedFilter(profileMetadata);
		
		try{
			double result = dbf.doFilter(r);
			System.out.println("MATCHING DEGREE: " + result);
			
			if (result != 0.0){
				int id = input.getIntegerByField("itemID");
				String title = input.getStringByField("title");
				String dataStr = input.getStringByField("dataStr");
				String IMDBurl = input.getStringByField("IMDBurl");
				LinkedList<String> gen = new LinkedList<String>();
				for(String s : genres)
					gen.add(s);
				
				//creazione oggetto Movie se il matching degree è > 0
				Movie m = new Movie();
				m.setId(id);
				m.setTitle(title);
				m.setDate(dataStr);
				m.setIMDBurl(IMDBurl);
				m.setGenres(gen);
				m.setGrade(result);
				profile.addInterestingMovie(m);  
				
			}
				
		} catch (Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("Movie")); //da cancellare, nel caso
		
	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}

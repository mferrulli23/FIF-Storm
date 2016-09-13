package com.fuzzystream.fif_storm;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class CleaningGenreBolt implements IRichBolt {
	
	private OutputCollector collector;
	public static HashMap<Integer, String> genresIndexes = new HashMap<Integer, String>();

	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		genresIndexes.put(4, "unknown");
		genresIndexes.put(5, "Action");
		genresIndexes.put(6, "Adventure");
		genresIndexes.put(7, "Animation");
		genresIndexes.put(8, "Children");
		genresIndexes.put(9, "Comedy");
		genresIndexes.put(10, "Crime");
		genresIndexes.put(11, "Documentary");
		genresIndexes.put(12, "Drama");
		genresIndexes.put(13, "Fantasy");
		genresIndexes.put(14, "FilmNoir");
		genresIndexes.put(15, "Horror");
		genresIndexes.put(16, "Musical");
		genresIndexes.put(17, "Mystery");
		genresIndexes.put(18, "Romance");
		genresIndexes.put(19, "SciFi");
		genresIndexes.put(20, "Thriller");
		genresIndexes.put(21, "War");
		genresIndexes.put(22, "Western");

	}

	public void execute(Tuple input) {
		int itemID = input.getIntegerByField("itemID"); 
		String title = input.getStringByField("title");
		String dataStr = input.getStringByField("releaseDate");
		String IMDBurl = input.getStringByField("IMDBurl");
		
		int[] allGenres = new int[19]; //array che conterrà valori 0 e 1
		LinkedList<Integer> validIndexes = new LinkedList<Integer>(); //array che conterrà l'indice delle colonne con valore 1
		for(int i = 0; i < allGenres.length; i++){
			allGenres[i] = input.getInteger(i+4);
			if(allGenres[i] == 1)
				validIndexes.add((i+1)+3);
		}
		
		String genresString = "";
		for(int x : validIndexes){
			genresString += genresIndexes.get(x) + " ";
			
		}
		Values cleanGenres = new Values(itemID, title, dataStr, IMDBurl, genresString);
		collector.emit(cleanGenres);
		/*
		int unknown = input.getIntegerByField("unknown");
		int action = input.getIntegerByField("action");
		int adventure = input.getIntegerByField("adventure");
		int animation = input.getIntegerByField("animation");
		int children = input.getIntegerByField("children");
		int comedy = input.getIntegerByField("comedy");
		int crime = input.getIntegerByField("crime");
		int documentary = input.getIntegerByField("documentary");
		int drama = input.getIntegerByField("drama");
		int fantasy = input.getIntegerByField("fantasy");
		int filmnoir = input.getIntegerByField("filmNoir");
		int horror = input.getIntegerByField("horror");
		int musical = input.getIntegerByField("musical");
		int mystery = input.getIntegerByField("mystery");
		int romance = input.getIntegerByField("romance");
		int scifi = input.getIntegerByField("sciFi");
		int thriller = input.getIntegerByField("thriller");
		int war = input.getIntegerByField("war");
		int western = input.getIntegerByField("western");
		 */
		
		
	}
	
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		 declarer.declare(new Fields("itemID", "title", "dataStr", "IMDBurl", "genresString"));
	}

	public void cleanup() {
		// TODO Auto-generated method stub

	}

	

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}

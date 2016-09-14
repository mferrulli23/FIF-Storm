package com.fuzzystream.fif_storm;

import com.fuzzystream.Profile;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;

public class Topology {

	public final static String MOVIES_SPOUT_ID = "movies-SPOUT";
    public final static String PREPARING_BOLT_ID = "preparing-bolt";
    public final static String FILTERING_BOLT_ID = "filtering-bolt";
    public final static String END_FILTERING_BOLT_ID = "end-filtering-bolt";
    public final static String FUZZY_FILTERING_TOPOLOGY_ID = "fuzzy-filtering-TOPOLOGY";
    public final static String WORDS_FILE_KEY = "wordsFile";
    
    
    
    
    
    
    public static void main(String[] args) throws Exception {
    	
    	Profile.profileSelection();
    	
    	
        //Prima parte - Definizione della TOPOLOGY        
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout(MOVIES_SPOUT_ID, 
        		new MoviesSpout());
        builder.setBolt(PREPARING_BOLT_ID, new PreparingBolt()).
            shuffleGrouping(MOVIES_SPOUT_ID);
		builder.setBolt(FILTERING_BOLT_ID, new FilteringBolt()).shuffleGrouping(PREPARING_BOLT_ID);
		

        //Seconda parte  - Configurazione Storm Cluster
        Config conf = new Config();
        conf.setDebug(true);
        //conf.put(WORDS_FILE_KEY, args[0]);
        conf.setNumWorkers(2);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(FUZZY_FILTERING_TOPOLOGY_ID, conf, builder.createTopology());
        
        
        Thread.sleep(100000);
        //cluster.shutdown();
        
        Profile profile = null;
        profile = Profile.getInstance();
        profile.showInterestingMovies();
        profile.printTxt();
        
        }
    
    	


}

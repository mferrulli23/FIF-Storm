package com.fuzzystream.fif_storm;

import java.io.Serializable;

import com.fuzzystream.AbstractProfile;
import com.fuzzystream.ProfileFactory;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;

public class Topology implements Serializable{

	public final static String SPOUT_ID = "movies-SPOUT";
    public final static String PREPARING_BOLT_ID = "preparing-bolt";
    public final static String FILTERING_BOLT_ID = "filtering-bolt";
    public final static String FUZZY_FILTERING_TOPOLOGY_ID = "fuzzy-filtering-TOPOLOGY";
    
    public static long endTime = 0;
    public static long startTime = 0;
    
    
    
    
    
    
    public static void main(String[] args) throws Exception {
    	
    	startTime = System.currentTimeMillis();
    	
    	ProfileFactory factory = new ProfileFactory();
    	int idProfile = factory.profileSelection();
        AbstractProfile profile = (AbstractProfile) factory.getInstance(idProfile);
    	
    	
        //Prima parte - Definizione della TOPOLOGY  
        long startTime = System.currentTimeMillis();
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout(SPOUT_ID, 
        		new MoviesSpout());
        builder.setBolt(PREPARING_BOLT_ID, new PreparingBolt()).
            shuffleGrouping(SPOUT_ID);
		builder.setBolt(FILTERING_BOLT_ID, new FilteringBolt()).shuffleGrouping(PREPARING_BOLT_ID);
		

        //Seconda parte  - Configurazione Storm Cluster
        Config conf = new Config();
        conf.setDebug(true);
        //conf.put(WORDS_FILE_KEY, args[0]);
        conf.setNumWorkers(2);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(FUZZY_FILTERING_TOPOLOGY_ID, conf, builder.createTopology());
        
        
        Thread.sleep(50000);
       
        System.out.println("Everything done in: " + (endTime - startTime));
        
        //profile.showInterestingMovies();
        profile.printTxt();
        
        
        
        }
    
    	


}

package com.fuzzystream.fif_storm;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class PreparingBolt implements IRichBolt {

	private OutputCollector collector;
	
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("ID", "title", "year", "allGen"));
	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}


	public void cleanup() {
		

	}

	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		
	}

	public void execute(Tuple input) {
		int id = input.getIntegerByField("ID");
		String title = input.getStringByField("title");
		String allGen = input.getStringByField("allGen");
	
		String year = "";
		for(int i = (title.length()- 5); i < (title.length() - 1); i++){
			year += title.charAt(i);
		}
		
		Values cleanTuple = new Values(id, title, year, allGen);
		collector.emit(cleanTuple);
		collector.ack(input);

		
	}

}

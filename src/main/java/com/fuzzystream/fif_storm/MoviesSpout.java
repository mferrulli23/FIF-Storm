package com.fuzzystream.fif_storm;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Map;

import com.fuzzystream.dataAccess.DataAccess;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class MoviesSpout implements IRichSpout {
	
	private SpoutOutputCollector collector;
	private boolean completed = false;
	DataAccess DAO;

	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		DAO = new DataAccess();
		try {
			DAO.connetti(4);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.collector = collector;
	}

	public void close() {
		// TODO Auto-generated method stub

	}

	public void activate() {
		// TODO Auto-generated method stub

	}

	public void deactivate() {
		// TODO Auto-generated method stub

	}

	public void nextTuple() {
		if (completed) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			return;
		}
		
		String sql = "SELECT * FROM movies;";
		
		try {
			DAO.stat = DAO.connessione.createStatement();
			DAO.res = DAO.stat.executeQuery(sql);

			while (DAO.res.next()) {
				int itemID = DAO.res.getInt("ID");
				String title = DAO.res.getString("title");
				String allGen = DAO.res.getString("genres");
				
				Values values = new Values(itemID, title, allGen);
				this.collector.emit(values);

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		completed = true;
	

	}

	public void ack(Object msgId) {
		// TODO Auto-generated method stub

	}

	public void fail(Object msgId) {
		// TODO Auto-generated method stub

	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		Fields fields = new Fields("ID", "title", "allGen");
		declarer.declare(fields);
	}

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}

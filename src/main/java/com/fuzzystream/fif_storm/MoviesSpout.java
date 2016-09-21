package com.fuzzystream.fif_storm;

import java.io.IOException;

import java.sql.SQLException;


import java.util.Map;
import com.fuzzystream.dataAccess.DBConnection;

import com.fuzzystream.exceptions.*;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class MoviesSpout implements IRichSpout {
	
	private SpoutOutputCollector collector;
	private boolean completed = false;
	DBConnection DBc; 
	
	static boolean finished = false;
	
	private static final int MOVIELENS1 = 2;
	private static final int MOVIELENS2 = 3;
	private static final int MOVIELENS3 = 4;
	
	private static final String ALL_MOVIES_QUERY = "SELECT * FROM movies;";


	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		DBc = new DBConnection();
		try {
			DBc.connetti(MOVIELENS1);
		} catch (SQLException e) {
			throw new DatabaseConnectionFailedException();
		} catch (IOException e) {
			throw new NotValidValueConfigFileException();
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
			finished = true;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			return;
		}
		
		
		try {
			DBc.stat = DBc.connessione.createStatement();
			DBc.res = DBc.stat.executeQuery(ALL_MOVIES_QUERY);
		} catch (SQLException e) {
			throw new NotValidQueryException();
		}

		try {
			while (DBc.res.next()) {
				int itemID = DBc.res.getInt("ID");
				String title = DBc.res.getString("title");
				String allGen = DBc.res.getString("genres");

				Values values = new Values(itemID, title, allGen);
				this.collector.emit(values);

			}
		} catch (SQLException e) {
			throw new NotValidDataException();
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

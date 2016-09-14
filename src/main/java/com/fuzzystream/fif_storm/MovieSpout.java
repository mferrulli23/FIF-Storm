package com.fuzzystream.fif_storm;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.fuzzystream.dataAccess.DataAccess;

//import com.fuzzystream.Profile;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class MovieSpout implements IRichSpout {

	private SpoutOutputCollector collector;
	private boolean completed = false;
	DataAccess DAO;
	
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {

		DAO = new DataAccess();
		try {
			DAO.connetti(1);
			System.out.println("CONNESSIONE CON IL DATABASE MOVIELENS STABILITA");
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

	public void nextTuple() {
		if (completed) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			return;
		}

		for (int i = 1; i < 1683; i++) { // CI SONO 1682 FILM NEL DATABASE
			String sql = "SELECT * FROM movies WHERE itemID = " + i + ";";
			try {
				DAO.stat = DAO.connessione.createStatement();
				DAO.res = DAO.stat.executeQuery(sql);

				while (DAO.res.next()) {
					int itemID = DAO.res.getInt("itemID");
					String title = DAO.res.getString("title");
					Date date = DAO.res.getDate("releaseDate");
					String releaseDate = date.toString();
					String IMDBurl = DAO.res.getString("IMDBurl");
					int unknown = DAO.res.getInt("Unknown");
					int action = DAO.res.getInt("Action");
					int adventure = DAO.res.getInt("Adventure");
					int animation = DAO.res.getInt("Animation");
					int children = DAO.res.getInt("Children");
					int comedy = DAO.res.getInt("Comedy");
					int crime = DAO.res.getInt("Crime");
					int documentary = DAO.res.getInt("Documentary");
					int drama = DAO.res.getInt("Drama");
					int fantasy = DAO.res.getInt("Fantasy");
					int filmnoir = DAO.res.getInt("FilmNoir");
					int horror = DAO.res.getInt("Horror");
					int musical = DAO.res.getInt("Musical");
					int mystery = DAO.res.getInt("Mystery");
					int romance = DAO.res.getInt("Romance");
					int scifi = DAO.res.getInt("SciFi");
					int thriller = DAO.res.getInt("Thriller");
					int war = DAO.res.getInt("War");
					int western = DAO.res.getInt("Western");

					Values values = new Values(itemID, title, releaseDate, IMDBurl, unknown, action, adventure,
							animation, children, comedy, crime, documentary, drama, fantasy, filmnoir, horror, musical,
							mystery, romance, scifi, thriller, war, western);

					this.collector.emit(values);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		completed = true;

	}
	
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		Fields fields = new Fields("itemID", "title", "releaseDate", "IMDBurl",
				"unknown", "action", "adventure", "animation", "children", "comedy",
				"crime", "documentary", "drama", "fantasy", "filmnoir", "horror",
				"musical", "mystery", "romance", "scifi", "thriller", "war", "western");
		declarer.declare(fields);
		
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

	public void ack(Object msgId) {
		// TODO Auto-generated method stub

	}

	public void fail(Object msgId) {
		// TODO Auto-generated method stub

	}

	

	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}

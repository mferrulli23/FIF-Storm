package com.fuzzystream;

import java.util.LinkedList;

public class Movie implements Comparable{
	
	private int id;
	private String title;
	private String date;
	private LinkedList<String> genres;
	private double grade;
	
	/*public Movie(int id, String title, String date, String IMDBurl, LinkedList<String> genres, double grade) {
		this.id = id;
		this.title = title;
		this.date = date;
		this.IMDBurl = IMDBurl;
		this.genres = genres;
		this.grade = grade;
	}*/
	
	public int getId(){
		return this.id;
	}

	public String getTitle() {
		return title;
	}

	public String getDate() {
		return date;
	}

	public LinkedList<String> getGenres() {
		return genres;
	}
	
	public double getGrade(){
		return this.grade;
	}
	
	public String toString(){
		return "ID: " + this.getId() + "   Title: " + this.getTitle() + "    MatchingDegree: " + this.getGrade();
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setGenres(LinkedList<String> genres) {
		this.genres = genres;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	
	public int compareTo(Object m) {
		//confronto solo su grado
		Movie m2 = (Movie) m;
		if(getGrade() < m2.getGrade())
			return -1;
		else if(getGrade() > m2.getGrade())
			return 1;
		else
			return 0;
	}
	
	
	

}

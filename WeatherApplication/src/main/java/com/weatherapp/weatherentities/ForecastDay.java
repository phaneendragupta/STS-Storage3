package com.weatherapp.weatherentities;

public class ForecastDay {

	private String date;

	private Day day;

	public ForecastDay(String date, Day day) {
		super();
		this.date = date;
		this.day = day;
	}

	public ForecastDay() {
		super();
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	@Override
	public String toString() {
		return "ForecastDay [date=" + date + ", day=" + day + "]";
	}


}

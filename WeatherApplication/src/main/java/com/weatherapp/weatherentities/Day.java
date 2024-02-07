package com.weatherapp.weatherentities;

public class Day {

	private double maxtemp_c;
	private double mintemp_c;
	private double avgtemp_c;
	private double maxwind_kph;
	private double totalprecip_mm;
	private double avghumidity;
	private Long daily_chance_of_rain;
	private double uv;



	public Day() {
		super();
	}


	public Day(double maxtemp_c, double mintemp_c, double avgtemp_c, double maxwind_kph,
			double totalprecip_mm, double avghumidity, Long daily_chance_of_rain, double uv) {
		super();

		this.maxtemp_c = maxtemp_c;
		this.mintemp_c = mintemp_c;
		this.avgtemp_c = avgtemp_c;
		this.maxwind_kph = maxwind_kph;
		this.totalprecip_mm = totalprecip_mm;
		this.avghumidity = avghumidity;
		this.daily_chance_of_rain = daily_chance_of_rain;
		this.uv = uv;
	}


	public double getMaxtemp_c() {
		return maxtemp_c;
	}


	public void setMaxtemp_c(double maxtemp_c) {
		this.maxtemp_c = maxtemp_c;
	}


	public double getMintemp_c() {
		return mintemp_c;
	}


	public void setMintemp_c(double mintemp_c) {
		this.mintemp_c = mintemp_c;
	}


	public double getAvgtemp_c() {
		return avgtemp_c;
	}


	public void setAvgtemp_c(double avgtemp_c) {
		this.avgtemp_c = avgtemp_c;
	}


	public double getMaxwind_kph() {
		return maxwind_kph;
	}


	public void setMaxwind_kph(double maxwind_kph) {
		this.maxwind_kph = maxwind_kph;
	}


	public double getTotalprecip_mm() {
		return totalprecip_mm;
	}


	public void setTotalprecip_mm(double totalprecip_mm) {
		this.totalprecip_mm = totalprecip_mm;
	}


	public double getAvghumidity() {
		return avghumidity;
	}


	public void setAvghumidity(double avghumidity) {
		this.avghumidity = avghumidity;
	}


	public Long getDaily_chance_of_rain() {
		return daily_chance_of_rain;
	}


	public void setDaily_chance_of_rain(Long daily_chance_of_rain) {
		this.daily_chance_of_rain = daily_chance_of_rain;
	}


	public double getUv() {
		return uv;
	}


	public void setUv(double uv) {
		this.uv = uv;
	}


	@Override
	public String toString() {
		return "\nDay ["
				+ "maxtemp_c=" + maxtemp_c
				+ ", nmintemp_c=" + mintemp_c
				+ ", avgtemp_c=" + avgtemp_c
				+ ", maxwind_kph=" + maxwind_kph
				+ ", totalprecip_mm=" + totalprecip_mm
				+ ", avghumidity=" + avghumidity
				+ ", daily_chance_of_rain=" + daily_chance_of_rain
				+ ", uv=" + uv
				+ "]";
	}


}


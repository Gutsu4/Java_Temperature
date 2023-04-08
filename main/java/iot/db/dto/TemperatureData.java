package iot.db.dto;

import java.time.LocalDateTime;

/**
 * Temperature テーブルの内容を保持するDTO
 */
public class TemperatureData {

	int id;
	LocalDateTime registDatetime;
	double temperature;
	double humidity;

	public TemperatureData(int id, LocalDateTime registDatetime, double temperature, double humidity) {
		this.id = id;
		this.registDatetime = registDatetime;
		this.temperature = temperature;
		this.humidity = humidity;
	}

	public int getId() {
		return id;
	}

	public LocalDateTime getRegistDatetime() {
		return registDatetime;
	}

	public double getTemperature() {
		return temperature;
	}

	public double getHumidity() {
		return humidity;
	}

	@Override
	public String toString() {
		return String.format("id=%d regTime=%tFT%<tR temp=%f hum=%f%n", id, registDatetime, temperature, humidity);
	}

}

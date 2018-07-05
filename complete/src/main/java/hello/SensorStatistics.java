package hello;

import java.util.concurrent.ThreadLocalRandom;

public class SensorStatistics {
	
	private Double temprature;
	private Double speed;
	private Double humidity;
	private Double pressure;
	public Double getTemprature() {
		return temprature;
	}
	public void setTemprature(Double temprature) {
		this.temprature = temprature;
	}
	public Double getSpeed() {
		return speed;
	}
	public void setSpeed(Double speed) {
		this.speed = speed;
	}
	public Double getHumidity() {
		return humidity;
	}
	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}
	public Double getPressure() {
		return pressure;
	}
	public void setPressure(Double pressure) {
		this.pressure = pressure;
	}
	
	public SensorStatistics randomiseData() {
		this.temprature= ThreadLocalRandom.current().nextDouble(-40, 70);
		this.speed= ThreadLocalRandom.current().nextDouble(-125, 125);
		this.humidity= ThreadLocalRandom.current().nextDouble(0, 100);
		this.pressure=  ThreadLocalRandom.current().nextDouble(0, 100);
		return this;
	}

}

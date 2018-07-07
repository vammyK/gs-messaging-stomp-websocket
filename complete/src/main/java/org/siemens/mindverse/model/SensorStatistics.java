package org.siemens.mindverse.model;

import java.util.concurrent.ThreadLocalRandom;

import lombok.Data;
@Data
public class SensorStatistics {
	
	private Double temprature;
	private Double speed;
	private Double humidity;
	private Double pressure;
	
	public SensorStatistics randomiseData() {
		this.temprature= ThreadLocalRandom.current().nextDouble(-40, 70);
		this.speed= ThreadLocalRandom.current().nextDouble(-125, 125);
		this.humidity= ThreadLocalRandom.current().nextDouble(0, 100);
		this.pressure=  ThreadLocalRandom.current().nextDouble(0, 100);
		return this;
	}

}

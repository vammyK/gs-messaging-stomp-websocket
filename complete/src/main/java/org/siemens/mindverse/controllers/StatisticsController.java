package org.siemens.mindverse.controllers;

import java.util.concurrent.ThreadLocalRandom;

import org.siemens.mindverse.model.SensorStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class StatisticsController {

	@Autowired
    private SimpMessagingTemplate template;

	@SendTo("/topic/data")
	@Scheduled(fixedRate = 1000)
	public void greeting() throws Exception {
		SensorStatistics sn = new SensorStatistics();
		Integer l = ThreadLocalRandom.current().nextInt(0, 10);
		if (l < 5) {
	        this.template.convertAndSend("/topic/data", sn.randomiseData());
		}

	}

}

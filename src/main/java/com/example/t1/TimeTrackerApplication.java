package com.example.t1;

import com.example.t1.model.Plant;
import com.example.t1.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication(scanBasePackages={"com.example.t1"})
public class TimeTrackerApplication {

	@Autowired
	private PlantService plantService;

	public static void main(String[] args) {
		SpringApplication.run(TimeTrackerApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onReady() {
		plantService.addPlant(new Plant("Роза", "Цветок"));
		plantService.addPlant(new Plant("Ромашка", "Цветок"));
		plantService.addPlant(new Plant("Дуб", "Дерево"));
		System.out.println(plantService.getPlantsByType("Цветок"));
		System.out.println(plantService.getPlantByName("Дуб"));
	}

}

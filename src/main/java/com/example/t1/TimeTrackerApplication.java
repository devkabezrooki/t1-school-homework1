package com.example.t1;

import com.example.t1.model.Plant;
import com.example.t1.model.enums.PlantType;
import com.example.t1.service.executing.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages={"com.example.t1"})
@EnableAsync
public class TimeTrackerApplication {

	@Autowired
	private PlantService plantService;

	public static void main(String[] args) {
		SpringApplication.run(TimeTrackerApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onReady() {
		plantService.addPlant(new Plant("Помидоры", PlantType.VEGETABLE, 72));
		var flowers = plantService.getPlantsByType(PlantType.FLOWER);
		System.out.println(flowers);
		System.out.println(plantService.getPlantByName("Дуб"));
		plantService.waterPlant(flowers.get(0));
		plantService.waterAllPlantsThatNeeded();
	}

}

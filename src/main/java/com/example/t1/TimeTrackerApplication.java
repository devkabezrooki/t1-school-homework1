package com.example.t1;

import com.example.t1.model.Plant;
import com.example.t1.model.enums.PlantType;
import com.example.t1.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication(scanBasePackages={"com.example.t1"})
@EnableTransactionManagement
public class TimeTrackerApplication {

	@Autowired
	private PlantService plantService;

	public static void main(String[] args) {
		SpringApplication.run(TimeTrackerApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onReady() throws Exception {
		plantService.addPlant(new Plant("Помидоры", PlantType.VEGETABLE, 72));
		List<Plant> flowers = plantService.getPlantsByType(PlantType.FLOWER);
		System.out.println(flowers.stream().map(Plant::getName).collect(Collectors.joining(", ")));
		System.out.println(plantService.getPlantByName("Дуб").getName());
		plantService.waterPlant(flowers.get(0));
		List<Plant> plantsThatRequireWaterings = plantService.getAllPlantsThatRequireWatering();
		plantService.waterPlants(plantsThatRequireWaterings);
	}

}

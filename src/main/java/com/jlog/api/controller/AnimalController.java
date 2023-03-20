package com.jlog.api.controller;

import com.jlog.api.domain.AnimalType;
import com.jlog.api.service.animal.AnimalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

// type -> cat -> CatService
// type -> dog -> DogService

// cat -> 냐옹
// dog -> 멍멍

// 1. component list 주입
// 2. map {key: beanName, value: service}
// 3. enum
@Slf4j
@RestController
@RequiredArgsConstructor
public class AnimalController {

//    private final List<AnimalService> animalServices;
//    private final AnimalServiceFinder animalServiceFinder;
    private final Map<String, AnimalService> animalService;

    @GetMapping("/sound")
    public String sound(@RequestParam String type) {
        log.info("animalService={}, key={}", animalService, animalService.keySet());

//        AnimalService service = animalService.get(type.toLowerCase() + "Service");

//        return animalServiceFinder.find(type).getSound();

//        AnimalService service = animalServices.stream()
//                .filter(animalService -> animalService.getType().name().equals(type))
//                .findAny()
//                .orElseThrow(RuntimeException::new);

        AnimalType animalType = AnimalType.valueOf(type);
        AnimalService service = animalType.create();
        return service.getSound();
    }
}

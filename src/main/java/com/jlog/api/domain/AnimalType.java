package com.jlog.api.domain;

import com.jlog.api.service.animal.AnimalService;
import com.jlog.api.service.animal.CatService;
import com.jlog.api.service.animal.DogService;

import java.lang.reflect.InvocationTargetException;

public enum AnimalType {
    CAT(CatService.class),
    DOG(DogService.class);

    private final Class<? extends AnimalService> animalService;

    AnimalType(Class<? extends AnimalService> animalService) {
        this.animalService = animalService;
    }

    public AnimalService create() {
        try {
            return animalService.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}

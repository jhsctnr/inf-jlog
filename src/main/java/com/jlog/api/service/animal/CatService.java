package com.jlog.api.service.animal;

import com.jlog.api.domain.AnimalType;
import org.springframework.stereotype.Component;

@Component
public class CatService implements AnimalService {

    @Override
    public String getSound() {
        return "냐옹";
    }

    @Override
    public AnimalType getType() {
        return AnimalType.CAT;
    }
}

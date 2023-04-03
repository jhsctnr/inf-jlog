package com.jlog.api.response;

import com.jlog.api.domain.Factory;
import lombok.Getter;

@Getter
public class FactoryResponse {
    private Long value;
    private String text;

    public FactoryResponse(Factory factory) {
        this.value = factory.getId();
        this.text = factory.getName();
    }
}

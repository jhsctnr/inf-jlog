package com.jlog.api.response;

import com.jlog.api.domain.Dept;
import lombok.Getter;

@Getter
public class DeptResponse {
    private Long value;
    private String text;

    public DeptResponse(Dept dept) {
        this.value = dept.getId();
        this.text = dept.getName();
    }
}

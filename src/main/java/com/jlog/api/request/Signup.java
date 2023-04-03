package com.jlog.api.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Signup {

    private String nickname;
    private String password;
    private String email;
}

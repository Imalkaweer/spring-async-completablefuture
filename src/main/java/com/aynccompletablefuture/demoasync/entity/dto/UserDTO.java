package com.aynccompletablefuture.demoasync.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class UserDTO {

    private String first_name;
    private String last_name;
    private String email;
    private String gender;
}

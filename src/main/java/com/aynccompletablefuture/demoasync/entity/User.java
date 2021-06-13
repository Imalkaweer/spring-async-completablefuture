package com.aynccompletablefuture.demoasync.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Document(value = "user")
public class User {

    @Id
    private String id;

    private String first_name;
    private String last_name;
    private String email;
    private String gender;
}

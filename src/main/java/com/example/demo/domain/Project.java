package com.example.demo.domain;

import com.example.demo.statemachine.States;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("projects")
@Data
public class Project {

    @Id
    private String id;

    private int stageOneInput = 0;

    private int stageTwoInput = 0;

    private String stageOneResult = "";

    private String stageTwoResult = "";

    private States state = States.CREATED;

}

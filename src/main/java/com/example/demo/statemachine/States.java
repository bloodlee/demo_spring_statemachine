package com.example.demo.statemachine;

public enum States {
    CREATED,

    IN_STAGE_ONE_SETUP,
    IN_STAGE_ONE_RUNNING,
    STAGE_ONE_FAILED,

    IN_STAGE_TWO_SETUP,
    IN_STAGE_TWO_RUNNING,
    STAGE_TWO_FAILED,

    STAGE_TWO_FINISHED,

}

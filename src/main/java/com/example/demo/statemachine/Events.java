package com.example.demo.statemachine;

public enum Events {
    INITIALIZE,

    UPDATE_STATE_ONE_INPUT,
    START_STAGE_ONE,
    FAIL_STAGE_ONE,
    FINISH_STAGE_ONE,
    CLEAN_STAGE_ONE,

    UPDATE_STATE_TWO_INPUT,
    START_STAGE_TWO,
    FAIL_STAGE_TWO,
    FINISH_STAGE_TWO,
    CLEAN_STAGE_TWO,

    DELETE,
}

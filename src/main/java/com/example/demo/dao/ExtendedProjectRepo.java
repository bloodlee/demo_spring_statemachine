package com.example.demo.dao;

import com.example.demo.statemachine.States;

public interface ExtendedProjectRepo {

    long updateStageOneInput(String id, int input);

    long updateStageTwoInput(String id, int input);

    String getState(String id);

    long updateState(String id, States newState);
}

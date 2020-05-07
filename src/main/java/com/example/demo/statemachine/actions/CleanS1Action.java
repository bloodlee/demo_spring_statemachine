package com.example.demo.statemachine.actions;

import com.example.demo.statemachine.Events;
import com.example.demo.statemachine.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

@Slf4j
public class CleanS1Action implements Action<States, Events> {

    @Override
    public void execute(StateContext<States, Events> stateContext) {
        log.info("Clean the S1 result");
    }
}

package com.example.demo.statemachine.actions;

import com.example.demo.statemachine.Events;
import com.example.demo.statemachine.States;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class StageTwoAction implements Action<States, Events> {

    @Override
    public void execute(StateContext<States, Events> stateContext) {

    }

}

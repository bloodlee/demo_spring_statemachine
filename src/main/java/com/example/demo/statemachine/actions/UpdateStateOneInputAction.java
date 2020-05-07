package com.example.demo.statemachine.actions;

import com.example.demo.dao.ProjectRepo;
import com.example.demo.statemachine.Events;
import com.example.demo.statemachine.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

@Slf4j
public class UpdateStateOneInputAction implements Action<States, Events> {

    private final String projectId;

    private final ProjectRepo projectRepo;

    public UpdateStateOneInputAction(String projectId, ProjectRepo projectRepo) {
        this.projectId = projectId;
        this.projectRepo = projectRepo;
    }

    @Override
    public void execute(StateContext<States, Events> stateContext) {
        log.info("Going to update the state one input");

        Object input = stateContext.getMessage().getHeaders().get("input");

        if (input instanceof Integer) {
            projectRepo.updateStageOneInput(projectId, (Integer)input);
        }
    }
}

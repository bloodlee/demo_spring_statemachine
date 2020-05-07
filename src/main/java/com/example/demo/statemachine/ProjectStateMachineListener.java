package com.example.demo.statemachine;

import com.example.demo.dao.ProjectRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Slf4j
public class ProjectStateMachineListener extends StateMachineListenerAdapter<States, Events> {

    private final String projectId;

    private final ProjectRepo projectRepo;

    public ProjectStateMachineListener(String projectId, ProjectRepo projectRepo) {
        this.projectId = projectId;
        this.projectRepo = projectRepo;
    }

    @Override
    public void stateEntered(State<States, Events> state) {
        log.info("going to update project {}'s state to {}", projectId, state.getId());
        projectRepo.updateState(projectId, state.getId());
    }
}

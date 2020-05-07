package com.example.demo.statemachine.actions;

import com.example.demo.dao.ProjectRepo;
import com.example.demo.domain.Project;
import com.example.demo.executor.ProjectTaskExecutor;
import com.example.demo.statemachine.Events;
import com.example.demo.statemachine.States;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

import java.util.Optional;

@Slf4j
public class StageOneAction implements Action<States, Events> {

    private final String projectId;

    private final ProjectRepo projectRepo;

    private final ProjectTaskExecutor taskExecutor;

    public StageOneAction(String projectId, ProjectRepo projectRepo, ProjectTaskExecutor taskExecutor) {
        this.projectId = projectId;
        this.projectRepo = projectRepo;
        this.taskExecutor = taskExecutor;
    }

    @Override
    public void execute(StateContext<States, Events> stateContext) {
        log.info("Start stage one action of project {}", projectId);

        taskExecutor.execute(() -> {
            try {
                Thread.sleep(4000);

                Optional<Project> project = projectRepo.findById(projectId);
                if (project.isPresent()) {
                    if (project.get().getStageOneInput() % 2 == 0) {
                        log.info("the input is even, finish the stage one state");
                        stateContext.getStateMachine().sendEvent(Events.FINISH_STAGE_ONE);
                    } else {
                        log.info("the input is odd, fail the stage one state");
                        stateContext.getStateMachine().sendEvent(Events.FAIL_STAGE_ONE);
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

}

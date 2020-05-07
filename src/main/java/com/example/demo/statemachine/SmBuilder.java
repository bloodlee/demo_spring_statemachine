package com.example.demo.statemachine;

import com.example.demo.dao.ProjectRepo;
import com.example.demo.executor.ProjectTaskExecutor;
import com.example.demo.statemachine.actions.InitAction;
import com.example.demo.statemachine.actions.StageOneAction;
import com.example.demo.statemachine.actions.StageTwoAction;
import com.example.demo.statemachine.actions.UpdateStateOneInputAction;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

@Component
public class SmBuilder {

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private ProjectTaskExecutor taskExecutor;

    public StateMachine<States, Events> build(String projectId) throws Exception {
        StateMachineBuilder.Builder<States, Events> builder = StateMachineBuilder.builder();

        builder.configureConfiguration().withConfiguration()
                .machineId("project_statemachine")
                .beanFactory(beanFactory);

        builder.configureStates().withStates()
                .initial(States.CREATED)
                .stateDo(States.IN_STAGE_ONE_RUNNING, new StageOneAction(projectId, projectRepo, taskExecutor))
                .stateDo(States.IN_STAGE_TWO_RUNNING, new StageTwoAction())
                .states(EnumSet.allOf(States.class));

        builder.configureTransitions()
                .withExternal()
                    .source(States.CREATED)
                    .target(States.IN_STAGE_ONE_SETUP)
                    .event(Events.INITIALIZE)
                    .action(new InitAction())
                .and()
                .withExternal()
                    .source(States.IN_STAGE_ONE_SETUP)
                    .target(States.IN_STAGE_ONE_SETUP)
                    .event(Events.UPDATE_STATE_ONE_INPUT)
                    .action(new UpdateStateOneInputAction(projectId, projectRepo))
                .and()
                .withExternal()
                    .source(States.IN_STAGE_ONE_SETUP)
                    .target(States.IN_STAGE_ONE_RUNNING)
                    .event(Events.START_STAGE_ONE)
                .and()
                .withExternal()
                    .source(States.IN_STAGE_ONE_RUNNING)
                    .target(States.STAGE_TWO_FINISHED)
                    .event(Events.FINISH_STAGE_ONE)
                .and()
                .withExternal()
                    .source(States.IN_STAGE_ONE_RUNNING)
                    .target(States.STAGE_ONE_FAILED)
                    .event(Events.FAIL_STAGE_ONE)
                .and()
                .withExternal()
                    .source(States.STAGE_ONE_FAILED)
                    .target(States.IN_STAGE_ONE_SETUP)
                    .event(Events.CLEAN_STAGE_ONE)
                .and()
                .withExternal()
                    .source(States.STAGE_TWO_FINISHED)
                    .target(States.IN_STAGE_ONE_SETUP)
                    .event(Events.CLEAN_STAGE_ONE)
                .and()
                .withExternal()
                    .source(States.IN_STAGE_TWO_RUNNING)
                    .target(States.STAGE_ONE_FAILED)
                    .event(Events.FAIL_STAGE_ONE)
                .and()
                .withExternal()
                    .source(States.IN_STAGE_TWO_RUNNING)
                    .target(States.STAGE_TWO_FINISHED)
                    .event(Events.FINISH_STAGE_ONE)
                .and()
                .withExternal()
                    .source(States.STAGE_ONE_FAILED)
                    .target(States.IN_STAGE_ONE_SETUP)
                    .event(Events.CLEAN_STAGE_ONE);

        return builder.build();
    }

}

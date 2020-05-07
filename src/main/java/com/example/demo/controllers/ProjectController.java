package com.example.demo.controllers;

import com.example.demo.dao.ProjectRepo;
import com.example.demo.domain.Project;
import com.example.demo.statemachine.Events;
import com.example.demo.statemachine.ProjectStateMachineListener;
import com.example.demo.statemachine.SmBuilder;
import com.example.demo.statemachine.States;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Controller
public class ProjectController {

    private Map<String, StateMachine<States, Events>> stateMachines = Maps.newConcurrentMap();

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private SmBuilder smBuilder;

    @PostMapping("/project")
    @ResponseBody
    public void create() {
        Project project = new Project();
        projectRepo.save(project);

        log.info("a project was just created. id = {}", project.getId());

        log.info("create the state machine.");
        try {
            StateMachine<States, Events> sm = smBuilder.build(project.getId());
            sm.addStateListener(new ProjectStateMachineListener(project.getId(), projectRepo));

            sm.start();
            sm.sendEvent(Events.INITIALIZE);

            stateMachines.put(project.getId(), sm);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @PutMapping("/project/updateStateOneInput")
    @ResponseBody
    public String updateStageOneInput(@RequestParam String projectId, @RequestParam Integer inputOne) {
        if (stateMachines.containsKey(projectId)) {
            boolean accepted =
                    stateMachines.get(projectId).sendEvent(
                        MessageBuilder.withPayload(Events.UPDATE_STATE_ONE_INPUT)
                            .setHeader("input", inputOne)
                            .build());
            return Boolean.toString(accepted);
        }

        return "false";
    }

    @PostMapping("/project/runS1")
    @ResponseBody
    public String runS1(@RequestParam String projectId) {
        if (stateMachines.containsKey(projectId)) {
            boolean accepted =
                    stateMachines.get(projectId).sendEvent(Events.START_STAGE_ONE);
            return Boolean.toString(accepted);
        }

        return "false";
    }

    @PostMapping("/project/cleanS1")
    @ResponseBody
    public String cleanS1(@RequestParam String projectId) {
        if (stateMachines.containsKey(projectId)) {
            boolean accepted =
                    stateMachines.get(projectId).sendEvent(Events.CLEAN_STAGE_ONE);
            return Boolean.toString(accepted);
        }

        return "false";
    }

    @PostMapping("/project/reRunS1")
    @ResponseBody
    public String reRunS1(@RequestParam String projectId) {
        if (stateMachines.containsKey(projectId)) {
            boolean accepted =
                    stateMachines.get(projectId).sendEvent(Events.CLEAN_STAGE_ONE);

            return "OK";
        }

        return "false";
    }

    @GetMapping("/project/state")
    @ResponseBody
    public String getState(@RequestParam String projectId) {
        return projectRepo.getState(projectId);
    }

}

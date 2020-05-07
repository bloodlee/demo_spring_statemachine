package com.example.demo.dao;

import com.example.demo.domain.Project;
import com.example.demo.statemachine.States;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class ExtendedProjectRepoImpl implements ExtendedProjectRepo {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public long updateStageOneInput(String id, int input) {
        Query query = new Query(where("id").is(id));
        Update update = new Update();
        update.set("stageOneInput", input);

        UpdateResult result = mongoTemplate.updateFirst(query, update, Project.class);

        if (result != null) {
            return result.getModifiedCount();
        } else {
            return 0;
        }
    }

    @Override
    public long updateStageTwoInput(String id, int input) {
        Query query = new Query(where("id").is(id));
        Update update = new Update();
        update.set("stageTwoInput", input);

        UpdateResult result = mongoTemplate.updateFirst(query, update, Project.class);

        if (result != null) {
            return result.getModifiedCount();
        } else {
            return 0;
        }
    }

    @Override
    public String getState(String id) {
       Query query = new Query(where("id").is(id));
       query.fields().include("state");

       Project project = mongoTemplate.findOne(query, Project.class);

       if (project != null) {
           return project.getState().name();
       } else {
           return "";
       }
    }

    @Override
    public long updateState(String id, States newState) {
        Query query = new Query(where("id").is(id));
        Update update = new Update();
        update.set("state", newState);

        UpdateResult result = mongoTemplate.updateFirst(query, update, Project.class);

        if (result != null) {
            return result.getModifiedCount();
        } else {
            return 0;
        }
    }
}

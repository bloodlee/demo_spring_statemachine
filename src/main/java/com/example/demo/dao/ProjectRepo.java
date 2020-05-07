package com.example.demo.dao;

import com.example.demo.domain.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepo extends MongoRepository<Project, String>, ExtendedProjectRepo {

}

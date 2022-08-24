package com.portfolio.service;


import com.portfolio.entity.Project;
import com.portfolio.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    public List<Project> list(){
        return projectRepository.findAll();
    }

    public Optional<Project> getOne(int id){
        return projectRepository.findById(id);
    }

    public Optional<Project> getByName(String name){
        return projectRepository.findByName(name);
    }

    public void  save(Project project){
        projectRepository.save(project);
    }

    public void delete(int id){
        projectRepository.deleteById(id);
    }

    public boolean existsById(int id){
        return projectRepository.existsById(id);
    }

    public boolean existsByName(String name){
        return projectRepository.existsByName(name);
    }
}

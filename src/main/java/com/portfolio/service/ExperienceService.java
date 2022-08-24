package com.portfolio.service;

import com.portfolio.entity.Experience;
import com.portfolio.repository.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExperienceService {

    @Autowired
    ExperienceRepository experienceRepository;

    public List<Experience> list(){
        return experienceRepository.findAll();
    }

    public Optional<Experience> getOne(int id){
        return experienceRepository.findById(id);
    }

    public Optional<Experience> getByName(String name){
        return experienceRepository.findByName(name);
    }

    public void  save(Experience experience){
        experienceRepository.save(experience);
    }

    public void delete(int id){
        experienceRepository.deleteById(id);
    }

    public boolean existsById(int id){
        return experienceRepository.existsById(id);
    }

    public boolean existsByName(String name){
        return experienceRepository.existsByName(name);
    }
}

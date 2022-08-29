package com.portfolio.service;


import com.portfolio.entity.Person;
import com.portfolio.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public List<Person> list(){
        return personRepository.findAll();
    }

    public Optional<Person> getOne(int id){
        return personRepository.findById(id);
    }

    public Optional<Person> getByEmail(String email){
        return personRepository.findByEmail(email);
    }

    public void  save(Person experience){
        personRepository.save(experience);
    }

    public void delete(int id){
        personRepository.deleteById(id);
    }

    public boolean existsById(int id){
        return personRepository.existsById(id);
    }

    public boolean existsByEmail(String email){
        return personRepository.existsByEmail(email);
    }
}

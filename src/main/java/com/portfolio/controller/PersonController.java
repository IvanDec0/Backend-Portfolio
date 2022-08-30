package com.portfolio.controller;


import com.portfolio.dto.Mensaje;
import com.portfolio.dto.PersonDto;
import com.portfolio.entity.Person;
import com.portfolio.service.PersonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
@CrossOrigin(exposedHeaders = "Access-Control-Allow-Origin")
public class PersonController {

    @Autowired
    PersonService personService;

    @GetMapping("/lista")
    public ResponseEntity<List<Person>> list() {
        List<Person> list = personService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Person> getById(@PathVariable("id") int id) {
        if (!personService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Person person = personService.getOne(id).get();
        return new ResponseEntity(person, HttpStatus.OK);
    }

    @GetMapping("/detailname/{email}")
    public ResponseEntity<Person> getByName(@PathVariable("email") String email) {
        if (!personService.existsByEmail(email))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Person person = personService.getByEmail(email).get();
        return new ResponseEntity(person, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PersonDto personDto) {
        if (StringUtils.isBlank(personDto.getTitle()))
            return new ResponseEntity(new Mensaje("el title es obligatorio"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(personDto.getParagraph()))
            return new ResponseEntity(new Mensaje("el parrafo es obligatorio"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(personDto.getUrl()))
            return new ResponseEntity(new Mensaje("la url es obligatoria"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(personDto.getEmail()))
            return new ResponseEntity(new Mensaje("el email es obligatorio"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(personDto.getGithub()))
            return new ResponseEntity(new Mensaje("github es obligatorio"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(personDto.getLinkedin()))
            return new ResponseEntity(new Mensaje("linkedin es obligatorio"), HttpStatus.BAD_REQUEST);

        Person person = new Person(personDto.getTitle(),
                personDto.getParagraph(),
                personDto.getUrl(),
                personDto.getEmail(),
                personDto.getGithub(),
                personDto.getLinkedin());
        personService.save(person);
        return new ResponseEntity(new Mensaje("persona creada"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody PersonDto personDto) {
        if (!personService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        if (StringUtils.isBlank(personDto.getTitle()))
            return new ResponseEntity(new Mensaje("el title es obligatorio"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(personDto.getParagraph()))
            return new ResponseEntity(new Mensaje("el parrafo es obligatorio"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(personDto.getUrl()))
            return new ResponseEntity(new Mensaje("la url es obligatoria"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(personDto.getEmail()))
            return new ResponseEntity(new Mensaje("el email es obligatorio"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(personDto.getGithub()))
            return new ResponseEntity(new Mensaje("github es obligatorio"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(personDto.getLinkedin()))
            return new ResponseEntity(new Mensaje("linkedin es obligatorio"), HttpStatus.BAD_REQUEST);

        Person person = personService.getOne(id).get();
        person.setTitle(personDto.getTitle());
        person.setParagraph(personDto.getParagraph());
        person.setUrl(personDto.getUrl());
        person.setEmail(personDto.getEmail());
        person.setGithub(personDto.getGithub());
        person.setLinkedin(personDto.getLinkedin());
        personService.save(person);
        return new ResponseEntity(new Mensaje("persona actualizada"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        if (!personService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        personService.delete(id);
        return new ResponseEntity(new Mensaje("persona eliminada"), HttpStatus.OK);
    }
}

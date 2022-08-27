package com.portfolio.controller;

import com.portfolio.dto.ExperienceDto;
import com.portfolio.dto.Mensaje;
import com.portfolio.entity.Experience;
import com.portfolio.service.ExperienceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/experience")
@CrossOrigin(origins = {"http://localhost:4200"})
public class ExperienceController {

    @Autowired
    ExperienceService experienceService;

    @GetMapping("/lista")
    public ResponseEntity<List<Experience>> list() {
        List<Experience> list = experienceService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Experience> getById(@PathVariable("id") int id) {
        if (!experienceService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Experience experience = experienceService.getOne(id).get();
        return new ResponseEntity(experience, HttpStatus.OK);
    }

    @GetMapping("/detailname/{name}")
    public ResponseEntity<Experience> getByName(@PathVariable("name") String name) {
        if (!experienceService.existsByName(name))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Experience experience = experienceService.getByName(name).get();
        return new ResponseEntity(experience, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ExperienceDto experienceDto) {
        if (StringUtils.isBlank(experienceDto.getName()))
            return new ResponseEntity(new Mensaje("el name es obligatorio"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(experienceDto.getDescription()))
            return new ResponseEntity(new Mensaje("la descripcion es obligatoria"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(experienceDto.getDateStart()))
            return new ResponseEntity(new Mensaje("la fecha de inicio es obligatoria"), HttpStatus.BAD_REQUEST);
        if (experienceService.existsByName(experienceDto.getName()))
            return new ResponseEntity(new Mensaje("ese name ya existe"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(experienceDto.getDateEnd())) {
            experienceDto.setDateEnd("Present");
        } else {
            experienceDto.setDateEnd(experienceDto.getDateEnd());
        }
        Experience experience = new Experience(experienceDto.getName(),
                experienceDto.getDescription(),
                experienceDto.getDateStart(),
                experienceDto.getDateEnd());
        experienceService.save(experience);
        return new ResponseEntity(new Mensaje("experiencia creada"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody ExperienceDto experienceDto) {
        if (!experienceService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        if (StringUtils.isBlank(experienceDto.getName()))
            return new ResponseEntity(new Mensaje("el name es obligatorio"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(experienceDto.getDescription()))
            return new ResponseEntity(new Mensaje("la descripcion es obligatoria"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(experienceDto.getDateStart()))
            return new ResponseEntity(new Mensaje("la fecha de inicio es obligatoria"), HttpStatus.BAD_REQUEST);

        Experience experience = experienceService.getOne(id).get();
        experience.setName(experienceDto.getName());
        experience.setDescription(experienceDto.getDescription());
        experience.setDateStart(experienceDto.getDateStart());
        if (StringUtils.isBlank(experienceDto.getDateEnd())) {
            experience.setDateEnd("Present");
        } else {
            experience.setDateEnd(experienceDto.getDateEnd());
        }
        experienceService.save(experience);
        return new ResponseEntity(new Mensaje("experiencia actualizada"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        if (!experienceService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        experienceService.delete(id);
        return new ResponseEntity(new Mensaje("experiencia eliminada"), HttpStatus.OK);
    }


}

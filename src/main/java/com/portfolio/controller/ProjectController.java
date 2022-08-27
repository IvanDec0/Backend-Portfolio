package com.portfolio.controller;


import com.portfolio.dto.Mensaje;

import com.portfolio.dto.ProjectDto;
import com.portfolio.entity.Project;
import com.portfolio.service.ProjectService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping("/lista")
    public ResponseEntity<List<Project>> list(){
        List<Project> list = projectService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Project> getById(@PathVariable("id") int id){
        if(!projectService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Project project = projectService.getOne(id).get();
        return new ResponseEntity(project, HttpStatus.OK);
    }

    @GetMapping("/detailname/{name}")
    public ResponseEntity<Project> getByNombre(@PathVariable("name") String name){
        if(!projectService.existsByName(name))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Project project = projectService.getByName(name).get();
        return new ResponseEntity(project, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProjectDto projectDto){
        if(StringUtils.isBlank(projectDto.getName()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(projectDto.getParagraph()))
            return new ResponseEntity(new Mensaje("el parrafo es obligatorio"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(projectDto.getImg()))
            return new ResponseEntity(new Mensaje("la imagen es obligatoria"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(projectDto.getGithub()))
            return new ResponseEntity(new Mensaje("github es obligatorio"), HttpStatus.BAD_REQUEST);
        if(projectService.existsByName(projectDto.getName()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        Project project = new Project(projectDto.getName(), projectDto.getParagraph(), projectDto.getImg(), projectDto.getGithub());
        projectService.save(project);
        return new ResponseEntity(new Mensaje("proyecto creado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody ProjectDto projectDto){
        if(!projectService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        if(StringUtils.isBlank(projectDto.getName()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(projectDto.getParagraph()))
            return new ResponseEntity(new Mensaje("el parrafo es obligatorio"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(projectDto.getImg()))
            return new ResponseEntity(new Mensaje("la imagen es obligatoria"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(projectDto.getGithub()))
            return new ResponseEntity(new Mensaje("github es obligatorio"), HttpStatus.BAD_REQUEST);

        Project project = projectService.getOne(id).get();
        project.setName(projectDto.getName());
        project.setParagraph(projectDto.getParagraph());
        project.setImg(projectDto.getImg());
        project.setGithub(projectDto.getGithub());
        projectService.save(project);
        return new ResponseEntity(new Mensaje("proyecto actualizado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if(!projectService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        projectService.delete(id);
        return new ResponseEntity(new Mensaje("proyecto eliminado"), HttpStatus.OK);
    }
}

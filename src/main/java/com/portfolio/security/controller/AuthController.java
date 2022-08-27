package com.portfolio.security.controller;

import com.portfolio.dto.ExperienceDto;
import com.portfolio.dto.Mensaje;
import com.portfolio.entity.Experience;
import com.portfolio.security.dto.JwtDto;
import com.portfolio.security.dto.LoginUsuario;
import com.portfolio.security.entity.Rol;
import com.portfolio.security.entity.Usuario;
import com.portfolio.security.enums.RolNombre;
import com.portfolio.security.service.RolService;
import com.portfolio.security.service.UsuarioService;
import com.portfolio.security.dto.NuevoUsuario;
import com.portfolio.security.jwt.JwtProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    JwtProvider jwtProvider;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos o email inválido"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(usuarioService.existsByEmail(nuevoUsuario.getEmail()))
            return new ResponseEntity(new Mensaje("ese email ya existe"), HttpStatus.BAD_REQUEST);
        Usuario usuario =
                new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(),
                        passwordEncoder.encode(nuevoUsuario.getPassword()), nuevoUsuario.getTitle(), nuevoUsuario.getParrafo(),
                        nuevoUsuario.getUrl(),nuevoUsuario.getGithub(), nuevoUsuario.getLinkedin());
        Set<Rol> roles = new HashSet<>();
        if(nuevoUsuario.getRoles().contains("admin"))
            roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        return new ResponseEntity(new Mensaje("usuario guardado"), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult) {
        if (!usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);

        Usuario usuario = usuarioService.getOne(id).get();
        usuario.setNombre(nuevoUsuario.getNombre());
        usuario.setNombreUsuario(nuevoUsuario.getNombreUsuario());
        usuario.setEmail(nuevoUsuario.getEmail());
        usuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));
        usuario.setTitle(nuevoUsuario.getTitle());
        usuario.setParrafo(nuevoUsuario.getParrafo());
        usuario.setUrl(nuevoUsuario.getUrl());
        usuario.setGithub(nuevoUsuario.getGithub());
        usuario.setLinkedin(nuevoUsuario.getLinkedin());

        usuarioService.save(usuario);
        return new ResponseEntity(new Mensaje("usuario actualizado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        if (!usuarioService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        usuarioService.delete(id);
        return new ResponseEntity(new Mensaje("usuario eliminado"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }
}

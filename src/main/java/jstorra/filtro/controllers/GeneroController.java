package jstorra.filtro.controllers;

import jstorra.filtro.models.Genero;
import jstorra.filtro.services.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/genero")
public class GeneroController {
    @Autowired
    GeneroService generoService;

    @PostMapping()
    public Map<Object, Object> guardarGenero(@RequestBody Genero genero) {
        return generoService.guardarGenero(genero);
    }
}

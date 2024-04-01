package jstorra.filtro.controllers;

import jstorra.filtro.models.Genero;
import jstorra.filtro.services.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/genero")
@CrossOrigin("*")
public class GeneroController {
    @Autowired
    GeneroService generoService;

    @PostMapping
    public Map<Object, Object> guardarGenero(@RequestBody Genero genero) {
        return generoService.guardarGenero(genero);
    }
}

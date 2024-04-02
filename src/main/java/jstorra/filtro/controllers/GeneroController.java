package jstorra.filtro.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jstorra.filtro.models.Genero;
import jstorra.filtro.services.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/genero")
@CrossOrigin("*")
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "bearerAuth")
public class GeneroController {
    @Autowired
    GeneroService generoService;

    @GetMapping
    public List<Genero> obtenerGeneros() {
        return generoService.obtenerGeneros();
    }

    @GetMapping("/{id}")
    public Genero obtenerGeneroPorId(@PathVariable Object id) {
        return generoService.obtenerGeneroPorId(id);
    }

    @PostMapping
    public Map<Object, Object> guardarGenero(@RequestBody Genero genero) {
        return generoService.guardarGenero(genero);
    }

    @PutMapping("/{id}")
    public Map<Object, Object> editarGenero(@PathVariable Object id, @RequestBody Genero genero) {
        return generoService.editarGenero(id, genero);
    }

    @DeleteMapping("/{id}")
    public Map<Object, Object> eliminarGenero(@PathVariable Object id) {
        return generoService.eliminarGenero(id);
    }
}

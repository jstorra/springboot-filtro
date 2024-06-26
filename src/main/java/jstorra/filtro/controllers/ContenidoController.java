package jstorra.filtro.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jstorra.filtro.models.dto.ContenidoDTO;
import jstorra.filtro.services.ContenidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contenido")
@CrossOrigin("*")
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "bearerAuth")
public class ContenidoController {
    @Autowired
    ContenidoService contenidoService;

    @GetMapping
    public List<Map<Object, Object>> obtenerContenidos() {
        return contenidoService.obtenerContenidos();
    }

    @GetMapping("/{id}")
    public Map<Object, Object> obtenerContenidoPorId(@PathVariable Object id) {
        return contenidoService.obtenerContenidoPorId(id);
    }

    @PostMapping
    public Map<Object, Object> guardarContenido(@RequestBody ContenidoDTO contenidoDTO) {
        return contenidoService.guardarContenido(contenidoDTO);
    }

    @PutMapping("/{id}")
    public Map<Object, Object> editarContenido(@PathVariable Object id, @RequestBody ContenidoDTO contenidoDTO) {
        return contenidoService.editarContenido(id, contenidoDTO);
    }

    @DeleteMapping("/{id}")
    public Map<Object, Object> eliminarContenido(@PathVariable Object id) {
        return contenidoService.eliminarContenido(id);
    }
}

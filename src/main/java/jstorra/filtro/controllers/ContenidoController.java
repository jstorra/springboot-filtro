package jstorra.filtro.controllers;

import jstorra.filtro.models.dto.ContenidoDTO;
import jstorra.filtro.services.ContenidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/contenido")
@CrossOrigin("*")
public class ContenidoController {
    @Autowired
    ContenidoService contenidoService;

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

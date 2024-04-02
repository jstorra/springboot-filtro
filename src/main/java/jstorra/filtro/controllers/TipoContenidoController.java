package jstorra.filtro.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jstorra.filtro.models.TipoContenido;
import jstorra.filtro.services.TipoContenidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("tipoContenido")
@CrossOrigin("*")
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "bearerAuth")
public class TipoContenidoController {
    @Autowired
    TipoContenidoService tipoContenidoService;

    @GetMapping
    public List<Map<Object, Object>> obtenerTiposContenido() {
        return tipoContenidoService.obtenerTiposContenido();
    }

    @GetMapping("/{id}")
    public Map<Object, Object> obtenerTiposContenidoPorId(@PathVariable Object id) {
        return tipoContenidoService.obtenerTiposContenidoPorId(id);
    }

    @PostMapping
    public Map<Object, Object> guardarTipoContenido(@RequestBody TipoContenido tipoContenido) {
        return tipoContenidoService.guardarTipoContenido(tipoContenido);
    }

    @PutMapping("/{id}")
    public Map<Object, Object> editarTipoContenido(@PathVariable Object id, @RequestBody TipoContenido tipoContenido) {
        return tipoContenidoService.editarTipoContenido(id, tipoContenido);
    }

    @PostMapping("/agregarPlataforma")
    public Map<Object, Object> agregarPlataforma(@RequestBody Map<Object, Object> valores) {
        return tipoContenidoService.agregarPlataforma(valores);
    }

    @DeleteMapping("/{id}")
    public Map<Object, Object> eliminarTipoContenido(@PathVariable Object id) {
        return tipoContenidoService.eliminarTipoContenido(id);
    }

    @GetMapping("/{id}/plataformasApropiadas")
    public List<String> plataformasApropiadas(@PathVariable Object id) {
        return tipoContenidoService.plataformasApropiadas(id);
    }
}

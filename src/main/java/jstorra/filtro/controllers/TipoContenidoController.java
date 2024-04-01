package jstorra.filtro.controllers;

import jstorra.filtro.models.TipoContenido;
import jstorra.filtro.services.TipoContenidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("tipocontenido")
@CrossOrigin("*")
public class TipoContenidoController {
    @Autowired
    TipoContenidoService tipoContenidoService;

    @PostMapping
    public Map<Object, Object> guardarTipoContenido(@RequestBody TipoContenido tipoContenido) {
        return tipoContenidoService.guardarTipoContenido(tipoContenido);
    }

    @PostMapping("/agregarPlataforma")
    public Map<Object, Object> agregarPlataforma(@RequestBody Map<Object, Object> valores) {
        return tipoContenidoService.agregarPlataforma(valores);
    }
}

package jstorra.filtro.controllers;

import jstorra.filtro.models.Plataforma;
import jstorra.filtro.services.PlataformaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/plataforma")
public class PlataformaController {
    @Autowired
    PlataformaService plataformaService;

    @PostMapping()
    public Map<Object, Object> guardarPlataforma(@RequestBody Plataforma plataforma) {
        return plataformaService.guardarPlataforma(plataforma);
    }

    @PostMapping("/agregarContenido")
    public Map<Object, Object> agregarTipoContenido(@RequestBody Map<Object, Object> valores) {
        return plataformaService.agregarTipoContenido(valores);
    }


}

package jstorra.filtro.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jstorra.filtro.models.Plataforma;
import jstorra.filtro.services.PlataformaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/plataforma")
@CrossOrigin("*")
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "bearerAuth")
public class PlataformaController {
    @Autowired
    PlataformaService plataformaService;

    @PostMapping
    public Map<Object, Object> guardarPlataforma(@RequestBody Plataforma plataforma) {
        return plataformaService.guardarPlataforma(plataforma);
    }

    @PostMapping("/agregarContenido")
    public Map<Object, Object> agregarTipoContenido(@RequestBody Map<Object, Object> valores) {
        return plataformaService.agregarTipoContenido(valores);
    }


}

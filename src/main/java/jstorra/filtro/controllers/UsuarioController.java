package jstorra.filtro.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jstorra.filtro.models.Contenido;
import jstorra.filtro.models.Usuario;
import jstorra.filtro.security.JWTAuthenticationConfig;
import jstorra.filtro.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {
    @Autowired
    JWTAuthenticationConfig jwtAuthenticationConfig;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/ingresar")
    public Usuario ingresar(
            @RequestParam("email") String email,
            @RequestParam("contraseña") String contraseña) {

        usuarioService.validacionUsuario(email, contraseña);
        String token = jwtAuthenticationConfig.getJWTToken(email);
        return new Usuario(email, contraseña, token);
    }

    @PostMapping("/registrar")
    public Map<Object, Object> registrar(
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("contraseña") String contraseña) {

        return usuarioService.registrarUsuario(new Usuario(nombre, email, contraseña, null));
    }

    @DeleteMapping
    public Map<Object, Object> eliminarUsuario(@PathVariable Object id) {
        return usuarioService.eliminarUsuario(id);
    }

    @GetMapping("/{id}/contenido")
    public List<Contenido> obtenerContenidos(@PathVariable Object id) {
        return usuarioService.obtenerContenidos(id);
    }
}

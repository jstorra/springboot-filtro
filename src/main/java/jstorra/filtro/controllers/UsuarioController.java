package jstorra.filtro.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jstorra.filtro.exceptions.InvalidEmailException;
import jstorra.filtro.models.Contenido;
import jstorra.filtro.models.Usuario;
import jstorra.filtro.security.JWTAuthenticationConfig;
import jstorra.filtro.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/usuario")
@CrossOrigin("*")
@PreAuthorize("hasRole('ADMIN')")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {
    @Autowired
    JWTAuthenticationConfig jwtAuthenticationConfig;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public List<Map<Object, Object>> obtenerUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    @GetMapping("/{id}")
    public Map<Object, Object> obtenerUsuarios(@PathVariable Object id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }

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

        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches())
            throw new InvalidEmailException("El correo ingresado no tiene un formato valido.");

        return usuarioService.registrarUsuario(new Usuario(nombre, email, contraseña, null));
    }

    @PutMapping("/{id}")
    public Map<Object, Object> editarUsuario(
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("contraseña") String contraseña,
            @PathVariable Object id) {

        return usuarioService.editarUsuario(id, new Usuario(nombre, email, contraseña, null));
    }

    @DeleteMapping("/{id}")
    public Map<Object, Object> eliminarUsuario(@PathVariable Object id) {
        return usuarioService.eliminarUsuario(id);
    }

    @GetMapping("/{id}/contenido")
    public List<Contenido> obtenerContenidos(@PathVariable Object id) {
        return usuarioService.obtenerContenidos(id);
    }
}

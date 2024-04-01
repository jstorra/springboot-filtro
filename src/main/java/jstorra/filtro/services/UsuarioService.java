package jstorra.filtro.services;

import jstorra.filtro.exceptions.*;
import jstorra.filtro.models.Contenido;
import jstorra.filtro.models.Usuario;
import jstorra.filtro.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public void validacionUsuario(String email, String contraseña) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null || !usuario.getContraseña().equalsIgnoreCase(contraseña)) {
            throw new InvalidUsuarioException("Las credenciales ingresadas no son validas.");
        }
    }

    public Map<Object, Object> registrarUsuario(Usuario usuario) {
        try {
            usuarioRepository.save(usuario);
            return new LinkedHashMap<>() {{
                put("message", "EL usuario ha sido registrado.");
            }};
        } catch (Exception e) {
            throw new UsuarioDuplicateException("El correo ingresado ya esta asociado a otro usuario.");
        }
    }

    public Map<Object, Object> eliminarUsuario(Object id) {
        try {
            int parsedId = Integer.parseInt(id.toString());

            Usuario usuario = usuarioRepository.findById(parsedId).orElseThrow(() -> new UsuarioNotFound("El usuario ingresado no existe."));

            usuarioRepository.deleteById(parsedId);
            return new LinkedHashMap<>() {{
                put("message", "El usuario ha sido eliminado.");
            }};
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Los parametros ingresados no tienen un formato valido.");
        }
    }

    public List<Contenido> obtenerContenidos(Object id) {
        try {
            int parsedId = Integer.parseInt(id.toString());

            Usuario usuario = usuarioRepository.findById(parsedId).orElseThrow(() -> new UsuarioNotFound("El usuario ingresado no existe."));

            return usuario.getContenidos();
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Los parametros ingresados no tienen un formato valido.");
        }
    }
}

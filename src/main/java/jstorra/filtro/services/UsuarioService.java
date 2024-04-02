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

    public List<Map<Object, Object>> obtenerUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuario -> {
                    Map<Object, Object> usuarioMap = new LinkedHashMap<>();
                    usuarioMap.put("id", usuario.getId());
                    usuarioMap.put("nombre", usuario.getNombre());
                    usuarioMap.put("email", usuario.getEmail());
                    usuarioMap.put("contraseña", usuario.getContraseña());
                    return usuarioMap;
                })
                .toList();
    }

    public Map<Object, Object> obtenerUsuarioPorId(Object id) {
        try {
            int parsedId = Integer.parseInt(id.toString());

            Usuario usuario = usuarioRepository.findById(parsedId).orElseThrow(() -> new UsuarioNotFound("El usuario ingresado no existe."));
            return new LinkedHashMap<>() {{
                put("id", usuario.getId());
                put("nombre", usuario.getNombre());
                put("email", usuario.getEmail());
                put("contraseña", usuario.getContraseña());
            }};
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Los parametros ingresados no tienen un formato valido.");
        }
    }

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

    public Map<Object, Object> editarUsuario(Object id, Usuario usuarioToUpdate) {
        try {
            int parsedId = Integer.parseInt(id.toString());

            Usuario usuario = usuarioRepository.findById(parsedId).orElseThrow(() -> new UsuarioNotFound("El usuario ingresado no existe."));
            usuarioToUpdate.setId(parsedId);
            registrarUsuario(usuarioToUpdate);
            return new LinkedHashMap<>() {{
                put("message", "El usuario ha sido actualizado.");
            }};
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Los parametros ingresados no tienen un formato valido.");
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

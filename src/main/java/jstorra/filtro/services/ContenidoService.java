package jstorra.filtro.services;

import jstorra.filtro.exceptions.*;
import jstorra.filtro.models.*;
import jstorra.filtro.models.dto.ContenidoDTO;
import jstorra.filtro.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContenidoService {
    @Autowired
    ContenidoRepository contenidoRepository;

    @Autowired
    TipoContenidoRepository tipoContenidoRepository;

    @Autowired
    GeneroRepository generoRepository;

    @Autowired
    PlataformaRepository plataformaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public Map<Object, Object> guardarContenido(ContenidoDTO contenidoDTO) {
        Contenido contenido = new Contenido();

        contenido.setId(contenidoDTO.getId());
        contenido.setNombre(contenidoDTO.getNombre());

        TipoContenido tipoContenido = tipoContenidoRepository.findById(contenidoDTO.getTipo_id())
                .orElseThrow(() -> new TipoContenidoNotFound("El tipo de contenido ingresado no existe."));

        contenido.setTipoContenido(tipoContenido);

        if (Objects.equals(contenidoDTO.getGenero1_id(), contenidoDTO.getGenero2_id()))
            throw new GeneroYaIngresado("Los generos ingresados no deben ser iguales.");

        Genero genero1 = generoRepository.findById(contenidoDTO.getGenero1_id())
                .orElseThrow(() -> new GeneroNotFound("El genero ingresado no existe."));

        contenido.setGenero1(genero1);

        if (contenidoDTO.getGenero2_id() != null) {
            Genero genero2 = generoRepository.findById(contenidoDTO.getGenero1_id())
                    .orElseThrow(() -> new GeneroNotFound("El genero ingresado no existe."));

            contenido.setGenero2(genero2);
        }

        List<String> estados = new ArrayList<>(Arrays.asList("abandonado", "terminado", "pendiente", "viendo", "en pausa"));

        if (!estados.contains(contenidoDTO.getEstado()))
            throw new InvalidEstado("El estado ingresado no es valido.");

        contenido.setEstado(contenidoDTO.getEstado());

        Plataforma plataforma = plataformaRepository.findById(contenidoDTO.getPlataforma_id())
                .orElseThrow(() -> new PlataformaNotFound("La plataforma ingresada no existe."));

        contenido.setPlataforma(plataforma);

        if (contenidoDTO.getCalificacion() != null && contenidoDTO.getComentario() != null && (contenidoDTO.getEstado().equalsIgnoreCase("Abandonado") || contenidoDTO.getEstado().equalsIgnoreCase("Terminado"))) {
            contenido.setCalificacion(contenidoDTO.getCalificacion());
            contenido.setComentario(contenidoDTO.getComentario());
        } else {
            throw new InvalidOperation("No se puede asignar califacion ni comentario sí el estado no es Terminado o Abandonado.");
        }

        Usuario usuario = usuarioRepository.findById(contenidoDTO.getUsuario_id())
                .orElseThrow(() -> new UsuarioNotFound("El usuario ingresado no existe."));

        contenido.setUsuario(usuario);

        contenidoRepository.save(contenido);
        return new LinkedHashMap<>() {{
            put("message", "El contenido ha sido registrado.");
        }};
    }

    public Map<Object, Object> editarContenido(Object id, ContenidoDTO contenidoDTO) {
        try {
            int parsedId = Integer.parseInt(id.toString());

            Contenido contenido = contenidoRepository.findById(parsedId).orElseThrow(() -> new ContenidoNotFound("El contenido ingresado no existe."));
            contenidoDTO.setId(parsedId);
            guardarContenido(contenidoDTO);
            return new LinkedHashMap<>() {{
                put("message", "El contenido ha sido actualizado.");
            }};
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Los parametros ingresados no tienen un formato valido.");
        }
    }

    public Map<Object, Object> eliminarContenido(Object id) {
        try {
            int parsedId = Integer.parseInt(id.toString());

            Contenido contenido = contenidoRepository.findById(parsedId).orElseThrow(() -> new ContenidoNotFound("El contenido ingresado no existe."));

            contenidoRepository.deleteById(parsedId);
            return new LinkedHashMap<>() {{
                put("message", "El contenido ha sido eliminado.");
            }};
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Los parametros ingresados no tienen un formato valido.");
        }
    }
}

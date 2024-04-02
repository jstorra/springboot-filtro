package jstorra.filtro.services;

import jakarta.transaction.Transactional;
import jstorra.filtro.exceptions.InvalidFormatException;
import jstorra.filtro.exceptions.TipoContenidoDuplicateException;
import jstorra.filtro.exceptions.TipoContenidoNotFound;
import jstorra.filtro.models.Plataforma;
import jstorra.filtro.models.TipoContenido;
import jstorra.filtro.repositories.PlataformaRepository;
import jstorra.filtro.repositories.TipoContenidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TipoContenidoService {
    @Autowired
    TipoContenidoRepository tipoContenidoRepository;

    @Autowired
    PlataformaRepository plataformaRepository;

    public List<Map<Object, Object>> obtenerTiposContenido() {
        return tipoContenidoRepository.findAll().stream()
                .map(tipoContenido -> {
                    Map<Object, Object> tipoContenidoMap = new LinkedHashMap<>();
                    tipoContenidoMap.put("id", tipoContenido.getId());
                    tipoContenidoMap.put("nombre", tipoContenido.getNombre());
                    tipoContenidoMap.put("plataformas", tipoContenido.getPlataformas());
                    return tipoContenidoMap;
                }).toList();
    }

    public Map<Object, Object> obtenerTiposContenidoPorId(Object id) {
        try {
            int parsedId = Integer.parseInt(id.toString());

            TipoContenido tipoContenido = tipoContenidoRepository.findById(parsedId)
                    .orElseThrow(() -> new TipoContenidoNotFound("El tipo de contenido ingresado no existe."));

            return new LinkedHashMap<>() {{
                put("id", tipoContenido.getId());
                put("nombre", tipoContenido.getNombre());
                put("plataformas", tipoContenido.getPlataformas());
            }};
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Los parametros ingresados no tienen un formato valido.");
        }
    }

    public Map<Object, Object> guardarTipoContenido(TipoContenido tipoContenido) {
        try {
            tipoContenidoRepository.save(tipoContenido);
            return new LinkedHashMap<>() {{
                put("message", "El tipo de contenido ha sido registrado.");
            }};
        } catch (Exception e) {
            throw new TipoContenidoDuplicateException("El tipo de contenido ingresado ya existe.");
        }
    }

    public Map<Object, Object> editarTipoContenido(Object id, TipoContenido tipoContenidoToUpdate) {
        try {
            int parsedId = Integer.parseInt(id.toString());

            tipoContenidoRepository.findById(parsedId).orElseThrow(() -> new TipoContenidoNotFound("El tipo de contenido ingresado no existe."));

            tipoContenidoToUpdate.setId(parsedId);
            guardarTipoContenido(tipoContenidoToUpdate);

            return new LinkedHashMap<>() {{
                put("message", "El tipo de contenido ha sido actualizado.");
            }};
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Los parametros ingresados no tienen un formato valido.");
        }
    }

    @Transactional
    public Map<Object, Object> agregarPlataforma(Map<Object, Object> valores) {
        try {
            int tipoId = Integer.parseInt(valores.get("tipo_id").toString());
            int plataformaId = Integer.parseInt(valores.get("plataforma_id").toString());

            TipoContenido tipoContenido = tipoContenidoRepository.findById(tipoId)
                    .orElseThrow(() -> new TipoContenidoNotFound("El tipo de contenido ingresado no existe."));

            Plataforma plataforma = plataformaRepository.findById(plataformaId)
                    .orElseThrow(() -> new TipoContenidoNotFound("La plataforma ingresada no existe."));

            tipoContenido.getPlataformas().add(plataforma);
            plataforma.getTipoContenidos().add(tipoContenido);

            return new LinkedHashMap<>() {{
                put("message", "Se ha agregado la plataforma al tipo de contenido.");
            }};
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Los parametros ingresados no tienen un formato valido.");
        }
    }

    public Map<Object, Object> eliminarTipoContenido(Object id) {
        try {
            int parsedId = Integer.parseInt(id.toString());

            tipoContenidoRepository.findById(parsedId).orElseThrow(() -> new TipoContenidoNotFound("El tipo de contenido ingresado no existe."));

            tipoContenidoRepository.deleteById(parsedId);

            return new LinkedHashMap<>() {{
                put("message", "El tipo de contenido ha sido eliminado.");
            }};
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Los parametros ingresados no tienen un formato valido.");
        }
    }

    public List<String> plataformasApropiadas(Object id) {
        try {
            int parsedId = Integer.parseInt(id.toString());

            tipoContenidoRepository.findById(parsedId).orElseThrow(() -> new TipoContenidoNotFound("El tipo de contenido ingresado no existe."));

            return tipoContenidoRepository.plataformasApropiadas(parsedId);
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Los parametros ingresados no tienen un formato valido.");
        }
    }
}

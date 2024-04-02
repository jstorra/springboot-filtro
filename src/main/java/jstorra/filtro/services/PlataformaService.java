package jstorra.filtro.services;

import jakarta.transaction.Transactional;
import jstorra.filtro.exceptions.InvalidFormatException;
import jstorra.filtro.exceptions.PlataformaDuplicateException;
import jstorra.filtro.exceptions.PlataformaNotFound;
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
public class PlataformaService {
    @Autowired
    PlataformaRepository plataformaRepository;

    @Autowired
    TipoContenidoRepository tipoContenidoRepository;

    public List<Map<Object, Object>> obtenerPlataformas() {
        return plataformaRepository.findAll().stream()
                .map(plataforma -> {
                    Map<Object, Object> plataformaMap = new LinkedHashMap<>();
                    plataformaMap.put("id", plataforma.getId());
                    plataformaMap.put("nombre", plataforma.getNombre());
                    plataformaMap.put("tipoContenidos", plataforma.getTipoContenidos());
                    return plataformaMap;
                }).toList();
    }

    public Map<Object, Object> obtenerPlataformaPorId(Object id) {
        try {
            int parsedId = Integer.parseInt(id.toString());

            Plataforma plataforma = plataformaRepository.findById(parsedId)
                    .orElseThrow(() -> new PlataformaNotFound("La plataforma ingresada no existe."));

            return new LinkedHashMap<>() {{
                put("id", plataforma.getId());
                put("nombre", plataforma.getNombre());
                put("tipoContenidos", plataforma.getTipoContenidos());
            }};
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Los parametros ingresados no tienen un formato valido.");
        }
    }

    public Map<Object, Object> guardarPlataforma(Plataforma plataforma) {
        try {
            plataformaRepository.save(plataforma);

            return new LinkedHashMap<>() {{
                put("message", "La plataforma ha sido registrada.");
            }};
        } catch (Exception e) {
            throw new PlataformaDuplicateException("La plataforma ingresada ya existe.");
        }
    }

    public Map<Object, Object> editarPlataforma(Object id, Plataforma plataformaToUpdate) {
        try {
            int parsedId = Integer.parseInt(id.toString());

            plataformaRepository.findById(parsedId).orElseThrow(() -> new PlataformaNotFound("La plataforma ingresada no existe."));

            plataformaToUpdate.setId(parsedId);
            guardarPlataforma(plataformaToUpdate);

            return new LinkedHashMap<>() {{
                put("message", "La plataforma ha sido actualizada.");
            }};
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Los parametros ingresados no tienen un formato valido.");
        }
    }

    @Transactional
    public Map<Object, Object> agregarTipoContenido(Map<Object, Object> valores) {
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
                put("message", "Se ha agregado el tipo de contenido a la plataforma.");
            }};
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Los parametros ingresados no tienen un formato valido.");
        }
    }

    public Map<Object, Object> eliminarPlataforma(Object id) {
        try {
            int parsedId = Integer.parseInt(id.toString());

            plataformaRepository.findById(parsedId).orElseThrow(() -> new PlataformaNotFound("La plataforma ingresada no existe."));

            plataformaRepository.deleteById(parsedId);

            return new LinkedHashMap<>() {{
                put("message", "La plataforma ha sido eliminada.");
            }};
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Los parametros ingresados no tienen un formato valido.");
        }
    }
}

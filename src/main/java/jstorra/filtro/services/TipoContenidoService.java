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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TipoContenidoService {
    @Autowired
    TipoContenidoRepository tipoContenidoRepository;

    @Autowired
    PlataformaRepository plataformaRepository;

    public List<Map<Object, Object>> mostrarTiposContenido() {
        try {
            List<Map<Object, Object>> tipoContenidoResponse = new ArrayList<>();
            List<TipoContenido> tipoContenidos = tipoContenidoRepository.findAll();
            tipoContenidos.stream().forEach(tco -> {
                Map<Object, Object> tipo = new LinkedHashMap<>();
                tipo.put("id", tco.getId());
                tipo.put("nombre", tco.getNombre());
                tipo.put("plataformas", tco.getPlataformas());
                tipoContenidoResponse.add(tipo);
            });
            return tipoContenidoResponse;
        } catch (Exception e) {
        }
        return null;
    }

    public Map<Object, Object> guardarTipoContenido(TipoContenido tipoContenido) {
        try {
            tipoContenidoRepository.save(tipoContenido);
            return new LinkedHashMap<>() {
                {
                    put("message", "El tipo de contenido ha sido registrado.");
                }
            };
        } catch (Exception e) {
            throw new TipoContenidoDuplicateException("El tipo de contenido ingresado ya existe.");
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
            plataforma.getTipocontenidos().add(tipoContenido);

            return new LinkedHashMap<>() {
                {
                    put("message", "Se ha agregado la plataforma al tipo de contenido.");
                }
            };
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Los parametros ingresados no tienen un formato valido.");
        }
    }
}

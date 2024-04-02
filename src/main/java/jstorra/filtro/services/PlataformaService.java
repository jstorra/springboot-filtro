package jstorra.filtro.services;

import jakarta.transaction.Transactional;
import jstorra.filtro.exceptions.InvalidFormatException;
import jstorra.filtro.exceptions.PlataformaDuplicateException;
import jstorra.filtro.exceptions.TipoContenidoNotFound;
import jstorra.filtro.models.Plataforma;
import jstorra.filtro.models.TipoContenido;
import jstorra.filtro.repositories.PlataformaRepository;
import jstorra.filtro.repositories.TipoContenidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class PlataformaService {
    @Autowired
    PlataformaRepository plataformaRepository;

    @Autowired
    TipoContenidoRepository tipoContenidoRepository;

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

    @Transactional
    public Map<Object, Object>  agregarTipoContenido(Map<Object, Object> valores) {
        try {
            int tipoId = Integer.parseInt(valores.get("tipo_id").toString());
            int plataformaId = Integer.parseInt(valores.get("plataforma_id").toString());

            TipoContenido tipoContenido = tipoContenidoRepository.findById(tipoId)
                    .orElseThrow(() -> new TipoContenidoNotFound("El tipo de contenido ingresado no existe."));

            Plataforma plataforma = plataformaRepository.findById(plataformaId)
                    .orElseThrow(() -> new TipoContenidoNotFound("La plataforma ingresada no existe."));

            tipoContenido.getPlataformas().add(plataforma);
            plataforma.getTipocontenidos().add(tipoContenido);

            return new LinkedHashMap<>() {{
                put("message", "Se ha agregado el tipo de contenido a la plataforma.");
            }};
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Los parametros ingresados no tienen un formato valido.");
        }
    }
}

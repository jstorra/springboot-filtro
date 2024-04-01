package jstorra.filtro.services;

import jstorra.filtro.exceptions.TipoContenidoDuplicateException;
import jstorra.filtro.models.TipoContenido;
import jstorra.filtro.repositories.TipoContenidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class TipoContenidoService {
    @Autowired
    TipoContenidoRepository tipoContenidoRepository;

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
}

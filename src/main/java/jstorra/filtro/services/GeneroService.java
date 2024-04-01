package jstorra.filtro.services;

import jstorra.filtro.exceptions.GeneroDuplicateException;
import jstorra.filtro.models.Genero;
import jstorra.filtro.repositories.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class GeneroService {
    @Autowired
    GeneroRepository generoRepository;

    public Map<Object, Object> guardarGenero(Genero genero) {
        try {
            generoRepository.save(genero);
            return new LinkedHashMap<>() {{
                put("message", "El genero ha sido registrado.");
            }};
        } catch (Exception e) {
            throw new GeneroDuplicateException("El genero ingresado ya existe.");
        }
    }
}

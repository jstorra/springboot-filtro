package jstorra.filtro.services;

import jstorra.filtro.exceptions.ContenidoNotFound;
import jstorra.filtro.exceptions.GeneroDuplicateException;
import jstorra.filtro.exceptions.GeneroNotFound;
import jstorra.filtro.exceptions.InvalidFormatException;
import jstorra.filtro.models.Contenido;
import jstorra.filtro.models.Genero;
import jstorra.filtro.repositories.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeneroService {
    @Autowired
    GeneroRepository generoRepository;

    public List<Genero> obtenerGeneros() {
        return generoRepository.findAll();
    }

    public Genero obtenerGeneroPorId(@PathVariable Object id) {
        try {
            int parsedId = Integer.parseInt(id.toString());

            Genero genero = generoRepository.findById(parsedId).orElseThrow(() -> new GeneroNotFound("El genero ingresado no existe."));

            return genero;
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Los parametros ingresados no tienen un formato valido.");
        }
    }

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

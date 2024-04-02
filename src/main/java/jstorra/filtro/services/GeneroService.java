package jstorra.filtro.services;

import jstorra.filtro.exceptions.GeneroDuplicateException;
import jstorra.filtro.exceptions.GeneroNotFound;
import jstorra.filtro.exceptions.InvalidFormatException;
import jstorra.filtro.models.Genero;
import jstorra.filtro.repositories.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Genero obtenerGeneroPorId(Object id) {
        try {
            int parsedId = Integer.parseInt(id.toString());

            return generoRepository.findById(parsedId).orElseThrow(() -> new GeneroNotFound("El genero ingresado no existe."));
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

    public Map<Object, Object> editarGenero(Object id, Genero generoToUpdate) {
        try {
            int parsedId = Integer.parseInt(id.toString());

            generoRepository.findById(parsedId).orElseThrow(() -> new GeneroNotFound("El genero ingresado no existe."));

            generoToUpdate.setId(parsedId);
            guardarGenero(generoToUpdate);

            return new LinkedHashMap<>() {{
                put("message", "El genero ha sido actualizado.");
            }};
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Los parametros ingresados no tienen un formato valido.");
        }
    }

    public Map<Object, Object> eliminarGenero(Object id) {
        try {
            int parsedId = Integer.parseInt(id.toString());

            generoRepository.findById(parsedId).orElseThrow(() -> new GeneroNotFound("El genero ingresado no existe."));

            generoRepository.deleteById(parsedId);

            return new LinkedHashMap<>() {{
                put("message", "El genero ha sido eliminado.");
            }};
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Los parametros ingresados no tienen un formato valido.");
        }
    }
}

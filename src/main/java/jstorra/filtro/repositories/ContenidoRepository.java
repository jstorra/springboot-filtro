package jstorra.filtro.repositories;

import jstorra.filtro.models.Contenido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContenidoRepository extends JpaRepository<Contenido, Integer> {
}

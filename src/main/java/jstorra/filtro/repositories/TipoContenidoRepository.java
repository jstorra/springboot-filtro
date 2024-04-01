package jstorra.filtro.repositories;

import jstorra.filtro.models.TipoContenido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoContenidoRepository extends JpaRepository<TipoContenido, Integer> {
}

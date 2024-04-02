package jstorra.filtro.repositories;

import jstorra.filtro.models.TipoContenido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoContenidoRepository extends JpaRepository<TipoContenido, Integer> {
    @Query("SELECT p.nombre FROM TipoContenido t " +
            "JOIN t.plataformas p " +
            "WHERE t.id = ?1")
    List<String> plataformasApropiadas(int id);
}

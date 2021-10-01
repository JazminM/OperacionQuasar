package cl.mercadolibre.operacionfuegodequasar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.mercadolibre.operacionfuegodequasar.entity.SateliteParametrosEntity;

@Repository
public interface SateliteParametrosRepository extends JpaRepository<SateliteParametrosEntity, Long>{
	public SateliteParametrosEntity findByNombreSatelite(String satellite_name);
}

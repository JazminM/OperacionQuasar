package cl.mercadolibre.operacionfuegodequasar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.mercadolibre.operacionfuegodequasar.entity.SatelitesEntity;

@Repository
public interface SatelitesRepository extends JpaRepository<SatelitesEntity, Long>{
	public SatelitesEntity findByNombreSatelite(String satelliteName);
}

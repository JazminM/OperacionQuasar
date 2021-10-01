package cl.mercadolibre.operacionfuegodequasar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.mercadolibre.operacionfuegodequasar.entity.MensajeEntity;

@Repository
public interface MensajeRepository extends JpaRepository<MensajeEntity, Long>{
	public List<MensajeEntity> findByIdSatelite(Long idSatelite);
	
	public List<MensajeEntity> findAllByOrderByPosicionAsc();
	
	@Query(value="select * from quasardb.mensaje where id_satelite = :idSatelite order by posicion asc", nativeQuery=true)
	public List<MensajeEntity> findMensajeByIdSateliteOrderByPosicionAsc(@Param("idSatelite") Long idSatelite);
}

package br.com.stuxnet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.stuxnet.model.ComponenteHardware;
import br.com.stuxnet.model.enums.CategoriaHardware;

@Repository
public interface ComponenteHardwareRepository extends JpaRepository<ComponenteHardware, Long> {
    boolean existsBySku(String sku);
    Optional<ComponenteHardware> findBySku(String sku);
    List<ComponenteHardware> findByAtivoTrue();
    List<ComponenteHardware> findByCategoriaAndAtivoTrue(CategoriaHardware categoria);
}
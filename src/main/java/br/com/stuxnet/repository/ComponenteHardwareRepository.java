package br.com.stuxnet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.stuxnet.model.ComponenteHardware;

@Repository
public interface ComponenteHardwareRepository extends JpaRepository<ComponenteHardware, Long> {
    boolean existsBySku(String sku);
    Optional<ComponenteHardware> findBySku(String sku);
}
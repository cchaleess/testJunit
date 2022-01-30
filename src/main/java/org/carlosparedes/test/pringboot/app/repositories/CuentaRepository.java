package org.carlosparedes.test.pringboot.app.repositories;

import org.carlosparedes.test.pringboot.app.models.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta,Long> {

    //YA ESTA TODO IMPLEMENTADO AL HEREDAR DE JPAREPOSITORY
    //List<Cuenta> findAll();
    //Cuenta findById(Long id);
    //void update(Cuenta cuenta);
    @Query("select c from Cuenta c where c.persona=?1")
    Optional<Cuenta> findByPersona(String persona);

}

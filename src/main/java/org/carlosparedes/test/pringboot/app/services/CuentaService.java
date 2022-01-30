package org.carlosparedes.test.pringboot.app.services;

import org.carlosparedes.test.pringboot.app.models.Cuenta;

import java.math.BigDecimal;
import java.util.List;

public interface CuentaService {

    List<Cuenta> findAll();
    Cuenta findById(Long id);
    Cuenta save(Cuenta cuenta);
    void deleteById(Long id);
    int revisarTotalTransferencias(Long bancoId);
    BigDecimal revisarSaldo(Long cuentaId);
    void transferir(Long numCuentaOrigen, Long numCuentaDestinio, BigDecimal monto, Long bancoId);
}

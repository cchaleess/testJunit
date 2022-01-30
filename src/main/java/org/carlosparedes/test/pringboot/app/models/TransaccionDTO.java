package org.carlosparedes.test.pringboot.app.models;

import java.math.BigDecimal;

public class TransaccionDTO {

    private Long cuentaOrigenId;
    private Long cuantaDestinoId;
    private BigDecimal monto;
    private Long bancoId;

    public Long getBancoId() {
        return bancoId;
    }

    public void setBancoId(Long bancoId) {
        this.bancoId = bancoId;
    }

    public Long getCuentaOrigenId() {
        return cuentaOrigenId;
    }

    public void setCuentaOrigenId(Long cuentaOrigenId) {
        this.cuentaOrigenId = cuentaOrigenId;
    }

    public Long getCuantaDestinoId() {
        return cuantaDestinoId;
    }

    public void setCuantaDestinoId(Long cuantaDestinoId) {
        this.cuantaDestinoId = cuantaDestinoId;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
}

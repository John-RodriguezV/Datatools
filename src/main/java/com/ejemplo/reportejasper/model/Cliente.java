package com.ejemplo.reportejasper.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "usuario_dominio")
public class Cliente {

    @Id
    private Long id;

    @Column(name = "tipo_documento")
    private String tipoDocumento;

    @Column(name = "numero_documento")
    private String numeroDocumento;

    @Column(name = "usuario_dominio")
    private String usuarioDominio;

    public Cliente() {
    }

    public Cliente(Long id, String tipoDocumento, String numeroDocumento, String usuarioDominio) {
        this.id = id;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.usuarioDominio = usuarioDominio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getUsuarioDominio() {
        return usuarioDominio;
    }

    public void setUsuarioDominio(String usuarioDominio) {
        this.usuarioDominio = usuarioDominio;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", tipoDocumento='" + tipoDocumento + '\'' +
                ", numeroDocumento='" + numeroDocumento + '\'' +
                ", usuarioDominio='" + usuarioDominio + '\'' +
                '}';
    }
}

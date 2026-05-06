package com.telecom.telecom_app.model;

public enum TipoServicio {
    INTERNET("Internet"),
    TELEVISION("Television"),
    INTERNET_TELEVISION("Internet + Television");

    private final String etiqueta;

    TipoServicio(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}

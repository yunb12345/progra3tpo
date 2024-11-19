//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

public class CultivoSeleccionado {
    private String nombreCultivo;
    private org.example.Coordenada esquinaSuperiorIzquierda;
    private org.example.Coordenada esquinaInferiorDerecha;
    private double montoInvertido;
    private double riesgoAsociado;
    private double gananciaObtenida;

    public CultivoSeleccionado() {
    }

    public CultivoSeleccionado(String nombreCultivo, org.example.Coordenada esquinaSuperiorIzquierda, org.example.Coordenada esquinaInferiorDerecha, double montoInvertido, double riesgoAsociado, double gananciaObtenida) {
        this.nombreCultivo = nombreCultivo;
        this.esquinaSuperiorIzquierda = esquinaSuperiorIzquierda;
        this.esquinaInferiorDerecha = esquinaInferiorDerecha;
        this.montoInvertido = montoInvertido;
        this.riesgoAsociado = riesgoAsociado;
        this.gananciaObtenida = gananciaObtenida;
    }

    public String getNombreCultivo() {
        return this.nombreCultivo;
    }

    public void setNombreCultivo(String nombreCultivo) {
        this.nombreCultivo = nombreCultivo;
    }

    public org.example.Coordenada getEsquinaSuperiorIzquierda() {
        return this.esquinaSuperiorIzquierda;
    }

    public void setEsquinaSuperiorIzquierda(org.example.Coordenada esquinaSuperiorIzquierda) {
        this.esquinaSuperiorIzquierda = esquinaSuperiorIzquierda;
    }

    public org.example.Coordenada getEsquinaInferiorDerecha() {
        return this.esquinaInferiorDerecha;
    }

    public void setEsquinaInferiorDerecha(org.example.Coordenada esquinaInferiorDerecha) {
        this.esquinaInferiorDerecha = esquinaInferiorDerecha;
    }

    public double getMontoInvertido() {
        return this.montoInvertido;
    }

    public void setMontoInvertido(double montoInvertido) {
        this.montoInvertido = montoInvertido;
    }

    public double getRiesgoAsociado() {
        return this.riesgoAsociado;
    }

    public void setRiesgoAsociado(double riesgoAsociado) {
        this.riesgoAsociado = riesgoAsociado;
    }

    public double getGananciaObtenida() {
        return this.gananciaObtenida;
    }

    public void setGananciaObtenida(double gananciaObtenida) {
        this.gananciaObtenida = gananciaObtenida;
    }

    public String toString() {
        return "CultivoSeleccionado{nombreCultivo='" + this.nombreCultivo + '\'' + ", esquinaSuperiorIzquierda=" + this.esquinaSuperiorIzquierda + ", esquinaInferiorDerecha=" + this.esquinaInferiorDerecha + ", montoInvertido=" + this.montoInvertido + ", riesgoAsociado=" + this.riesgoAsociado + ", gananciaObtenida=" + this.gananciaObtenida + '}';
    }
}

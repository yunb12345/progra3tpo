package org.example;

import java.util.ArrayList;
import java.util.List;

public class PlanificarCultivosImplementacion implements PlanificarCultivos {

    @Override
    public List<CultivoSeleccionado> obtenerPlanificacion(List<Cultivo> var1, double[][] var2, String var3) {
        double[][] campo = new double[100][100];
        List<CultivoSeleccionado> distribucionActual = new ArrayList<>();
        List<CultivoSeleccionado> mejorDistribucion = new ArrayList<>();
        return BackTrack(0, var1, campo, 0,
                distribucionActual,
                0, mejorDistribucion, var3, var2);
    }

    public double calcularPotencial(int filaInicio, int columnaInicio, int filaFin, int columnaFin, Cultivo cultivo, double[][] matrizRiesgo) {
        double suma = 0;
        for (int i = filaInicio; i < filaFin; i++) {
            for (int j = columnaInicio; j < columnaFin; j++) {
                suma += ((1 - matrizRiesgo[i][j])) * ((cultivo.getPrecioDeVentaPorParcela() - cultivo.getCostoPorParcela()));
            }
        }
        return suma;
    }

    public double calcularRiesgoPromedio(double[][] matrizRiesgo, int filaInicio, int columnaInicio, int filaFin, int columnaFin) {
        double suma = 0;
        int conteo = 0;
        for (int i = filaInicio; i < filaFin; i++) {
            for (int j = columnaInicio; j < columnaFin; j++) {
                suma += matrizRiesgo[i][j];
                conteo++;
            }
        }
        return suma / conteo;
    }

    public boolean AreaValida(Coordenada esquinaIzq1, Coordenada esquinaDer1, List<CultivoSeleccionado> distribucionActual) {
        for (CultivoSeleccionado cultivo : distribucionActual) {
            if (colisionan(esquinaIzq1, esquinaDer1, cultivo.getEsquinaSuperiorIzquierda(), cultivo.getEsquinaInferiorDerecha())) {
                return false;
            }
        }
        return true;
    }

    /*public boolean colisionan(Coordenada esquinaIzq1, Coordenada esquinaDer1, Coordenada esquinaIzq2, Coordenada esquinaDer2) {
        if (esquinaDer1.getX() < esquinaIzq2.getX() || esquinaDer2.getX() < esquinaIzq1.getX()) {
            return false;
        } else if (esquinaDer1.getY() < esquinaIzq2.getY() || esquinaDer2.getY() < esquinaIzq1.getY()) {
            return false;
        }
        return true;
    }*/
    public boolean colisionan(Coordenada esquinaIzq1, Coordenada esquinaDer1, Coordenada esquinaIzq2, Coordenada esquinaDer2) {
        if (esquinaIzq1.getX() > esquinaDer2.getX() || esquinaIzq2.getX() > esquinaDer1.getX()) {
            return false;
        } else if (esquinaIzq1.getY() > esquinaDer2.getY() || esquinaIzq2.getY() > esquinaDer1.getY()) {
            return false;
        }
        return true;
    }

    /*public List<CultivoSeleccionado> BackTrack(int nivel, List<Cultivo> cultivos, double[][] matrizCampo, double gananciaActual,
                                               List<CultivoSeleccionado> distribucionActual,
                                               double mejorGanancia, List<CultivoSeleccionado> mejorDistribucion, String temporadaActual, double[][] matrizRiesgo) {
        if (gananciaActual >= mejorGanancia) {
            mejorGanancia = gananciaActual;
            // Actualizamos mejorDistribucion con una copia de distribucionActual
            mejorDistribucion.clear();
            mejorDistribucion.addAll(new ArrayList<>(distribucionActual));
        } else {
            mejorDistribucion = BackTrack(nivel - 1, cultivos, matrizCampo, gananciaActual,
                    distribucionActual, mejorGanancia, mejorDistribucion, temporadaActual, matrizRiesgo);

        }
        if (nivel >= cultivos.size()) {
            // Si hemos alcanzado el último nivel, comparamos la ganancia actual con la mejor ganancia
            return mejorDistribucion;
        }

        Cultivo cultivo = cultivos.get(nivel);

        // Usamos equals para comparar cadenas en lugar de !=
        if (!cultivo.getTemporadaOptima().equals(temporadaActual)) {
            return BackTrack(nivel + 1, cultivos, matrizCampo, gananciaActual, distribucionActual,
                    mejorGanancia, mejorDistribucion, temporadaActual, matrizRiesgo);
        }

        for (int n = 1; n <= 10; n++) {
            for (int m = 1; m <= 10; m++) {
                for (int x = 0; x <= 100 - n; x++) {
                    for (int y = 0; y <= 100 - m; y++) {
                        Coordenada esquinaSuperiorIzquierda = new Coordenada(x, y);
                        Coordenada esquinaInferiorDerecha = new Coordenada(x + n, y + m);
                        if (AreaValida(esquinaSuperiorIzquierda, esquinaInferiorDerecha, distribucionActual)) {
                            double riesgoPromedio = calcularRiesgoPromedio(matrizRiesgo, x, y, x + n, y + m);
                            double potencialTotal = calcularPotencial(x, y, x + n, y + m, cultivo, matrizRiesgo);
                            double ganancia = potencialTotal - cultivo.getInversionRequerida();

                            CultivoSeleccionado cultivoSeleccionado = new CultivoSeleccionado(cultivo.getNombre(),
                                    esquinaSuperiorIzquierda,
                                    esquinaInferiorDerecha,
                                    cultivo.getInversionRequerida(), (int) riesgoPromedio, ganancia);

                            distribucionActual.add(cultivoSeleccionado);
                            mejorDistribucion = BackTrack(nivel + 1, cultivos, matrizCampo, gananciaActual + ganancia,
                                    distribucionActual, mejorGanancia, mejorDistribucion, temporadaActual, matrizRiesgo);
                            distribucionActual.remove(distribucionActual.size() - 1);
                        }
                    }
                }
            }
        }
        return mejorDistribucion;
    }*/
    public List<CultivoSeleccionado> BackTrack(int nivel, List<Cultivo> cultivos, double[][] matrizCampo, double gananciaActual,
                                               List<CultivoSeleccionado> distribucionActual,
                                               double mejorGanancia, List<CultivoSeleccionado> mejorDistribucion, String temporadaActual, double[][] matrizRiesgo) {
        if (nivel >= cultivos.size()) {
            // Si hemos alcanzado el último nivel, comparamos la ganancia actual con la mejor ganancia
            if (gananciaActual > mejorGanancia) {
                mejorGanancia = gananciaActual;
                // Actualizamos mejorDistribucion con una copia de distribucionActual
                mejorDistribucion.clear();
                mejorDistribucion.addAll(new ArrayList<>(distribucionActual));
            }
            return mejorDistribucion;
        }

        Cultivo cultivo = cultivos.get(nivel);

        // Usamos equals para comparar cadenas en lugar de !=
        if (!cultivo.getTemporadaOptima().equals(temporadaActual)) {
            return BackTrack(nivel + 1, cultivos, matrizCampo, gananciaActual, distribucionActual,
                    mejorGanancia, mejorDistribucion, temporadaActual, matrizRiesgo);
        }

        Coordenada esquinaSuperiorIzquierda = new Coordenada();
        Coordenada esquinaInferiorDerecha = new Coordenada();
        double riesgoPromedio = 0;
        double ganancia = 0;
        for (int n = 1; n <= 10; n++) {
            for (int m = 1; m <= 10; m++) {
                for (int x = 0; x <= 100 - n; x++) {
                    for (int y = 0; y <= 100 - m; y++) {
                        esquinaSuperiorIzquierda.setX(x);
                        esquinaSuperiorIzquierda.setY(y);
                        esquinaInferiorDerecha.setX(x + n);
                        esquinaInferiorDerecha.setY(y + m);
                        if (AreaValida(esquinaSuperiorIzquierda, esquinaInferiorDerecha, distribucionActual)) {
                            riesgoPromedio = calcularRiesgoPromedio(matrizRiesgo, x, y, x + n, y + m);
                            double potencialTotal = calcularPotencial(x, y, x + n, y + m, cultivo, matrizRiesgo);
                            ganancia = potencialTotal - cultivo.getInversionRequerida();
                            if (ganancia <= gananciaActual){
                                ganancia = gananciaActual;
                            }
                        }
                    }
                }
            }
        }
        CultivoSeleccionado cultivoSeleccionado = new CultivoSeleccionado(cultivo.getNombre(),
                esquinaSuperiorIzquierda,
                esquinaInferiorDerecha,
                cultivo.getInversionRequerida(), (int) riesgoPromedio, ganancia);

        distribucionActual.add(cultivoSeleccionado);
        BackTrack(nivel + 1, cultivos, matrizCampo, gananciaActual + ganancia,
                distribucionActual, mejorGanancia, mejorDistribucion, temporadaActual, matrizRiesgo);
        distribucionActual.remove(distribucionActual.size() - 1);
        return mejorDistribucion;
    }
}
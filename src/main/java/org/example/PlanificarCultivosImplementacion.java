package org.example;
import java.util.List;

public class PlanificarCultivosImplementacion implements PlanificarCultivos {

    @Override
    public List<org.example.CultivoSeleccionado> obtenerPlanificacion(List<org.example.Cultivo> var1, double[][] var2, String var3) {
        return List.of();

    }

    public double calcularPotencial(int filaInicio, int columnaInicio, int filaFin, int columnaFin, Cultivo cultivo, double[][] matrizRiesgo) {
        double suma = 0;
        for (int i = filaInicio; i < filaFin; i++) {

            for (int j = columnaInicio; j < columnaFin; j++) {
                suma += (1 - matrizRiesgo[i][j] * (cultivo.getPrecioDeVentaPorParcela() - cultivo.getCostoPorParcela()));

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
                conteo += 1;
            }
        }
        return suma / conteo;
    }

    public boolean AreaValida(Coordenada esquinaIzq1, Coordenada esquinaDer1, List<CultivoSeleccionado> distribucionActual) {

        for (int i = 0; i < distribucionActual.size(); i++) {
            if (colisionan(esquinaDer1, esquinaIzq1, distribucionActual.get(i).getEsquinaInferiorDerecha(), distribucionActual.get(i).getEsquinaSuperiorIzquierda())) {
                return false;
            }
        }
        return true;
    }

    public boolean colisionan(Coordenada esquinaIzq1, Coordenada esquinaDer1, Coordenada esquinaIzq2, Coordenada esquinaDer2) {
        if (esquinaDer1.getX() < esquinaIzq2.getX() || esquinaDer2.getX() < esquinaIzq1.getX()) {
            return false;
        } else if (esquinaDer1.getY() < esquinaIzq2.getY() || esquinaDer2.getY() < esquinaIzq1.getY()) {
            return false;

        }
        return true;
    }


    public List<CultivoSeleccionado> BackTrack(int nivel, List<Cultivo> cultivos, double[][] matrizCampo, double gananciaActual,
                                               List<CultivoSeleccionado> distribucionActual,
                                               double mejorGanacia, List<CultivoSeleccionado> mejorDistribucion, String temporadaActual, double[][] matrizRiesgo) {
        if (nivel >= cultivos.size()) {
            if (gananciaActual > mejorGanacia) {
                mejorGanacia = gananciaActual;
                mejorDistribucion = distribucionActual;
            }
            return distribucionActual;
        }
        Cultivo cultivo = cultivos.get(nivel);

        if (cultivo.getTemporadaOptima() != temporadaActual) {
            BackTrack(nivel + 1, cultivos, matrizCampo, gananciaActual, distribucionActual, mejorGanacia, mejorDistribucion, temporadaActual, matrizRiesgo);
        }
        for (int n = 0; n < 11; n++) {
            for (int m = 0; m < 11; m++) {
                if (n + m <= 11) {
                    for (int x = 0; x < 100 - (n + 1); x++) {
                        for (int y = 0; y < 100 - (m + 1); y++) {
                            Coordenada coordenada = new Coordenada(x, y);
                            Coordenada coordenada2 = new Coordenada(n, m);
                            if (AreaValida(coordenada, coordenada2, distribucionActual)) {
                                double riesgoPromedio = calcularRiesgoPromedio(matrizRiesgo, x, y, n, m);
                                double potencialTotal = calcularPotencial(x, y, n, m, cultivo, matrizRiesgo);
                                double ganancia = potencialTotal - cultivo.getInversionRequerida();

                                CultivoSeleccionado cultivoSeleccionado = new CultivoSeleccionado(cultivo.getNombre(),
                                        coordenada,
                                        coordenada2,
                                        cultivo.getInversionRequerida(), (int) riesgoPromedio, ganancia);

                                distribucionActual.add(cultivoSeleccionado);
                                BackTrack(nivel + 1, cultivos, matrizCampo, gananciaActual, distribucionActual, mejorGanacia, mejorDistribucion, temporadaActual, matrizRiesgo);
                                distribucionActual.removeLast();
                            }

                        }
                    }
                }
            }
        }
        return mejorDistribucion;

    }
}

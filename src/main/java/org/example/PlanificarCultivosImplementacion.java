package org.example;

import java.util.ArrayList;
import java.util.List;

public class PlanificarCultivosImplementacion implements PlanificarCultivos {

    @Override
    public List<CultivoSeleccionado> obtenerPlanificacion(List<Cultivo> var1, double[][] var2, String var3) {
        double[][] campo = new double[20][20];
        MejorGanancia ganancia = new MejorGanancia();
        List<CultivoSeleccionado> distribucionActual = new ArrayList<>();
        List<CultivoSeleccionado> mejorDistribucion = new ArrayList<>();
        return BackTrack(0, var1, campo, 0,
                distribucionActual,
                ganancia, mejorDistribucion, var3, var2);
    }

    public double calcularPotencial(int x, int y, int xf, int yf, Cultivo cultivo, double[][] matrizRiesgo) {
        double suma = 0;
        for (int i = x; i < xf; i++) {
            for (int j = y; j < yf; j++) {
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
                System.out.println("Área inválida por colisión: (" + esquinaIzq1.getX() + "," + esquinaIzq1.getY() + ") con cultivo " + cultivo.getNombreCultivo());
                return false;
            }
        }
        System.out.println("Área valida (" + esquinaIzq1.getX() + "," + esquinaIzq1.getY() + ")");
        return true;
    }

    public boolean colisionan(Coordenada esquinaIzq1, Coordenada esquinaDer1, Coordenada esquinaIzq2, Coordenada esquinaDer2) {
        if (esquinaIzq1.getX() > esquinaDer2.getX() || esquinaIzq2.getX() > esquinaDer1.getX()) {
            return false;
        }
        if (esquinaIzq1.getY() > esquinaDer2.getY() || esquinaIzq2.getY() > esquinaDer1.getY()) {
            return false;
        }
        return true;
    }
    class MejorGanancia{
        double ganancia = 0;
    }

    public List<CultivoSeleccionado> BackTrack(int nivel, List<Cultivo> cultivos, double[][] matrizCampo, double gananciaActual,
                                               List<CultivoSeleccionado> distribucionActual,
                                               MejorGanancia mejorGanancia, List<CultivoSeleccionado> mejorDistribucion, String temporadaActual, double[][] matrizRiesgo) {

        System.out.println("Entrando al Nivel: " + nivel + ", Ganancia Actual: " + gananciaActual + "mejorGanancia:" + mejorGanancia);
        if (nivel >= cultivos.size()) {
            System.out.println("Nivel máximo alcanzado, evaluando ganancia...");
            if (gananciaActual > mejorGanancia.ganancia) {
                mejorGanancia.ganancia = gananciaActual;
                mejorDistribucion.clear();
                mejorDistribucion.addAll(new ArrayList<>(distribucionActual));
                System.out.println("Nueva mejor ganancia encontrada: " + mejorGanancia);
                //distribucionActual.removeLast();
                //System.out.println("Removiendo cultivo: " + " en nivel: " + nivel);
            }
            return mejorDistribucion;
        }

        Cultivo cultivo = cultivos.get(nivel);

        // Usamos equals para comparar cadenas en lugar de !=
        if (!cultivo.getTemporadaOptima().equals(temporadaActual)) {
            System.out.println("Saltando cultivo " + cultivo.getNombre() + " (temporada incorrecta)");
            return BackTrack(nivel + 1, cultivos, matrizCampo, gananciaActual, distribucionActual,
                    mejorGanancia, mejorDistribucion, temporadaActual, matrizRiesgo);
        }

        //Coordenada esquinaSuperiorIzquierda = new Coordenada();
        //Coordenada esquinaInferiorDerecha = new Coordenada();
        double riesgoPromedio = 0;
        double ganancia = 0;
        CultivoSeleccionado cultivoSeleccionado = new CultivoSeleccionado();
        for (int n = 1; n <= 10; n++) {
            for (int m = 1; m <= 10; m++) {
                if(n+m<11){
                    for (int x = 0; x <= 20 - n; x++) {
                        for (int y = 0; y <= 20 - m; y++) {
                            /*
                            esquinaSuperiorIzquierda.setX(x);
                            esquinaSuperiorIzquierda.setY(y);
                            esquinaInferiorDerecha.setX(x + n);
                            esquinaInferiorDerecha.setY(y + m);
                             */
                            Coordenada esquinaSuperiorIzquierda = new Coordenada(x,y);
                            Coordenada esquinaInferiorDerecha = new Coordenada(x+n,y+n);

                            if (AreaValida(esquinaSuperiorIzquierda, esquinaInferiorDerecha, distribucionActual)) {
                                riesgoPromedio = calcularRiesgoPromedio(matrizRiesgo, x, y, x + n, y + m);
                                double potencialTotal = calcularPotencial(x, y, x + n, y + m, cultivo, matrizRiesgo);
                                ganancia = potencialTotal - cultivo.getInversionRequerida();

                                CultivoSeleccionado cultivoSeleccionado1 = new CultivoSeleccionado(cultivo.getNombre(),esquinaSuperiorIzquierda,esquinaInferiorDerecha,
                                        cultivo.getInversionRequerida(),riesgoPromedio,ganancia);

                                if(ganancia>mejorGanancia.ganancia){
                                    System.out.println("Añadiendo cultivo: " + cultivo.getNombre() + " en nivel: " + nivel);
                                    distribucionActual.add(cultivoSeleccionado1); //agrego a la distribucion actual
                                    mejorDistribucion = BackTrack(nivel+1,cultivos,matrizCampo,gananciaActual + ganancia,
                                            distribucionActual,mejorGanancia,mejorDistribucion,temporadaActual,matrizRiesgo);
                                    System.out.println("Bajando al nivel:" + nivel + " cultivo:" + cultivos.get(nivel).getNombre());
                                    distribucionActual.removeLast();
                                    System.out.println("Removiendo cultivo: " + cultivo.getNombre() + " en nivel: " + nivel);
                                }

                                //System.out.println("Removiendo cultivo: " + cultivo.getNombre() + " en nivel: " + nivel);
                                //distribucionActual.remove(distribucionActual.size() - 1);




                                //probamos con todas las posibles pos y tamaños


                            }
                        }
                    }
                }

            }
        }
        return mejorDistribucion;
    }
}
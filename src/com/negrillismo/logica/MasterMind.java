package com.negrillismo.logica;

import java.util.Scanner;
import java.util.ArrayList;

public class MasterMind {

    private static final Scanner LEER_CONSOLA = new Scanner(System.in);
    private static final int[] OPCIONES = {1,2,3,4,5,9,0};
    private int opcion = 0;
    private Jugada secreta = new Jugada();
    private ArrayList<int[]> intentosJugador = new ArrayList<>();
    private ArrayList<char[]> resultadosIntentos = new ArrayList<>();

    public void iniciar() {
        abrirMenu();
        opcion = leerEntero();
        while (!opcionValida(opcion)) {
            abrirMenu();
            opcion = leerEntero();
        }
        ejecutarMenu(opcion);
    }

    private void ejecutarMenu(int opcion) {
        switch (opcion) {
            case 1:
                desarrollo(4,4,false,10,opcion);
                break;
            case 2:
                desarrollo(5,7,false,12,opcion);
                break;
            case 3:
                desarrollo(5,7,true,12,opcion);
                break;
            case 4:
                desarrollo(6,10,true,12,opcion);
                break;
            case 5:
                desarrollo(6,10,true,10,opcion);
                break;
            case 9:
                abrirAyuda();
                break;
            case 0:
                break;
            default:
                iniciar();
                break;
        }
    }

    private void abrirAyuda() {
        System.out.println("\n\nEsta es la información de los diferentes niveles:\n"+
                "1) MUY FÁCIL\n" +
                "\t- 4 incógnitas de 4 posibles\n" +
                "\t- 10 turnos\n" +
                "\t- Sin repeticiones\n" +
                "2) FÁCIL\n" +
                "\t- 5 incógnitas de 7 posibles\n" +
                "\t- 12 turnos\n" +
                "\t- Sin repeticiones\n" +
                "3) NORMAL\n" +
                "\t- 5 incógnitas de 7 posibles\n" +
                "\t- 12 turnos\n" +
                "\t- Con repeticiones\n" +
                "4) DIFÍCIL\n" +
                "\t- 6 incógnitas de 10 posibles\n" +
                "\t- 12 turnos\n" +
                "\t- Con repeticiones\n" +
                "5) MUY DIFÍCIL\n" +
                "\t- 6 incógnitas de 10 posibles\n" +
                "\t- 10 turnos\n" +
                "\t- Con repeticiones");
        iniciar();
    }

    /**
     * Imprime por pantalla el menú del juego
     */
    private void abrirMenu() {
        System.out.println("\n\nBienvenido a MasterMind. Por favor, elige el nivel en el que quieres jugar: \n"+
                "\t1. MUY FÁCIL\n"+
                "\t2. FÁCIL\n"+
                "\t3. NORMAL\n"+
                "\t4. DIFÍCIL\n"+
                "\t5. MUY DIFÍCIL\n"+
                "\n\t9. Ayuda\n"+
                "\t0. Salir\n");
    }

    /**
     * Comprueba que el número que ha introducido el usuario es una de las opciones que muestra el menú.
     * @param n -> Es lo que el usuario ha introducido por teclado
     * @return -> Devuelve TRUE si la opción está contemplada o FALSE si no.
     */
    private boolean opcionValida(int n) {
        boolean salida = false;
        for (int dato : OPCIONES) {
            if (dato == n)
                salida = true;
        }
        return salida;
    }

    private static int leerEntero() {
        return LEER_CONSOLA.nextInt();
    }

    /**
     * Este es el método en el que voy a controlar todo el desarrollo del juego
     * @param incognitas -> Cuántas incógnitas debe adivinar el jugador
     * @param posibilidades -> Entre cuántas posibilidades puede elebir
     * @param repeticiones -> Si hay repeticiones, o no
     */
    private void desarrollo(int incognitas,int posibilidades, boolean repeticiones, int limite, int opcion) {
        Jugada secreta = new Jugada();
        secreta.crearJugadaAleatoria(incognitas,posibilidades,repeticiones,limite); //Creo la jugada que tiene que acertar el jugador
        while(secreta.finDelJuego()) { //De este bucle solo se sale cuando hay una coincidencia plena o se ha llegado al límite de jugadas
            System.out.println("Este es tu intento número "+(1+intentosJugador.size()));//Le digo al jugador por qué jugada va
            System.out.println("Te quedan "+secreta.getLimite()+" intentos más");
            Jugada propia = new Jugada(leerIntentos(incognitas,posibilidades));
            intentosJugador.add(propia.getSecreto());
            secreta.reducirLimite();
            resultadosIntentos.add(secreta.compararJugada(propia.getSecreto()));
            mostrarResultadosParciales(intentosJugador.get(intentosJugador.size()-1),resultadosIntentos.get(resultadosIntentos.size()-1));
        }
        mostrarResultados(opcion,secreta.getFin(),secreta.getSecreto());
    }

    private void mostrarResultadosParciales(int[] numeros, char[] caracteres) {
        System.out.println("\nAsí ha quedado el intento número "+intentosJugador.size());
        imprimirArray(numeros);
        imprimirArray(caracteres);
    }

    private void mostrarResultados(int opcion,boolean finalCorrecto,int[] secreto) {
        if (finalCorrecto) {
            System.out.println("¡¡¡ENHORABUENA!!!\n" +
                    "Has logrado acertar la combinación secreta en tu intento número " + intentosJugador.size() + ".\n" +
                    "Nivel: " + mostrarNivel(opcion) +
                    "\nEste ha sido tu desempeño:\n");
            imprimirResultados();
        }
        else {
            System.out.println("FALLASTE. No has logrado la combinación secreta, que era: \n");
            imprimirArray(secreto);
        }
    }

    private void imprimirResultados() {
        int[] numeros;
        char[] caracteres;
        for (int i = 0; i<intentosJugador.size(); i++) {
            numeros = intentosJugador.get(i);
            caracteres = resultadosIntentos.get(i);
            System.out.println("#"+(i+1)+":");
            imprimirArray(numeros);
            imprimirArray(caracteres);
            System.out.println();
        }
    }

    private void imprimirArray(int[] numeros) {
        for (int dato : numeros)
            System.out.print(dato+"\t");
        System.out.println();
    }

    private void imprimirArray(char[] caracteres) {
        for (char dato : caracteres)
            System.out.print(dato+"\t");
        System.out.println();
    }

    private String mostrarNivel(int opcion) {
        String salida = "";
        switch (opcion) {
            case 1:
                salida += "MUY FÁCIL";
                break;
            case 2:
                salida += "FÁCIL";
                break;
            case 3:
                salida += "NORMAL";
                break;
            case 4:
                salida += "DIFÍCIL";
                break;
            case 5:
                salida += "MUY DIFÍCIL";
                break;
            default:
                break;
        }
        return salida;
    }

    private int[] leerIntentos (int extension,int posibilidades) {
        int[] salida = new int[extension];
        for (int i=0;i<extension;i++) {
            do {
                System.out.println("Estas son las opciones válidas: "+imprimirOpciones(posibilidades));
                System.out.println("Introduce el número para la posición "+ (i+1));
                salida[i] = leerEntero();
            }while(!rangoCorrecto(salida[i],posibilidades));
        }
        return salida;
    }

    private boolean rangoCorrecto(int dato, int posibilidades) {
        return (dato>=0 && dato<posibilidades);
    }

    private String imprimirOpciones(int extension) {
        String salida = "";
        for (int i=0;i<extension;i++)
            salida += i+"\t";
        return salida;
    }

}

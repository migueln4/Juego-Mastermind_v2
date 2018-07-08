package com.negrillismo.logica;

import java.security.SecureRandom;

public class Jugada {
    private int[] secreto;
    private boolean fin = false;
    private int limite;

    public Jugada() {
        super();
    }

    public Jugada(int[] datos) {
        secreto = datos;
    }

    public Jugada(int extension) {
        super();
        secreto = new int[extension];
    }

    public void setSecreto(int[] secreto) {
        this.secreto = secreto;
    }

    public void setFin(boolean fin) {
        this.fin = fin;
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }

    public int[] getSecreto() {
        return secreto;
    }

    public boolean getFin() {
        return fin;
    }

    public int getLimite () {
        return limite;
    }

    public String toString() {
        return "Fin: "+fin+"\nArray: "+arrayAString()+"\nLimite: "+limite;
    }

    private String arrayAString() {
        String salida = "";
        for(int n : secreto)
            salida += n+"\t";
        return salida;
    }

    public void crearJugadaAleatoria(int incognitas,int posibilidades, boolean repeticiones, int limite) {
        this.limite = limite;
        int[] base = crearBase(posibilidades);
        SecureRandom sr = new SecureRandom();
        this.secreto = new int[incognitas];
        int k = posibilidades;
        if (!repeticiones) {
            for (int i = 0; i < this.secreto.length; i++) {
                int n = sr.nextInt(k);
                this.secreto[i] = base[n];
                base[n] = base[k - 1];
                k--;
            }
        } else {
            for (int i = 0; i < this.secreto.length; i++) {
                this.secreto[i] = sr.nextInt(k);
            }
        }
    }

    private int[] crearBase(int extension) {
        int[] salida = new int[extension];
        for (int i = 0; i < extension; i++)
            salida[i] = i;
        return salida;
    }

    public void reducirLimite() {
        --limite;
    }


    public char[] compararJugada(int[] intento) {
        char[] salida = new char[this.secreto.length];
        boolean controlPlus = true;
        for (int i = 0; i<this.secreto.length; i++) {
            if (intento[i] == this.secreto[i])
                salida[i] = '·';
            else {
                controlPlus = false;
                boolean control = false;
                for (int j = 0; j < this.secreto.length && !control; j++) {
                    if (intento[i] == this.secreto[j])
                        control = true;
                }
                salida[i] = control ? 'Q' : 'X';
            }
        }
        if(controlPlus)
            this.fin = true;
        return salida;
    }

    public boolean finDelJuego() {
        return !fin && limite > 0;
    }
}


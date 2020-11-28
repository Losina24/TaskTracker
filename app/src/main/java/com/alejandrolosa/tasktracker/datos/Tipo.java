package com.alejandrolosa.tasktracker.datos;

public enum Tipo {
    TRABAJO ("Trabajo"),
    ESTUDIOS ("Estudios"),
    HOGAR ("Hogar"),
    OCIO ("Ocio"),
    SOCIAL ("Social"),
    FAMILIA ("Familia"),
    OTROS ("Otros");

    private final String tipo;

    Tipo(String tipo){
        this.tipo = tipo;
    }

    public String getTipo(){
        return tipo;
    }

    public static String[] getTipos() {
        String[] resultado = new String[Tipo.values().length];
        for (Tipo tipo : Tipo.values()) {
            resultado[tipo.ordinal()] = tipo.tipo;
        }
        return resultado;
    }
}

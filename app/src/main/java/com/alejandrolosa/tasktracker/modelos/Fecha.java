package com.alejandrolosa.tasktracker.modelos;

public class Fecha {
    private int dia;
    private int mes;
    private int anyo;

    //Constructores
    public Fecha() {
    }

    public Fecha(int dia, int mes, int anyo) {
        this.dia = dia;
        this.mes = mes;
        this.anyo = anyo;
    }

    //setters y getters
    public void setDia(int d) {
        dia = d;
    }
    public void setMes(int m) {
        mes = m;
    }
    public void setAnyo(int a) {
        anyo = a;
    }
    public int getDia() {
        return dia;
    }
    public int getMes() {
        return mes;
    }
    public int getAnyo() {
        return anyo;
    }

    //Método para comprobar si la fecha es correcta
    public boolean fechaCorrecta() {
        boolean diaCorrecto, mesCorrecto, anyoCorrecto;
        anyoCorrecto = anyo > 0;
        mesCorrecto = mes >= 1 && mes <= 12;
        switch (mes) {
            case 2:
                if (esBisiesto()) {
                    diaCorrecto = dia >= 1 && dia <= 29;
                } else {
                    diaCorrecto = dia >= 1 && dia <= 28;
                }
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                diaCorrecto = dia >= 1 && dia <= 30;
                break;
            default:
                diaCorrecto = dia >= 1 && dia <= 31;
        }
        return diaCorrecto && mesCorrecto && anyoCorrecto;
    }

    //Método privado para comprobar si el año es bisiesto
    //Este método lo utiliza el método fechaCorrecta
    private boolean esBisiesto() {
        return (anyo % 4 == 0 && anyo % 100 != 0 || anyo % 400 == 0);
    }

    //Método que modifica la fecha cambiándola por la del día siguiente
    public void diaSiguiente() {
        dia++;
        if (!fechaCorrecta()) {
            dia = 1;
            mes++;
            if (!fechaCorrecta()) {
                mes = 1;
                anyo++;
            }

        }
    }

    //Método toString para mostrar la fecha
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (dia < 10) {
            sb.append("0");
        }
        sb.append(dia);
        sb.append("-");
        if (mes < 10) {
            sb.append("0");
        }
        sb.append(mes);
        sb.append("-");
        sb.append(anyo);
        return sb.toString();
    }
}

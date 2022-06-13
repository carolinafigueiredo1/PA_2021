package jogo.logica;

public enum Evento {
    EVENTO_ESTADO("prop_estado");
    String valor;
    Evento(String s) { valor = s; }
    @Override
    public String toString() { return valor; }
}
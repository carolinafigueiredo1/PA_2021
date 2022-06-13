package jogo;



import jogo.logica.MaqEstados;
import jogo.ui.texto.UItexto;


public class Main  {
    public static void main(String[] args) {
        MaqEstados me = new MaqEstados();
        UItexto uiTexto = new UItexto(me);
        uiTexto.comeca();
    }

}

package jogo.logica.dados;

import java.io.Serializable;

public abstract class MiniJogo implements Serializable {

    public MiniJogo(){ }
    public void setPergunta(){ }

    public boolean getGanhou() { return false; }
    public String getPergunta() { return " ";}
    public String getNome(){return ""; }

    public int getCaracteres(){ return 0; }

    public int getTipo() { return 0; }

    public boolean terminaJogo(int resposta){ return true; }

    public boolean terminaJogo(String reposta){return true;}

    public int getTempo(){ return 0; }
}

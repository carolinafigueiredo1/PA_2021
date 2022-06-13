package jogo.logica.dados;

import java.io.Serializable;

public class Jogador implements Serializable {
    private String nome;
    private int nJogadas;
    private boolean pecaEspecial;
    private int nPecaEspecial;
    private boolean humano;
    private int creditos;
    private int miniJogos;
    private boolean usouPeca;

    public Jogador(String nome){
        this.nome = nome;
        nJogadas = 0;
        pecaEspecial = false;
        nPecaEspecial = 0;
        creditos = 5;
        humano = false;
        miniJogos = 0;
        usouPeca = true;
    }

    public void setNome(String nome) { this.nome = nome; }

    public void setHumano() { humano = true; }



    public String getNome() { return nome; }

    public boolean getHumano() { return humano; }

    public boolean getPecaEspecial() { return pecaEspecial; }

    public int getCreditos() { return creditos; }

    public int getMiniJogos() { return miniJogos; }

    public int getnJogadas() { return nJogadas; }

    public int getnPecaEspecial() { return nPecaEspecial; }



    public void incrementaJogadas() { nJogadas++; }

    public void incrementaMJ(){ miniJogos++; }

    public void perdeJogadas() { nJogadas = 0; }


    public void ganhaPecaEspecial() {
        nPecaEspecial++;
        pecaEspecial = true;
        usouPeca = true;
    }

    public void setUsouPeca(boolean usouPeca) {
        this.usouPeca = usouPeca;
    }

    public boolean getUsouPeca(){ return usouPeca; }

    public void usaPecaEspecial() {
        nPecaEspecial--;
        if (nPecaEspecial == 0)
            pecaEspecial = false;
    }


    public void usaCreditos(int creditos) { if(creditos > 0) this.creditos = creditos-1; }





    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("-> Nome: " + nome);
        s.append("\n");
        s.append("-> Numero Jogadas: " + nJogadas);
        s.append("\n");
        if(humano) {
            if (pecaEspecial) {
                if (nPecaEspecial > 1)
                    s.append("-> Tem " + nPecaEspecial + " Peças Especiais!\n");
                else
                    s.append("-> Tem 1 Peca Especial!\n");
            } else
                s.append("-> Não tem Peca Especial!\n");

            s.append("-> Creditos: " + creditos);
            s.append("\n");
            s.append("-> Mini Jogos: " + miniJogos);
        }
        else{
            s.append("-> Jogador Computador\n");
        }
        return s.toString();
    }

    public void setCreditos(int creditosOJ) { creditos = creditosOJ; }


}

package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

import java.io.Serializable;

public class AguardaJogada extends EstadoAdapter implements Serializable {

    protected AguardaJogada(Jogo jogo) {
        super(jogo);
        jogo.setDecidiuPeca(false);
    }

    @Override
    public IEstado joga(int coluna) {
        String nome;
        nome = jogo.getNomeJogadorAtual();


        if(jogo.tabuleiroCheio()) {
            jogo.addMsgLog("Tabuleiro Cheio!");
            return new AguardaDecisaoFinal(jogo);
        }

        if(!jogo.movePeca(coluna))
            return this;

        jogo.addComentario(nome + " colocou peça na coluna " + coluna + "\n" + "Avança Turno");

        if(jogo.completouLinha()) {
            jogo.addMsgLog(nome + " colocou peça na coluna " + coluna + "Completou Linha" + "\n" + jogo.getTabuleiro());
            jogo.addComentario(nome + " colocou peça na coluna" + coluna + "\nVencedor: " + jogo.getNomeJogadorAtual());
            return new AguardaDecisaoFinal(jogo);
        }
        else
            jogo.addMsgLog(nome + " colocou peça na coluna " + coluna + "\n" + "Avança Turno" + "\n" + jogo.getTabuleiro());


        return new AguardaJogada(jogo);
    }

    /*@Override
    public IEstado jogaComputador(){
        int n;
        boolean move = false;

        do {
            n = (int)(Math.random() * 7) + 1;
            move = jogo.movePeca(n);
        }while(!move);

        if(jogo.tabuleiroCheio()) {
            jogo.addMsgLog("Tabueiro Cheio");
            return new AguardaDecisaoFinal(jogo);
        }

        if(!jogo.completouLinha()) {
            jogo.addComentario("Colocou peça na coluna " + n + "\n" + "Completou Linha");
            jogo.addMsgLog(jogo.getNomeJogadorAtual() + " colocou peça na coluna " + n + "\n" + "Avança Turno" + "\n" + jogo.getTabuleiro());
            return new AguardaJogada(jogo);
        }
        else {
            jogo.addComentario("Colocou peça na coluna " + n);
            jogo.addMsgLog(jogo.getNomeJogadorAtual() + " colocou peça na coluna " + n + "\n" + "Completou Linha" + "\n" + jogo.getTabuleiro());
            return new AguardaDecisaoFinal(jogo);
        }
    }*/

    @Override
    public IEstado avancaMiniJogo() {
        jogo.addComentario("Avanca Mini Jogo");
        jogo.perdeJogadas();
        return new AguardaMiniJogo(jogo,this);
    }

    @Override
    public IEstado mudaTurno() {
        jogo.addComentario("Não Avança Mini Jogo");
        jogo.incrementaTurno();
        jogo.mudaJogadorAtual();
        return new AguardaJogada(jogo);
    }

    @Override
    public IEstado continuarJogo(){
        jogo.addComentario("Não uso Peca");
        jogo.setDecidiuPeca(true);
        return this;
    }

    @Override
    public IEstado usaPeca(int nCol) {
        if(jogo.usaPeca(nCol)){
            jogo.addMsgLog("Usou peca" +  "\n" + jogo.getTabuleiro());
            jogo.addComentario("Usou peça");
        }
        return this;
    }

    public IEstado reinicia(){ return new Inicio(jogo); }

    @Override
    public Situacao getSituacao() { return Situacao.AguardaJogada; }

    @Override
    public IEstado sair() {
        return new Fim(jogo);
    }

}

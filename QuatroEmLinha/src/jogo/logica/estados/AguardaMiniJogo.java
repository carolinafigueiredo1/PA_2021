package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;
import jogo.logica.dados.MiniJogo;

import java.io.Serializable;

public class AguardaMiniJogo extends EstadoAdapter implements Serializable {
    IEstado anterior;

    protected AguardaMiniJogo(Jogo jogo, IEstado anterior) {
        super(jogo);
        this.anterior = anterior;
        jogo.setMiniJogo();
    }

    @Override
    public IEstado jogaMJ() {
        //fazer funçao para verficar tempo e respostas
        //no UI fazer funçao para receber apenas uma conta ou uma palavra
        //fazer MiniJogo2 para 5 contas
        //Atualizar funçoes de MaqEstados para Jogo
        //Verficar se esta tudo a rodar bem

        if(jogo.getGanhouMJ()){
            jogo.addComentario("Ganhou Mini Jogo");
            jogo.addMsgLog("Ganhou Mini Jogo");
            jogo.ganhaPecaEspecial();
            return anterior;
        }
        else {
            jogo.addComentario("Perdeu Mini Jogo");
            jogo.addMsgLog("Perdeu Mini Jogo");
            jogo.incrementaTurno();
            jogo.mudaJogadorAtual();
            return new AguardaJogada(jogo);
        }
    }

    @Override
    public IEstado sair(){
        return new Fim(jogo);
    }

    @Override
    public Situacao getSituacao() { return Situacao.AguardaMiniJogo; }
}

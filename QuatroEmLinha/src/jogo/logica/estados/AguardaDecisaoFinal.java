package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

import java.io.Serializable;

public class AguardaDecisaoFinal extends EstadoAdapter implements Serializable{

    protected AguardaDecisaoFinal(Jogo jogo) {
        super(jogo);
    }

    @Override
    public IEstado reinicia() {
        return new Inicio(jogo);
    }

    @Override
    public IEstado sair() {
        return new Fim(jogo);
    }

    @Override
    public Situacao getSituacao() { return Situacao.AguardaDecisaoFinal; }
}

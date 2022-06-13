package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

import java.io.Serializable;
import java.util.List;

public class Inicio extends EstadoAdapter implements Serializable {

    public Inicio(Jogo jogo) {
        super(jogo);
    }

    @Override
    public IEstado comeca(List<String> nomes) {
        jogo.clearMsgLog();
        if(nomes.size() == 2)
            jogo.inicia(nomes.get(0),nomes.get(1));
        else if(nomes.size() == 1)
            jogo.inicia(nomes.get(0));
        else
            jogo.inicia();

        return new AguardaJogada(jogo);
    }

    @Override
    public IEstado continuarJogo() {
        return new AguardaJogada(jogo);
    }

    @Override
    public IEstado reinicia() {
        return new Inicio(jogo);
    }

    @Override
    public Situacao getSituacao() {
        return Situacao.Inicio;
    }
}

package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;

import java.io.Serializable;

public class Fim extends EstadoAdapter implements Serializable {

    protected Fim(Jogo jogo) { super(jogo); }

    @Override
    public Situacao getSituacao() { return Situacao.Fim; }
}

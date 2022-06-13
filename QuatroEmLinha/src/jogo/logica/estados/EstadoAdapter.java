package jogo.logica.estados;

import jogo.logica.dados.Jogo;
import jogo.logica.dados.MiniJogo;

import java.io.Serializable;
import java.util.List;

public abstract class EstadoAdapter implements Serializable,IEstado {
    protected Jogo jogo;

    protected EstadoAdapter(Jogo jogo){ this.jogo = jogo; }

    @Override
    public Jogo getJogo() { return jogo; }

    @Override
    public IEstado reinicia() { return this; }


    @Override
    public IEstado avancaMiniJogo() { return this; }

    @Override
    public IEstado jogaComputador() { return this; }

    @Override
    public IEstado comeca(List<String> nomes) { return this; }

    @Override
    public IEstado joga(int coluna) { return this; }


    @Override
    public IEstado sair() { return this; }


    @Override
    public IEstado continuarJogo() { return this; }


    @Override
    public IEstado mudaTurno() { return this; }

    @Override
    public IEstado usaPeca(int nCol) { return this; }

    @Override
    public IEstado jogaMJ() { return this; }
}

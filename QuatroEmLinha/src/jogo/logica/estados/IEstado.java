package jogo.logica.estados;

import jogo.logica.Situacao;
import jogo.logica.dados.Jogo;
import jogo.logica.dados.MiniJogo;

import java.util.List;

public interface IEstado {
    IEstado comeca(List<String> nomes);
    IEstado joga(int coluna);
    IEstado avancaMiniJogo();
    IEstado reinicia();
    IEstado sair();
    IEstado jogaComputador();
    IEstado continuarJogo();
    IEstado mudaTurno();
    IEstado usaPeca(int nCol);
    IEstado jogaMJ();

    Situacao getSituacao();

    Jogo getJogo();



}

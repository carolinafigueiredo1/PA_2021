package jogo.ui.texto;


import jogo.logica.MaqEstados;
import jogo.utilsUI.Utils;

import java.util.*;

public class UItexto {
    private MaqEstados me;
    private boolean sair = false;

    public UItexto(MaqEstados me){ this.me = me; }

    public void comeca()
    {
        while(!sair)
        {
            switch (me.getSituacao()){
                case Inicio:
                    inicioUI();
                    break;
                case AguardaJogada:
                    AguardaJogadaUI();
                    break;
                case AguardaMiniJogo:
                    AguardaMiniJogoUI();
                    break;
                case AguardaDecisaoFinal:
                    AguardaDecisaoFinalUI();
                    break;
                case Fim:
                    FimUI();
                    break;
            }
        }
    }




    private void inicioUI() {
        me = new MaqEstados();
        me.setComentario("");
        boolean salvouJogo = false;
        List<String> nomes;
        nomes = new ArrayList<>();
        System.out.println("\n--------------------------------4 em Linha!--------------------------------");

        System.out.println("Regras:");
        System.out.println("- Deve escolher dentro das 7 colunas do tabuleiro;");
        System.out.println("- Apos 4 jogadas vai poder escolher se quer jogar um Mini Jogo;");
        System.out.println("- Se ganhar o jogo ganha uma peca especial (+1 jogada);");
        System.out.println("- Ganha o jogo quando completar uma linha de 4 pecas (vertical,horizontal).");
        System.out.println("---------------------------------------------------------------------------\n");

        if(me.existeFicheiro()){
            System.out.println("Pretende dar load ao jogo anterior?");

            switch (Utils.escolheOpcao("Sim", "Não")) {
                case 1:
                    me = MaqEstados.loadJogo();
                    if (me == null)
                        me = new MaqEstados();
                    else {
                        salvouJogo = true;
                        me.continuarJogo();
                    }
                    break;
                default:
                    break;
            }
            if(me.eliminaFicheiro())
                System.out.println("Ficheiro sera eliminado!");
        }

        if(me.loadCincoJogos()){
            boolean repetir = true;
            do {
                System.out.println("Quer repetir algum dos jogos Guardados?");
                switch (Utils.escolheOpcao("Sim", "Não")) {
                    case 1:
                        replay();
                        break;
                    default:
                        repetir = false;
                        break;
                }
            }while(repetir);

        }
        if(!salvouJogo) {

            System.out.println("Selecione o tipo de jogo:");

            switch (Utils.escolheOpcao("Humano vs Humano", "Humano vs Computador", "Computador vs Computador", "Sair")) {
                case 1:
                    System.out.println("\nIntroduza o nome dos jogadores:");
                    nomes.add(Utils.pedeString("Jogador 1: "));
                    do {
                        nomes.add(Utils.pedeString("Jogador 2: "));
                        if (nomes.get(0).equals(nomes.get(1))) {
                            nomes.remove(1);
                            System.out.println("Nome já existe!");

                        }
                    } while (nomes.size() == 1);
                    break;
                case 2:
                    System.out.println("\nIntroduza o nome do jogador:");
                    nomes.add(Utils.pedeString("Jogador 1: "));
                    break;
                case 3:
                    System.out.println("Ambos Computadores");
                    break;
                default:
                    me.guardarJogosFicheiro();
                    me.sair();
                    break;
            }
            me.comeca(nomes);
        }

    }

    private void AguardaJogadaUI() {
        System.out.println(me.getComentario());
        me.setComentario(" ");

        System.out.println();
        System.out.println("Turno " + me.getTurno());
        System.out.println(me.getTabuleiroTexto());
        System.out.println(me.getNomeJogadorAtual());
        boolean creditos = false;
        boolean joga = false;
        if(me.getJogadorAtualTipo()) {
            if (me.getnJogadasJA() == 3 && me.getJogadorAtualTipo()) {
                System.out.println("Neste momento pode escolher se joga ou não o Mini Jogo!");
                switch (Utils.escolheOpcao("Sim", "Não")) {
                    case 1:
                        me.avancaMiniJogo();
                        break;
                    default:
                        me.perdeJogadasJA();
                        me.mudaTurno();
                        break;
                }
            } else if (me.peca() && !me.getUsouPeca()) {
                System.out.println("Quer usar uma Peca Especial?");
                switch (Utils.escolheOpcao("Sim", "Não")) {
                    case 1:
                        while (!me.getUsouPeca()) {
                            int nCol = Utils.pedeInteiro("Coluna: ");
                            me.usaPeca(nCol);

                        }
                        break;
                    default:
                        me.continuarJogo();
                        break;
                }
            }
            else{
                while (!creditos || !joga)
                    switch (Utils.escolheOpcao("Jogar", "Voltar Atrás", "Histórico", "Ver meus dados", "Saír")) {
                        case 1:
                            int coluna;
                            coluna = Utils.pedeInteiro("Indique a coluna que pretende meter a peça: ");
                            joga = true;
                            me.joga(coluna);
                            creditos = true;
                            break;
                        case 2:
                            int creditosJA = me.getCreditosJA();
                            if (me.getCreditosJA() > 0) {
                                int c = Utils.pedeInteiro("Quantidade Creditos");
                                int ja = me.getIntJogadorAtual();
                                for (int i = 0; i < c; i++)
                                    creditosJA = me.undo(ja, creditosJA);
                                creditos = true;
                                joga = true;
                            } else
                                System.out.println("Nao tem creditos suficientes");
                            break;
                        case 3:
                            System.out.println();
                            System.out.println(me.escreveMsgLogs());
                            System.out.println();
                            break;
                        case 4:
                            System.out.println();
                            System.out.println(me.getJogadorAtual());
                            System.out.println();
                            break;
                        default:
                            System.out.println("Pretende guardar o jogo?");
                            int op = Utils.escolheOpcao("Sim", "Não");
                            switch (op) {
                                case 1:
                                    me.guarda();
                                    me.guardarJogosFicheiro();
                                    break;
                                default:
                                    break;
                            }
                            creditos = true;
                            joga = true;
                            me.sair();
                            break;
                    }
            }
        }
        else{
            int coluna;
            System.out.print("Indique a coluna que pretende meter a peça: ");
            coluna = (int)(Math.random() * 7) + 1;
            System.out.println(coluna);
            me.joga(coluna);
        }
    }


    private void AguardaMiniJogoUI() {
        boolean joga = false;

        System.out.println();
        System.out.println(me.getComentario());
        System.out.println();

        me.setComentario("");

        while(!joga) {
            switch (Utils.escolheOpcao("Jogar Mini Jogo", "Saír")) {
                case 1:
                    System.out.println(me.getNomeMJ());
                    if (me.getTipoMJ() == 1) {
                        int resposta;
                        do {
                            System.out.println("Tempo: " + me.getTempoMJ());
                            resposta = Utils.pedeInteiro(me.getPerguntaMJ());
                        }while(!me.terminouMJ(resposta));
                    }
                    else if (me.getTipoMJ() == 2) {
                        String resposta;
                        do {
                            System.out.println("Tempo: " + me.getTempoMJ());
                            System.out.println("Caracteres: " + me.getCaracteresMJ());
                            resposta = Utils.pedeString(me.getPerguntaMJ());
                        }while(!me.terminouMJ(resposta));
                    }
                    joga = true;
                    me.jogaMJ();
                    break;
                default:
                    joga =  true;
                    System.out.println("Pretende guardar o jogo?");
                    int op = Utils.escolheOpcao("Sim","Não");
                    switch (op){
                        case 1:
                            me.guarda();
                            me.guardarJogosFicheiro();
                            break;
                        default:
                            break;
                    }
                    me.sair();
                    break;
            }
        }
    }


    private void AguardaDecisaoFinalUI() {
        boolean decide = false;
        System.out.println("Vencedor: " + me.getNomeJogadorAtual() + "!");
        System.out.println(me.getTabuleiroTexto());
        me.guardarJogo();
        while (!decide) {
            switch (Utils.escolheOpcao("Recomeçar", "Dados Jogo", "Replay", "Saír")) {
                case 1:
                    decide = true;
                    me.reinicia();
                    break;
                case 2:
                    System.out.println();
                    System.out.println(me.getJogo());
                    System.out.println();
                    break;
                case 3:
                    replay();
                    break;
                default:
                    decide = true;
                    me.guardarJogosFicheiro();
                    me.sair();
                    break;
            }
        }
    }

    private void FimUI() {
        me.sair();
        sair =  true;
    }

    private void replay(){
        int n = 0;
        System.out.println();
        int jogo;
        MaqEstados aux;
        do {
            jogo = Utils.pedeInteiro("Tem " + me.nJogosGuardados() + " jogos guardados, inclusive este!\n Qual jogo pretende dar Replay? 1-" + me.nJogosGuardados());
            if(jogo <= me.nJogosGuardados())
                aux = me.jogoAt(jogo-1);
            else aux = null;
        }while(aux == null);
        do{
            System.out.println(aux.getLogAt(n));
            switch (Utils.escolheOpcao("Next", "Sair")){
                case 1:
                    n++;
                    break;
                default:
                    n = aux.getMsgLogsSize();
                    break;
            }
        }while(aux.nextLog(n));
    }
}

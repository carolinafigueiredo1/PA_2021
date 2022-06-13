
package jogo.logica.dados;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Jogo implements Serializable {
    private final static int NCol = 7;
    private final static int NLin = 6;
    private final static int NPeca = 4;

    private char[][] tabuleiro = new char[NLin][NCol];

    ArrayList<char[][]> tabuleiros;
    ArrayList<Jogador> jogadasJogador;

    private int turno;
    private Jogador jogadorAtual;
    private ArrayList<Jogador> jogadores;
    private final List<String> msgLog;
    private String comentario;
    private MiniJogo miniJogo;


    public Jogo(){
        msgLog = new ArrayList<>();
        tabuleiros = new ArrayList<>();
        jogadasJogador = new ArrayList<>();
        inicia();
    }

    public void setTabuleiro(char[][] tabuleiro){ this.tabuleiro = tabuleiro;}

    public Jogador getJogador(int i){ return jogadasJogador.get(i);}

    public void setJogador(Jogador jogador){ jogadorAtual = jogador;}

    public char[][] getTabuleiros(int i){ return tabuleiros.get(i);}

    public void clearMsgLog(){ msgLog.clear(); }

    public List<String> getMsgLog(){ return msgLog; }

    public void addMsgLog(String msg){ msgLog.add(msg); }

    public String getUltLog() { return msgLog.get(msgLog.size()-1); }

    public String getLogAt(int n){ return msgLog.get(n); }

    public void inicia() {
        turno = 1;
        comentario = "";
        jogadores = new ArrayList<>();
        jogadores.add(new Jogador("Jogador1"));
        jogadores.add(new Jogador("Jogador2"));
        sorteio();
        for(int i = 0; i < NLin; i++) {
            for (int j = 0; j < NCol; j++) {
                tabuleiro[i][j] = '_';
            }
        }
        msgLog.clear();
    }


    public void inicia(String nome) {
        comentario = "";
        turno = 1;
        jogadores = new ArrayList<>();
        jogadores.add(new Jogador(nome));
        jogadores.get(0).setHumano();
        jogadores.add(new Jogador("Jogador2"));

        sorteio();

        for(int i = 0; i < NLin; i++) {
            for (int j = 0; j < NCol; j++) {
                tabuleiro[i][j] = '_';
            }
        }
        msgLog.clear();
    }

    public void inicia(String nome1,String nome2) {
        comentario = "";
        turno = 1;
        jogadores = new ArrayList<>();
        jogadores.add(new Jogador(nome1));
        jogadores.get(0).setHumano();
        jogadores.add(new Jogador(nome2));
        jogadores.get(1).setHumano();


        sorteio();

        for(int i = 0; i < NLin; i++) {
            for (int j = 0; j < NCol; j++) {
                tabuleiro[i][j] = '_';
            }
        }
        msgLog.clear();
    }

    public boolean movePeca(int nCol){


        if(nCol > NCol  || nCol <= 0)
            return false;


        for(int i = NLin - 1; i >= 0; i--){
            if(tabuleiro[i][nCol-1] == '_') {
                if (jogadorAtual.equals(jogadores.get(0)))
                    tabuleiro[i][nCol-1] = 'x';
                else
                    tabuleiro[i][nCol-1] = 'o';
                turno++;
                jogadorAtual.incrementaJogadas();
                tabuleiros.add(tabuleiro);
                jogadasJogador.add(jogadorAtual);
                if(!completouLinha())
                    mudaJogadorAtual();
                return true;
            }
        }

        return false;
    }

    public boolean completouLinha(){
        int certas = 0;
        char tipo = ' ';
        //Horizontal

        for (int i = 0; i < NLin; i++) {
            certas = 0;
            for (int j = 0; j < NPeca; j++) {
                if (tabuleiro[i][j] != '_') {
                    tipo = tabuleiro[i][j];
                    certas++;
                    for (int k = 1; k < NPeca; k++)
                        if (tipo == tabuleiro[i][j + k]) {
                            certas++;
                        }
                        else {
                            certas = 0;
                            tipo = ' ';
                            k=NPeca;
                        }
                }
                if(certas == NPeca)
                    return true;
            }
        }

        //Vertical

        for (int i = 0; i < NCol; i++) {
            certas = 0;
            for (int j = NLin - 1; j > NLin - 3; j--) {
                if (tabuleiro[j][i] != '_') {
                    tipo = tabuleiro[j][i];
                    certas++;
                    for (int k = 1; k < NPeca; k++) {
                        if (tipo == tabuleiro[j - k][i])
                            certas++;
                        else {
                            certas = 0;
                            tipo = ' ';
                            k = NPeca;
                        }
                    }

                }
                if(certas == NPeca)
                    return true;
            }
        }


        certas = 0;
        //Diagonal top-left para bottom-right incluindo o meio
        for (int i = 0; i <= NLin - NPeca; i++)
        {
            tipo = ' ';
            int posicaoLin = i;
            for (int j = 0; j < NCol && posicaoLin < NLin; j++)
            {
                if(tabuleiro[posicaoLin][j] != '_' && tipo == ' ') {
                    tipo = tabuleiro[posicaoLin][j];
                    certas++;
                }
                else {
                    if (tipo == tabuleiro[posicaoLin][j])
                        certas++;
                    else {
                        certas = 0;
                        tipo = ' ';
                    }

                }
                if (certas == NPeca)
                    return true;

                posicaoLin++;
            }
        }
        certas = 0;
        //Diagonal top-left para bottom-right depois do meio
        for (int i = 1; i <= NCol - NPeca; i++)
        {
            tipo = ' ';
            certas = 0;
            int posicaoCol = i;
            for (int j = 0; j < NLin && posicaoCol < NCol; j++)
            {
                if(tabuleiro[j][posicaoCol] != '_' && tipo == ' ') {
                    tipo = tabuleiro[j][posicaoCol];
                    certas++;
                }
                else {
                    if (tipo == tabuleiro[j][posicaoCol])
                        certas++;
                    else {
                        tipo = ' ';
                        certas = 0;
                    }
                }
                if (certas == NPeca)
                    return true;

                posicaoCol++;
            }
        }
        certas = 0;

        //Diagonal bottom-left para top-right incluido o meio
        for (int i = NLin - 1; i >= NLin - NPeca -1; i--)
        {
            tipo = ' ';
            certas = 0;
            int posicaoLin = i;
            for (int j = 0; j < NCol && posicaoLin < NLin && posicaoLin >= 0; j++)
            {
                if(tabuleiro[posicaoLin][j] != '_' && tipo == ' ') {
                    tipo = tabuleiro[posicaoLin][j];
                    certas++;
                }
                else {
                    if (tipo == tabuleiro[posicaoLin][j])
                        certas++;
                    else {
                        tipo = ' ';
                        certas = 0;
                    }
                }
                if (certas == NPeca)
                    return true;

                posicaoLin--;
            }
        }

        //Diagonal bottom-left para top-right depois do meio
        for (int i = 1; i < NCol; i++)
        {
            tipo = ' ';
            certas = 0;
            int posicaoCol = i;
            for (int j = NLin - 1; j < NLin && j >= 0 && posicaoCol < NCol && posicaoCol >= 1; j++)
            {
                if(tabuleiro[j][posicaoCol] != '_' &&  tipo == ' ') {
                    tipo = tabuleiro[j][posicaoCol];
                    certas++;
                }
                else {
                    if (tipo == tabuleiro[j][posicaoCol])
                        certas++;
                    else {
                        tipo = ' ';
                        certas = 0;
                    }
                }
                if (certas == NPeca)
                    return true;

                posicaoCol++;
            }
        }

        return false;
    }

    public boolean tabuleiroCheio(){
        int ocupadas = 0;
        for(int i = 0; i < NLin; i++)
            for(int j = 0; j < NCol; j++)
                if(tabuleiro[i][j] != '_')
                    ocupadas++;
        if(ocupadas == NLin*NCol)
            return true;
        else
            return false;
    }

    public String getTabuleiro(){
        StringBuilder str = new StringBuilder();
        for(char[] celula : tabuleiro)
            str.append(celula);
        return str.toString();
    }


    public String getTabuleiroTexto() {
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < NLin; i++) {
            for (int j = 0; j < NCol; j++)
                str.append(String.format(" %s", tabuleiro[i][j]));
            str.append("\n");
        }
        return str.toString();
    }

    private void sorteio() {
        int sorteio = (int)(Math.random() * 2) + 1;
        if(sorteio == 1)
            jogadorAtual = jogadores.get(0);
        else if(sorteio == 2)
            jogadorAtual = jogadores.get(1);
    }

    public int getTurno() { return turno; }

    public void perdeJogadas(){ jogadorAtual.perdeJogadas(); }

    public String getJogadorAtual() { return jogadorAtual.toString(); }


    public String getNomeJogadorAtual() { return jogadorAtual.getNome(); }

    public boolean getJogadorAtualTipo(){ return jogadorAtual.getHumano(); }

    public boolean getPecaEspecialJA() { return jogadorAtual.getPecaEspecial(); }

    public int getCreditosJA(){ return jogadorAtual.getCreditos(); }

    public int getnPecaEspecialJA() { return jogadorAtual.getnPecaEspecial(); }

    public int getnJogadasJA() { return jogadorAtual.getnJogadas(); }

    public int getMiniJogoJA() { return jogadorAtual.getMiniJogos(); }

    public void mudaJogadorAtual() {
        if (jogadorAtual.equals(jogadores.get(0))) {
            jogadores.set(0,jogadorAtual);
            jogadorAtual = jogadores.get(1);
        } else {
            jogadores.set(1,jogadorAtual);
            jogadorAtual = jogadores.get(0);
        }
    }

    public int getIntJogadorAtual(){
        if (jogadorAtual.equals(jogadores.get(0))) {
            return 0;
        } else {
            return 1;
        }
    }


    public void incrementaTurno(){ turno++; }

    public boolean peca() { return jogadorAtual.getPecaEspecial(); }

    public boolean usaPeca(int nCol){
        if(nCol <= 0 || nCol > NCol)
            return false;
        for(int i = 0; i < NLin; i++)
            tabuleiro[i][nCol - 1] = '_';

        jogadorAtual.setUsouPeca(true);
        jogadorAtual.usaPecaEspecial();
        return true;
    }

    public void setDecidiuPeca(boolean usouPeca){ jogadorAtual.setUsouPeca(usouPeca);}

    public boolean getUsouPeca(){ return jogadorAtual.getUsouPeca(); }

    public void ganhaPecaEspecial() { jogadorAtual.ganhaPecaEspecial();}

    public void setCreditos(int creditos) { jogadorAtual.usaCreditos(creditos);}

    public void setMiniJogo() {
        if(jogadorAtual.getMiniJogos() == 0 || jogadorAtual.getMiniJogos() % 2 == 0)
            miniJogo = new MiniJogo1();
        else
            miniJogo = new MiniJogo2();
        jogadorAtual.incrementaMJ();
    }



    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Turnos: " + turno);
        s.append("\n");
        s.append("Jogador Vencedor: " + jogadorAtual.getNome());
        s.append("\n\n");
        s.append("----Jogador 1:\n" + jogadores.get(0).toString());
        s.append("\n");
        s.append("----Jogador 2:\n" + jogadores.get(1).toString());
        s.append("------\n");
        s.append("\nTabuleiro:\n"+ getTabuleiro());

        return s.toString();
    }

    //Logs

    public String getComentario(){return comentario;}

    public void addComentario(String s) { comentario = s; }

    public void clearUltLog() {
        if(msgLog.size()!=0)
            msgLog.remove(msgLog.size()-1);
    }



    //MiniJogo
    public String getPerguntaMJ() { return miniJogo.getPergunta(); }

    public String getNomeMJ(){ return miniJogo.getNome(); }

    public boolean getGanhouMJ(){ return miniJogo.getGanhou(); }

    public int getTipoMJ() { return miniJogo.getTipo();}

    public boolean terminouMJ(int resposta) { return miniJogo.terminaJogo(resposta);}

    public boolean terminouMJ(String resposta) { return miniJogo.terminaJogo(resposta);}

    public int getTempoMJ() { return miniJogo.getTempo(); }

    public int getCaracteresMJ() { return miniJogo.getCaracteres(); }

    public void setComentario(String s) { comentario = s;}

    public int setUndoJogador(int jogadorAtual, int creditos, int creditosOJ) {
            jogadores.get(jogadorAtual).perdeJogadas();
            jogadores.get(jogadorAtual).usaCreditos(creditos);
            creditos = jogadores.get(jogadorAtual).getCreditos();
            if(jogadorAtual == 0)
                jogadores.get(1).setCreditos(creditosOJ);
            else
                jogadores.get(0).setCreditos(creditosOJ);

        return creditos;
    }

    public int getCreditosOutroJogador(int jogadorAtual) {
        if(jogadorAtual == 0)
            return jogadores.get(1).getCreditos();
        else
            return jogadores.get(0).getCreditos();
    }
}


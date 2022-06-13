package jogo.logica;

import jogo.logica.dados.Jogo;
import jogo.logica.estados.IEstado;
import jogo.logica.estados.Inicio;
import jogo.logica.memento.CareTaker;
import jogo.logica.memento.IMementoOriginator;
import jogo.logica.memento.Memento;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static jogo.files.FileUtility.FILENAME1;
import static jogo.files.FileUtility.FILENAME2;


public class MaqEstados implements Serializable,IMementoOriginator{
    private final static int MAXJOGOS = 5;
    private final ArrayList<MaqEstados> cincoJogos;
    IEstado atual;
    private Jogo jogo;
    private final CareTaker careTaker;


    public MaqEstados(){
        jogo = new Jogo();
        atual = new Inicio(jogo);
        careTaker = new CareTaker(this);
        cincoJogos = new ArrayList<>();
    }


    public void usaPeca(int nCol){ atual = atual.usaPeca(nCol); }

    public void avancaMiniJogo(){ atual = atual.avancaMiniJogo(); }
    public void comeca(List<String> nomes){ atual = atual.comeca(nomes); }

    public void joga(int coluna){
        careTaker.gravaMemento();
        atual = atual.joga(coluna);
    }
    public void sair(){ atual = atual.sair(); }
    public void continuarJogo() { atual = atual.continuarJogo(); }
    public void reinicia() { atual = atual.reinicia();}

    public void mudaTurno() { atual = atual.mudaTurno(); }

    public Situacao getSituacao(){ return atual.getSituacao(); }

    public void jogaMJ() { atual = atual.jogaMJ();}

    public int getTurno() { return jogo.getTurno(); }

    public String getTabuleiroTexto() { return jogo.getTabuleiroTexto();}


    public String getJogo(){ return jogo.toString(); }

    public boolean tabuleiroCheio(){ return jogo.tabuleiroCheio(); }


    public String getNomeJogadorAtual() { return jogo.getNomeJogadorAtual(); }

    public boolean getJogadorAtualTipo(){ return jogo.getJogadorAtualTipo(); }

    public boolean getPecaEspecialJA() { return jogo.getPecaEspecialJA(); }

    public int getCreditosJA(){ return jogo.getCreditosJA(); }

    public int getnPecaEspecialJA() { return jogo.getnPecaEspecialJA(); }

    public int getnJogadasJA() { return jogo.getnJogadasJA(); }

    public int getMiniJogoJA() { return jogo.getMiniJogoJA(); }

    public String getJogadorAtual() { return jogo.getJogadorAtual(); }

    public String getPerguntaMJ() { return jogo.getPerguntaMJ();}

    public boolean peca() { return jogo.peca(); }


    @Override
    public Memento getMemento() throws IOException {
        return new Memento(atual);
    }

    @Override
    public void setMemento(Memento m) throws IOException, ClassNotFoundException {
        atual = (IEstado) m.getSnapshot();
        jogo = atual.getJogo();
    }

    public int undo(int jogadorAtual, int creditos) {
        int creditosOutroJogador;
        if(jogo.getCreditosJA()>0){
            if(getMsgLogsSize() > 0) {
                creditosOutroJogador = jogo.getCreditosOutroJogador(jogadorAtual);
                careTaker.undo();
                creditos = jogo.setUndoJogador(jogadorAtual , creditos, creditosOutroJogador);
            }
        }
        return creditos;
    }

    public void perdeJogadasJA(){ jogo.perdeJogadas();}

    public String escreveMsgLogs(){
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < getMsgLogsSize(); i++)
            s.append("-> ").append(getLogAt(i)).append(";\n");
        return s.toString();
    }

    public List<String> getMsgLogs(){ return jogo.getMsgLog(); }


    public int getMsgLogsSize(){ return jogo.getMsgLog().size();}

    public String getLogAt(int n){ return jogo.getLogAt(n);}

    public void clearMsgLogs(){ jogo.clearMsgLog(); }

    public String getUltLogs(){ return jogo.getUltLog(); }



    public String getComentario() { return jogo.getComentario(); }


    public boolean nextLog(int n){
        if(n >= getMsgLogsSize())
            return false;
        else
            return true;
    }



    public boolean guarda(){
        int erro = 1;
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME1));
            erro++;
            oos.writeObject(this);
            erro++;
            oos.close();
            erro = 0;
        } catch (Exception e) {
            System.err.println("Erro a gravar: " + erro);
        }
        return erro == 0;
    }

    public static MaqEstados loadJogo(){
        File f = new File(FILENAME1);
        int erro;
        erro = 1;
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                erro++;
                MaqEstados me = (MaqEstados) ois.readObject();
                erro++;
                ois.close();
                erro = 0;
                if(!f.delete())
                    erro = 5;
                return me;
            } catch (Exception e) {
                System.err.println("Erro a ler " + erro);
            }
        return null;
    }

    public boolean loadCincoJogos(){
        File f = new File(FILENAME2);
        int n;
        int erro = 1;
        if(cincoJogos.isEmpty()) {
            if (!f.exists())
                return false;

            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                erro = 2;
                n = ois.readInt();
                erro++;
                for (int i = 0; i < n; i++) {
                    cincoJogos.add((MaqEstados) ois.readObject());
                    erro++;
                }
                if (!f.delete())
                    erro++;
                ois.close();

                erro = 0;
                return true;

            } catch (Exception e) {
                System.err.println("Erro a ler" + erro);
            }
        }
        return erro == 0;
    }

    public void guardarJogo() {
        File f = new File(FILENAME2);
        if (cincoJogos.size() == MAXJOGOS)
            cincoJogos.remove(0);
        cincoJogos.add(this);
    }

    public boolean guardarJogosFicheiro(){
        File f = new File(FILENAME2);
        int erro = 1;

        if(!cincoJogos.isEmpty()){
            if(f.exists())
                if(!f.delete())
                    erro++;
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME2));

                erro++;
                oos.writeInt(cincoJogos.size());
                erro++;
                for (MaqEstados cincoJogo : cincoJogos) {
                    oos.writeObject(cincoJogo);
                    erro++;
                }
                oos.close();
                erro = 0;
            } catch (Exception e) {
                System.err.println("Erro a gravar: " + erro);
            }
        }
        return erro == 0;
    }

    public boolean getUsouPeca() { return jogo.getUsouPeca(); }

    public String getNomeMJ() { return jogo.getNomeMJ(); }

    public int getTipoMJ() { return jogo.getTipoMJ(); }

    public boolean terminouMJ(int resposta) { return jogo.terminouMJ(resposta);}

    public int getTempoMJ() { return jogo.getTempoMJ();}

    public boolean terminouMJ(String resposta) { return jogo.terminouMJ(resposta); }

    public int getCaracteresMJ() { return jogo.getCaracteresMJ(); }

    public void setComentario(String s) { jogo.setComentario(s);}

    public MaqEstados jogoAt(int n) {
        if(n < 0 || n  >
                4)
            return null;
        return cincoJogos.get(n);
    }

    public int nJogosGuardados(){ return cincoJogos.size(); }

    public boolean existeFicheiro(){
        File f = new File(FILENAME1);
        return f.exists();
    }



    public boolean eliminaFicheiro() {
        File f = new File(FILENAME1);
        if(f.exists())
            return f.delete();

        return false;
    }

    public int getIntJogadorAtual() { return jogo.getIntJogadorAtual(); }

    public String getTabuleiro() { return jogo.getTabuleiro(); }

    public void mudaTurnoReplay(int i) {
        //jogo.setJogador(jogo.getJogador(i));
        jogo.setTabuleiro(jogo.getTabuleiros(i));
    }
}

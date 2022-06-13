package jogo.logica;

import jogo.files.FileUtility;
import jogo.logica.memento.Memento;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.List;

import static jogo.logica.Constantes.*;


public class JogoObservavel {

    private MaqEstados maqEstados;
    private final PropertyChangeSupport propertyChangeSupport;


    public JogoObservavel(MaqEstados maqEstados){
        this.maqEstados = maqEstados;
        propertyChangeSupport = new PropertyChangeSupport(maqEstados);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }
    public void miniJogoTerminou(){
        propertyChangeSupport.firePropertyChange(PROPRIEDADE_MINI_JOGO, null, null);
    }

    public MaqEstados getMaqEstados() {
        return maqEstados;
    }

    public void usaPeca(int nCol){
        maqEstados.usaPeca(nCol);
        propertyChangeSupport.firePropertyChange(PROPRIEDADE_ESTADOS, null, null);
    }

    public void continuaUndo(){
        propertyChangeSupport.firePropertyChange(PROPRIEDADE_ESTADOS, null, null);

    }

    public void mudaTurnoReplay(int i){
        maqEstados.mudaTurnoReplay(i);
    }


    public void avancaMiniJogo(){
        maqEstados.avancaMiniJogo();
        propertyChangeSupport.firePropertyChange(PROPRIEDADE_ESTADOS, null, null);

    }

    public void comeca(List<String> nomes){
        maqEstados.comeca(nomes);
        propertyChangeSupport.firePropertyChange(PROPRIEDADE_ESTADOS, null, null);

    }

    public void iniciaJogada(){
        }

    public void joga(int coluna){
        maqEstados.joga(coluna);
        propertyChangeSupport.firePropertyChange(PROPRIEDADE_ESTADOS, null, null);
        propertyChangeSupport.firePropertyChange(PROPRIEDADE_MOVE, null, null);

    }
    public void sair(){
        maqEstados.sair();
        propertyChangeSupport.firePropertyChange(PROPRIEDADE_ESTADOS, null, null);

    }
    public void continuarJogo(){
        maqEstados.continuarJogo();
        propertyChangeSupport.firePropertyChange(PROPRIEDADE_ESTADOS, null, null);

    }
    public void reinicia(){
        maqEstados.reinicia();
        propertyChangeSupport.firePropertyChange(PROPRIEDADE_ESTADOS, null, null);

    }

    public void mudaTurno(){
        maqEstados.mudaTurno();
        propertyChangeSupport.firePropertyChange(PROPRIEDADE_ESTADOS, null, null);

    }

    public void jogaMJ(){
        maqEstados.jogaMJ();
        propertyChangeSupport.firePropertyChange(PROPRIEDADE_ESTADOS, null, null);

    }

    public Situacao getSituacao(){ return maqEstados.getSituacao(); }

    public int getTurno(){ return maqEstados.getTurno(); }

    public String getTabuleiroTexto(){ return maqEstados.getTabuleiroTexto(); }

    public String getTabuleiro(){ return maqEstados.getTabuleiro(); }

    public boolean  tabuleiroCheio(){ return maqEstados.tabuleiroCheio(); }

    public String getJogo(){return maqEstados.getJogo(); }


    //---------------------------Jogador--------------------------------------

    public String getNomeJogadorAtual() { return maqEstados.getNomeJogadorAtual(); }

    public boolean getJogadorAtualTipo(){ return maqEstados.getJogadorAtualTipo(); }

    public boolean getPecaEspecialJA() { return maqEstados.getPecaEspecialJA(); }

    public int getCreditosJA(){ return maqEstados.getCreditosJA(); }

    public int getnPecaEspecialJA() { return maqEstados.getnPecaEspecialJA(); }

    public int getnJogadasJA() { return maqEstados.getnJogadasJA(); }

    public int getMiniJogoJA() { return maqEstados.getMiniJogoJA(); }

    public String getJogadorAtual() { return maqEstados.getJogadorAtual(); }

    //-----------------------------------------------------------------------------


    //-----------------------------MINIJOGO----------------------------------------

    public String getNomeMJ(){ return maqEstados.getNomeMJ(); }

    public int getTipoMJ(){ return maqEstados.getTipoMJ(); }



    public boolean terminouMJ(int resposta){ return maqEstados.terminouMJ(resposta); }

    public int getTempoMJ(){ return maqEstados.getTempoMJ(); }

    public boolean terminouMJ(String resposta){ return maqEstados.terminouMJ(resposta); }

    public int getCaracteresMJ(){ return maqEstados.getCaracteresMJ(); }

    public String getPerguntaMJ(){return maqEstados.getPerguntaMJ();}

    //-----------------------------------------------------------------------------





    public boolean peca(){ return maqEstados.peca(); }

    public Memento getMemento() throws IOException{ return maqEstados.getMemento(); }

    public void setMemento(Memento m) throws IOException, ClassNotFoundException { maqEstados.setMemento(m); }

    public int undo(int jogadorAtual, int creditos) { return maqEstados.undo(jogadorAtual, creditos); }

    public void perdeJogadasJA(){ maqEstados.perdeJogadasJA(); }

    public String escreveMsgLogs(){ return maqEstados.escreveMsgLogs(); }

    public List<String> getMsgLogs(){ return maqEstados.getMsgLogs(); }

    public int getMsgLogsSize(){ return maqEstados.getMsgLogsSize(); }

    public String getLogAt(int n){ return maqEstados.getLogAt(n); }

    public void clearMsgLogs(){ maqEstados.clearMsgLogs(); }

    public String getUltLogs(){ return maqEstados.getUltLogs();}

    public String getComentario() { return maqEstados.getComentario(); }

    public boolean nextLog(int n){ return maqEstados.nextLog(n); }

    public boolean guarda(){ return maqEstados.guarda(); }

    //public static MaqEstados loadJogo(){ return MaqEstados.loadJogo(); }

    public boolean loadCincoJogos(){ return maqEstados.loadCincoJogos(); }

    public void guardarJogo(){ maqEstados.guardarJogo(); }

    public boolean guardarJogosFicheiro(){ return maqEstados.guardarJogosFicheiro(); }

    public boolean getUsouPeca(){ return maqEstados.getUsouPeca(); }


    public void setComentario(String s){ maqEstados.setComentario(s); }

    public MaqEstados jogoAt(int n){ return maqEstados.jogoAt(n); }

    public int nJogosGuardados(){ return maqEstados.nJogosGuardados(); }

    public boolean existeFicheiro(){ return maqEstados.existeFicheiro(); }

    public boolean eliminaFicheiro(){ return maqEstados.eliminaFicheiro(); }

    public int getIntJogadorAtual() { return maqEstados.getIntJogadorAtual(); }

    public boolean gravar(File filename)  {

        try {
            FileUtility.saveGameToFile(filename, getMaqEstados());
            return true;
        }catch (IOException e) {
            System.err.println("ERRO ao gravar");
            return false;
        }
    }

    public boolean ler(File filename) {
        try {
            MaqEstados maqEstados = (MaqEstados) FileUtility.retrieveGameFromFile(filename);
            if (maqEstados != null) {
                setMaqEstados(maqEstados);
            }
        }catch (IOException | ClassNotFoundException e) {
            System.err.println("ERRO ao ler");
            return false;
        }
        return true;
    }

    private void setMaqEstados(MaqEstados maqEstados) {
        this.maqEstados = maqEstados;
        propertyChangeSupport.firePropertyChange(null, null, null);
    }
}

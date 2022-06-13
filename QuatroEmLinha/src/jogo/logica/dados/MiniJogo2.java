package jogo.logica.dados;


import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MiniJogo2 extends MiniJogo implements Serializable {
    private final int MAXPAL = 5;
    private List<Integer> nRands;
    private String pergunta;
    Dicionario dicionario;
    private final String nome;
    private final int tipo;
    private LocalTime secondsInicial;
    private LocalTime secondsFinal;
    private boolean ganhou;
    private int nPal;
    private int caracteres;

    public MiniJogo2() {
        dicionario = new Dicionario();
        setPergunta();
        pergunta = getPergunta();
        nome = "MiniJogo2";
        nRands = new ArrayList<>();
        tipo = 2;
        ganhou = false;
        nPal = 0;
    }

    @Override
    public void setPergunta() {
        if(nPal == 0)
            secondsInicial = LocalTime.now();
        int rand;
        do {
            rand = (int) (Math.random() * 100) + 1;
        }while(repetida(rand));
        pergunta = Dicionario.getPalavra(rand);
        setCaracteres();
    }

    public boolean repetida(int rand){
        if(nRands == null || nRands.size() == 0)
            return false;

        for(int i = 0; i < nRands.size(); i++) {
            if (rand == nRands.get(i))
                return true;
        }
        nRands.add(rand);
        return false;
    }


    public boolean verificaResposta(String resposta) {
        return pergunta.equalsIgnoreCase(resposta);
    }


    @Override
    public String getPergunta() {
        return pergunta;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public int getTipo() {
        return tipo;
    }

    @Override
    public boolean terminaJogo(String resposta) {
        nPal++;
        secondsFinal = LocalTime.now();

        int seconds = secondsFinal.toSecondOfDay() - secondsInicial.toSecondOfDay();

        if (seconds <= caracteres) {
            if (verificaResposta(resposta)) {
                if (nPal == MAXPAL) {
                    ganhou = true;
                    return true;
                }
            } else
                return true;
        }
        else
            return true;

        setPergunta();
        return false;
    }

    public void setCaracteres(){
        for (int i = 0; i < pergunta.length(); i++)
            caracteres++;
    }

    @Override
    public int getCaracteres() { return caracteres; }

    @Override
    public boolean getGanhou() {
        return ganhou;
    }

    @Override
    public int getTempo() {
        if(nPal == 0)
            return 0;

        return secondsFinal.toSecondOfDay() - secondsInicial.toSecondOfDay();
    }
}


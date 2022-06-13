package jogo.logica.dados;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Arrays;

public class MiniJogo1 extends MiniJogo implements Serializable {
    private final int MAXJOGOS = 5;
    private final int MAXTEMPO = 30;
    private int respostaCerta;
    private String pergunta;
    private final String nome;
    private LocalTime secondsInicial;
    private final int tipo;
    private LocalTime secondsFinal;
    private boolean ganhou;
    private int nJogos;

    public MiniJogo1(){
        respostaCerta = 0;
        setPergunta();
        pergunta = getPergunta();
        nome = "MiniJogo1";
        tipo = 1;
        ganhou = false;
        nJogos = 0;
    }



    @Override
    public void setPergunta() {
        if(nJogos == 0)
            secondsInicial = LocalTime.now();
        int primeiro = (int) (Math.random() * 100);
        int segundo = (int) (Math.random() * 100);
        switch ((int) (Math.random() * 4) + 1) {
            case 1:
                respostaCerta = primeiro + segundo;
                pergunta = Arrays.toString(new String[]{primeiro + " + " + segundo});
                break;
            case 2:
                respostaCerta = primeiro - segundo;
                pergunta = Arrays.toString(new String[]{primeiro + " - " + segundo});
                break;
            case 3:
                respostaCerta = primeiro / segundo;
                pergunta= Arrays.toString(new String[]{primeiro + " / " + segundo});
                break;
            case 4:
                respostaCerta = primeiro * segundo;
                pergunta = Arrays.toString(new String[]{primeiro + " * " + segundo});
                break;
        }
    }


    public boolean verificaResposta(int resposta){ return (resposta == respostaCerta);  }

    @Override
    public boolean terminaJogo(int resposta){
        nJogos++;
        secondsFinal =  LocalTime.now();

        int seconds = secondsFinal.toSecondOfDay() - secondsInicial.toSecondOfDay();


        if((seconds <= MAXTEMPO)){
            if(verificaResposta(resposta)) {
                if (nJogos == MAXJOGOS) {
                    ganhou = true;
                    return true;
                }
            }
            else
                return true;
        }
        else
            return true;

        setPergunta();
        return false;
    }

    @Override
    public boolean getGanhou(){ return ganhou; }

    @Override
    public String getPergunta() { return pergunta; }

    @Override
    public String getNome() { return nome; }

    @Override
    public int getTipo() { return tipo; }

    @Override
    public int getTempo() {
        if(nJogos == 0)
            return 0;

        return secondsFinal.toSecondOfDay() - secondsInicial.toSecondOfDay();
    }
}

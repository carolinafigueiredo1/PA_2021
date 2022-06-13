package jogo.logica.dados;

import java.io.Serializable;

public class Dicionario implements Serializable {
    private static final String [] palavras = {"Barco","Estofamento","Peregrino","Presentes","Ganhar",
            "Toldo","Bisão","Disputa","Bravo","Comida","Esculpir","Pilha","Felicidade","Mesquita",
            "Sesta","Solta","Patins","Atacar","Donald","Fruta","Formal","Defender","Honeymoon",
            "Maturidade","Bruto","Estreito","Cartas","Flowerpot","Palavras","Claustrofobia",
            "Tattoo","Orangotango","Solta","Desporto","Albergue","Cobertura","Delta","Ousar","Curva",
            "Agricultor","Piquenique","Axila","Tubos","Comprimento","Todos","Coletor","Arquitetura",
            "Alasca","Fantoche","Jovens","Impressao","Bebado","Oferta","Pimenta","Atender","Cabelo",
            "Cisne","Linhas","Roubar","Arqueologia","Aldeia","Medio","Futebol","Rodovia","Pomar",
            "Zumbido","Cerveja","Palha","Atletismo","Sangrar","Vento","Amizade","Saudar","Carta",
            "Agulha","Fratura","Minhoca","Curral","Espuma","Retrato","Pacote","Integral","Himalaias",
            "Intrometido","Incomodar","Insonia","Estatua","Piada","Final","Saliva","Esquiar","Remetente",
            "Exemplo","Costureira","Geometria","Lista","Floresta","Salada","Sereia","Sobrancelhas"
    };

    public static String getPalavra(int i)
    {
        if(i<0 || i>=palavras.length)
            return null;

        return palavras[i];
    }

}

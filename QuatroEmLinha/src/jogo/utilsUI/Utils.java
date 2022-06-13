package jogo.utilsUI;

import java.util.Scanner;

public final class Utils {
    static Scanner sc;

    static {
        sc = new Scanner(System.in);
    }

    private Utils() {}

    public static int pedeInteiro(String pergunta) {
        System.out.print(pergunta + " ");
        while (!sc.hasNextInt())
            sc.next();
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }
    public static String pedeString(String pergunta) {
        String resposta;
        do {
            System.out.print(pergunta + " ");
            resposta = sc.nextLine().trim();
        } while (resposta.isEmpty());
        return resposta;
    }

    public static int escolheOpcao(String... opcoes) {
        int opcao;
        do {
            for (int i = 0; i < opcoes.length-1; i++)
                System.out.printf("%d - %s\n",i+1,opcoes[i]);
            System.out.printf("%d - %s",0,opcoes[opcoes.length-1]);
            opcao = pedeInteiro("\n> ");
        } while (opcao<0 || opcao>=opcoes.length);
        return opcao;
    }
}


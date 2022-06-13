package jogo.ui.gui.estados;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.control.Label;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import jogo.logica.JogoObservavel;

import jogo.ui.gui.TabuleiroPane;
import javafx.scene.control.Button;


import java.util.Timer;
import java.util.TimerTask;

import static jogo.logica.Constantes.PROPRIEDADE_ESTADOS;
import static jogo.logica.Situacao.AguardaJogada;
import static jogo.ui.gui.ConstantesGUI.*;

public class AguardaJogadaPane extends BorderPane {

    private JogoObservavel jogoObservavel;
    private final Button miniJogo, voltarAtras, usarPeca;
    private TabuleiroPane tabuleiroPane;
    private HBox bottomBox, toolBar;
    private TextField creditosVA, coluna;
    private Text jogador, nPecas, creditosJA;
    private int nCol;

    public AguardaJogadaPane(JogoObservavel jogoObservavel) {
        this.jogoObservavel = jogoObservavel;
        miniJogo = new Button("Mini Jogo");
        voltarAtras = new Button("Voltar Atras");
        usarPeca = new Button("Usar Peca");
        createLayoutRegisterListeners();
        registerObserver();
        update();
    }

    private void createLayoutRegisterListeners() {
        tabuleiroPane = new TabuleiroPane(jogoObservavel);
        toolBar = new HBox(10);

        // STACKPANE COM OS PAINEIS DOS ESTADOS
        bottomBox = new HBox(10);
        bottomBox.setMinSize(DIM_X_BOTTOM_PANEL, DIM_Y_BOTTOM_PANEL);
        bottomBox.getChildren().addAll(miniJogo, voltarAtras, usarPeca);
        bottomBox.setAlignment(Pos.CENTER);
        setBottom(bottomBox);

        VBox leftBox = new VBox(10);
        leftBox.getChildren().addAll(toolBar, tabuleiroPane, bottomBox);
        setLeft(leftBox);

        miniJogo.setOnAction(e-> jogoObservavel.avancaMiniJogo());
        voltarAtras.setOnAction(new VoltarAtras());
        usarPeca.setOnAction(new UsaPeca());
        HBox center = new HBox(10);
        center.getChildren().addAll(leftBox);
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(10,10,10,10));
        setCenter(center);
    }

    private void registerObserver() {
        jogoObservavel.addPropertyChangeListener(PROPRIEDADE_ESTADOS, evt -> {
            update();
            if(!jogoObservavel.getJogadorAtualTipo()){
                jogoComputador();
            }
        });
    }

    private void jogoComputador(){
        Timer timer = new Timer();
        nCol = (int)(Math.random() * 7) + 1;
        timer.schedule(new MyTimerTask(), 1 * 1000);
    }

    private void update() {
        miniJogo.setDisable(!(jogoObservavel.getnJogadasJA() == 3 && jogoObservavel.getJogadorAtualTipo()));
        voltarAtras.setDisable(!(jogoObservavel.getCreditosJA() > 0 && jogoObservavel.getnJogadasJA() != 3 && jogoObservavel.getJogadorAtualTipo()));
        usarPeca.setDisable(!(jogoObservavel.getnPecaEspecialJA() > 0));
        tabuleiroPane.setDisable(jogoObservavel.getnJogadasJA() == 3);
        this.setVisible(jogoObservavel.getSituacao() == AguardaJogada);

        if (jogoObservavel.getnJogadasJA() == 3 && jogoObservavel.getJogadorAtualTipo()) {
            Alert dialogoResultado = new Alert(Alert.AlertType.INFORMATION);
            dialogoResultado.setHeaderText("Mini Jogo");
            dialogoResultado.setContentText("Pode jogar o Mini Jogo!" +
                    "\nCaso não jogue perde a opurtunidade! " +
                    "\n(Mini Jogo de 4 em 4 jogadas)");
            dialogoResultado.showAndWait();


            jogoObservavel.perdeJogadasJA();

            Button avanca = new Button("Avanca");
            bottomBox.getChildren().clear();
            bottomBox.getChildren().addAll(miniJogo,avanca);

            avanca.setOnAction(e-> jogoObservavel.mudaTurno());
        }
        else {

            if (jogoObservavel.getnPecaEspecialJA() == 1) {
                Alert dialogoResultado = new Alert(Alert.AlertType.INFORMATION);
                dialogoResultado.setHeaderText("Peca");
                dialogoResultado.setContentText("Pode jogar usar Peca!");
                dialogoResultado.showAndWait();
            }

            bottomBox.getChildren().clear();
            bottomBox.getChildren().addAll(miniJogo, voltarAtras, usarPeca);
            //se tiver de mudar de turno posso usar funçao mudaTurno
        }

        jogador = new Text("Jogador " + (jogoObservavel.getIntJogadorAtual() + 1));
        nPecas = new Text("Peça Especial: " + jogoObservavel.getnPecaEspecialJA());
        creditosJA = new Text("Creditos: " + jogoObservavel.getCreditosJA());

        jogador.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        nPecas.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        creditosJA.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));

        toolBar.getChildren().clear();
        toolBar.getChildren().addAll(jogador, nPecas, creditosJA);
        toolBar.setPadding(new Insets(10));
        toolBar.setAlignment(Pos.CENTER);
        if (jogoObservavel.getIntJogadorAtual() == 0)
            changeBackground(toolBar, Color.rgb(219, 50, 50));
        else
            changeBackground(toolBar, Color.rgb(226, 191, 71));
        setTop(toolBar);
    }

    private class VoltarAtras implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            Label creditosLabel = new Label("Creditos a usar");

            creditosVA = new TextField(" ");
            creditosVA.setMaxWidth(50);

            Button enter = new Button("Enter");
            enter.setOnAction(new Enter());

            bottomBox.getChildren().clear();
            bottomBox.getChildren().addAll(creditosLabel, creditosVA,enter);
        }

        private class Enter implements EventHandler<ActionEvent> {
            @Override
            public void handle(ActionEvent actionEvent) {
                int creditos = 0;
                int creditosJA = jogoObservavel.getCreditosJA();
                try {
                    creditos = getValue();
                } catch (NumberFormatException ex){
                    return;
                }

                if (creditos > 0){
                    int ja = jogoObservavel.getIntJogadorAtual();
                    for (int i = 0; i < creditos && creditos-i > 0; i++)
                        creditosJA = jogoObservavel.undo(ja, creditosJA);
                }
                jogoObservavel.continuaUndo();

            }
            private int getValue() throws NumberFormatException {

                String s1 = (creditosVA.getText()).trim();

                if (s1.length() < 1){
                    throw new NumberFormatException();
                }
                int value = Integer.parseInt(s1);
                return value;

            }

        }
    }

    private void changeBackground(Region region, Color color){
        region.setBackground(new Background(new BackgroundFill(color,CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private class UsaPeca implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            Label colunaLabel = new Label("Coluna: ");
            coluna = new TextField(" ");
            coluna.setMaxWidth(50);

            Button enter = new Button("Enter");
            enter.setOnAction(new Enter());

            bottomBox.getChildren().clear();
            bottomBox.getChildren().addAll(colunaLabel, coluna, enter);

        }

        private class Enter implements EventHandler<ActionEvent> {
            @Override
            public void handle(ActionEvent actionEvent) {
                int n = 0;
                try {
                    n = getValue();
                } catch (NumberFormatException ex){
                    return;
                }

                if (n > 0 && n < N_COL_TABULEIRO){
                    jogoObservavel.usaPeca(n);
                }

                jogoObservavel.continuarJogo();
                //jogoObservavel.mudaTurno();
            }
        }

        private int getValue() throws NumberFormatException {

            String s1 = (coluna.getText()).trim();

            if (s1.length() < 1){
                throw new NumberFormatException();
            }
            int value = Integer.parseInt(s1);
            return value;

        }


    }


    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            Platform.runLater(()-> jogoObservavel.joga(nCol));
        }
    }
}

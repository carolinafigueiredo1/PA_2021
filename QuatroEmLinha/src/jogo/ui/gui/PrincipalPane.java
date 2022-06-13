package jogo.ui.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import jogo.logica.JogoObservavel;
import javafx.scene.control.Button;

import jogo.ui.gui.estados.InicioPane;

import jogo.ui.gui.resources.ImageLoader;


import java.io.File;

import static jogo.files.FileUtility.FILENAME1;
import static jogo.logica.Constantes.PROPRIEDADE_ESTADOS;
import static jogo.logica.Situacao.Inicio;
import static jogo.ui.gui.ConstantesGUI.*;
import static jogo.ui.gui.ConstantesGUI.DIM_Y_BOTTOM_PANEL;


public class PrincipalPane extends BorderPane {
    private JogoObservavel jogoObservavel;
    private Button joga;
    private Button voltarAtras;
    private Button jogarPeca;
    private Button miniJogo;
    private Button loadJogoAnterior;

    private TextField numeroJogo;

    public PrincipalPane(JogoObservavel jogoObservavel) {
        this.jogoObservavel = jogoObservavel;
        createLayoutRegisterListeners();
        registerObserver();
        update();
    }

    private void createLayoutRegisterListeners() {
        jogoObservavel.loadCincoJogos();

        ImageView imageView = new ImageView();
        Image image = ImageLoader.getImage(ConstantesGUI.TABULEIRO);
        imageView.setImage(image);
        imageView.setFitHeight(DIM_Y_TABULEIRO);
        imageView.setFitWidth(DIM_X_TABULEIRO);

        VBox box = new VBox(10);

        if(jogoObservavel.existeFicheiro()){
            loadJogoAnterior = new Button("Load Jogo");
            this.getChildren().add(loadJogoAnterior);
            loadJogoAnterior.setOnAction(new LerJogo());
            box.getChildren().add(loadJogoAnterior);
        }
        Button iniciarButton = new Button("Novo Jogo");
        Button repetirButton = new Button("Repetir Jogos");

        getChildren().addAll(iniciarButton, repetirButton);

        iniciarButton.setOnAction(new IniciaJogo());
        repetirButton.setOnAction(new RepetirJogoAnterior());
        repetirButton.setVisible(jogoObservavel.nJogosGuardados() > 0);
        repetirButton.setVisible(false);
        box.getChildren().addAll(imageView,iniciarButton,repetirButton);
        box.setAlignment(Pos.CENTER);
        setCenter(box);
    }

    private void registerObserver() {
        /*jogoObservavel.addPropertyChangeListener(PROPRIEDADE_MOVE, evt -> {
            update();
        });*/
    }

    private void update() {

        this.setVisible(jogoObservavel.getSituacao() == Inicio);
    }

    class LerJogo implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            File f = new File(".\\jogosgravados\\" + FILENAME1);
            jogoObservavel.ler(f);
        }
    }

    private class IniciaJogo implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            JogoPane jogoPane = new JogoPane(jogoObservavel);
            HBox center = new HBox(10);
            center.setPadding(new Insets(10, 10, 10, 10));
            center.setAlignment(Pos.CENTER);
            center.getChildren().addAll(jogoPane);
            setCenter(center);
        }
    }


    private class RepetirJogoAnterior implements EventHandler<ActionEvent> {
        private int i;
        @Override
        public void handle(ActionEvent e) {
            Label label = new Label("Numero do Jogo [0-" + jogoObservavel.nJogosGuardados() + "]: ");
            numeroJogo = new TextField("  ");
            numeroJogo.setMaxWidth(50);
            Button mudaJogo = new Button("Escolhe");

            mudaJogo.setOnAction(new EventHandler<ActionEvent>(){
                TabuleiroPane tabuleiroPane;
                HBox toolBar, bottomBox;
                VBox leftBox;
                @Override
                public void handle(ActionEvent actionEvent) {
                    int value = 0;
                    try {
                        while (value < 0 || value >= jogoObservavel.nJogosGuardados())
                            value = getValue();
                    } catch (NumberFormatException ex){
                        return;
                    }

                    JogoObservavel novo = new JogoObservavel(jogoObservavel.jogoAt(value-1));
                    i = 0;
                    while(i < jogoObservavel.getTurno()) {
                        getChildren().clear();
                        novo.mudaTurnoReplay(i);
                        tabuleiroPane = new TabuleiroPane(novo);
                        toolBar = new HBox(10);

                        Button proximaJogada = new Button("Proxima Jogada");
                        Button voltarAtras = new Button("Voltar Atras");

                        // STACKPANE COM OS PAINEIS DOS ESTADOS
                        bottomBox = new HBox(10);
                        bottomBox.setMinSize(DIM_X_BOTTOM_PANEL, DIM_Y_BOTTOM_PANEL);
                        bottomBox.getChildren().addAll(proximaJogada,voltarAtras);
                        bottomBox.setAlignment(Pos.CENTER);
                        setBottom(bottomBox);

                        leftBox = new VBox(10);
                        leftBox.getChildren().addAll(toolBar, tabuleiroPane, bottomBox);
                        setLeft(leftBox);

                        proximaJogada.setOnAction(e-> i++);

                        voltarAtras.setOnAction(e -> jogoObservavel.reinicia());
                   }
                }


            });
            getChildren().addAll(label, numeroJogo,mudaJogo);

            HBox box = new HBox(10);
            box.setPadding(new Insets(10, 10, 10, 10));
            box.setAlignment(Pos.CENTER);
            box.getChildren().addAll(label, numeroJogo, mudaJogo);
            setCenter(box);


        }

    }

    private int getValue() throws NumberFormatException {

        String s1 = (numeroJogo.getText()).trim();

        if (s1.length() < 1){
            throw new NumberFormatException();
        }
        int value = Integer.parseInt(s1);
        return value;

    }
}

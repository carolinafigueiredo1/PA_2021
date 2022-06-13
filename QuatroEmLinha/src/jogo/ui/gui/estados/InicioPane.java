package jogo.ui.gui.estados;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import jogo.logica.JogoObservavel;
import jogo.ui.gui.ConstantesGUI;
import jogo.ui.gui.resources.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import static jogo.logica.Constantes.PROPRIEDADE_ESTADOS;
import static jogo.logica.Situacao.*;
import static jogo.ui.gui.ConstantesGUI.DIM_X_TABULEIRO;
import static jogo.ui.gui.ConstantesGUI.DIM_Y_TABULEIRO;

public class InicioPane extends BorderPane {
    private JogoObservavel jogoObservavel;
    private Button loadJogoAnterior;
    private final TextField nome1, nome2;
    private VBox topBox;

    public InicioPane(JogoObservavel jogoObservavel) {
        this.jogoObservavel = jogoObservavel;
        nome1 = new TextField();
        nome2 = new TextField();
        createLayoutRegisterListeners();
        registerObserver();
        update();
    }

    private void createLayoutRegisterListeners() {
        ImageView imageView = new ImageView();
        Image image = ImageLoader.getImage(ConstantesGUI.TABULEIRO);
        imageView.setImage(image);
        imageView.setFitHeight(DIM_Y_TABULEIRO);
        imageView.setFitWidth(DIM_X_TABULEIRO);

        Label nJogadores = new Label("Tipo de Jogo");
        Button hVh = new Button("Humano vs Humano");
        Button hVc = new Button("Humano vs Computador");
        Button cVc = new Button("Computador vs Computador");


        HBox bottomBox = new HBox(10);
        bottomBox.setPadding(new Insets(10, 10, 10, 10));
        bottomBox.getChildren().addAll(hVh,hVc,cVc);
        bottomBox.setAlignment(Pos.CENTER);

        topBox = new VBox(10);
        topBox.setPadding(new Insets(10, 10, 10, 10));
        topBox.getChildren().addAll(imageView, nJogadores, bottomBox);
        topBox.setAlignment(Pos.CENTER);
        setCenter(topBox);

        hVh.setOnAction(new HumanovsHumano());

        hVc.setOnAction(new HumanovsComputador());

        cVc.setOnAction(e -> {
            List<String> nomes = new ArrayList<>() ;
            jogoObservavel.comeca(nomes);
        });


    }

    private void registerObserver() {
        jogoObservavel.addPropertyChangeListener( PROPRIEDADE_ESTADOS, evt -> {
            update();
        });
    }

    private void update() {
        this.setVisible(jogoObservavel.getSituacao() == Inicio);
    }


    private class HumanovsHumano implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            HBox box = new HBox(10);

            Label label = new Label("Nome Jogador 1: ");
            nome1.setMaxWidth(100);
            nome2.setMaxWidth(100);
            Button insereNome = new Button("Insere Nomes");

            getChildren().addAll(label, nome1);

            box.getChildren().addAll(label, nome1);


            label = new Label("Nome Jogador 2: ");
            getChildren().addAll(label, nome2,insereNome);
            box.getChildren().addAll(label, nome2);

            box.setAlignment(Pos.CENTER);


            VBox centerBox = new VBox(10);
            centerBox.setPadding(new Insets(10, 10, 10, 10));
            centerBox.getChildren().addAll(topBox,box,insereNome);
            centerBox.setAlignment(Pos.CENTER);
            setCenter(centerBox);


            insereNome.setOnAction(e-> {
                List<String> nomes = new ArrayList<>();
                if(!nome1.getText().isEmpty() && !nome1.getText().isBlank())
                    nomes.add(nome1.getText());
                if(!nome2.getText().isEmpty() && !nome2.getText().isBlank())
                    nomes.add(nome2.getText());
                else
                    return;

                if(!nomes.get(0).equals(nomes.get(1)))
                    jogoObservavel.comeca(nomes);

            });

        }
    }

    private class HumanovsComputador implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            HBox box = new HBox(10);

            Label label = new Label("Nome Jogador 1: ");
            nome1.setMaxWidth(100);
            Button insereNome = new Button("Insere Nomes");

            getChildren().addAll(label, nome1);

            box.getChildren().addAll(label, nome1);

            box.setAlignment(Pos.CENTER);

            VBox centerBox = new VBox(10);
            centerBox.setPadding(new Insets(10, 10, 10, 10));
            centerBox.getChildren().addAll(topBox,box,insereNome);
            centerBox.setAlignment(Pos.CENTER);
            setCenter(centerBox);




            insereNome.setOnAction(e-> {
                List<String> nomes = new ArrayList<>();
                if(!nome1.getText().isEmpty() && !nome1.getText().isBlank())
                    nomes.add(nome1.getText());
                else
                    return;

                if(!nomes.get(0).equals("Jogador2") )
                    jogoObservavel.comeca(nomes);

            });

        }
    }

}

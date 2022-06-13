package jogo.ui.gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import jogo.logica.JogoObservavel;
import jogo.logica.Situacao;


import java.util.ArrayList;
import java.util.List;

import static jogo.logica.Constantes.PROPRIEDADE_ESTADOS;
import static jogo.logica.Constantes.PROPRIEDADE_MOVE;
import static jogo.logica.Situacao.AguardaJogada;
import static jogo.ui.gui.ConstantesGUI.*;

public class TabuleiroPane extends BorderPane{

    private JogoObservavel jogoObservavel;
    private GridPane grid;
    private GridPane pecas;
    private Pane gridPane;


    public TabuleiroPane(JogoObservavel jogoObservavel) {
        this.jogoObservavel = jogoObservavel;
        grid = new GridPane();
        pecas = new GridPane();
        gridPane = new Pane();
        createLayout();
        registerObserver();
        update();
        layoutFinal();
    }

    private void layoutFinal() {


        VBox centerBox = new VBox(10);
        centerBox.getChildren().addAll(pecas,gridPane);
        centerBox.setAlignment(Pos.CENTER);
        setCenter(centerBox);


    }


    private void registerObserver(){
        // regista um observador do jogoObservavel
        jogoObservavel.addPropertyChangeListener(PROPRIEDADE_ESTADOS, evt -> {
            update();
            layoutFinal();
        });
        jogoObservavel.addPropertyChangeListener(PROPRIEDADE_MOVE, evt -> {
            update();
            layoutFinal();
        });
    }

    private void createLayout(){

        pecas.setPrefSize(DIM_X_TABULEIRO,DIM_Y_CELULA);
        pecas.setHgap(GAP_X_CELULAS);
        pecas.setVgap(GAP_Y_CELULAS);


        grid.setPrefSize(DIM_X_TABULEIRO, DIM_Y_TABULEIRO);
        grid.setBorder(new Border(new BorderStroke(Color.MEDIUMBLUE, BorderStrokeStyle.SOLID,
                null, new BorderWidths(2))));

        grid.setBackground(new Background(
                new BackgroundFill(Color.MEDIUMBLUE, null, null)));

        grid.setPadding(new Insets(10, 10, 10, 10));



        grid.setHgap(GAP_X_CELULAS);
        grid.setVgap(GAP_Y_CELULAS);
        grid.setAlignment(Pos.CENTER);


        pecas.setAlignment(Pos.CENTER);


    }

    private void update() {
        String tabuleiro = jogoObservavel.getTabuleiro();

        this.getChildren().clear();
        gridPane.getChildren().clear();
        int i = 0;
        int x;
        int y;
        char celula;

        for (x = 0; x < N_COL_TABULEIRO; x++) {
            Pane pane = new Pane();
            pane.setPrefHeight(DIM_Y_CELULA);
            pane.setPrefWidth(DIM_X_CELULA);

            pane.setShape(new Circle(0,0,10.0));
            changeBackground(pane,Color.TRANSPARENT);
            pecas.add(pane,x,0);

        }


        while (i < tabuleiro.length()){
            for (y = 0; y < N_LIN_TABULEIRO; y++) {
                for (x = 0; x < N_COL_TABULEIRO; x++) {
                    celula = tabuleiro.charAt(i);

                    Pane pane = new Pane();
                    pane.setPrefHeight(DIM_Y_CELULA);
                    pane.setPrefWidth(DIM_X_CELULA);

                    pane.setShape(new Circle(0,0,10.0));
                    if (celula == 'x') {
                        changeBackground(pane, Color.RED);
                    } else if (celula == 'o') {
                        changeBackground(pane, Color.YELLOW);
                    }
                    else {
                        changeBackground(pane, Color.WHITE);
                    }
                    grid.add(pane, x, y);
                    i++;
                }
            }
        }

        gridPane.getChildren().add(grid);
        if(jogoObservavel.getJogadorAtualTipo() && jogoObservavel.getSituacao() == AguardaJogada)
            gridPane.getChildren().addAll(makeColumns());
        this.getChildren().add(pecas);
        this.getChildren().add(gridPane);
    }

    private void changeBackground(Region region, Color color){
        region.setBackground(new Background(new BackgroundFill(color,CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private List<Rectangle> makeColumns() {
        List<Rectangle> list = new ArrayList<>();
        for (int x = 0; x < N_COL_TABULEIRO; x++) {
            Rectangle rect = new Rectangle(DIM_X_CELULA + GAP_X_CELULAS,(DIM_Y_CELULA+GAP_Y_CELULAS) * (N_LIN_TABULEIRO));

            rect.setTranslateX(x * (DIM_X_CELULA + GAP_X_CELULAS));
            rect.setFill(Color.TRANSPARENT);
            int finalX = x;
            rect.setOnMouseClicked(mouseEvent -> jogoObservavel.joga( finalX+ 1));
                rect.setOnMouseEntered(mouseEvent -> {

                    Pane pane = (Pane) pecas.getChildren().get(finalX);

                    if(jogoObservavel.getIntJogadorAtual() == 1) {
                        rect.setFill(Color.rgb(174, 160, 60, 0.5));
                        changeBackground(pane, Color.YELLOW);
                    }
                    else {
                        rect.setFill(Color.rgb(174, 60, 60, 0.5));
                        changeBackground(pane, Color.RED);
                    }
                });


            rect.setOnMouseExited(mouseEvent -> {
                rect.setFill(Color.TRANSPARENT);
                changeBackground((Pane)pecas.getChildren().get(finalX),Color.TRANSPARENT);

            });


            list.add(rect);
        }

        return list;
    }

}

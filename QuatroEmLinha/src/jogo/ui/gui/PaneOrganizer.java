package jogo.ui.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jogo.logica.JogoObservavel;
import jogo.logica.Situacao;
import jogo.logica.estados.AguardaJogada;

import java.io.File;

import static jogo.logica.Constantes.PROPRIEDADE_ESTADOS;

public class PaneOrganizer extends BorderPane {
    private JogoObservavel jogoObservavel;
    private MenuItem novoJogoMI,gravarObjMI;
    MenuItem dadosJogadorMI;

    public PaneOrganizer(JogoObservavel jogoObservavel){
        this.jogoObservavel = jogoObservavel;

        createCentralLayout();
        menus();
        registerObserver();
        update();
    }


    void createCentralLayout(){
        PrincipalPane principalPane =  new PrincipalPane(jogoObservavel);
        setCenter(principalPane);

        HBox center = new HBox(10);
        center.setPadding(new Insets(10, 10, 10, 10));
        center.setAlignment(Pos.CENTER);
        center.getChildren().addAll(principalPane);
        setCenter(center);


    }

    void registerObserver(){
        jogoObservavel.addPropertyChangeListener(PROPRIEDADE_ESTADOS, evt -> {
            update();
        });
    }


    private void menus() {
        MenuBar menuBar = new MenuBar();
        setTop(menuBar);

        // menu Jogo
        Menu jogoMenu = new Menu("_Jogo");  // underscore: abre com alt + j

        novoJogoMI = new MenuItem("Novo jogo");
        novoJogoMI.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        MenuItem lerObjMI = new MenuItem("Ler jogo");
        lerObjMI.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));

        gravarObjMI = new MenuItem("Gravar");
        gravarObjMI.setAccelerator(new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN));

        MenuItem sairMI = new MenuItem("Sair");
        sairMI.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        jogoMenu.getItems().addAll( novoJogoMI, lerObjMI, gravarObjMI,new SeparatorMenuItem(), sairMI);

        novoJogoMI.setOnAction((e)-> jogoObservavel.reinicia());

        lerObjMI.setOnAction(new LerObjMenuBarListener());

        gravarObjMI.setOnAction(new GravarObjMenuBarListener());

        sairMI.setOnAction(new Sair());


        // menu ajuda
        Menu ajudaMenu = new Menu("_Ajuda");

        MenuItem comoJogarMI = new MenuItem("Como jogar");

        comoJogarMI.setAccelerator(new KeyCodeCombination(KeyCode.J, KeyCombination.CONTROL_DOWN));
        ajudaMenu.getItems().addAll(comoJogarMI);
        comoJogarMI.setOnAction(new AjudaListener());

        Menu dadosMenu = new Menu("Dados");

        dadosJogadorMI = new MenuItem("Meus Dados");


        dadosMenu.getItems().addAll(dadosJogadorMI);

        dadosJogadorMI.setOnAction(new DadosJogador());

        menuBar.getMenus().addAll(jogoMenu,ajudaMenu,dadosMenu);
    }

    class LerObjMenuBarListener implements EventHandler<ActionEvent>  {
        Button avancar, voltar;
        Stage avisoStage;
        @Override
        public void handle(ActionEvent e) {
            BorderPane aviso = new BorderPane();
            avisoStage = new Stage();

            Scene scene1 = new Scene(aviso, 250, 150);
            avisoStage.setScene(scene1);
            avisoStage.setTitle("Quatro em Linha");
            avisoStage.setMinWidth(100);
            avisoStage.setMinHeight(100);
            avisoStage.show();

            Text avisoTexto = new Text("O novo jogo vai sobrepor o seu jogo");


            avancar = new Button("Avancar");
            voltar = new Button("Voltar");

            HBox buttonBox = new HBox(10);
            buttonBox.getChildren().addAll(avancar,voltar);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.setPadding(new Insets(10,10,10,10));
            aviso.setTop(buttonBox);

            VBox center = new VBox(10);
            center.getChildren().addAll(avisoTexto,buttonBox);
            center.setAlignment(Pos.CENTER);
            aviso.setCenter(center);


            avancar.setOnAction((ActionEvent l) ->{
                FileChooser fileChooser = new FileChooser();


                fileChooser.setInitialDirectory(new File("QuatroEmLinha/jogosinacabados"));
                File selectedFile = fileChooser.showOpenDialog(null);

                if (selectedFile != null) {
                    jogoObservavel.ler(selectedFile);
                    jogoObservavel.continuarJogo();
                } else {
                    System.err.println("Leitura cancelada ");
                }
                avisoStage.close();
            });


            voltar.setOnAction((ActionEvent x)->{
                avisoStage.close();
            });
        }

    }

    class GravarObjMenuBarListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            FileChooser fileChooser = new FileChooser();
            if(jogoObservavel.getSituacao() == Situacao.AguardaDecisaoFinal)
                fileChooser.setInitialDirectory(new File("QuatroEmLinha/jogosAcabados"));
            else
                fileChooser.setInitialDirectory(new File("QuatroEmLinha/jogosinacabados"));

            File selectedFile = fileChooser.showSaveDialog(null);
            if (selectedFile != null) {
                jogoObservavel.gravar(selectedFile);


            } else {
                System.err.println("Gravacao cancelada ");
            }
        }
    }



    class AjudaListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            Alert dialogoResultado = new Alert(Alert.AlertType.INFORMATION);
            dialogoResultado.setHeaderText("Ajuda");
            dialogoResultado.setContentText(" -Deve escolher dentro das 7 colunas do tabuleiro;\n" +
                                            " -Apos 4 jogadas vai poder escolher se quer jogar um Mini Jogo;\n" +
                                            " -Se ganhar o jogo ganha uma peca especial (+1 jogada);\n" +
                                            " -Ganha o jogo quando completar uma linha de 4 pecas (vertical,horizontal).\n");
            dialogoResultado.showAndWait();
        }
    }

    class DadosJogador implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent actionEvent) {
            Alert dialogoResultado = new Alert(Alert.AlertType.INFORMATION);
            dialogoResultado.setHeaderText("Meus dados");

            dialogoResultado.setContentText(jogoObservavel.getNomeJogadorAtual() +
                                            "\nPeças Especiais: " + jogoObservavel.getnPecaEspecialJA() +
                                            "\nCreditos: " + jogoObservavel.getCreditosJA() +
                                            "\nJogadas para MiniJogo: " + jogoObservavel.getnJogadasJA() +
                                            "\nNº MiniJogos: " +  jogoObservavel.getMiniJogoJA());
            dialogoResultado.showAndWait();
        }
    }

    private void update() {
        dadosJogadorMI.setOnAction(new DadosJogador());
        novoJogoMI.setDisable(!(jogoObservavel.getSituacao() ==  Situacao.AguardaJogada));
        dadosJogadorMI.setDisable(!(jogoObservavel.getSituacao() ==  Situacao.AguardaJogada));
        gravarObjMI.setDisable(jogoObservavel.getSituacao() == Situacao.Inicio);
    }

    private class Sair implements EventHandler<ActionEvent> {
        Button guardar, sair, voltar;
        Stage avisoStage;
        @Override
        public void handle(ActionEvent actionEvent) {
            BorderPane aviso = new BorderPane();
            Text avisoTexto;
            avisoStage = new Stage();

            Scene scene1 = new Scene(aviso, 250, 150);
            avisoStage.setScene(scene1);
            avisoStage.setTitle("Sair");
            avisoStage.setMinWidth(100);
            avisoStage.setMinHeight(100);
            avisoStage.show();


            voltar = new Button("Voltar");
            guardar = new Button("Guardar");
            sair = new Button("Sair");

            HBox buttonBox = new HBox(10);
            buttonBox.getChildren().addAll(guardar, sair);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.setPadding(new Insets(10,10,10,10));
            aviso.setTop(buttonBox);

            if(jogoObservavel.getSituacao() == Situacao.AguardaJogada){
                avisoTexto = new Text("Quer guardar Jogo Inacabado");
                guardar.setOnAction((ActionEvent l) -> jogoObservavel.guarda());
                avisoStage.close();
                Stage janela2 = (Stage) avisoStage.getScene().getWindow();
                fireEvent( new WindowEvent(janela2, WindowEvent.WINDOW_CLOSE_REQUEST));

            }
            else {
                avisoTexto = new Text("Quer sair sem guardar jogo");
                guardar.setOnAction(new GravarObjMenuBarListener());
            }

            VBox center = new VBox(10);
            center.getChildren().addAll(avisoTexto,buttonBox);
            center.setAlignment(Pos.CENTER);
            aviso.setCenter(center);


            voltar.setOnAction((ActionEvent x)->{
                avisoStage.close();
            });


            sair.setOnAction((ActionEvent x)->{
                avisoStage.close();
                Stage janela2 = (Stage) avisoStage.getScene().getWindow();
                fireEvent( new WindowEvent(janela2, WindowEvent.WINDOW_CLOSE_REQUEST));
            });
        }
    }
}

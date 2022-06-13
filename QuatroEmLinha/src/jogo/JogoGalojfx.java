package jogo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jogo.logica.JogoObservavel;
import jogo.logica.MaqEstados;
import jogo.ui.gui.PaneOrganizer;

public class JogoGalojfx extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        MaqEstados maqEstados = new MaqEstados();
        JogoObservavel jogoObservavel = new JogoObservavel(maqEstados);

        System.out.println("start");

        PaneOrganizer root = new PaneOrganizer(jogoObservavel);

        Scene scene = new Scene(root, 630, 630);
        stage.setScene(scene);
        stage.setTitle("Quatro em Linha");
        stage.setMinWidth(630);stage.setMinHeight(630);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

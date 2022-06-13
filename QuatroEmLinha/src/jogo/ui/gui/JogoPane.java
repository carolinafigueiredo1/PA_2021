package jogo.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import jogo.logica.JogoObservavel;
import jogo.logica.estados.AguardaJogada;
import jogo.logica.estados.Inicio;
import jogo.ui.gui.estados.*;

import static jogo.ui.gui.ConstantesGUI.DIM_X_PRINCIPAL;
import static jogo.ui.gui.ConstantesGUI.DIM_Y_PRINCIPAL;

public class JogoPane extends BorderPane {
    private JogoObservavel jogoObservavel;
    public JogoPane(JogoObservavel jogoObservavel) {
        this.jogoObservavel = jogoObservavel;
        createLayoutRegisterListeners();
        registerObserver();
        update();
    }

    private void createLayoutRegisterListeners() {
        AguardaJogadaPane aguardaJogadaPane = new AguardaJogadaPane(jogoObservavel);
        AguardaMiniJogoPane aguardaMiniJogoPane = new AguardaMiniJogoPane(jogoObservavel);
        AguardaDecisaoFinalPane aguardaDecisaoFinalPane = new AguardaDecisaoFinalPane(jogoObservavel);
        InicioPane inicioPane = new InicioPane(jogoObservavel);

        StackPane centro = new StackPane(inicioPane,aguardaJogadaPane,aguardaMiniJogoPane,aguardaDecisaoFinalPane);
        centro.setPrefSize(DIM_X_PRINCIPAL,DIM_Y_PRINCIPAL);
        centro.setAlignment(Pos.CENTER);

        this.setCenter(centro);
        setPrefSize(800,800);
    }

    private void registerObserver() {

    }

    private void update() {

    }

}

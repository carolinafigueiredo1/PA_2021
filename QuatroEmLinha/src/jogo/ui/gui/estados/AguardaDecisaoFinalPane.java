package jogo.ui.gui.estados;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jogo.logica.JogoObservavel;
import jogo.ui.gui.PrincipalPane;
import jogo.ui.gui.TabuleiroPane;

import static jogo.logica.Constantes.PROPRIEDADE_ESTADOS;
import static jogo.logica.Situacao.AguardaDecisaoFinal;

public class AguardaDecisaoFinalPane extends BorderPane {
    private JogoObservavel jogoObservavel;
    private VBox box;
    private Text vencedor;
    private HBox tabuleiro, bottomBox;
    private Button replay, sair, novoJogo;

    public AguardaDecisaoFinalPane(JogoObservavel jogoObservavel) {
        this.jogoObservavel = jogoObservavel;
        vencedor = new Text();
        jogoObservavel.guardarJogo();
        jogoObservavel.guardarJogosFicheiro();
        createLayoutRegisterListeners();
        registerObserver();
        update();
    }

    private void createLayoutRegisterListeners() {
        TabuleiroPane tabuleiroPane = new TabuleiroPane(jogoObservavel);
        novoJogo = new Button("Novo Jogo");
        replay = new Button("Repetir Jogos");
        sair = new Button("Sair");

        tabuleiro = new HBox(10);
        tabuleiro.getChildren().add(tabuleiroPane);
        tabuleiro.setAlignment(Pos.CENTER);

        bottomBox = new HBox(10);
        bottomBox.getChildren().addAll(novoJogo,replay,sair);
        bottomBox.setAlignment(Pos.CENTER);
        setBottom(bottomBox);

        novoJogo.setOnAction(e->jogoObservavel.reinicia());
        replay.setOnAction(new Replay());
        replay.setDisable(true);
        sair.setOnAction((ActionEvent e)-> {
            Stage janela2 = (Stage) this.getScene().getWindow();
            fireEvent( new WindowEvent(janela2, WindowEvent.WINDOW_CLOSE_REQUEST));
        });

        box = new VBox(10);
        box.getChildren().addAll(tabuleiro,vencedor,bottomBox);
        box.setAlignment(Pos.CENTER);
        setCenter(box);

    }

    private void registerObserver() {
        jogoObservavel.addPropertyChangeListener(PROPRIEDADE_ESTADOS, evt -> {
            update();

        });
    }

    private void update() {
        tabuleiro.getChildren().clear();
        TabuleiroPane tabuleiroPane = new TabuleiroPane(jogoObservavel);
        tabuleiro.getChildren().add(tabuleiroPane);
        box.getChildren().clear();
        //if(!jogoObservavel.tabuleiroCheio())
            vencedor = new Text("Parabéns " + jogoObservavel.getNomeJogadorAtual() + "!");
        //else
        //        vencedor = new Text("Empate");
        vencedor.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));

        box.getChildren().addAll(tabuleiro,vencedor,bottomBox);
        this.setVisible(jogoObservavel.getSituacao() == AguardaDecisaoFinal);

    }

    private class Replay implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {

        }
    }
}

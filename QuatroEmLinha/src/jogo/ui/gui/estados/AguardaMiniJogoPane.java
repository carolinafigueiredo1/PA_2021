package jogo.ui.gui.estados;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import jogo.logica.JogoObservavel;
import javafx.scene.control.Button;
import jogo.utilsUI.Utils;

import static jogo.logica.Constantes.PROPRIEDADE_ESTADOS;
import static jogo.logica.Constantes.PROPRIEDADE_MINI_JOGO;
import static jogo.logica.Situacao.AguardaMiniJogo;

public class AguardaMiniJogoPane extends BorderPane {
    private JogoObservavel jogoObservavel;
    private Text pergunta;
    private TextField resposta;
    private Button inserirResposta;
    private boolean terminou;
    private VBox box;

    public AguardaMiniJogoPane(JogoObservavel jogoObservavel) {
        this.jogoObservavel = jogoObservavel;
        pergunta = new Text("Mini Jogo");
        resposta = new TextField(" ");
        inserirResposta = new Button("Responder");

        createLayoutRegisterListeners();
        registerObserver();
        update();
    }

    private void createLayoutRegisterListeners() {
        if(jogoObservavel.getIntJogadorAtual() == 1)
            setBackground(new Background(
                new BackgroundFill(Color.LIGHTYELLOW, null, null)));
        else
            setBackground(new Background(
                    new BackgroundFill(Color.INDIANRED, null, null)));
    }

    private void registerObserver() {
        jogoObservavel.addPropertyChangeListener(PROPRIEDADE_ESTADOS, evt -> {
            update();
        });
        jogoObservavel.addPropertyChangeListener(PROPRIEDADE_MINI_JOGO, evt -> {
            update();
        });
    }

    private void update() {
        this.setVisible(jogoObservavel.getSituacao() == AguardaMiniJogo);
        box = new VBox(10);
        box.setPadding(new Insets(10, 10, 10, 10));

        box.getChildren().clear();


        if(jogoObservavel.getSituacao() == AguardaMiniJogo) {
            String str = jogoObservavel.getPerguntaMJ();

            str = str.replace("[", " ");
            str = str.replace("]", " ");
            pergunta.setText(str);
            pergunta.setFont(Font.font("Helvetica", FontWeight.BOLD, 50));

            box.getChildren().add(pergunta);

            resposta = new TextField(" ");
            resposta.setMaxWidth(140);
            resposta.setFont(Font.font("Helvetica", 20));
            box.getChildren().add(resposta);
            box.getChildren().add(inserirResposta);

            if(jogoObservavel.getTipoMJ() == 1) {
                inserirResposta.setOnAction(new InsereIntResposta());
            }
            else
                inserirResposta.setOnAction(a -> {
                    terminou = jogoObservavel.terminouMJ(resposta.getText().trim());
                    if(!terminou)
                        jogoObservavel.miniJogoTerminou();
                    else
                        jogoObservavel.jogaMJ();
                });



            box.setAlignment(Pos.CENTER);
            setCenter(box);
        }

    }

    private class InsereIntResposta implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            int n = 0;
            try {
                n = getValue();

            } catch (NumberFormatException ex){
                return;
            }
            //System.out.println(jogoObservavel.getPerguntaMJ());
            terminou = jogoObservavel.terminouMJ(n);
            if(!terminou)
                jogoObservavel.miniJogoTerminou();
            else
                jogoObservavel.jogaMJ();

        }
    }


    private int getValue() throws NumberFormatException {

        String s1 = (resposta.getText()).trim();

        if (s1.length() < 1){
            throw new NumberFormatException();
        }
        int value = Integer.parseInt(s1);
        return value;

    }
}

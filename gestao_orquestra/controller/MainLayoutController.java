package gestao_orquestra.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;

public class MainLayoutController {

    @FXML
    private StackPane conteudoCentral;

    @FXML
    public void initialize() {
        // Assim que o layout principal abre, carrega a Tela de Boas-Vindas no centro
        carregarTela("BoasVindasView.fxml", "fundo_inicio.jpg");
    }

    private void carregarTela(String arquivoFxml, String imagemFundo) {
        try {
            URL caminhoTela = getClass().getResource("/gestao_orquestra/view/" + arquivoFxml);

            if (caminhoTela == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Tela não encontrada");
                alert.setHeaderText(null);
                alert.setContentText("O arquivo /gestao_orquestra/view/" + arquivoFxml + " não foi encontrado.\n" +
                        "Se você já criou o arquivo, clique em Build > Rebuild Project.");
                alert.showAndWait();
                return;
            }

            Parent novaTela = FXMLLoader.load(caminhoTela);
            conteudoCentral.getChildren().clear();

            // 1. Adiciona a imagem desfocada e a película de leitura no fundo
            aplicarFundo(imagemFundo, false);

            // 2. Adiciona a tela do módulo por cima de tudo
            conteudoCentral.getChildren().add(novaTela);

        } catch (Exception e) {
            e.printStackTrace();

            Throwable causaRaiz = e;
            while (causaRaiz.getCause() != null) {
                causaRaiz = causaRaiz.getCause();
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao Carregar Tela");
            alert.setHeaderText("Falha ao abrir " + arquivoFxml);
            alert.setContentText(causaRaiz.toString());
            alert.showAndWait();
        }
    }

    private void aplicarFundo(String nomeImagem, boolean manterTextoBoasVindas) {
        if (nomeImagem == null) return;

        URL urlImagem = getClass().getResource("/gestao_orquestra/view/" + nomeImagem);
        if (urlImagem != null) {
            ImageView fundo = new ImageView(new Image(urlImagem.toExternalForm()));
            fundo.setManaged(false);
            fundo.setPreserveRatio(false);
            fundo.fitWidthProperty().bind(conteudoCentral.widthProperty());
            fundo.fitHeightProperty().bind(conteudoCentral.heightProperty());
            fundo.setEffect(new GaussianBlur(12)); // Nível de desfoque

            // Película branca semitransparente para manter os textos bem legíveis
            Region peliculaLeitura = new Region();
            peliculaLeitura.setStyle("-fx-background-color: rgba(248, 250, 252, 0.78);");

            if (manterTextoBoasVindas) {
                conteudoCentral.getChildren().add(0, fundo);
                conteudoCentral.getChildren().add(1, peliculaLeitura);
            } else {
                conteudoCentral.getChildren().addAll(fundo, peliculaLeitura);
            }
        }
    }

    @FXML
    private void abrirTelaMusicos() {
        carregarTela("MusicosView.fxml", "fundo_musicos.jpg");
    }

    @FXML
    private void abrirTelaAcervo() {
        carregarTela("InstrumentosView.fxml", "fundo_instrumentos.jpg");
    }

    @FXML
    private void abrirTelaEmprestimos() {
        carregarTela("EmprestimosView.fxml", "fundo_emprestimos.jpg");
    }

    @FXML
    private void abrirTelaConcertos() {
        carregarTela("ConcertosView.fxml", "fundo_concertos.jpg");
    }

    @FXML
    private void abrirTelaRelatorios() {
        carregarTela("RelatoriosView.fxml", "fundo_relatorios.jpg");
    }

    @FXML
    private void sairDaConta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestao_orquestra/view/TelaLogin.fxml"));
            Parent loginRoot = loader.load();

            Stage stageAtual = (Stage) conteudoCentral.getScene().getWindow();
            stageAtual.setScene(new Scene(loginRoot, 600, 400));
            stageAtual.setTitle("Sistema de Orquestra - Login");
            stageAtual.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro ao Sair");
            alert.setHeaderText(null);
            alert.setContentText("Não foi possível voltar para a tela de login: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
package gestao_orquestra.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;

public class MainLayoutController {

    @FXML
    private StackPane conteudoCentral;

    private void carregarTela(String arquivoFxml) {
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

    @FXML
    private void abrirTelaMusicos() {
        carregarTela("MusicosView.fxml");
    }

    @FXML
    private void abrirTelaAcervo() {
        carregarTela("InstrumentosView.fxml");
    }

    @FXML
    private void abrirTelaEmprestimos() {
        carregarTela("EmprestimosView.fxml");
    }

    @FXML
    private void abrirTelaConcertos() {
        carregarTela("ConcertosView.fxml");
    }

    @FXML
    private void abrirTelaRelatorios() {
        carregarTela("RelatoriosView.fxml");
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
package gestao_orquestra.controller;

import gestao_orquestra.App;
import gestao_orquestra.exceptions.SenhaIncorretaException;
import gestao_orquestra.exceptions.UsuarioNaoEncontradoException;
import gestao_orquestra.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaLoginController {

    @FXML
    private TextField campoLogin;

    @FXML
    private PasswordField campoSenha;

    @FXML
    private void fazerLogin() {
        String login = campoLogin.getText();
        String senha = campoSenha.getText();

        if (login.isEmpty() || senha.isEmpty()) {
            exibirAlerta(Alert.AlertType.WARNING, "Campos Incompletos", "Preencha o login e a senha para continuar.");
            return;
        }

        try {
            Usuario logado = App.getFachada().fazerLogin(login, senha);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestao_orquestra/view/MainLayout.fxml"));
            Parent mainRoot = loader.load();

            Stage stageAtual = (Stage) campoLogin.getScene().getWindow();
            stageAtual.setScene(new Scene(mainRoot, 900, 600));
            stageAtual.setTitle("Sistema de Gestão de Orquestra - Painel Principal");
            stageAtual.setResizable(true);

        } catch (UsuarioNaoEncontradoException | SenhaIncorretaException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro de Autenticação", e.getMessage());
        } catch (Exception e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro Inesperado", e.getMessage());
        }
    }

    @FXML
    private void abrirTelaCadastro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestao_orquestra/view/TelaCadastroUsuario.fxml"));
            Parent cadastroRoot = loader.load();

            Stage stageAtual = (Stage) campoLogin.getScene().getWindow();
            stageAtual.setScene(new Scene(cadastroRoot, 600, 400));
            stageAtual.setTitle("Sistema de Orquestra - Cadastro de Usuário");
        } catch (Exception e) {
            e.printStackTrace();
            exibirAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível abrir a tela de cadastro: " + e.getMessage());
        }
    }

    private void exibirAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
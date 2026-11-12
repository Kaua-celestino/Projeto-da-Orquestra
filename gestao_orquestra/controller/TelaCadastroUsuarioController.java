package gestao_orquestra.controller;

import gestao_orquestra.App;
import gestao_orquestra.model.PerfilAcesso;
import gestao_orquestra.model.Usuario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaCadastroUsuarioController {

    @FXML private TextField campoNovoLogin;
    @FXML private PasswordField campoNovaSenha;
    @FXML private PasswordField campoConfirmarSenha;
    @FXML private ComboBox<PerfilAcesso> comboPerfil;

    @FXML
    public void initialize() {
        comboPerfil.setItems(FXCollections.observableArrayList(PerfilAcesso.values()));
    }

    @FXML
    private void cadastrarUsuario() {
        String login = campoNovoLogin.getText().trim();
        String senha = campoNovaSenha.getText();
        String confirmar = campoConfirmarSenha.getText();
        PerfilAcesso perfil = comboPerfil.getValue();

        if (login.isEmpty() || senha.isEmpty() || confirmar.isEmpty() || perfil == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Campos Incompletos", "Preencha todos os campos e selecione o perfil.");
            return;
        }

        if (!senha.equals(confirmar)) {
            exibirAlerta(Alert.AlertType.WARNING, "Senhas Diferentes", "A senha e a confirmação não coincidem.");
            return;
        }

        try {
            Usuario novoUsuario = new Usuario(login, senha, perfil);
            App.getFachada().cadastrarUsuario(novoUsuario);

            exibirAlerta(Alert.AlertType.INFORMATION, "Cadastro Concluído", "Usuário '" + login + "' cadastrado com sucesso!");
            voltarLogin();
        } catch (Exception e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro no Cadastro", e.getMessage());
        }
    }

    @FXML
    private void voltarLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestao_orquestra/view/TelaLogin.fxml"));
            Parent loginRoot = loader.load();

            Stage stageAtual = (Stage) campoNovoLogin.getScene().getWindow();
            stageAtual.setScene(new Scene(loginRoot, 600, 400));
            stageAtual.setTitle("Sistema de Orquestra - Login");
        } catch (Exception e) {
            e.printStackTrace();
            exibirAlerta(Alert.AlertType.ERROR, "Erro", "Falha ao voltar para o login: " + e.getMessage());
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
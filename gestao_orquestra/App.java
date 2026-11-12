package gestao_orquestra;

import gestao_orquestra.negocios.OrquestraFachada;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    // Fachada global estática para que todas as telas tenham acesso aos mesmos dados
    private static OrquestraFachada fachada = new OrquestraFachada();

    public static OrquestraFachada getFachada() {
        return fachada;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // 1. Carrega os dados do CSV antes da tela abrir
        fachada.carregarBackupGeral();

        // 2. GARANTIA DE ACESSO: Se não houver usuários no CSV, cria o admin padrão
        if (fachada.listarUsuarios().length == 0) {
            System.out.println("-> Base de usuários vazia. Criando 'admin' padrão...");
            fachada.cadastrarUsuario(new gestao_orquestra.model.Usuario("admin", "1234", gestao_orquestra.model.PerfilAcesso.MAESTRO));
        }

        // 3. Carrega o ficheiro FXML desenhado da tela de Login
        Parent root = FXMLLoader.load(getClass().getResource("/gestao_orquestra/view/TelaLogin.fxml"));

        // 4. Configura a janela inicial
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Sistema de Orquestra - Login");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // Impede de maximizar e desconfigurar o layout

        // 5. Salva o CSV automaticamente quando o utilizador clica no "X" para fechar
        primaryStage.setOnCloseRequest(event -> {
            try {
                System.out.println("-> A guardar os dados no CSV antes de fechar a janela...");
                fachada.salvarBackupGeral();
            } catch (Exception e) {
                System.out.println("-> Erro ao guardar backup: " + e.getMessage());
            }
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Comando que liga o motor do JavaFX
    }
}
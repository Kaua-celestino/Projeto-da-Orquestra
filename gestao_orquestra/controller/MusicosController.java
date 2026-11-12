package gestao_orquestra.controller;

import gestao_orquestra.App;
import gestao_orquestra.model.Musico;
import gestao_orquestra.model.Naipe;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MusicosController {

    @FXML private TextField campoCpf;
    @FXML private TextField campoNome;
    @FXML private TextField campoEmail;
    @FXML private ComboBox<Naipe> comboNaipe;
    @FXML private TextField campoNivel;

    @FXML private TableView<Musico> tabelaMusicos;
    @FXML private TableColumn<Musico, String> colunaCpf;
    @FXML private TableColumn<Musico, String> colunaNome;
    @FXML private TableColumn<Musico, String> colunaEmail;
    @FXML private TableColumn<Musico, String> colunaNaipe;
    @FXML private TableColumn<Musico, Integer> colunaNivel;

    private ObservableList<Musico> observableListaMusicos;

    @FXML
    public void initialize() {
        // Carrega automaticamente os valores do seu enum Naipe no menu suspenso
        comboNaipe.setItems(FXCollections.observableArrayList(Naipe.values()));

        // Configura cada coluna para buscar os dados corretos do objeto Musico
        colunaCpf.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCpf()));
        colunaNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        colunaEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        // Transforma o Enum Naipe em String para aparecer na tabela
        colunaNaipe.setCellValueFactory(cellData -> {
            Naipe naipe = cellData.getValue().getNaipePrincipal();
            return new SimpleStringProperty(naipe != null ? naipe.name() : "");
        });

        // Para números (int), usamos o SimpleObjectProperty
        colunaNivel.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNivelTecnico()));

        atualizarTabela();
    }

    @FXML
    private void cadastrarMusico() {
        String cpf = campoCpf.getText();
        String nome = campoNome.getText();
        String email = campoEmail.getText();
        Naipe naipe = comboNaipe.getValue();
        String nivelStr = campoNivel.getText();

        // 1. Valida se preencheu tudo
        if (cpf.isEmpty() || nome.isEmpty() || email.isEmpty() || naipe == null || nivelStr.isEmpty()) {
            exibirAlerta(Alert.AlertType.WARNING, "Campos Vazios", "Preencha todos os dados e escolha o Naipe.");
            return;
        }

        // 2. Tenta converter o nível técnico para número
        int nivel;
        try {
            nivel = Integer.parseInt(nivelStr);
        } catch (NumberFormatException e) {
            exibirAlerta(Alert.AlertType.WARNING, "Valor Inválido", "O nível técnico deve ser um número.");
            return;
        }

        // 3. Cadastra usando o construtor correto
        try {
            Musico novoMusico = new Musico(cpf, nome, email, naipe, nivel);

            App.getFachada().cadastrarMusico(novoMusico);
            exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Músico cadastrado com sucesso!");

            // Limpa o formulário
            campoCpf.clear();
            campoNome.clear();
            campoEmail.clear();
            comboNaipe.getSelectionModel().clearSelection();
            campoNivel.clear();

            atualizarTabela();

        } catch (Exception e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro no Cadastro", e.getMessage());
        }
    }

    @FXML
    private void removerMusico() {
        Musico musicoSelecionado = tabelaMusicos.getSelectionModel().getSelectedItem();

        if (musicoSelecionado == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Nenhuma Seleção", "Clique num músico da tabela primeiro.");
            return;
        }

        try {
            App.getFachada().removerMusico(musicoSelecionado.getCpf());
            exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Músico removido.");
            atualizarTabela();
        } catch (Exception e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro ao Remover", e.getMessage());
        }
    }

    private void atualizarTabela() {
        Musico[] arrayDeMusicos = App.getFachada().listarMusicos();
        observableListaMusicos = FXCollections.observableArrayList(arrayDeMusicos);
        tabelaMusicos.setItems(observableListaMusicos);
    }

    private void exibirAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
package gestao_orquestra.controller;

import gestao_orquestra.App;
import gestao_orquestra.model.Concerto;
import gestao_orquestra.model.Musico;
import gestao_orquestra.model.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConcertosController {

    @FXML private TextField campoEspetaculo;
    @FXML private TextField campoLocal;
    @FXML private DatePicker dataConcertoPicker;
    @FXML private ComboBox<Usuario> comboMaestro;

    @FXML private TableView<Concerto> tabelaConcertos;
    @FXML private TableColumn<Concerto, String> colunaEspetaculo;
    @FXML private TableColumn<Concerto, String> colunaData;
    @FXML private TableColumn<Concerto, String> colunaLocal;
    @FXML private TableColumn<Concerto, String> colunaMaestro;
    @FXML private TableColumn<Concerto, String> colunaEscalados;

    @FXML private Label labelConcertoSelecionado;
    @FXML private ComboBox<Musico> comboMusicoEscalar;
    @FXML private ComboBox<Musico> comboMusicoRemover;

    private final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    public void initialize() {
        // Carrega usuários (maestros) e músicos cadastrados
        comboMaestro.setItems(FXCollections.observableArrayList(App.getFachada().listarUsuarios()));
        comboMusicoEscalar.setItems(FXCollections.observableArrayList(App.getFachada().listarMusicos()));

        comboMaestro.setConverter(new StringConverter<>() {
            @Override
            public String toString(Usuario u) {
                return (u != null) ? u.getLogin() : "";
            }
            @Override
            public Usuario fromString(String s) { return null; }
        });

        StringConverter<Musico> conversorMusico = new StringConverter<>() {
            @Override
            public String toString(Musico m) {
                return (m != null) ? m.getNome() + " (" + m.getNaipePrincipal() + ")" : "";
            }
            @Override
            public Musico fromString(String s) { return null; }
        };
        comboMusicoEscalar.setConverter(conversorMusico);
        comboMusicoRemover.setConverter(conversorMusico);

        dataConcertoPicker.setValue(LocalDate.now());

        // Configura colunas da tabela
        colunaEspetaculo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNomeEspetaculo()));
        colunaData.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getData() != null ? cell.getValue().getData().format(formatoData) : ""
        ));
        colunaLocal.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getLocal()));
        colunaMaestro.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getMaestro() != null ? cell.getValue().getMaestro().getLogin() : "-"
        ));
        colunaEscalados.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getIndiceMusicos() + " músico(s)"
        ));

        // Atualiza a lista de músicos escalados sempre que o usuário clica em um concerto na tabela
        tabelaConcertos.getSelectionModel().selectedItemProperty().addListener((obs, antigo, selecionado) -> {
            atualizarPainelEscalacao(selecionado);
        });

        atualizarTabela();
    }

    private void atualizarPainelEscalacao(Concerto concerto) {
        if (concerto == null) {
            labelConcertoSelecionado.setText("Selecione um concerto na tabela acima para gerenciar músicos e validar:");
            comboMusicoRemover.getItems().clear();
            return;
        }

        labelConcertoSelecionado.setText("Gerenciando: " + concerto.getNomeEspetaculo() + " (" + concerto.getIndiceMusicos() + " escalado(s))");

        List<Musico> ativos = new ArrayList<>();
        Musico[] escalados = concerto.getMusicosEscalados();
        for (int i = 0; i < concerto.getIndiceMusicos(); i++) {
            if (escalados[i] != null) {
                ativos.add(escalados[i]);
            }
        }
        comboMusicoRemover.setItems(FXCollections.observableArrayList(ativos));
    }

    @FXML
    private void cadastrarConcerto() {
        String espetaculo = campoEspetaculo.getText().trim();
        String local = campoLocal.getText().trim();
        LocalDate data = dataConcertoPicker.getValue();
        Usuario maestro = comboMaestro.getValue();

        if (espetaculo.isEmpty() || local.isEmpty() || data == null || maestro == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Campos Incompletos", "Preencha espetáculo, local, data e maestro.");
            return;
        }

        try {
            Concerto novo = new Concerto(espetaculo, data, local, maestro);
            App.getFachada().cadastrarConcerto(novo);

            exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Concerto cadastrado com sucesso!");
            campoEspetaculo.clear();
            campoLocal.clear();
            atualizarTabela();
        } catch (Exception e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro no Cadastro", e.getMessage());
        }
    }

    @FXML
    private void escalarMusico() {
        Concerto concerto = tabelaConcertos.getSelectionModel().getSelectedItem();
        Musico musico = comboMusicoEscalar.getValue();

        if (concerto == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Nenhuma Seleção", "Selecione um concerto na tabela primeiro.");
            return;
        }
        if (musico == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Músico não selecionado", "Escolha um músico para escalar.");
            return;
        }

        // Evita duplicar o mesmo músico no mesmo concerto
        for (int i = 0; i < concerto.getIndiceMusicos(); i++) {
            if (concerto.getMusicosEscalados()[i].getCpf().equals(musico.getCpf())) {
                exibirAlerta(Alert.AlertType.WARNING, "Já Escalado", "Este músico já está escalado neste concerto.");
                return;
            }
        }

        try {
            App.getFachada().escalarMusicoNoConcerto(concerto.getNomeEspetaculo(), musico);
            comboMusicoEscalar.getSelectionModel().clearSelection();
            atualizarTabelaMantendoSelecao(concerto.getNomeEspetaculo());
        } catch (Exception e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro ao Escalar", e.getMessage());
        }
    }

    @FXML
    private void removerMusicoEscalado() {
        Concerto concerto = tabelaConcertos.getSelectionModel().getSelectedItem();
        Musico musico = comboMusicoRemover.getValue();

        if (concerto == null || musico == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Seleção Incompleta", "Selecione o concerto na tabela e o músico escalado que deseja remover.");
            return;
        }

        try {
            App.getFachada().removerMusicoDoConcerto(concerto.getNomeEspetaculo(), musico.getCpf());
            atualizarTabelaMantendoSelecao(concerto.getNomeEspetaculo());
        } catch (Exception e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro ao Remover Músico", e.getMessage());
        }
    }

    @FXML
    private void aprovarConcerto() {
        Concerto concerto = tabelaConcertos.getSelectionModel().getSelectedItem();

        if (concerto == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Nenhuma Seleção", "Selecione um concerto na tabela para validar.");
            return;
        }

        try {
            App.getFachada().aprovarConcerto(concerto.getNomeEspetaculo());
            exibirAlerta(Alert.AlertType.INFORMATION, "Concerto Viável!",
                    "O concerto '" + concerto.getNomeEspetaculo() + "' possui músicos de Cordas, Sopros e Percussão e está aprovado!");
        } catch (Exception e) {
            exibirAlerta(Alert.AlertType.ERROR, "Concerto Inviável", e.getMessage());
        }
    }

    @FXML
    private void removerConcerto() {
        Concerto concerto = tabelaConcertos.getSelectionModel().getSelectedItem();

        if (concerto == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Nenhuma Seleção", "Selecione um concerto na tabela para remover.");
            return;
        }

        try {
            App.getFachada().removerConcerto(concerto.getNomeEspetaculo());
            exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Concerto removido.");
            atualizarTabela();
            atualizarPainelEscalacao(null);
        } catch (Exception e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro ao Remover", e.getMessage());
        }
    }

    private void atualizarTabela() {
        Concerto[] lista = App.getFachada().listarConcertos();
        tabelaConcertos.setItems(FXCollections.observableArrayList(lista));
    }

    private void atualizarTabelaMantendoSelecao(String nomeEspetaculo) {
        atualizarTabela();
        for (Concerto c : tabelaConcertos.getItems()) {
            if (c.getNomeEspetaculo().equalsIgnoreCase(nomeEspetaculo)) {
                tabelaConcertos.getSelectionModel().select(c);
                atualizarPainelEscalacao(c);
                break;
            }
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
package gestao_orquestra.controller;

import gestao_orquestra.App;
import gestao_orquestra.model.Emprestimo;
import gestao_orquestra.model.Instrumento;
import gestao_orquestra.model.Musico;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmprestimosController {

    @FXML private ComboBox<Musico> comboMusico;
    @FXML private ComboBox<Instrumento> comboInstrumento;
    @FXML private DatePicker dataRetiradaPicker;
    @FXML private DatePicker dataPrevistaPicker;

    @FXML private TableView<Emprestimo> tabelaEmprestimos;
    @FXML private TableColumn<Emprestimo, String> colunaMusico;
    @FXML private TableColumn<Emprestimo, String> colunaInstrumento;
    @FXML private TableColumn<Emprestimo, String> colunaRetirada;
    @FXML private TableColumn<Emprestimo, String> colunaPrevista;
    @FXML private TableColumn<Emprestimo, String> colunaDevolucao;
    @FXML private TableColumn<Emprestimo, String> colunaStatus;

    private final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @FXML
    public void initialize() {
        // Preenche os ComboBoxes com músicos e instrumentos já cadastrados
        comboMusico.setItems(FXCollections.observableArrayList(App.getFachada().listarMusicos()));
        comboInstrumento.setItems(FXCollections.observableArrayList(App.getFachada().listarInstrumentos()));

        // Formata como o Músico aparece no ComboBox
        comboMusico.setConverter(new StringConverter<>() {
            @Override
            public String toString(Musico m) {
                return (m != null) ? m.getNome() + " (" + m.getCpf() + ")" : "";
            }
            @Override
            public Musico fromString(String s) { return null; }
        });

        // Formata como o Instrumento aparece no ComboBox
        comboInstrumento.setConverter(new StringConverter<>() {
            @Override
            public String toString(Instrumento i) {
                return (i != null) ? i.getNome() + " [" + i.getCodigoTombamento() + "]" : "";
            }
            @Override
            public Instrumento fromString(String s) { return null; }
        });

        // Sugere hoje como data de retirada e 7 dias depois como previsão
        dataRetiradaPicker.setValue(LocalDate.now());
        dataPrevistaPicker.setValue(LocalDate.now().plusDays(7));

        // Configura as colunas da tabela
        colunaMusico.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMusico().getNome()));
        colunaInstrumento.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getInstrumento().getNome() + " (" + cell.getValue().getInstrumento().getCodigoTombamento() + ")"
        ));
        colunaRetirada.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getDataRetirada().format(formatoData)
        ));
        colunaPrevista.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getDataDevolucaoPrevista().format(formatoData)
        ));
        colunaDevolucao.setCellValueFactory(cell -> {
            LocalDate dev = cell.getValue().getDataDevolucaoFeita();
            return new SimpleStringProperty(dev != null ? dev.format(formatoData) : "-");
        });
        colunaStatus.setCellValueFactory(cell -> {
            Emprestimo emp = cell.getValue();
            if (emp.getDataDevolucaoFeita() != null) {
                return new SimpleStringProperty("Devolvido");
            } else if (emp.getDataDevolucaoPrevista().isBefore(LocalDate.now())) {
                return new SimpleStringProperty("Atrasado");
            }
            return new SimpleStringProperty("Ativo");
        });

        atualizarTabela();
    }

    @FXML
    private void registrarEmprestimo() {
        Musico musico = comboMusico.getValue();
        Instrumento instrumento = comboInstrumento.getValue();
        LocalDate retirada = dataRetiradaPicker.getValue();
        LocalDate prevista = dataPrevistaPicker.getValue();

        if (musico == null || instrumento == null || retirada == null || prevista == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Campos Incompletos", "Selecione o músico, o instrumento e as datas.");
            return;
        }

        if (prevista.isBefore(retirada)) {
            exibirAlerta(Alert.AlertType.WARNING, "Data Inválida", "A data prevista de devolução não pode ser anterior à retirada.");
            return;
        }

        try {
            Emprestimo novoEmprestimo = new Emprestimo(musico, instrumento, retirada, prevista);
            App.getFachada().cadastroEmprestimo(novoEmprestimo);

            exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Empréstimo registrado com sucesso!");
            comboMusico.getSelectionModel().clearSelection();
            comboInstrumento.getSelectionModel().clearSelection();
            atualizarTabela();
        } catch (Exception e) {
            exibirAlerta(Alert.AlertType.ERROR, "Regra Violada", e.getMessage());
        }
    }

    @FXML
    private void devolverEmprestimo() {
        Emprestimo selecionado = tabelaEmprestimos.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Nenhuma Seleção", "Selecione um empréstimo na tabela primeiro.");
            return;
        }

        if (selecionado.getDataDevolucaoFeita() != null) {
            exibirAlerta(Alert.AlertType.INFORMATION, "Aviso", "Este instrumento já foi devolvido.");
            return;
        }

        try {
            App.getFachada().retornarEmprestimo(
                    selecionado.getMusico().getCpf(),
                    selecionado.getInstrumento().getCodigoTombamento()
            );
            exibirAlerta(Alert.AlertType.INFORMATION, "Devolução Concluída", "Instrumento devolvido com sucesso!");
            atualizarTabela();
        } catch (Exception e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro na Devolução", e.getMessage());
        }
    }

    @FXML
    private void calcularMulta() {
        Emprestimo selecionado = tabelaEmprestimos.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Nenhuma Seleção", "Selecione um empréstimo na tabela primeiro.");
            return;
        }

        try {
            double multa = App.getFachada().calcularMultaEmprestimo(
                    selecionado.getMusico().getCpf(),
                    selecionado.getInstrumento().getCodigoTombamento(),
                    5.0 // Taxa diária padrão de R$ 5,00
            );

            if (multa > 0) {
                exibirAlerta(Alert.AlertType.WARNING, "Multa por Atraso", String.format("Valor da multa acumulada: R$ %.2f", multa));
            } else {
                exibirAlerta(Alert.AlertType.INFORMATION, "Sem Pendências", "Este empréstimo não possui multa por atraso.");
            }
        } catch (Exception e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro ao Calcular Multa", e.getMessage());
        }
    }

    private void atualizarTabela() {
        Emprestimo[] lista = App.getFachada().listarEmprestimos();
        tabelaEmprestimos.setItems(FXCollections.observableArrayList(lista));
    }

    private void exibirAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
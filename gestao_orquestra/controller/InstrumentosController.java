package gestao_orquestra.controller;

import gestao_orquestra.App;
import gestao_orquestra.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class InstrumentosController {

    @FXML private ComboBox<String> comboTipo;
    @FXML private TextField campoTombamento;
    @FXML private TextField campoNome;
    @FXML private ComboBox<EstadoConservacao> comboEstado;
    @FXML private TextField campoEspecifico;
    @FXML private CheckBox checkUsaBaquetas;

    @FXML private TableView<Instrumento> tabelaInstrumentos;
    @FXML private TableColumn<Instrumento, String> colunaTombamento;
    @FXML private TableColumn<Instrumento, String> colunaNome;
    @FXML private TableColumn<Instrumento, String> colunaFamilia;
    @FXML private TableColumn<Instrumento, String> colunaEstado;
    @FXML private TableColumn<Instrumento, String> colunaDetalhe;

    @FXML
    public void initialize() {
        comboTipo.setItems(FXCollections.observableArrayList("Corda", "Percussão", "Sopro"));
        comboEstado.setItems(FXCollections.observableArrayList(EstadoConservacao.values()));

        // Alterna o campo específico conforme o tipo de instrumento selecionado
        comboTipo.valueProperty().addListener((obs, antigo, novoTipo) -> {
            campoEspecifico.clear();
            checkUsaBaquetas.setSelected(false);

            if ("Percussão".equals(novoTipo)) {
                campoEspecifico.setVisible(false);
                campoEspecifico.setManaged(false);
                checkUsaBaquetas.setVisible(true);
                checkUsaBaquetas.setManaged(true);
            } else {
                checkUsaBaquetas.setVisible(false);
                checkUsaBaquetas.setManaged(false);
                campoEspecifico.setVisible(true);
                campoEspecifico.setManaged(true);

                if ("Corda".equals(novoTipo)) {
                    campoEspecifico.setPromptText("Qtd. de Cordas (ex: 4)");
                } else if ("Sopro".equals(novoTipo)) {
                    campoEspecifico.setPromptText("Tipo de Palheta (ex: Simples)");
                }
            }
        });

        // Mapeamento das colunas da tabela
        colunaTombamento.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCodigoTombamento()));
        colunaNome.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNome()));

        colunaFamilia.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getFamilia() != null ? cell.getValue().getFamilia().name() : ""
        ));

        colunaEstado.setCellValueFactory(cell -> new SimpleStringProperty(
                cell.getValue().getEstado() != null ? cell.getValue().getEstado().name() : ""
        ));

        // Exibe o atributo específico de cada subclasse na última coluna
        colunaDetalhe.setCellValueFactory(cell -> {
            Instrumento inst = cell.getValue();
            if (inst instanceof InstrumentoCorda corda) {
                return new SimpleStringProperty(corda.getQuantidadeCordas() + " cordas");
            } else if (inst instanceof InstrumentoPercussao perc) {
                return new SimpleStringProperty(perc.isUsaBaquetas() ? "Usa baquetas" : "Sem baquetas");
            } else if (inst instanceof InstrumentoSopro sopro) {
                return new SimpleStringProperty("Palheta: " + sopro.getTipoPalheta());
            }
            return new SimpleStringProperty("-");
        });

        atualizarTabela();
    }

    @FXML
    private void cadastrarInstrumento() {
        String tipo = comboTipo.getValue();
        String tombamento = campoTombamento.getText().trim();
        String nome = campoNome.getText().trim();
        EstadoConservacao estado = comboEstado.getValue();

        if (tipo == null || tombamento.isEmpty() || nome.isEmpty() || estado == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Campos Incompletos", "Preencha tipo, tombamento, nome e estado.");
            return;
        }

        try {
            Instrumento novoInstrumento;

            switch (tipo) {
                case "Corda" -> {
                    String qtdStr = campoEspecifico.getText().trim();
                    if (qtdStr.isEmpty()) {
                        exibirAlerta(Alert.AlertType.WARNING, "Campo Vazio", "Informe a quantidade de cordas.");
                        return;
                    }
                    int qtdCordas = Integer.parseInt(qtdStr);
                    novoInstrumento = new InstrumentoCorda(tombamento, nome, estado, qtdCordas);
                }
                case "Percussão" -> {
                    boolean usaBaquetas = checkUsaBaquetas.isSelected();
                    novoInstrumento = new InstrumentoPercussao(tombamento, nome, estado, usaBaquetas);
                }
                case "Sopro" -> {
                    String palheta = campoEspecifico.getText().trim();
                    if (palheta.isEmpty()) {
                        exibirAlerta(Alert.AlertType.WARNING, "Campo Vazio", "Informe o tipo de palheta.");
                        return;
                    }
                    novoInstrumento = new InstrumentoSopro(tombamento, nome, estado, palheta);
                }
                default -> {
                    return;
                }
            }

            App.getFachada().cadastrarInstrumento(novoInstrumento);
            exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Instrumento cadastrado no acervo!");

            campoTombamento.clear();
            campoNome.clear();
            campoEspecifico.clear();
            checkUsaBaquetas.setSelected(false);
            comboEstado.getSelectionModel().clearSelection();

            atualizarTabela();

        } catch (NumberFormatException e) {
            exibirAlerta(Alert.AlertType.WARNING, "Valor Inválido", "A quantidade de cordas deve ser um número inteiro.");
        } catch (Exception e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro no Cadastro", e.getMessage());
        }
    }

    @FXML
    private void removerInstrumento() {
        Instrumento selecionado = tabelaInstrumentos.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Nenhuma Seleção", "Selecione um instrumento na tabela primeiro.");
            return;
        }

        try {
            App.getFachada().removerInstrumento(selecionado.getCodigoTombamento());
            exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Instrumento removido do acervo.");
            atualizarTabela();
        } catch (Exception e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro ao Remover", e.getMessage());
        }
    }

    private void atualizarTabela() {
        Instrumento[] lista = App.getFachada().listarInstrumentos();
        tabelaInstrumentos.setItems(FXCollections.observableArrayList(lista));
    }

    private void exibirAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
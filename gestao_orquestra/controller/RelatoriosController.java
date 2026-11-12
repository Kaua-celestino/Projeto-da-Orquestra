package gestao_orquestra.controller;

import gestao_orquestra.App;
import gestao_orquestra.model.Concerto;
import gestao_orquestra.model.Emprestimo;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;

public class RelatoriosController {

    @FXML private ComboBox<Emprestimo> comboEmprestimosAtivos;
    @FXML private ComboBox<Concerto> comboConcertos;

    @FXML
    public void initialize() {
        // Carrega apenas empréstimos ativos (ainda não devolvidos), conforme exigido pela busca na fachada
        List<Emprestimo> ativos = new ArrayList<>();
        for (Emprestimo emp : App.getFachada().listarEmprestimos()) {
            if (emp.getDataDevolucaoFeita() == null) {
                ativos.add(emp);
            }
        }
        comboEmprestimosAtivos.setItems(FXCollections.observableArrayList(ativos));

        comboEmprestimosAtivos.setConverter(new StringConverter<>() {
            @Override
            public String toString(Emprestimo e) {
                if (e == null) return "";
                return e.getMusico().getNome() + " - " + e.getInstrumento().getNome() +
                        " [" + e.getInstrumento().getCodigoTombamento() + "]";
            }
            @Override
            public Emprestimo fromString(String s) { return null; }
        });

        // Carrega todos os concertos cadastrados
        comboConcertos.setItems(FXCollections.observableArrayList(App.getFachada().listarConcertos()));

        comboConcertos.setConverter(new StringConverter<>() {
            @Override
            public String toString(Concerto c) {
                return (c != null) ? c.getNomeEspetaculo() + " (" + c.getLocal() + ")" : "";
            }
            @Override
            public Concerto fromString(String s) { return null; }
        });
    }

    @FXML
    private void gerarPdfAcervo() {
        try {
            App.getFachada().gerarRelatorioAcervoPDF("Relatorio_Acervo.pdf");
            exibirAlerta(Alert.AlertType.INFORMATION, "PDF Gerado", "Arquivo 'Relatorio_Acervo.pdf' gerado com sucesso na pasta do projeto!");
        } catch (Exception e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro ao Gerar PDF", e.getMessage());
        }
    }

    @FXML
    private void gerarPdfLutheria() {
        try {
            App.getFachada().gerarRelatorioLutheriaPDF("Relatorio_Lutheria.pdf");
            exibirAlerta(Alert.AlertType.INFORMATION, "PDF Gerado", "Arquivo 'Relatorio_Lutheria.pdf' gerado com sucesso na pasta do projeto!");
        } catch (Exception e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro ao Gerar PDF", e.getMessage());
        }
    }

    @FXML
    private void gerarPdfEmprestimo() {
        Emprestimo selecionado = comboEmprestimosAtivos.getValue();
        if (selecionado == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Nenhuma Seleção", "Selecione um empréstimo ativo na lista antes de gerar o termo.");
            return;
        }

        try {
            App.getFachada().gerarTermoEmprestimoPDF(
                    selecionado.getMusico().getCpf(),
                    selecionado.getInstrumento().getCodigoTombamento(),
                    "Termo_Emprestimo.pdf"
            );
            exibirAlerta(Alert.AlertType.INFORMATION, "PDF Gerado", "Arquivo 'Termo_Emprestimo.pdf' gerado com sucesso!");
        } catch (Exception e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro ao Gerar PDF", e.getMessage());
        }
    }

    @FXML
    private void gerarPdfConcerto() {
        Concerto selecionado = comboConcertos.getValue();
        if (selecionado == null) {
            exibirAlerta(Alert.AlertType.WARNING, "Nenhuma Seleção", "Selecione um concerto na lista antes de gerar o programa.");
            return;
        }

        try {
            App.getFachada().gerarProgramaConcertoPDF(
                    selecionado.getNomeEspetaculo(),
                    "Programa_Concerto.pdf"
            );
            exibirAlerta(Alert.AlertType.INFORMATION, "PDF Gerado", "Arquivo 'Programa_Concerto.pdf' gerado com sucesso!");
        } catch (Exception e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro ao Gerar PDF", e.getMessage());
        }
    }

    @FXML
    private void gerarTodosPdfs() {
        StringBuilder resumo = new StringBuilder("Arquivos PDF gerados na raiz do projeto:\n\n");

        try {
            // 1. Acervo
            App.getFachada().gerarRelatorioAcervoPDF("Relatorio_Acervo.pdf");
            resumo.append("• Relatorio_Acervo.pdf\n");

            // 2. Lutheria
            App.getFachada().gerarRelatorioLutheriaPDF("Relatorio_Lutheria.pdf");
            resumo.append("• Relatorio_Lutheria.pdf\n");

            // 3. Termo de Empréstimo (usa o selecionado ou o primeiro empréstimo ativo disponível)
            Emprestimo emp = comboEmprestimosAtivos.getValue();
            if (emp == null && !comboEmprestimosAtivos.getItems().isEmpty()) {
                emp = comboEmprestimosAtivos.getItems().get(0);
            }
            if (emp != null) {
                App.getFachada().gerarTermoEmprestimoPDF(
                        emp.getMusico().getCpf(),
                        emp.getInstrumento().getCodigoTombamento(),
                        "Termo_Emprestimo.pdf"
                );
                resumo.append("• Termo_Emprestimo.pdf (").append(emp.getMusico().getNome()).append(")\n");
            } else {
                resumo.append("• Termo_Emprestimo.pdf (ignorado: sem empréstimos ativos)\n");
            }

            // 4. Programa do Concerto (usa o selecionado ou o primeiro concerto disponível)
            Concerto conc = comboConcertos.getValue();
            if (conc == null && !comboConcertos.getItems().isEmpty()) {
                conc = comboConcertos.getItems().get(0);
            }
            if (conc != null) {
                App.getFachada().gerarProgramaConcertoPDF(
                        conc.getNomeEspetaculo(),
                        "Programa_Concerto.pdf"
                );
                resumo.append("• Programa_Concerto.pdf (").append(conc.getNomeEspetaculo()).append(")\n");
            } else {
                resumo.append("• Programa_Concerto.pdf (ignorado: sem concertos cadastrados)\n");
            }

            exibirAlerta(Alert.AlertType.INFORMATION, "Exportação Concluída", resumo.toString());
        } catch (Exception e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro na Exportação em Lote", e.getMessage());
        }
    }

    @FXML
    private void salvarBackupCsv() {
        try {
            App.getFachada().salvarBackupGeral();
            exibirAlerta(Alert.AlertType.INFORMATION, "Backup CSV", "Todos os arquivos CSV foram atualizados com sucesso!");
        } catch (Exception e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro no Backup CSV", e.getMessage());
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
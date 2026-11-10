package gestao_orquestra.negocios;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;

import gestao_orquestra.model.*;

import javax.print.Doc;
import java.io.FileOutputStream;
import java.io.IOException;

public class GeradorPDF {
    private static final Font TITULO_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
    private static final Font SUBTITULO_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
    private static final Font CORPO_FONT = FontFactory.getFont(FontFactory.HELVETICA, 12);

    //Construtor privado impede que façam: new GeradorPDF()

    private GeradorPDF() {}

    // Requisito 32: PROGRAMA DO CONCERTO EM PDF
    public static void gerarProgramaConcerto(Concerto concerto, String caminnhoArquivo) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(caminnhoArquivo));
        document.open();

        document.add(new Paragraph("ORQUESTRA SINFÔNICA - PROGRAMA DO CONCERTO", TITULO_FONT));
        document.add(new Paragraph("--------------------------------------------------------------------------------", CORPO_FONT));
        document.add(new Paragraph("Espetáculo: " + concerto.getNomeEspetaculo(), SUBTITULO_FONT));
        document.add(new Paragraph("Data: " + concerto.getData(), CORPO_FONT));
        document.add(new Paragraph("Local" + concerto.getLocal(), CORPO_FONT));
        document.add(new Paragraph("Maestro: " + concerto.getMaestro(), CORPO_FONT));
        document.add(new Paragraph("\nESCALA DOS MÚSICOS:", SUBTITULO_FONT));

        Musico[] musicos = concerto.getMusicosEscalados();
        for (int i = 0; i<concerto.getIndiceMusicos(); i++) {
            Musico m = musicos[i];
            document.add(new Paragraph((i + 1) + ". " + m.getNome() + " | Naipe: " + m.getNaipePrincipal() + " | Nível : " + m.getNivelTecnico(), CORPO_FONT));
        }

        document.close();
    }

    //REQUISITO 33: TERMO DE EMPRÉSITMO EM PDF

    public static void gerarTermoEmprestimo(Emprestimo emprestimo, String caminhoArquivo) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo));
        document.open();

        document.add(new Paragraph("TERMO DE RESPONSABILIDADE E EMPRÉSTIMO DE INSTRUMENTO", TITULO_FONT));
        document.add(new Paragraph("--------------------------------------------------------------------------------", CORPO_FONT));
        document.add(new Paragraph("Músico Responsável: " + emprestimo.getMusico().getNome() + " (CPF: " + emprestimo.getMusico().getCpf() + ")", CORPO_FONT));
        document.add(new Paragraph("Instrumento: " + emprestimo.getInstrumento().getNome() + " (Tombamento: " + emprestimo.getInstrumento().getCodigoTombamento() + ")", CORPO_FONT));
        document.add(new Paragraph("Data de Retirada: " + emprestimo.getDataRetirada(), CORPO_FONT));
        document.add(new Paragraph("Data Limite de Devolução: " + emprestimo.getDataDevolucaoPrevista(), CORPO_FONT));
        document.add(new Paragraph("\n\n_________________________________________\nAssinatura do Músico", CORPO_FONT));

        document.close();
    }

    //REQUISITO 34: Relatório do ACERVO em PDF
    public static void gerarRelatorioAcervo(Instrumento[] instrumentos, int totalInstrumentos, String caminhoArquivo) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo));
        document.open();

        document.add(new Paragraph("RELATÓRIO GERAL DO ACERVO DE INSTRUMENTOS", TITULO_FONT));
        document.add(new Paragraph("--------------------------------------------------------------------------------", CORPO_FONT));

        for (int i = 0; i < totalInstrumentos; i++) {
            Instrumento inst = instrumentos[i];
            document.add(new Paragraph("[" + inst.getCodigoTombamento() + "] " + inst.getNome() + " | Família: " + inst.getFamilia() + " | Estado: " + inst.getEstado(), CORPO_FONT));
        }

        document.close();
    }

    //REQUISITO 35: Relatório da LUTHERIA em PDF
    public static void gerarRelatorioLutheria(Instrumento [] instrumentosManutencao, String caminhoArquivo) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo));
        document.open();

        document.add(new Paragraph("RELATÓRIO DE INSTRUMENTOS EM LUTHERIA / MANUTENÇÃO", TITULO_FONT));
        document.add(new Paragraph("--------------------------------------------------------------------------------", CORPO_FONT));

        if (instrumentosManutencao.length == 0) {
            document.add(new Paragraph("Nenhum instrumento necessitando de reparo/luthearia."));
        } else {
            for (int i = 0; i < instrumentosManutencao.length; i++) {
                Instrumento inst = instrumentosManutencao[i];
                document.add(new Paragraph("[" + inst.getCodigoTombamento() + "] " + inst.getNome() + " | Estado: " + inst.getEstado(), CORPO_FONT));
            }
        }
    }
}

















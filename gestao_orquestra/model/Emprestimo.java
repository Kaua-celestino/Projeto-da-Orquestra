package gestao_orquestra.model;

import java.time.LocalDate;

public class Emprestimo {
    private Musico musico;
    private Instrumento instrumento;
    private LocalDate dataRetirada;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoFeita;

    public Emprestimo(Musico musico, Instrumento instrumento, LocalDate dataRetirada, LocalDate dataDevolucaoPrevista, LocalDate dataDevolucaoFeita) {
        this.musico = musico;
        this.instrumento = instrumento;
        this.dataRetirada = dataRetirada;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.dataDevolucaoFeita = null;
    }

    public Musico getMusico(){
        return musico;
    }
    public void setMusico(Musico musico){
        this.musico = musico;
    }

    public Instrumento getInstrumento() {
        return instrumento;
    }
    public void setInstrumento(Instrumento instrumento) {
        this.instrumento = instrumento;
    }

    public LocalDate getDataRetirada() {
        return dataRetirada;
    }
    public void setDataRetirada(LocalDate dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }
    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public LocalDate getDataDevolucaoFeita() {
        return dataDevolucaoFeita;
    }
    public void setDataDevolucaoFeita(LocalDate dataDevolucaoFeita) {
        this.dataDevolucaoFeita = dataDevolucaoFeita;
    }
}


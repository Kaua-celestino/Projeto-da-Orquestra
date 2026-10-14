package gestao_orquestra.model;

import gestao_orquestra.exceptions.MusicoNaoEncontradoException;

import java.time.LocalDate;


public class Concerto {
    private String nomeEspetaculo;
    private LocalDate data;
    private String local;
    private Usuario maestro;
    private Musico[] musicosEscalados;
    private int indiceMusicos;

    public Concerto(String nomeEspetaculo, LocalDate data, String local, Usuario maestro) {
        this.nomeEspetaculo = nomeEspetaculo;
        this.data = data;
        this.local = local;
        this.maestro = maestro;
        this.musicosEscalados = new Musico[100];
        this.indiceMusicos = 0;
    }

    public String getNomeEspetaculo() {
        return nomeEspetaculo;
    }
    public void setNomeEspetaculo(String nomeEspetaculo) {
        this.nomeEspetaculo = nomeEspetaculo;
    }

    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }
    public void setLocal(String local) {
        this.local = local;
    }

    public Usuario getMaestro() {
        return maestro;
    }
    public void setMaestro(Usuario maestro) {
        this.maestro = maestro;
    }

    public Musico[] getMusicosEscalados() {
        return musicosEscalados;
    }

    public int getIndiceMusicos() {
        return indiceMusicos;
    }

//métodos de manipulação da lista de músicos
    public void adicionarMusico(Musico musico) {
        if (this.indiceMusicos < this.musicosEscalados.length) {
            this.musicosEscalados[this.indiceMusicos] = musico;
            this.indiceMusicos++;
        }
    }

    public void removerMusico(String cpfMusico) throws MusicoNaoEncontradoException {
        for (int i = 0; i < this.indiceMusicos; i++) {
            if (this.musicosEscalados[i].getCpf().equals(cpfMusico)) {
                //Puxa todos os elementos uma posição para trás e apaga o músico sobrepondo-o
                for (int j = i; j < this.indiceMusicos - 1; j++) {
                    this.musicosEscalados[j] = this.musicosEscalados[j + 1];
                }
                this.musicosEscalados[this.indiceMusicos - 1] = null;
                this.indiceMusicos--;
                return;
            }
            //Se o for rodou inteiro e não deu return, n achou
        } throw new MusicoNaoEncontradoException(cpfMusico);
    }
}



